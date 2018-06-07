package com.demo.apicommon.api;

import com.demo.apicommon.HttpPostService;
import com.zd.retrofitlibrary.api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

public class WeatherGetApi extends BaseApi {
    /**
     * 默认初始化需要给定初始设置
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * 设置可查看BaseApi
     */
    public WeatherGetApi() {
        setCache(false);
        setCancel(false);
        setShowProgress(true);
        setMethod("/data/sk/101010100.html");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService httpService = retrofit.create(HttpPostService.class);
        return httpService.getWeather();
    }

}
