package com.shmtu.myprojectforsmu.setting;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.R;

public class SettingFragment extends Fragment implements OnClickListener{

	private LinearLayout settingPerinfo;
	private LinearLayout settingTaskinfo;
	private LinearLayout settingMap;
	private LinearLayout settingAbout;
	private LinearLayout settingExit;
	private TextView tvPerdetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View settingLayout = inflater.inflate(R.layout.setting_layout,
				container, false);
		return settingLayout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		init();
	}
	
	private void init(){
		settingPerinfo = (LinearLayout) getActivity().findViewById(R.id.layout_perinfo);
		settingTaskinfo = (LinearLayout) getActivity().findViewById(R.id.layout_taskinfo);
		settingMap = (LinearLayout) getActivity().findViewById(R.id.layout_map);
		settingAbout = (LinearLayout) getActivity().findViewById(R.id.layout_about);
		settingExit = (LinearLayout) getActivity().findViewById(R.id.layout_exit);
		tvPerdetail = (TextView) getActivity().findViewById(R.id.tv_perdetail);
		
		SharedPreferences sp = getActivity().getSharedPreferences("myProjectForSMU", 0);
		String name = sp.getString("userName", null);
		tvPerdetail.setHint(name);
		
		settingPerinfo.setOnClickListener(this);
		settingTaskinfo.setOnClickListener(this);
		settingMap.setOnClickListener(this);
		settingAbout.setOnClickListener(this);
		settingExit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_perinfo:
			SettingPerInfo.startSettingPerInfo(getActivity());
			Toast.makeText(getActivity(), "个人信息", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_taskinfo:
			SettingTaskInfo.startSettingTaskInfo(getActivity());
			Toast.makeText(getActivity(), "已领取任务", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_map:
			SettingMap.startSettingMap(getActivity());
			Toast.makeText(getActivity(), "地图", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_about:
			SettingAbout.startSettingAbout(getActivity());
			Toast.makeText(getActivity(), "关于", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_exit:
			getActivity().finish();
			Toast.makeText(getActivity(), "退出", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
