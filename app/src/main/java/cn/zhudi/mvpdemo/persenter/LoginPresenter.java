package cn.zhudi.mvpdemo.persenter;

import android.content.Context;

import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.model.entities.User;
import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.model.ILoginModelBiz;
import cn.zhudi.mvpdemo.model.impl.LoginBizImpl;
import cn.zhudi.mvpdemo.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {
    private Context context;
    private ILoginModelBiz requestBiz;

    public LoginPresenter(Context context) {
        this.context = context;
        requestBiz = new LoginBizImpl(context);
    }


    public void login(String userName, String userPassword) {
        if (requestBiz.isEmpty(userName)) {
            getView().showMessage("请输入用户名");
            return;
        }
        if (requestBiz.isEmpty(userPassword)) {
            getView().showMessage("请输入密码");
            return;
        }
        requestBiz.requestForData(userName, userPassword, new OnRequestListener<User>() {
            @Override
            public void onSuccess(User data) {
                getView().loginSuccess(data);
            }

            @Override
            public void onFailed(int code, String msg) {
                getView().showMessage(msg);
            }
        });
    }
}
