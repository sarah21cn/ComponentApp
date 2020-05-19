package com.ys.base;

import com.ys.base.empty_service.EmptyAccountService;

import java.util.HashMap;
import java.util.Map;


public class ServiceFactory {

    public static final String LOGIN_SERVICE = "login_service";

    Map<String, Object> servicesMap;

    private ServiceFactory() {
        servicesMap = new HashMap<>();

        registerService(LOGIN_SERVICE, new EmptyAccountService());
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
