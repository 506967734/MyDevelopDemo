package com.demo.autotest.operation;

import com.demo.autotest.base.Assertion;
import com.demo.autotest.base.OperateAppium;
import com.demo.autotest.pages.IndexPage;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class IndexOperate extends OperateAppium {

    private IndexPage indexPage;

    public IndexOperate(AndroidDriver androidDriver) {
        super(androidDriver);
        indexPage = new IndexPage(androidDriver);
    }


    /**
     * 测试左右滑动
     */
    public boolean verfiySwip() {


        //专家
        swipeToLeft(1000);

        //学府tab1
        swipeToLeft(1000);


        sleep(1000);
        //学府tab2
        swipeToLeft(1000);
        //学府tab3
        swipeToLeft(1000);
        //学府tab4
        swipeToLeft(1000);
        //自查
        swipeToLeft(1000);

        if (indexPage.isTextExist("监护")) {
            //存在监护就是监护人了，滑动多一遍
            swipeToLeft(1000);
        }


        //划出消息中心
        swipeToLeft(1000, 10);

        //回到自查，检测每个界面
        swipeToRight(1000, 10);


        //
        return true;

    }


    /**
     * 验证个人中心
     */
    public boolean verifyPersonCenter() {
        swipeToRight(1000, 10);
        return indexPage.isCurrPersonCenter();
    }


    /**
     * 验证首页
     */
    public boolean verifyMain() {
        //goMain();
        //点击
        clickView(indexPage.findByXpath("//android.widget.TextView[@text='首页']"), "底部主页按钮");
        return indexPage.isCurrExpertMain() || indexPage.isCurrNotExpertMain();
    }

    /**
     * 首页listView点击
     */
    public void clickMainListView() {
        indexPage.clickListView("android.widget.ListView", 1, "android.widget.RelativeLayout", 1);
    }


    /**
     * 验证专家搜索
     */
    public boolean verifyExpertSearch() {

        //goMain();
        String xpath = "//android.widget.TextView[@text='专家']";
        if (waitAutoByXp(xpath, 1) == null) {
            press();   //引导点击
        }

        //点击
        clickView(indexPage.findByXpath(xpath), "底部专家文本控件");
        return indexPage.isCurrExpertSearch();
    }


    /**
     * 验证学府
     */
    public boolean verifySchool() {

        //goMain();

        //点击
        clickView(indexPage.findByXpath("//android.widget.TextView[@text='学府']"), "底部学府文本控件");

        return indexPage.isCurrSchool();
    }

    /**
     * 验证学府的tab
     */
    public boolean verifySchoolTab(int flag) {
        //verifySchool();
        String name = indexPage.getSCHOOL_TAB_FLAG_TEXT()[flag];
        if (clickView(indexPage.findByXpath("//android.widget.TextView[@text='" + name + "']"), "点击学府tab" + name)) {
            return indexPage.isCurrSchool();
        } else {
            return false;
        }

    }


    /**
     * 验证自查
     */
    public boolean verifyInspection() {

        //goMain();
        //点击
        clickView(indexPage.findByXpath("//android.widget.TextView[@text='实践']"), "底部实践文本控件");

        //return isXpathExist("//android.widget.TextView[@text='"+PERSON_DATA_FLAG_TEXT+"']");
        return indexPage.isTextExist();
    }


    /**
     * 验证监护
     */
    public void verifyIntentcare() {
        //goMain();
        if (indexPage.isExistIntentcare()) {
            //点击
            clickView(indexPage.findByXpath("//android.widget.TextView[@text='监护']"), "底部监护文本控件");
            boolean flag = indexPage.isCurrIntentcare();
            Assertion.verifyEquals(flag, true);
        } else {
            print("监护不存在!");
        }
    }

    /**
     * 验证消息中心
     */
    public boolean verifyNoticeCenter() {
        //划出消息中心
        swipeToLeft(1000, 10);

        return indexPage.isCurrNotice();


    }


    /**
     * 关闭左右侧滑
     */
    public void goMain() {
        if (indexPage.isCurrPersonCenter()) {
            //回到首页
            swipeToLeft(1000, 9);
        } else if (indexPage.isCurrNotice()) {
            //关闭消息中心
            swipeToRight(1000, 10);
        }
    }
}
