## 简介
ComponentApp是一个纯组件化项目，主要解决以下问题：
1. 各组件独立开发，可以独立编译，也可以作为Library引入主工程或其他工程。
2. 各组件之间物理隔离，即使引用也不能调用方法
3. 组件之间通过base模块进行调用，可以直接调用base方法
4. 各组件单独编译时，采用同样的包名，共用appId，共用登录Cookie信息
4. 通过EventBus共享状态信息
5. 通过ARouter实现页面间跳转
6. 各组件在ServiceFactory中注册service，从而将服务提供出去

## 整体结构
- app为主模块
- base为公用组件
- login为登录组件
- share为分享组件

业务逻辑：
- app依赖于share和login，需要login的登录信息
- share依赖于login，需要登录后获取cookie信息才能分享成功，否则跳登录页面
- login不依赖于其他模块
- base不依赖于其他模块

## 几个核心问题
#### 如何在不使用方法调用的前提下，实现组件间交互？
方案：将每个组件提供的方法抽象成一个service，注册到base模块的ServiceFactory中。其他组件需要使用的时候，可以从base模块中get相应的service使用。

下面是ServiceFactory代码：
```java
package com.ys.base;

import androidx.annotation.StringDef;

import com.ys.base.empty_service.EmptyAccountService;
import com.ys.base.empty_service.EmptyShareService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shanyin on 2020/5/20
 */
public class ServiceFactory {

    @StringDef({Service.LOGIN_SERVICE, Service.SHARE_SERVICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Service{
        String LOGIN_SERVICE = "login_service";
        String SHARE_SERVICE = "share_service";
    }

    Map<String, Object> servicesMap;

    private ServiceFactory() {
        servicesMap = new HashMap<>();

        registerService(Service.LOGIN_SERVICE, new EmptyAccountService());
        registerService(Service.SHARE_SERVICE, new EmptyShareService());
    }

    public static ServiceFactory getServiceFactory(){
        return Inner.sInstance;
    }

    private static class Inner{
        private static ServiceFactory sInstance = new ServiceFactory();
    }

    public <T> void registerService(String name, T services){
        servicesMap.put(name, services);
    }

    public <T> T getService(String name){
        return (T) servicesMap.get(name);
    }
}
```
在初始化时，使用默认的EmptyAccountService来填充，防止出现service为空的情况。

### 组件如何单独编译？
方案：在gradle.properties中添加isRunAlone参数
```gradle
# Run as a independent application
isRunAlone=true
```
在build.gradle中引入判断，如果isRunAlone为true，则编译成一个app，如果isRunAlone为false，编译成一个Library。
```gradle
if (isRunAlone.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
由于编译成app和Library的Manifest不一样，需要配置两份Manifest，在build.gradle中指定使用的manifest文件
```gradle
sourceSets {
    main {
        if (isRunAlone.toBoolean()) {
            manifest.srcFile 'src/main/runalone/AndroidManifest.xml'
        } else {
            manifest.srcFile 'src/main/AndroidManifest.xml'
        }
    }
}
```
下面是两份Manifest文件：
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.ys.componentapp">

    <application>
        <activity android:name="com.ys.login.LoginActivity"></activity>
    </application>

</manifest>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.ys.share">

    <application
        android:name="com.ys.share.ShareApp"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name="com.ys.share.ShareActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

### 注册服务的方法放在哪里，才能保证一定执行？
方案：如果是以app运行，可以放在application中。作为Library运行，需要此时的app来主动调用到相应的初始化代码。

声明一个BaseApp，所有的模块都继承自BaseApp，并且在其中声明自己依赖的模块，以及自己提供的服务。在BaseApp中，反射调用依赖模块的Application方法来注册各依赖模块的服务。

BaseApp代码如下：
```java
package com.ys.base.app;

import android.app.Application;

/**
 * Created by shanyin on 2020/5/21
 */
public abstract class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleList();
        initModulesApp();
    }

    // 默认不需要初始化其他的module，如果模块需要初始化某些module，需要覆盖此成员变量
    public String[] moduleApps = {};

    public void initModulesApp(){
        // 反射调用各App的initModuleApp方法
        for (String moduleApp : moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp) clazz.newInstance();
                baseApp.initModuleServices(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化moduleApps
     * 如果不覆盖此方法，默认为不进行初始化其他module
     */
    public abstract void initModuleList();

    /**
     * 各Module注册自己的服务
     * @param application
     */
    public abstract void initModuleServices(Application application);
}
```

#### 如何实现组件间物理隔离，防止意外调用到其他组件方法？
方案：在build.gradle中使用runtimeOnly方式依赖，只在运行时可用，编译时完全隔离。
```gradle
runtimeOnly project(":login")
runtimeOnly project(":share")
```

## 待完善部分
1. 使用EventBus作组件间数据共享，会导致很多Event下沉到base模块，使得base模块难以维护
2. 每注册一个组件都需要在ServiceFactory中声明，不够简洁
