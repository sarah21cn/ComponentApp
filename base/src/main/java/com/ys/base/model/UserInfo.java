package com.ys.base.model;

/**
 * Created by shanyin on 2020/5/20
 */
public class UserInfo {
    private String accountId;
    private String accountName;

    public UserInfo(String accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
    }

    public String getAccountId(){
        return accountId;
    }

    public String getAccountName(){
        return accountName;
    }
}
