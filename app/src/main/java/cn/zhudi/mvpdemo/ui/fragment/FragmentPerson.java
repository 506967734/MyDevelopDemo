package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;

/**
 * 类名称：个人中心
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentPerson extends BaseFragment {

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
