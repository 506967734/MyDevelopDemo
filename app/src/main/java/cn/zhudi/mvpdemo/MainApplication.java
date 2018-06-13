package cn.zhudi.mvpdemo;

import android.util.Log;

import com.demo.common.base.BaseApplication;
import com.demo.common.log.LogBackUtil;
import com.demo.common.utils.AppHelper;
import com.facebook.stetho.Stetho;
import com.tencent.smtt.sdk.QbSdk;

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
        initWebViewX5Environment();

    }

    private void initLog() {
        boolean doEncryption = !AppHelper.isApkDebugable(this);
        boolean showLogCat = AppHelper.isApkDebugable(this);
        LogBackUtil.configureLogbackDirectly(this, doEncryption, showLogCat);
    }

    private void initWebViewX5Environment() {
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean arg0) {
                //TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                logger.info(" onViewInitFinished is {}", arg0);
            }
        });
    }

}
