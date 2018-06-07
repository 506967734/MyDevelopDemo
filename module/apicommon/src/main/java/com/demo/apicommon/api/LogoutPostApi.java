package com.demo.apicommon.api;

import com.demo.apicommon.HttpPostService;
import com.zd.retrofitlibrary.api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 注销接口
 */
public class LogoutPostApi extends BaseApi {

    /**
     * 默认初始化需要给定初始设置
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * 设置可查看BaseApi
     */
    public LogoutPostApi() {
        setCache(false);
        setCancel(false);
        setShowProgress(true);
        setMethod("/logout");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService httpService = retrofit.create(HttpPostService.class);
        return httpService.logout();
    }

}
