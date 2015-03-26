package com.shmtu.myprojectforsmu;

/**
 * 用于显示当前页面在哪一个Activity，便于定位
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("BaseActivity", getApplication().getClass().getSimpleName());
	}
}
