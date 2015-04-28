package com.shmtu.myprojectforsmu;

/**
 * 用于显示当前页面在哪一个Activity，便于定位
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.shmtu.myprojectforsmu.utils.ActivityCollector;

public class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("BaseActivity", getClass().getSimpleName());
		ActivityCollector.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
