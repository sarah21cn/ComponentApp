package com.ys.base;

import com.ys.base.empty_service.EmptyAccountService;
import com.ys.base.empty_service.EmptyShareService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shanyin on 2020/5/20
 */
public class ServiceFactory {

    // TODO: 2020/5/20 使用注解 代替enum
    public static final String LOGIN_SERVICE = "login_service";
    public static final String SHARE_SERVICE = "share_service";

    Map<String, Object> servicesMap;

    private ServiceFactory() {
        servicesMap = new HashMap<>();

        registerService(LOGIN_SERVICE, new EmptyAccountService());
        registerService(SHARE_SERVICE, new EmptyShareService());
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
