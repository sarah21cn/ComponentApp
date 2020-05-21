package com.ys.base.app;

import android.app.Application;

/**
 * Created by shanyin on 2020/5/21
 */
public abstract class BaseApp extends Application {

    // TODO: 2020/5/21 如何让每个App都必须初始化
    // 默认不需要初始化其他的module，如果模块需要初始化某些module，需要覆盖此成员变量
    public String[] moduleApps = {};

    public void initModulesApp(){
        // 反射调用各App的initModuleApp方法
        for (String moduleApp : moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp) clazz.newInstance();
                baseApp.initModuleApp(this);
                baseApp.initModuleData(this);
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
     */
    public abstract void initModuleList();

    /**
     * 各Module进行相应的初始化
     * @param application
     */
    public abstract void initModuleApp(Application application);

    public abstract void initModuleData(Application application);
}
