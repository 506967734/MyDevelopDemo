package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.common.utils.ToastCompat;
import com.mic.etoast2.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseMVPFragment;
import cn.zhudi.mvpdemo.persenter.FragmentCarPresenter;
import cn.zhudi.mvpdemo.view.FragmentCarView;

/**
 * 类名称：购物车
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentCar extends BaseMVPFragment<FragmentCarView, FragmentCarPresenter> implements FragmentCarView {

    public static FragmentCar newInstance(String s) {
        FragmentCar fragment = new FragmentCar();
        Bundle bundle = new Bundle();
        bundle.putString("string", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int getLayoutView() {
        return R.layout.fragment_car;
    }

    @Override
    protected FragmentCarPresenter initPresenter() {
        return new FragmentCarPresenter(getActivity());
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
        this.showMoney("123");
        //presenter.showMoney("123");
    }

    @BindView(R.id.tvMoney)
    TextView tvMoney;

    @Override
    public void showMoney(String str) {
        tvMoney.setText(str);
    }

    @OnClick(R.id.tvBuy)
    public void buyClick() {
        ToastCompat.show(getContext(), "点击", android.widget.Toast.LENGTH_LONG);
        presenter.getWeatherMessage();
    }
}
