package com.shmtu.myprojectforsmu.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class SettingAbout extends Activity {
	
	public static void startSettingAbout(Context context){
		Intent intent = new Intent(context, SettingAbout.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_about);
	}
	
}
