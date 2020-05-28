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
        // 不需要依赖任何模块，do nothing
    }

    @Override
    public void initModuleServices(Application application) {
        ServiceFactory.getServiceFactory().registerService(ServiceFactory.Service.LOGIN_SERVICE, new AccountService());
    }
}
