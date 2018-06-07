package cn.zhudi.mvpdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;


public class WelcomeActivity extends Activity {
    private static final int sleepTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = View.inflate(this, R.layout.activity_welcome, null);
        setContentView(view);
        initView(view);
    }

    /********************************************************/
    /*               欢迎界面逻辑                             */
    /* 1.判断是否有网络，没有网络加载默认的，有网络请求数据         */
    /* 2.有网络根据服务器返回图片显示                           */
    /*******************************************************/
    private void initView(final View view) {
//        if (SystemInfoUtil.checkNetWorkStatus(getApplicationContext())) {
//            //网络请求
//            getNetImage(view);
//        } else {
            startMain(sleepTime);
//        }
    }



    /**
     * 启动主Activity
     */
    private void startMain(long time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },time);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }

}



