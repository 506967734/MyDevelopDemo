package cn.zhudi.mvpdemo.model;

import com.zd.retrofitlibrary.listener.HttpOnNextListener;

import cn.zhudi.mvpdemo.base.IBaseModelBiz;
import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.model.entities.User;

public interface IWeatherModelBiz extends IBaseModelBiz {

    /**
     * 调用登录接口
     * @param listener     返回的监听
     */
    void requestForData(HttpOnNextListener listener);
}
