package com.demo.autotest.base;

import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import static com.demo.autotest.base.InitAppium.appPackage;
import static io.appium.java_client.android.AndroidKeyCode.BACKSPACE;
import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_MOVE_END;

public class OperateAppium {

    AndroidDriver driver;

    //单个触摸操作类
    TouchAction touchAction;

    //多个触摸操作时间
    MultiTouchAction multiTouchAction;

    private static int WAIT_TIME = 3;    //默认的等待控件时间

    private final int SWIPE_DEFAULT_PERCENT = 5;   //默认滑动百分比

    public final String SWIP_UP = "UP",
            SWIP_DOWN = "DOWN",
            SWIP_LEFT = "LEFT",
            SWIP_RIGHT = "RIGHT";


    public OperateAppium(AndroidDriver androidDriver) {
        this.driver = androidDriver;
    }


    /**
     * 打印字符
     *
     * @param str 要打印的字符
     */
    public <T> void print(T str) {
        if (!TextUtils.isEmpty(String.valueOf(str))) {
            System.out.println(str);
        } else {
            System.out.println("输出了空字符");
        }
    }

    /**
     * Click点击空格键
     *
     * @param ae 要点击的控件
     * @return 返回是否点击
     */
    public boolean clickView(AndroidElement ae) {
        return clickView(ae, "");
    }


    /**
     * Click点击控件
     *
     * @param ae  控件
     * @param str 控件的文字描述，供错误时候输出
     * @return 返回是否存在控件
     */
    public boolean clickView(AndroidElement ae, String str) {
        if (ae != null) {
            ae.click();
            return true;
        } else {
            print(str + "为空，点击错误");
        }
        return false;
    }


    /**
     * 线程休眠秒数，单位秒
     *
     * @param s 要休眠的秒数
     */
    public void sleep(long s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取当前的activity,返回文件名
     *
     * @return
     */
    public String getCurrActivity() {
        String str = driver.currentActivity();
        return str.substring(str.lastIndexOf(".") + 1);
    }

    /**
     * 获取触摸实例
     *
     * @return
     */
    public TouchAction getTouch() {
        if (driver == null) {
            print("单点触摸时候driver为空");
            return null;
        } else {
            if (touchAction == null) {
                return new TouchAction(driver);
            } else {
                return touchAction;
            }

        }
    }


    /**
     * 获取多点触摸实例
     *
     * @return
     */
    public MultiTouchAction getMultiTouch() {
        if (driver == null) {
            print("多点触摸时候driver为空");
            return null;
        } else {
            if (multiTouchAction == null) {
                return new MultiTouchAction(driver);
            } else {
                return multiTouchAction;
            }

        }
    }

    /**
     * 往控件输入字符串
     *
     * @param ae  要输入的控件
     * @param str 要输入的字符串
     */
    public void input(AndroidElement ae, String str) {
        if (ae == null) {
            print("控件为空,输入内容失败:" + str);
        } else {
            ae.sendKeys(str);
        }

    }

    public void swipeToUp(int during) {
        swipeToUp(during, SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向上滑动，
     *
     * @param during
     */
    public void swipeToUp(int during, int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width / 2, height * (percent - 1) / percent, width / 2, height / percent, during);
    }

    public void swipeToDown(int during) {
        swipeToDown(during, SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向下滑动，
     *
     * @param during 滑动时间
     */
    public void swipeToDown(int during, int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width / 2, height / percent, width / 2, height * (percent - 1) / percent, during);
    }


    public void swipeToLeft(int during) {
        swipeToLeft(during, SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向左滑动，
     *
     * @param during  滑动时间
     * @param percent 位置的百分比，2-10， 例如3就是 从2/3滑到1/3
     */
    public void swipeToLeft(int during, int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width * (percent - 1) / percent, height / 2, width / percent, height / 2, during);
    }


    public void swipeToRight(int during) {
        swipeToRight(during, SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向右滑动，
     *
     * @param during  滑动时间
     * @param percent 位置的百分比，2-10， 例如3就是 从1/3滑到2/3
     */
    public void swipeToRight(int during, int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width / percent, height / 2, width * (percent - 1) / percent, height / 2, during);
    }


    /**
     * 显示等待，等待Id对应的控件出现time秒，一出现马上返回，time秒不出现也返回
     */
    public AndroidElement waitAuto(final By by, int time) {
        try {
            return new AndroidDriverWait(driver, time)
                    .until(new ExpectedCondition<AndroidElement>() {
                        @Override
                        public AndroidElement apply(AndroidDriver androidDriver) {
                            return (AndroidElement) androidDriver.findElement(by);
                        }
                    });
        } catch (TimeoutException e) {
            print("查找元素超时!! " + time + " 秒之后还没找到元素 [" + by.toString() + "]");
            return null;
        }
    }

    public AndroidElement waitAutoById(String id) {
        return waitAutoById(id, WAIT_TIME);
    }

    public AndroidElement waitAutoById(String id, int time) {
        return waitAuto(By.id(id), time);
    }

    public AndroidElement waitAutoByName(String name) {
        return waitAutoByName(name, WAIT_TIME);
    }

    public AndroidElement waitAutoByName(String name, int time) {
        return waitAuto(By.name(name), time);
    }


    public AndroidElement waitAutoByXp(String xPath) {
        return waitAutoByXp(xPath, WAIT_TIME);
    }

    public AndroidElement waitAutoByXp(String xPath, int time) {
        return waitAuto(By.xpath(xPath), time);
    }

    public void waitAuto() {
        waitAuto(WAIT_TIME);
    }

    /**
     * ，隐式等待，如果在指定时间内还是找不到下个元素则会报错停止脚本
     * 全局设定的，find控件找不到就会按照这个事件来等待
     *
     * @param time 要等待的时间
     */
    public void waitAuto(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    /**
     * 打开Activity
     *
     * @param activityName activity的名字
     */
    public void startActivity(String activityName) {
        driver.startActivity(appPackage, activityName);
    }


    /**
     * 获取当前界面的所有EditText，并依次输入内容
     *
     * @param str 要输入的数组
     */
    public void inputManyText(String... str) {
        List<AndroidElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
        for (int i = 0; i < str.length; i++) {
            textFieldsList.get(i).click();
            clearText(textFieldsList.get(i));   //清除内容
            textFieldsList.get(i).sendKeys(str[i]);
        }
    }

    /**
     * 点击屏幕中间
     */
    public void press() {
        press(getScreenWidth() / 2, getScreenHeight() / 2);
    }


    /**
     * 点击某个控件
     *
     * @param ae 要点击的控件
     */
    public void press(AndroidElement ae) {
        try {
            getTouch().tap(ae).perform();
        } catch (Exception e) {
            print("tab点击元素错误" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 点击某个坐标
     *
     * @param x
     * @param y
     */
    public void press(int x, int y) {
        try {
            driver.tap(1, x, y, 500);
            //getTouch().tap(x, y).perform();
            print("tab点击位置(" + x + "," + y + ")");
        } catch (Exception e) {
            print("tab点击元素位置异常" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 长按某个控件
     *
     * @param ae 要点击的控件
     */
    public void longPress(AndroidElement ae) {
        try {
            getTouch().longPress(ae).release().perform();
        } catch (Exception e) {
            print("长按点击元素错误" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 长按某个坐标
     *
     * @param x
     * @param y
     */
    public void longPress(int x, int y) {
        try {
            getTouch().longPress(x, y).release().perform();
        } catch (Exception e) {
            print("长按点击元素错误" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 在控件上滑动
     *
     * @param element   要滑动的控件
     * @param direction 方向，事件不设置默认1秒
     */
    public void swipOnElement(AndroidElement element, String direction) {
        swipOnElement(element, direction, 1000);  //不设置时间就为2秒
    }

    /**
     * 在某一个控件上滑动
     *
     * @param element   在那个元素上滑动
     * @param direction 方向，UP  DOWN LEFT RIGHT
     */
    public void swipOnElement(AndroidElement element, String direction, int duration) {
        //获取元素的起初xy，在左上角
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        //获取元素的宽高
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        switch (direction) {
            case SWIP_UP:
                int startX = x + width / 2;
                //在4/5的底部的中间向上滑动
                driver.swipe(startX, y + height * 4 / 5, startX, y + height / 5, duration);
                break;
            case SWIP_DOWN:
                startX = x + width / 2;
                //在4/5的底部的中间向上滑动
                driver.swipe(startX, y + height / 5, startX, y + height * 4 / 5, duration);
                break;

            case SWIP_LEFT:
                int startY = y + width / 2;
                driver.swipe(x + width * 4 / 5, startY, x + width / 5, startY, duration);
                break;
            case SWIP_RIGHT:
                startY = y + width / 2;
                driver.swipe(x + width / 5, startY, x + width * 4 / 5, startY, duration);
                break;
        }
    }

    /**
     * 在某个方向上滑动
     *
     * @param direction 方向，UP DOWN LEFT RIGHT
     * @param duration  持续时间
     */
    public void swip(String direction, int duration) {
        switch (direction) {
            case "UP":
                swipeToUp(duration);
                break;
            case "DOWN":
                swipeToDown(duration);
                break;
            case "LEFT":
                swipeToLeft(duration);
                break;
            case "RIGHT":
                swipeToRight(duration);
                break;
        }
    }


    /**
     * 在指定次数的条件下，某个方向滑动，直到这个元素出现
     *
     * @param by         控件
     * @param direction  方向，UP DOWN  LEFT RIGHT
     * @param duration   滑动一次持续时间
     * @param maxSwipNum 最大滑动次数
     */
    public void swipUtilElementAppear(By by, String direction, int duration, int maxSwipNum) {
        int i = maxSwipNum;
        Boolean flag = true;
        while (flag) {
            try {
                if (i <= 0) {
                    flag = false;
                }
                driver.findElement(by);
                flag = false;
            } catch (Exception e) {
                i--;
                swip(direction, duration);
            }
        }
    }

    /**
     * 在某个方向滑动直到这个元素出现
     *
     * @param by        控件
     * @param direction 方向，UP DOWN  LEFT RIGHT
     * @param duration  滑动一次持续时间
     */
    public void swipUtilElementAppear(By by, String direction, int duration) {
        Boolean flag = true;
        while (flag) {
            try {
                driver.findElement(by);
                flag = false;
            } catch (Exception e) {
                swip(direction, duration);
            }
        }
    }


    /**
     * 获取屏幕的宽高
     *
     * @return 返回宽高的数组
     */
    public int[] getScreen() {
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        return new int[]{width, height};
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return driver.manage().window().getSize().getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        return driver.manage().window().getSize().getHeight();
    }


    /**
     * 逐字删除编辑框中的文字
     *
     * @param element 文本框架控件
     */
    public void clearText(AndroidElement element) {
        String text = element.getText();
        //跳到最后
        driver.pressKeyCode(KEYCODE_MOVE_END);
        for (int i = 0; i < text.length(); i++) {
            //循环后退删除
            driver.pressKeyCode(BACKSPACE);
        }

    }


}