package com.ys.login.manager;

import com.ys.base.model.UserInfo;
import com.ys.base.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by shanyin on 2020/5/20
 */
public class LoginManager {

    private UserInfo loginUser;

    private LoginManager(){

    }

    private static class Inner{
        private static LoginManager sManager = new LoginManager();
    }

    public static LoginManager getManager(){
        return Inner.sManager;
    }

    public void login(UserInfo user){
        loginUser = user;
        // TODO: 2020/5/20 发送登录成功的EventBus
        EventBus.getDefault().post(new LoginEvent());
    }

    public void logout(){
        loginUser = null;
        EventBus.getDefault().post(new LoginEvent());
    }

    public boolean isLogin(){
        return loginUser != null;
    }

    public UserInfo getLoginUser(){
        return loginUser;
    }
}
