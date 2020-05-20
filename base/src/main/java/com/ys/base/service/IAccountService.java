package com.ys.base.service;

import com.ys.base.model.UserInfo;

/**
 * Created by shanyin on 2020/5/20
 */
public interface IAccountService {

    boolean isLogin();

    UserInfo getUserInfo();
}
