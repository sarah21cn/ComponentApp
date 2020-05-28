package com.ys.base;

import androidx.annotation.StringDef;

import com.ys.base.empty_service.EmptyAccountService;
import com.ys.base.empty_service.EmptyShareService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shanyin on 2020/5/20
 */
public class ServiceFactory {

    // 使用StringDef代替Java枚举
    @StringDef({Service.LOGIN_SERVICE, Service.SHARE_SERVICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Service{
        String LOGIN_SERVICE = "login_service";
        String SHARE_SERVICE = "share_service";
    }

    Map<String, Object> servicesMap;

    private ServiceFactory() {
        servicesMap = new HashMap<>();

        registerService(Service.LOGIN_SERVICE, new EmptyAccountService());
        registerService(Service.SHARE_SERVICE, new EmptyShareService());
    }

    public static ServiceFactory getServiceFactory(){
        return Inner.sInstance;
    }

    private static class Inner{
        private static ServiceFactory sInstance = new ServiceFactory();
    }

    public <T> void registerService(String name, T services){
        servicesMap.put(name, services);
    }

    public <T> T getService(String name){
        return (T) servicesMap.get(name);
    }
}
