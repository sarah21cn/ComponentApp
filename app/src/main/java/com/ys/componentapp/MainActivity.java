package com.ys.componentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ys.base.ServiceFactory;
import com.ys.base.event.LoginEvent;
import com.ys.base.service.IAccountService;
import com.ys.base.utils.EventBusRegisterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView loginStatus;
    private Button jumpToLogin;
    private Button jumpToShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginStatus = findViewById(R.id.login_status_tv);
        jumpToLogin = findViewById(R.id.jump_to_login_btn);
        jumpToShare = findViewById(R.id.jump_to_share_btn);

        // 使用ARouter实现Activity之间的跳转
        jumpToLogin.setOnClickListener(v -> ARouter.getInstance().build("/login/login").navigation());
        jumpToShare.setOnClickListener(v -> ARouter.getInstance().build("/share/share").navigation());

        EventBusRegisterUtil.register(this);

        updateLoginStatus();
    }

    private void updateLoginStatus(){
        IAccountService accountService = ServiceFactory.getServiceFactory().getService(ServiceFactory.Service.LOGIN_SERVICE);
        // TODO: 2020/5/28 目前使用EventBus进行模块间数据同步，Event必须注册到base模块，会造成base模块臃肿
        // 参考微信技术团队架构重构文章：https://mp.weixin.qq.com/s/6Q818XA5FaHd7jJMFBG60w
        // 由各模块团队输出.api文件，完成接口输出和调用。只输出接口，隐藏具体实现。
        if(accountService.isLogin()){
            loginStatus.setText("登录账号：" + accountService.getUserInfo().getAccountName());
        }else{
            loginStatus.setText("未登录");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusRegisterUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event){
        updateLoginStatus();
    }
}
