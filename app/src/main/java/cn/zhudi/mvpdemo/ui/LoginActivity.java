package cn.zhudi.mvpdemo.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhudi.mvpdemo.MainActivity;
import cn.zhudi.mvpdemo.base.BaseMVPActivity;
import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.model.entities.User;
import cn.zhudi.mvpdemo.persenter.LoginPresenter;
import cn.zhudi.mvpdemo.view.LoginView;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseMVPActivity<LoginView, LoginPresenter> implements LoginView {

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitleText() {
        return null;
    }

    @Override
    public int getRightImg() {
        return 0;
    }


    @OnClick(R.id.btnLogin)
    public void loginClick() {
        //presenter.login(getUserName(), getUserPassword());
        //startSelfActivity(this, MainActivity.class);
    }

    @BindView(R.id.tvShow)
    TextView tvShow;
    @Override
    public void loginSuccess(User data) {
        //tvShow.setText("success");
        //startSelfActivity(this, MainActivity.class);
    }

    @BindView(R.id.etUserName)
    EditText etUserName;

    @Override
    public String getUserName() {
        return etUserName.getText().toString().trim();
    }

    @BindView(R.id.etUserPassword)
    EditText etPassword;

    @Override
    public String getUserPassword() {
        return etPassword.getText().toString().trim();
    }
}
