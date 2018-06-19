package com.demo.loginmodule;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * author : zhudi
 * e-mail : zhudi@geely.com
 * time   : 2018/06/19
 * desc   :
 */
@Route(path = "/loginmodule/activity/MainLoginActivity")
public class MainLoginActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }
}
