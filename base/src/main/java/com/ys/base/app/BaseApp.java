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
