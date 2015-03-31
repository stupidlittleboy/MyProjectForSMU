package com.shmtu.myprojectforsmu.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class SettingTaskInfo extends Activity {

	public static void startSettingTaskInfo(Context context){
		Intent intent = new Intent(context, SettingTaskInfo.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_task_info);
	}
	
}
