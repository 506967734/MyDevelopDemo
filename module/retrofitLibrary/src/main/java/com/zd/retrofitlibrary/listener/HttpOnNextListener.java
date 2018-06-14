package com.zd.retrofitlibrary.listener;


import com.zd.retrofitlibrary.exception.ApiException;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface HttpOnNextListener {
    /**
     * 成功后回调方法
     *
     * @param result
     * @param method
     */
    void onNext(String result, String method);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     *
     * @param e
     * @param method
     */
    void onError(ApiException e, String method);

    /**
     * 加载成功
     */
    void onFinish();
}
