package cn.zhudi.mvpdemo.model.impl;

import android.content.Context;
import android.text.TextUtils;

import com.demo.apicommon.api.WeatherGetApi;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zd.retrofitlibrary.http.HttpManager;
import com.zd.retrofitlibrary.listener.HttpOnNextListener;

import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.model.ILoginModelBiz;
import cn.zhudi.mvpdemo.model.IWeatherModelBiz;
import cn.zhudi.mvpdemo.model.entities.User;


public class WeatherBizImpl implements IWeatherModelBiz {
    private RxAppCompatActivity context;

    public WeatherBizImpl(RxAppCompatActivity context) {
        this.context = context;
    }

    @Override
    public boolean isEmpty(String name) {
        if (TextUtils.isEmpty(name)) {
            return true;
        }
        return false;
    }

    @Override
    public void requestForData(HttpOnNextListener listener) {
        HttpManager.getInstance().setAppCompatActivity(context).doHttpDeal(new WeatherGetApi(),listener);
    }
}
