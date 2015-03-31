package com.shmtu.myprojectforsmu.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class SettingMap extends Activity {

	public static void startSettingMap(Context context){
		Intent intent = new Intent(context, SettingMap.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_map);
	}
	
}
