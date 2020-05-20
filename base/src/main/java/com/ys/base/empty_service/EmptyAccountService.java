package com.ys.base.empty_service;

import com.ys.base.model.UserInfo;
import com.ys.base.service.IAccountService;

/**
 * Created by shanyin on 2020/5/20
 */
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
