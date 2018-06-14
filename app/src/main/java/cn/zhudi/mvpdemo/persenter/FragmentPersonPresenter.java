package cn.zhudi.mvpdemo.persenter;

import android.content.Context;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zd.retrofitlibrary.exception.ApiException;
import com.zd.retrofitlibrary.listener.HttpOnNextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.model.IWeatherModelBiz;
import cn.zhudi.mvpdemo.model.impl.WeatherBizImpl;
import cn.zhudi.mvpdemo.view.FragmentCarView;
import cn.zhudi.mvpdemo.view.FragmentPersonView;

/**
 * Created by zhudi on 2018/06/14.
 */

public class FragmentPersonPresenter extends BasePresenter<FragmentPersonView> {
    private static final Logger logger = LoggerFactory.getLogger("FragmentPersonPresenter");
    private Context context;

    public FragmentPersonPresenter(Context context) {
        this.context = context;
    }

    public void showMoney(String str) {
        getView().showMoney(str);
    }

    public void getWeatherMessage() {

    }
}
