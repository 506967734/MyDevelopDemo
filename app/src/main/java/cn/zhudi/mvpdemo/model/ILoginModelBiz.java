package cn.zhudi.mvpdemo.model;

import cn.zhudi.mvpdemo.base.IBaseModelBiz;
import cn.zhudi.mvpdemo.model.entities.User;
import cn.zhudi.mvpdemo.impl.OnRequestListener;

public interface ILoginModelBiz extends IBaseModelBiz {

    /**
     * 调用登录接口
     *  @param userName     用户名
     * @param userPassword 密码已加密
     * @param listener     返回的监听
     */
    void requestForData(String userName, String userPassword, OnRequestListener<User> listener);
}
