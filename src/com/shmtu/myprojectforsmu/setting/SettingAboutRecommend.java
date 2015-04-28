package com.shmtu.myprojectforsmu.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;

public class SettingAboutRecommend extends BaseActivity {
	
	public static void startSettingAboutRecommend(Context context){
		Intent intent = new Intent(context, SettingAboutRecommend.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_about_recommend);
	}

}
