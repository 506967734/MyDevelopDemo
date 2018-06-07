package cn.zhudi.mvpdemo.model.impl;

import android.content.Context;
import android.text.TextUtils;

import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.model.ILoginModelBiz;


public class LoginBizImpl implements ILoginModelBiz {
    private Context context;

    public LoginBizImpl(Context context) {
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
    public void requestForData(String userName, String userPassword, final OnRequestListener listener) {
    }
}
