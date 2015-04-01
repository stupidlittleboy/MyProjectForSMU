package com.shmtu.myprojectforsmu.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.R;

public class SettingAbout extends Activity implements OnClickListener {

	private RelativeLayout layoutSettingVersion;
	private RelativeLayout layoutSettingTeam;
	private RelativeLayout layoutSettingRecommend;
	private TextView tvSettingVersion;
	private TextView tvSettingAboutVersion;
	private String version;

	public static void startSettingAbout(Context context){
		Intent intent = new Intent(context, SettingAbout.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_about);

		init();
	}

	private void init(){
		layoutSettingVersion = (RelativeLayout) findViewById(R.id.layout_setting_version);
		layoutSettingTeam = (RelativeLayout) findViewById(R.id.layout_setting_team);
		layoutSettingRecommend = (RelativeLayout) findViewById(R.id.layout_setting_recommend);
		tvSettingVersion = (TextView) findViewById(R.id.tv_setting_version);
		tvSettingAboutVersion = (TextView) findViewById(R.id.tv_setting_about_version);
		
		version = "V " + getAppVersionName(this);
		tvSettingVersion.setHint(version);
		tvSettingAboutVersion.setHint(version);

		layoutSettingVersion.setOnClickListener(this);
		layoutSettingTeam.setOnClickListener(this);
		layoutSettingRecommend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.layout_setting_version:
			Toast.makeText(SettingAbout.this, "当前应用程序版本号为：" + version, Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_setting_team:
//			Toast.makeText(SettingAbout.this, "点击团队" + version, Toast.LENGTH_SHORT).show();
			SettingAboutTeam.startSettingAboutTeam(SettingAbout.this);
			break;
			
		case R.id.layout_setting_recommend:
//			Toast.makeText(SettingAbout.this, "点击介绍" + version, Toast.LENGTH_SHORT).show();
			SettingAboutRecommend.startSettingAboutRecommend(SettingAbout.this);
			break;

		default:
			break;
		}
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// —get the package info—
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
//			versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

}
