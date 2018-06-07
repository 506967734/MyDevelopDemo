package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;

/**
 * 类名称：商城
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentShop extends BaseFragment {

    public static FragmentShop newInstance(String s) {
        FragmentShop fragment = new FragmentShop();
        Bundle bundle = new Bundle();
        bundle.putString("string", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int getLayoutView() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }


}
