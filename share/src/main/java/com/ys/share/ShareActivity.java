package com.ys.share;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ys.base.ServiceFactory;
import com.ys.base.service.IAccountService;
import com.ys.base.ui.BaseActivity;

/**
 * Created by shanyin on 2020/5/20
 */
public class ShareActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity_share);

        findViewById(R.id.share_btn).setOnClickListener(v -> {
            IAccountService accountService = ServiceFactory.getServiceFactory().getService(ServiceFactory.LOGIN_SERVICE);
            if(accountService.isLogin()){
                // TODO: 2020/5/21 如何获取到登录的账号Cookie信息
                Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
            }else{
                // TODO: 2020/5/21 ARouter跳转登录页面
                Toast.makeText(this, "分享失败，请先登录", Toast.LENGTH_LONG).show();
            }
        });
    }

}
