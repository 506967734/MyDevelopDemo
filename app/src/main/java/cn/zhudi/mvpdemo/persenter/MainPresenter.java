package cn.zhudi.mvpdemo.persenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.impl.MainListener;
import cn.zhudi.mvpdemo.model.IMainModelBiz;
import cn.zhudi.mvpdemo.model.impl.MainBizImpl;
import cn.zhudi.mvpdemo.view.MainView;

public class MainPresenter extends BasePresenter<MainView> {
    private IMainModelBiz modelBiz;

    public MainPresenter(Context context) {
        modelBiz = new MainBizImpl();
    }


    public void initFragment(FragmentActivity fragment, int id, List<View> tabView) {
        modelBiz.initFragment(fragment,id,tabView,new MainListener() {
            @Override
            public void showTitleListener(int resId) {
                getView().showTitleName(resId);
            }
        });
    }

}
