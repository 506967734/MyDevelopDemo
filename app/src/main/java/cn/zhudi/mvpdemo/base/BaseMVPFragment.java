package cn.zhudi.mvpdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by zhudi on 2017/11/29.
 */

public abstract class BaseMVPFragment<V, T extends BasePresenter<V>> extends BaseFragment {
    /**
     * 控制器
     */
    public T presenter;

    /**
     * 初始化控制器
     *
     * @return
     */
    protected abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();//创建Presenter
        presenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }
}
