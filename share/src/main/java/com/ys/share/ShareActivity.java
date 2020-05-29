package com.ys.share;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ys.base.ServiceFactory;
import com.ys.base.service.IAccountService;
import com.ys.base.ui.BaseActivity;
import com.ys.componentapp.R;

/**
 * Created by shanyin on 2020/5/20
 */
@Route(path = "/share/share")
public class ShareActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity_share);

        findViewById(R.id.share_btn).setOnClickListener(v -> {
            IAccountService accountService = ServiceFactory.getServiceFactory().getService(ServiceFactory.Service.LOGIN_SERVICE);
            if(accountService.isLogin()){
                // 通过AccountService获取Cookie信息
                // 所有module使用同样的appid和包名，保证登录信息可以通用
                if(!TextUtils.isEmpty(accountService.getCookie())){
                    Toast.makeText(this, "获取cookie成功，分享成功", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "分享失败，请先登录", Toast.LENGTH_LONG).show();
                ARouter.getInstance().build("/login/login").navigation();
            }
        });
    }

}
