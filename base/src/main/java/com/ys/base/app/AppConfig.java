package com.ys.base.app;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by shanyin on 2020/5/21
 */
public class AppConfig {


    @StringDef({
            APP.LOGIN_APP,
            APP.SHARE_APP
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface APP{
        // 所有引入的Module
        String LOGIN_APP = "com.ys.login.LoginApp";
        String SHARE_APP = "com.ys.share.ShareApp";
    }

}
