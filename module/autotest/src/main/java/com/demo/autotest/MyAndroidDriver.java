package com.demo.autotest;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MyAndroidDriver {
    private static AndroidDriver driver = null;

    public static AndroidDriver getDriver() {
        return driver;
    }

    public static AndroidDriver getInstance() {
        if (driver == null) {
            synchronized (MyAndroidDriver.class) {
                try {
                    driver = driverInit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return driver;
    }

    private static AndroidDriver driverInit() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");//设置HTML5的自动化，打开谷歌浏览器
        //capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");//安卓自动化还是IOS自动化
        capabilities.setCapability(MobileCapabilityType.UDID, "49f24398");//设备id
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Xiaomi MI 4LTE");//设备名称
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");//安卓操作系统版本
        capabilities.setCapability("sessionOverride", "true"); //每次启动时覆盖session，否则第二次后运行会报错不能新建session
        //capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "cn.zhudi.mvpdemo");//被测app的包名
        capabilities.setCapability("appActivity", ".WelcomeActivity");//被测app的入口Activity名称
        return new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
}
