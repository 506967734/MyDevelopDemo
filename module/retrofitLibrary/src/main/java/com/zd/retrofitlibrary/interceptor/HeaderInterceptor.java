package com.zd.retrofitlibrary.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.demo.common.base.BaseApplication;
import com.demo.common.utils.AppHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Version;

/**
 * 请求头拦截器
 * Created by cwf on 16/7/5.
 */
public class HeaderInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger("HeaderInterceptor");
    private static final String HEADER_NAME_UUID = "X-HEME-UUID";
    private static final String X_HEME_SOURCE = "X-HEME-SOURCE";
    private static final String X_HEME_VERSION = "X-HEME-VERSION";
    private static final String HEADER_NAME_Device = "X-HEME-Device";
    private static final String HEADER_NAME_UserAgent = "User-Agent";
    private static final String HEADER_NAME_Date = "Date";
    private static final String HEADER_Authorization = "Authorization";

    private String deviceValue = null;
    private String userAgent = null;
    private boolean initUserAgent = false;
    private SimpleDateFormat serverTimeFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    private Date serverTime;
    private Date localTime;

    public HeaderInterceptor() {
        serverTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Context context = BaseApplication.getInstance();
        if (TextUtils.isEmpty(deviceValue)) {
            deviceValue = AppHelper.getUUID(context);
        }
        String uuid = UUID.randomUUID().toString();
        Request request = chain.request();
        if (!initUserAgent) {
            StringBuilder builder = new StringBuilder();
            builder.append(Version.userAgent());
            builder.append(",").append(AppHelper.getPackageName(context));
            builder.append(",").append(AppHelper.getVersionName(context));
            userAgent = builder.toString();
            initUserAgent = true;
        }
        logger.info("intercept, replace userAgnet, and Add X-HEME-UUID, X-HEME-Device");
        logger.info("deviceValue:{}, uuid:{}, userAgent:{}", deviceValue, uuid, userAgent);
        Request.Builder builder = request.newBuilder();
        //builder.addHeader(HEADER_NAME_UUID, uuid);
        builder.addHeader(HEADER_NAME_UUID, deviceValue);//HEADER_NAME_Device
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
        builder.addHeader("Accept-Language", "zh-CN");
        builder.addHeader(X_HEME_SOURCE, "Android");
        builder.removeHeader(HEADER_NAME_UserAgent);
        builder.addHeader(HEADER_NAME_UserAgent, userAgent);
        builder.addHeader(X_HEME_VERSION, AppHelper.getVersionName(context));
//        String token = GlobalVariable.aCache.getAsString(SPlConstant.TOKEN_KEY);
//        if (!TextUtils.isEmpty(token)) {
//            builder.addHeader(HEADER_Authorization, "Bearer " + GlobalVariable.aCache.getAsString(SPlConstant.TOKEN_KEY));
//        }
        Response response = chain.proceed(builder.build());

        String headerDate = response.header(HEADER_NAME_Date, "");
        if (!TextUtils.isEmpty(headerDate)) {
            //默认时间格式：Mon, 26 Dec 2016 07:48:08 GMT
            try {
                serverTime = serverTimeFormat.parse(headerDate);
                localTime = new Date();
                logger.info("headerDate:{}, serverTime:{}, localTime:{}", headerDate, serverTime, localTime);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return response;
    }

    public Date getServerTime() {
        if (serverTime == null || localTime == null) {
            return new Date();
        }
        return new Date(serverTime.getTime() - localTime.getTime() + System.currentTimeMillis());
    }
}
