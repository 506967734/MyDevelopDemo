package com.demo.testautomatic;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppDemo {
//    private static AppiumDriver<AndroidElement> driver;
    private static AndroidDriver<AndroidElement> driver;

    @Before
    public void setUp() throws Exception {
        // set up appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");//设置HTML5的自动化，打开谷歌浏览器
        //capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");//安卓自动化还是IOS自动化
        capabilities.setCapability(MobileCapabilityType.UDID, "49f24398");//设备id
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Xiaomi MI 4LTE");//设备名称
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");//安卓操作系统版本
        //capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "cn.zhudi.mvpdemo");//被测app的包名
        capabilities.setCapability("appActivity", ".WelcomeActivity");//被测app的入口Activity名称
//        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);//把以上配置传到appium服务端并连接手机
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("App is launched!");
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
        driver.pressKeyCode(AndroidKeyCode.BACK);//模拟点击BACK键操作
        driver.findElementById("cn.zhudi.mvpdemo:id/tvBuy").click();
    }

}
