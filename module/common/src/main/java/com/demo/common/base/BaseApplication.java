package com.demo.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.demo.common.utils.AppHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 程序初始化
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    protected static Logger logger = null;
    public static Activity currentActivity = null;
    protected HashMap<String, Activity> mActivitiesStack = null;
    public static BaseApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        logger.info("onCreate={}, processName={}", this, AppHelper.getProcess(this));
        mActivitiesStack = new HashMap<String, Activity>();
    }

    @Override
    protected void attachBaseContext(Context base) {
        instance = this;
        long start = System.currentTimeMillis();
        MultiDex.install(base);
        long end = System.currentTimeMillis();
        logger = LoggerFactory.getLogger("BaseApplication");
        super.attachBaseContext(base);
        logger.info("attachBaseContext finished, spend={}ms", end - start);
    }

    @Override
    public void onLowMemory() {
        logger.info("onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        logger.info("onTerminate");
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        logger.info("onTrimMemory, level={}", level);
        super.onTrimMemory(level);
    }

    private ExecutorService executorService;

    public synchronized ExecutorService getDefaultExecutor() {
        if(executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
        return executorService;
    }

    @Deprecated
    public HashMap<String, Activity> getActivityStack() {
        return mActivitiesStack;
    }

    public String getExternalFilesDirROOT() {
        return "";
    }

    public void onTokenInvalid() {

    }
}
