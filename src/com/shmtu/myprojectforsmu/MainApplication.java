package com.shmtu.myprojectforsmu;

import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
		//注意该方法要再setContentView方法之前实现  
//		SDKInitializer.initialize(getApplicationContext());
	}
	
}
