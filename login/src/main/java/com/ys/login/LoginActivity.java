package com.ys.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ys.base.model.UserInfo;
import com.ys.base.ui.BaseActivity;
import com.ys.base.utils.EventBusRegisterUtil;
import com.ys.login.event.LoginEvent;
import com.ys.login.manager.LoginManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by shanyin on 2020/5/20
 */
public class LoginActivity extends BaseActivity {

    private TextView userNameTv;
    private Button loginBtn, logoutBtn;
    private LoginManager loginManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);

        userNameTv = findViewById(R.id.login_user_name);
        loginBtn = findViewById(R.id.login_btn);
        logoutBtn = findViewById(R.id.logout_btn);

        loginManager = LoginManager.getManager();

        loginBtn.setOnClickListener(v -> loginManager.login(new UserInfo("1", "SarahYin")));
        logoutBtn.setOnClickListener(v -> loginManager.logout());

        updateViews();

        EventBusRegisterUtil.register(this);
    }

    // TODO: 2020/5/20 放入 Presenter (MVP) 或 ViewModel (MVVM) 中处理
    private void updateViews(){
        if(loginManager.isLogin()){
            userNameTv.setVisibility(View.VISIBLE);
            final String userName = loginManager.getLoginUser() != null ? loginManager.getLoginUser().getAccountName() : "";
            userNameTv.setText(getResources().getString(R.string.login_activity_login_user_name, userName));
            loginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        }else{
            userNameTv.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event){
        updateViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusRegisterUtil.unregister(this);
    }
}
