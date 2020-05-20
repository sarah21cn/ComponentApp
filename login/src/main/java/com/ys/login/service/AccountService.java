package com.ys.login.service;

import com.ys.base.model.UserInfo;
import com.ys.base.service.IAccountService;
import com.ys.login.manager.LoginManager;

/**
 * Created by shanyin on 2020/5/20
 */
public class AccountService implements IAccountService {

    @Override
    public boolean isLogin() {
        return LoginManager.getManager().isLogin();
    }

    @Override
    public UserInfo getUserInfo() {
        return LoginManager.getManager().getLoginUser();
    }
}
