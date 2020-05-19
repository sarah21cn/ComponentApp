package com.ys.componentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ys.base.ServiceFactory;
import com.ys.base.service.IAccountService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IAccountService accountService = ServiceFactory.getServiceFactory().getService(ServiceFactory.LOGIN_SERVICE);
        System.out.println(accountService.isLogin());
    }
}
