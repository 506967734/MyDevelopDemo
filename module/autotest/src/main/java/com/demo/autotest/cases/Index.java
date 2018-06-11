package com.demo.autotest.cases;

import com.demo.autotest.base.Assertion;
import com.demo.autotest.base.InitAppium;
import com.demo.autotest.operation.IndexOperate;
import com.demo.autotest.operation.LoginOperate;
import com.demo.autotest.pages.IndexPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Index extends InitAppium {
    private LoginOperate loginOperate;
    private IndexOperate indexOperate;


    @BeforeClass
    public void initDriver() {


        Assert.assertNotNull(driver);
        loginOperate = new LoginOperate(driver);
        indexOperate = new IndexOperate(driver);

    }

    @Test
    public void Login() {
        //这里先登录，
        boolean flag = loginOperate.login("17326056602", "123456");
        if (flag) {
            Reporter.log("登录成功");
        }
        //断言是否成功登录
        Assert.assertTrue(flag);
    }

    /**
     * 测试个人中心
     */
    @Test(priority = 1)
    public void testPersonCenter() {
        //验证个人中心
        Assertion.verifyEquals(indexOperate.verifyPersonCenter(), true, "个人中心是否正常显示");
        Reporter.log("个人中心正常显示");
    }

    /**
     * 测试首页
     */
    @Test(priority = 2)
    public void testMain() {
        Assertion.verifyEquals(indexOperate.verifyMain(), true, "首页是否正常显示");
        Reporter.log("首页正常显示");
        indexOperate.clickMainListView();
    }

    /**
     * 测试专家搜索页面
     */
    @Test(priority = 3)
    public void testExpertSearch() {
        Assertion.verifyEquals(indexOperate.verifyExpertSearch(), true, "首页是否正常显示");

    }

    /**
     * 测试学府
     */
    @Test(priority = 4)
    public void testSchool() {
        Assertion.verifyEquals(indexOperate.verifySchool(), true, "学府整体是否正常显示");
    }

    /**
     * 测试学府Tab
     */
    @Test(priority = 5)
    public void testSchoolTab() {
        for (int i = 0; i < IndexPage.SCHOOL_TAB_FLAG_TEXT.length; i++) {
            Assertion.verifyEquals(indexOperate.verifySchoolTab(i), true, "学府Tab" + IndexPage.SCHOOL_TAB_FLAG_TEXT[i] + "是否正常");
        }
    }


    /**
     * 测试实践
     */
    @Test(priority = 6)
    public void testInspection() {
        Assertion.verifyEquals(indexOperate.verifyInspection(), true, "实践是否正常显示");
    }

    /**
     * 测试监护
     */
    @Test(priority = 7)
    public void testIntentcare() {
        indexOperate.verifyIntentcare();
    }

    /**
     * 测试消息中心
     */
    @Test(priority = 8)
    public void testNoticeCenter() {
        Assertion.verifyEquals(indexOperate.verifyNoticeCenter(), true, "测试消息中心是否正常");
    }
}
