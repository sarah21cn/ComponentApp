package com.ys.share;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ys.base.ServiceFactory;
import com.ys.base.service.IAccountService;
import com.ys.base.ui.BaseActivity;

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
            IAccountService accountService = ServiceFactory.getServiceFactory().getService(ServiceFactory.LOGIN_SERVICE);
            if(accountService.isLogin()){
                // TODO: 2020/5/21 如何获取到登录的账号Cookie信息，可以通过accountservice分享出来
                // TODO: 2020/5/28 包名不一样，登录信息怎么共用？ 
                Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "分享失败，请先登录", Toast.LENGTH_LONG).show();
                ARouter.getInstance().build("/login/login").navigation();
            }
        });
    }

}
