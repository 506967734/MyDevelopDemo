package com.zd.retrofitlibrary.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.demo.common.constant.Constants;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zd.retrofitlibrary.api.BaseApi;
import com.zd.retrofitlibrary.exception.RetryWhenNetworkException;
import com.zd.retrofitlibrary.http.func.ExceptionFunc;
import com.zd.retrofitlibrary.http.func.ResulteFunc;
import com.zd.retrofitlibrary.interceptor.HeaderInterceptor;
import com.zd.retrofitlibrary.interceptor.HttpLoggingInterceptor;
import com.zd.retrofitlibrary.listener.HttpOnNextListener;
import com.zd.retrofitlibrary.subscribers.ProgressSubscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by zhudi on 2016/7/16.
 */
public class HttpManager {
    private static final Logger logger = LoggerFactory.getLogger("HttpManager");
    private static HttpManager mInstance;
    private static OkHttpClient okHttp;
    private static Retrofit retrofit;
    /*超时时间-默认5秒*/
    private int connectionTime = 5;
    /*超时时间-默认10秒*/
    private int readTime = 10;
    /*基础url*/
    private String baseUrl = "http://www.weather.com.cn";
    /*软引用對象*/
    //private SoftReference<HttpOnNextSubListener> onNextSubListener;
    private RxAppCompatActivity appCompatActivity;

    public HttpManager setAppCompatActivity(RxAppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        return this;
    }

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    private HttpManager() {
        init();
    }

    private void init() {
        if (TextUtils.isEmpty(baseUrl)) {
            logger.warn("find url is null");
            return;
        }
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectionTime, TimeUnit.SECONDS);
        builder.readTimeout(readTime, TimeUnit.SECONDS);
        builder.writeTimeout(readTime, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false);
        //设置X-Ehuodi的追踪HEADER,和更新时间
        builder.addInterceptor(new HeaderInterceptor());
        //设置HEADERS的追踪日志
        builder.addNetworkInterceptor(new HttpLoggingInterceptor(Constants.NET_LOG_HEADERS, HttpLoggingInterceptor.Level.HEADERS));
        //设置追踪BODYS的日志
        builder.addInterceptor(new HttpLoggingInterceptor(Constants.NET_LOG_BODYS, HttpLoggingInterceptor.Level.BODY));
        //接入stetho调试器
        Interceptor stethoInterceptor = getStethoInterceptor();
        if (stethoInterceptor != null) {
            logger.warn("find com.facebook.stetho.okhttp3.StethoInterceptor, add it");
            builder.addNetworkInterceptor(stethoInterceptor);
        }
        //设置Mock调试器
        Interceptor mockInterceptor = getMockInterceptor();
        if (mockInterceptor != null) {
            logger.warn("find com.demo.mockmodule.mockApi.Mockinterceptor, add it");
            builder.addInterceptor(mockInterceptor);
        }
        builder.addInterceptor(getUserTokenInterceptor());
        okHttp = builder.build();
        return okHttp;
    }

    public static Interceptor getStethoInterceptor() {
        try {
            Class clazz = Class.forName("com.facebook.stetho.okhttp3.StethoInterceptor");
            return (Interceptor) clazz.newInstance();
        } catch (Exception e) {
            logger.info("com.facebook.stetho.okhttp3.StethoInterceptor not find, can not debug use Stetho.");
        }
        return null;
    }

    public static Interceptor getMockInterceptor() {
        try {
            Class clazz = Class.forName("com.demo.mockmodule.mockApi.Mockinterceptor");
            return (Interceptor) clazz.newInstance();
        } catch (Exception e) {
            logger.info("com.demo.mockmodule.mockApi.Mockinterceptor not find, can not debug use mock.");
        }
        return null;
    }


    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(final BaseApi basePar, HttpOnNextListener onNextListener) {
        httpDeal(basePar.getObservable(retrofit), basePar, onNextListener);
    }


    /**
     * RxRetrofit处理
     *
     * @param observable
     * @param basePar
     */
    public void httpDeal(Observable observable, BaseApi basePar, HttpOnNextListener onNextListener) {
        /*失败后的retry配置*/
        observable = observable.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*异常处理*/
                .onErrorResumeNext(new ExceptionFunc())
                /*生命周期管理*/
                //.compose(appCompatActivity.get().bindToLifecycle())
                //Note:手动设置在activity onDestroy的时候取消订阅
                .compose(appCompatActivity.bindUntilEvent(ActivityEvent.DESTROY))
                /*返回数据统一判断*/
                .map(new ResulteFunc())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());

        /**
         * ober回调，链接式返回
         * 暂时不用
         */
//        if (onNextSubListener != null && null != onNextSubListener.get()) {
//            onNextSubListener.get().onNext(observable, basePar.getMethod());
//        }

        /*数据String回调*/
        if (onNextListener != null) {
            ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
            observable.subscribe(subscriber);
        }
    }

    /**
     * 设置用户token
     *
     * @return
     */

    @NonNull
    private Interceptor getUserTokenInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("gtoken", "asdfasdfasdfasdf")
                        .build();
                return chain.proceed(request);
            }
        };
    }

}
