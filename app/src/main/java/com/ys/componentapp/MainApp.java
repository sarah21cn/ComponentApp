package com.ys.componentapp;

import android.app.Application;

import com.ys.base.app.AppConfig;
import com.ys.base.app.BaseApp;

/**
 * Created by shanyin on 2020/5/21
 */
public class MainApp extends BaseApp {

    @Override
    public void initModuleList() {
        moduleApps = new String[]{AppConfig.LOGIN_APP};
    }

    @Override
    public void initModuleApp(Application application) {

    }

    @Override
    public void initModuleData(Application application) {

    }
}
