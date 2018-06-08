package com.demo.autotest.base;

import com.google.common.base.Function;

import io.appium.java_client.android.AndroidDriver;

public interface ExpectedCondition<T> extends Function<AndroidDriver, T> {}
