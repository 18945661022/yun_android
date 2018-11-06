package com.example.yun;

import java.util.HashSet;
import java.util.Set;


import android.app.Activity;
import android.app.Application;


public class MyApplication extends Application {

	private static final String TAG = MyApplication.class.getSimpleName();

	private static MyApplication instance;
	
	
	public static String RT_UPLOAD_URL;
	public static String RT_SHOW_URL;
	public static String RT_WAP_URL;	

	public static MyApplication getInstance() {
		return instance;
	}

	public static Set<Activity> setActivities;


	public void onCreate() {
		instance = this;
		setActivities = new HashSet<Activity>();
	}

    

}
