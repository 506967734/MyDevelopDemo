package com.demo.mockmodule.mockApi;

import android.text.TextUtils;


import com.demo.common.base.BaseApplication;
import com.demo.common.constant.Constants;
import com.demo.common.utils.AppHelper;
import com.demo.common.utils.CacheUtil;
import com.demo.common.utils.FileHelper;
import com.demo.mockmodule.MockBean;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zhudi on 2018/06/04.
 */
public class Mockinterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if(AppHelper.isDebugable(BaseApplication.getInstance()) && "1".equals(CacheUtil.getString(BaseApplication.getInstance(), Constants.MOCK_OPEN))) {
            //这里读取我们需要返回的 Json 字符串
            String url = chain.request().url().toString();
            if(!TextUtils.isEmpty(url)&&url.indexOf('?')>0){
                url = url.substring(0,url.indexOf('?'));
            }
            MockBean bean = MockConfigManger.getInstance().findMockBean(url);
            if(bean==null||bean.getClose()){
                return  chain.proceed(chain.request());
            }
            if(bean.getNetWorkDelay()>0){
                try {
                    Thread.sleep(bean.getNetWorkDelay());
                }catch (Exception e){}
            }
            String responseString = FileHelper.readAssertResource(BaseApplication.getInstance(), bean.getFileName());
            response = new Response.Builder()
                    .code(200)
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {
            response = chain.proceed(chain.request());
        }

        return response;
    }
}
