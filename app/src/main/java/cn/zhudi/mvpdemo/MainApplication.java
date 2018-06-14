package cn.zhudi.mvpdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.demo.common.base.BaseApplication;
import com.demo.common.log.LogBackUtil;
import com.demo.common.utils.AppHelper;
import com.demo.common.utils.DynamicTimeFormat;
import com.facebook.stetho.Stetho;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
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
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.public_bg);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }

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
