package cn.zhudi.mvpdemo;

import android.app.Application;

import com.demo.common.base.BaseApplication;
import com.demo.common.log.LogBackUtil;
import com.demo.common.utils.AppHelper;
import com.facebook.stetho.Stetho;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/12/8 18:20
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initLog();
    }

    private void initLog() {
        boolean doEncryption = !AppHelper.isApkDebugable(this);
        boolean showLogCat = AppHelper.isApkDebugable(this);
        LogBackUtil.configureLogbackDirectly(this, doEncryption, showLogCat);
    }
}
