package com.demo.common.utils;

import android.app.Activity;
import android.content.Context;

import com.demo.common.base.BaseApplication;

public class ApplicationManager {
	private static Activity sCurrentActivity;
	
	public static Context getApplicationContext() {
		return BaseApplication.getInstance();
	}
	
	public static void setCurrentActivity(Activity activity){
		sCurrentActivity = activity;
	}
	
	public static Activity getCurrentActivity(){
		return sCurrentActivity;
	}
}