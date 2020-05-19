package com.ys.base.service;

import com.ys.base.model.UserInfo;

public interface IAccountService {

    boolean isLogin();

    UserInfo getUserInfo();
}
