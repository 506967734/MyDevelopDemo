package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.demo.webview.X5WebView;

import butterknife.BindView;
import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;

/**
 * 类名称：商城
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentShop extends BaseFragment {
//    @BindView(R.id.webView)
//    ViewGroup viewParent;
//    /**
//     * 作为一个浏览器的示例展示出来，采用android+web的模式
//     */
//    private X5WebView mWebView;

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

//    private void initView() {
//
//        mWebView = new X5WebView(getActivity(), null);
//
//        viewParent.addView(mWebView, new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT));
//
//        mWebView.loadUrl("http://app.html5.qq.com/navi/index");
//    }


}
