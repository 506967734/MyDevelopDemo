package cn.zhudi.mvpdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentTab extends BaseFragment {
    @BindView(R.id.tv)
    TextView tv;
    private String showString;

    public static FragmentTab newInstance(String s) {
        FragmentTab fragment = new FragmentTab();
        Bundle bundle = new Bundle();
        bundle.putString("string", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int getLayoutView() {
        return R.layout.fragment_tab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            showString = bundle.getString("string");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        tv.setText(showString);
    }
}
