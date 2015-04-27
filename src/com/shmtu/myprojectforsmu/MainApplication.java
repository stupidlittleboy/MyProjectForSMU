package com.shmtu.myprojectforsmu;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class MainApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

}
