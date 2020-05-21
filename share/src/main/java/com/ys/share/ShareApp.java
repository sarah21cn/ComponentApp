package com.ys.share;

import android.app.Application;

import com.ys.base.ServiceFactory;
import com.ys.base.app.AppConfig;
import com.ys.base.app.BaseApp;
import com.ys.share.service.ShareService;

/**
 * Created by shanyin on 2020/5/21
 */
public class ShareApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleList();
    }

    @Override
    public void initModuleList() {
        moduleApps = new String[]{AppConfig.LOGIN_APP};
        initModulesApp();
    }

    @Override
    public void initModuleApp(Application application) {
        ServiceFactory.getServiceFactory().registerService(ServiceFactory.SHARE_SERVICE, new ShareService());
    }

    @Override
    public void initModuleData(Application application) {

    }
}
