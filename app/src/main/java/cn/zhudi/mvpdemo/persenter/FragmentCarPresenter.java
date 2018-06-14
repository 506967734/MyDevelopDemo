package cn.zhudi.mvpdemo.persenter;

import android.content.Context;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zd.retrofitlibrary.exception.ApiException;
import com.zd.retrofitlibrary.listener.HttpOnNextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.model.IWeatherModelBiz;
import cn.zhudi.mvpdemo.model.entities.User;
import cn.zhudi.mvpdemo.model.impl.WeatherBizImpl;
import cn.zhudi.mvpdemo.view.FragmentCarView;

/**
 * Created by zhudi on 2017/11/29.
 */

public class FragmentCarPresenter extends BasePresenter<FragmentCarView> {
    private static final Logger logger = LoggerFactory.getLogger("FragmentCarPresenter");
    private Context context;
    private IWeatherModelBiz request;

    public FragmentCarPresenter(Context context) {
        this.context = context;
        request = new WeatherBizImpl((RxAppCompatActivity) context);
    }

    public void showMoney(String str) {
        getView().showMoney(str);
    }

    public void getWeatherMessage() {
        request.requestForData(new HttpOnNextListener() {

            @Override
            public void onNext(String result, String method) {
                logger.debug(result);
            }

            @Override
            public void onError(ApiException e, String method) {
                getView().showMoney(e.getDisplayMessage());
            }
        });
    }
}
