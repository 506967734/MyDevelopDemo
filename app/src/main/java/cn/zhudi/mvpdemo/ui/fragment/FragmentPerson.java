package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;
import cn.zhudi.mvpdemo.base.BaseListMVPFragment;
import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.persenter.FragmentPersonPresenter;
import cn.zhudi.mvpdemo.view.FragmentPersonView;

/**
 * 类名称：个人中心
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentPerson extends BaseListMVPFragment<FragmentPersonView,FragmentPersonPresenter> implements FragmentPersonView {

    public static FragmentPerson newInstance(String s) {
        FragmentPerson fragment = new FragmentPerson();
        Bundle bundle = new Bundle();
        bundle.putString("string", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int getLayoutView() {
        return R.layout.fragment_person;
    }

    @Override
    protected FragmentPersonPresenter initPresenter() {
        return new FragmentPersonPresenter(getActivity());
    }

    @Override
    protected String requestData() {
        return null;
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected void loadMore() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }


    @Override
    public void showMoney(String str) {

    }
}
