package cn.zhudi.mvpdemo.model;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

import cn.zhudi.mvpdemo.base.IBaseModelBiz;
import cn.zhudi.mvpdemo.impl.MainListener;

/**
 * Created by apple on 2017/11/29.
 */

public interface IMainModelBiz extends IBaseModelBiz {
    void initFragment(FragmentActivity fragment, int id, List<View> tabView, MainListener listener);
}
