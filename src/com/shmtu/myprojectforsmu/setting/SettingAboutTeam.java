package com.shmtu.myprojectforsmu.setting;

import com.shmtu.myprojectforsmu.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SettingAboutTeam extends Activity {

	public static void startSettingAboutTeam(Context context){
		Intent intent = new Intent(context, SettingAboutTeam.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_about_team);
	}
	
}
