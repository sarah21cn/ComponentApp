package com.ys.base.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by shanyin on 2020/5/20
 */
public class EventBusRegisterUtil {

    public static void register(Object subscriber){
        // 防止eventbus注册失败crash
        if(!EventBus.getDefault().isRegistered(subscriber)){
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber){
        if(EventBus.getDefault().isRegistered(subscriber)){
            EventBus.getDefault().unregister(subscriber);
        }
    }
}
