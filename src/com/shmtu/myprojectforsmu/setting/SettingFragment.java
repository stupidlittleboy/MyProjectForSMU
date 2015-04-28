package com.shmtu.myprojectforsmu.setting;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class SettingFragment extends Fragment implements OnClickListener{

	private final static String NICKNAME_URL = Constant.URL + "get_nickname.php";

	private LinearLayout settingPerinfo;
	private LinearLayout settingTaskinfo;
	private LinearLayout settingMap;
	private LinearLayout settingAbout;
	private LinearLayout settingExit;
	private LinearLayout settingChange;
	private TextView tvPerdetail;
	private RequestQueue mQueue = null;
	private String empNo;

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
		settingChange = (LinearLayout) getActivity().findViewById(R.id.layout_change);
		settingExit = (LinearLayout) getActivity().findViewById(R.id.layout_exit);
		tvPerdetail = (TextView) getActivity().findViewById(R.id.tv_perdetail);

		SharedPreferences sp = getActivity().getSharedPreferences("myProjectForSMU", 0);
		String name = sp.getString("userName", null);
		JSONObject json = new JSONObject();
		try {
			json.put("userName", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(NICKNAME_URL, json, 
				new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				try {
					tvPerdetail.setText(response.getString("emp_nickname").toString().trim());
					empNo = response.getString("emp_no").toString().trim();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mQueue.add(jsonObjectRequest);

		tvPerdetail.setHint(name);

		settingPerinfo.setOnClickListener(this);
		settingTaskinfo.setOnClickListener(this);
		settingMap.setOnClickListener(this);
		settingAbout.setOnClickListener(this);
		settingChange.setOnClickListener(this);
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
			SettingTaskInfo.startSettingTaskInfo(getActivity(), empNo);
			Toast.makeText(getActivity(), "已领取任务", Toast.LENGTH_SHORT).show();
			break;

		case R.id.layout_map:
			SettingMap.startSettingMapLocation(getActivity());
			Toast.makeText(getActivity(), "地图", Toast.LENGTH_SHORT).show();
			break;

		case R.id.layout_about:
			SettingAbout.startSettingAbout(getActivity());
			Toast.makeText(getActivity(), "关于", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_change:
			SettingChangePass.startSettingChangePass(getActivity());
			break;

		case R.id.layout_exit:
			SharedPreferences sp = getActivity().getSharedPreferences("myProjectForSMU", getActivity().MODE_PRIVATE);
			sp.edit().clear().commit();
			getActivity().finish();
			Toast.makeText(getActivity(), "退出", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
