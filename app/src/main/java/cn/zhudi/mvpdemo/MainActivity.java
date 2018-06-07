package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import butterknife.BindViews;
import cn.zhudi.mvpdemo.base.BaseMVPActivity;
import cn.zhudi.mvpdemo.persenter.MainPresenter;
import cn.zhudi.mvpdemo.view.MainView;

public class MainActivity extends BaseMVPActivity<MainView, MainPresenter> implements MainView {
    private static final Logger logger = LoggerFactory.getLogger("MainActivity");
    @BindViews({R.id.ivHome, R.id.ivCategory, R.id.ivPersonal, R.id.ivCar})
    List<View> tabView;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public String getTitleText() {
        return null;
    }

    @Override
    public int getRightImg() {
        return 0;
    }

    @Override
    public void showTitleName(int resId) {
        //titleView(R.id.tvTitle, resId);
    }

    private void initView() {
        presenter.initFragment(this, R.id.content, tabView);
    }
}
