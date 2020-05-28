package com.ys.share;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ys.base.ServiceFactory;
import com.ys.base.app.AppConfig;
import com.ys.base.app.BaseApp;
import com.ys.share.service.ShareService;

/**
 * Created by shanyin on 2020/5/21
 */
public class ShareApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        // 只有在作为独立app运行时才会调用，所以可以在这里进行ARouter的初始化
        if(isDebug()){
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    @Override
    public void initModuleList() {
        // 单独编译依赖login模块，可以实现跳转login模块功能
        moduleApps = new String[]{AppConfig.LOGIN_APP};
    }

    @Override
    public void initModuleServices(Application application) {
        ServiceFactory.getServiceFactory().registerService(ServiceFactory.SHARE_SERVICE, new ShareService());
    }

    // TODO: 2020/5/29 获取gradle.properties中的isRunAlone状态
    public boolean isRunAlone(){
        return false;
    }

    public boolean isDebug(){
        return BuildConfig.DEBUG;
    }
}
