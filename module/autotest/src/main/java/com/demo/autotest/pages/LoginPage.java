package com.demo.autotest.pages;

import com.demo.autotest.base.PageAppium;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginPage extends PageAppium {
    //登录界面的登录按钮
    public final String LOGIN_BUTTON_ID = "login_btn_login";
    //第一屏的登录按钮
    public final String WELCOME_LOGIN_BUTTON_ID = "welcome_login_btn";

    //成功登录到首页标识
    public final String INDEX_TEXT = "首页";

    //是否在关于屏标识
    public final String ABOUT_TEXT = "AboutActivity";

    //关于页面的按钮id
    public final String ABOUT_BUTTON_ID = "about_go_button";


    //帐号密码控件
    public final String NAME_PASS_ELEMENT = "android.widget.EditText";
    //首页控件
    public final String INDEX_ELEMENT = "tl_index";

    //登录完成之后的activity名字
    public final String INDEX_ACTIVITY_NAME = "IndexActivity";

    //首页头像ID
    public final String HEADER_BUTTON_ID = "commonheader_left";

    //注销
    private final String LOGOUT_BUTTON_ID = "slidingmenu_exit_account";

    //注销确认按键
    private final String LOGON_BUTTON_SURE_ID = "dilaog_button3";


    public LoginPage(AndroidDriver driver) {
        super(driver);
    }


    /**
     * 是否在欢迎界面
     */
    public boolean isWelcome() {
        return isIdElementExist(WELCOME_LOGIN_BUTTON_ID, 3, true);
    }


    /**
     * 获取关于界面的activity的名字
     *
     * @return
     */
    public String getAboutText() {
        return ABOUT_TEXT;
    }


    /**
     * 获取关于界面的按钮
     *
     * @return
     */
    public AndroidElement getAboutButton() {
        return waitAutoById(ABOUT_BUTTON_ID);
    }


    public AndroidElement getWelcmoeLoginButton() {
        return findById(WELCOME_LOGIN_BUTTON_ID);
    }


    public AndroidElement getHeaderButton() {
        return findById(HEADER_BUTTON_ID);
    }

    public AndroidElement getLogoutButton() {
        return findById(LOGOUT_BUTTON_ID);
    }

    public AndroidElement getLogoutSureButton() {
        return findById(LOGON_BUTTON_SURE_ID);
    }

    public AndroidElement getLoginButton() {
        return findById(LOGIN_BUTTON_ID, "登录按键");
    }

    /**
     * 获取账号密码框的控件
     *
     * @return
     */
    public List<AndroidElement> getNamePassElement() {
        return getManyElementByClassName(NAME_PASS_ELEMENT, 2);
    }


    /**
     * 首页标识，是否成功登录
     *
     * @return
     */
    public boolean getIndexflag() {
        /*AndroidElement element =  waitAutoByXp(LoginPage.INDEX_TEXT);
        return  element != null;*/

        AndroidElement element = findById(INDEX_ELEMENT);
        return element != null;
    }


    /**
     * 获取首页的一个元素，让操作程序等待
     */
    public String getIndexElementId() {
        return INDEX_ELEMENT;
    }

    /**
     * 获取首页的一个元素，让操作程序等待
     */
    public AndroidElement getIndexElement() {
        return findById(INDEX_ELEMENT);
    }


    public String getIndexActivity() {
        return INDEX_ACTIVITY_NAME;
    }
}
