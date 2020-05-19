package com.ys.base.empty_service;

import com.ys.base.model.UserInfo;
import com.ys.base.service.IAccountService;

public class EmptyAccountService implements IAccountService {

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public UserInfo getUserInfo() {
        return null;
    }
}
