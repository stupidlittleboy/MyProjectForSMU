package com.shmtu.myprojectforsmu.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class SettingPerInfo extends Activity {
	
	public static void startSettingPerInfo(Context context){
		Intent intent = new Intent(context, SettingPerInfo.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_per_info);
	}
	
}
