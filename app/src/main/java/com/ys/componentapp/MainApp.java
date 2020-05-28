package com.ys.componentapp;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ys.base.BuildConfig;
import com.ys.base.app.AppConfig;
import com.ys.base.app.BaseApp;

/**
 * Created by shanyin on 2020/5/21
 */
public class MainApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        if(isDebug()){
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    private boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void initModuleList() {
        moduleApps = new String[]{AppConfig.APP.LOGIN_APP, AppConfig.APP.SHARE_APP};
    }

    @Override
    public void initModuleServices(Application application) {
        // 不对外提供服务，do nothing
    }
}
