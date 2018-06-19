package com.demo.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class CommonApplication extends Application {
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
