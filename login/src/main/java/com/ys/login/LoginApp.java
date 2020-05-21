package com.ys.login;

import android.app.Application;

import com.ys.base.ServiceFactory;
import com.ys.base.app.BaseApp;
import com.ys.login.service.AccountService;

/**
 * Created by shanyin on 2020/5/21
 */
public class LoginApp extends BaseApp {

    @Override
    public void initModuleList() {
        // do nothing
    }

    @Override
    public void initModuleApp(Application application) {
        ServiceFactory.getServiceFactory().registerService(ServiceFactory.LOGIN_SERVICE, new AccountService());
    }

    @Override
    public void initModuleData(Application application) {

    }
}
