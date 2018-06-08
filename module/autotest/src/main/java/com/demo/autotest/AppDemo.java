package com.demo.autotest;


import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import static junit.framework.Assert.assertTrue;

public class AppDemo {
    private static AndroidDriver<AndroidElement> driver;

    @Before
    public void setUp() throws Exception {
        driver = MyAndroidDriver.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void addContact() throws InterruptedException {
        System.out.println("App is done!");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//等待时间操作
        WebElement el = driver.findElementById("cn.zhudi.mvpdemo:id/ivCar");
        //WebElement el = driver.findElement(By.name("Add Contact"));
        el.click();
//        List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
        //textFieldsList.get(0).sendKeys("Some Name");
        //textFieldsList.get(2).sendKeys("Some@example.com");
        //driver.swipe()
        //driver.pressKeyCode(AndroidKeyCode.BACK);//模拟点击BACK键操作
        driver.findElementById("cn.zhudi.mvpdemo:id/tvBuy").click();
        assertTrue(driver.findElementById("cn.zhudi.mvpdemo:id/tvBuy").getLocation().getY() < el.getLocation().getY());
        //TestCase.assertEquals(Integer.parseInt("19"),20);
    }

}
