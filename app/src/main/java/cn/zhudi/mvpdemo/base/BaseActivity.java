package cn.zhudi.mvpdemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.demo.common.R;
import com.demo.common.base.BaseApplication;
import com.demo.titlebar.RootLinearLayout;
import com.demo.titlebar.TitleBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity的基类,所有的Activity都将继承这个基类
 * Created by cwf on 18/9/1.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder binder;
    private LinearLayout mThemeFrameLayout;
    private TitleBar titleLayout;
    private Activity mActivity;
    private final static int CUSTOM_LAYOUT_WEIGHT = 9;
    private static final Logger logger = LoggerFactory.getLogger("BaseActivity");
    public RootLinearLayout rootLinearLayout;

    protected abstract int getLayoutView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        mActivity = this;
        BaseApplication.currentActivity = this;
        setContentView();
    }

    public void setContentView() {
        View customLayout = LayoutInflater.from(this).inflate(getLayoutView(), null);
        setupCustomLayout(customLayout, null);

    }


    private void setupCustomLayout(View customLayout, ViewGroup.LayoutParams params) {
        mThemeFrameLayout = new LinearLayout(mActivity);
        mThemeFrameLayout.setOrientation(LinearLayout.VERTICAL);

        if (hasTitleBar()) {
            titleLayout = new TitleBar(mActivity);
            LinearLayout.LayoutParams titleLlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.titlebar_height));
            mThemeFrameLayout.addView(titleLayout, titleLlp);
            if (getRightImg() == 0) {
            } else titleLayout.setRightImg(getRightImg());
            titleLayout.setOnTitleBarRightClickedListener(tbRightClick);
            if (getLeftImg()) {
                titleLayout.setLeftImg(R.drawable.actionbar_back_test1);
            }
            titleLayout.setOnTitleBarLeftClickedListener(tbLeftClick);
            initThemeFrame();
        }
        if (params == null) {
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, CUSTOM_LAYOUT_WEIGHT);
            mThemeFrameLayout.addView(customLayout, llp);
        } else {
            mThemeFrameLayout.addView(customLayout, params);
        }
        setContentView(mThemeFrameLayout);
        binder = ButterKnife.bind(this, mThemeFrameLayout);
    }


    TitleBar.TitleBarLeftClickedListener tbLeftClick = new TitleBar.TitleBarLeftClickedListener() {
        @Override
        public void onLeftClicked(View v) {
            backClick(v);
        }
    };

    TitleBar.TitleBarRightClickedListener tbRightClick = new TitleBar.TitleBarRightClickedListener() {
        @Override
        public void onRightClicked(View v) {
            RightClick(v);
        }
    };

    public void RightClick(View v) {

    }

    public TitleBar getTitleBar() {
        return titleLayout;
    }


    protected void initThemeFrame() {
        if (TextUtils.isEmpty(getTitleText())) {
            titleLayout.setTitle("服务站");
        } else {
            titleLayout.setTitle(getTitleText());
        }
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200);
        titleLayout.tileView.startAnimation(anim);
    }

    public abstract String getTitleText();

    public abstract int getRightImg();

    public void backClick(View v) {
        finish();
        //ThemeControl.overrideTransitionRightLeft(mActivity);
    }

    public void setTitleBarVisible() {
        titleLayout.setVisibility(View.VISIBLE);
    }

    public void setTitleBarHide() {
        titleLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        recover(savedInstanceState);
    }

    private void recover(Bundle bundle) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.info("onResume={}", "onResume");
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        logger.info("onPause={}", "onPause");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.info("onStop={}", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binder != null) {
            binder.unbind();
        }
        logger.info("onDestroy={}", "onDestroy");
    }

    /**
     * 是否显示返回按钮
     *
     * @return true:显示、false: 不显示
     */
    protected boolean getLeftImg() {
        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public boolean hasTitleBar() {
        return true;
    }

}
