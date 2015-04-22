package com.shmtu.myprojectforsmu.setting;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class SettingPerInfo extends BaseActivity implements OnClickListener {

	private final static String PER_INFO_URL = Constant.URL + "per_info.php";

	private TextView tvPerInfoNickname;
	private TextView tvPerInfoName;
	private TextView tvPerInfoSex;
	private TextView tvPerInfoAge;
	private TextView tvPerInfoPhoneNum;
	private TextView tvPerInfoEmail;
	private TextView tvPerInfoEmpNo;
	private TextView tvPerInfoDepartment;
	private TextView tvPerInfoPosition;
	private TextView tvPerInfoEntryDate;
	private TextView tvPerInfoBirthday;
	private TextView tvPerInfoNation;
	private TextView tvPerInfoCity;
	private TextView tvPerInfoAddress;

	private RequestQueue mQueue = null;

	public static void startSettingPerInfo(Context context){
		Intent intent = new Intent(context, SettingPerInfo.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_per_info);

		init();
	}

	private void init(){

		tvPerInfoNickname = (TextView) findViewById(R.id.tv_per_info_nickname);
		tvPerInfoName = (TextView) findViewById(R.id.tv_per_info_name);
		tvPerInfoSex = (TextView) findViewById(R.id.tv_per_info_sex);
		tvPerInfoAge = (TextView) findViewById(R.id.tv_per_info_age);
		tvPerInfoPhoneNum = (TextView) findViewById(R.id.tv_per_info_phone_num);
		tvPerInfoEmail = (TextView) findViewById(R.id.tv_per_info_email);

		tvPerInfoEmpNo = (TextView) findViewById(R.id.tv_per_info_emp_no);
		tvPerInfoDepartment = (TextView) findViewById(R.id.tv_per_info_department);
		tvPerInfoPosition = (TextView) findViewById(R.id.tv_per_info_position);
		tvPerInfoEntryDate = (TextView) findViewById(R.id.tv_per_info_entry_date);

		tvPerInfoBirthday = (TextView) findViewById(R.id.tv_per_info_birthday);
		tvPerInfoNation = (TextView) findViewById(R.id.tv_per_info_nation);
		tvPerInfoCity = (TextView) findViewById(R.id.tv_per_info_city);
		tvPerInfoAddress = (TextView) findViewById(R.id.tv_per_info_address);

		tvPerInfoPhoneNum.setOnClickListener(this);
		tvPerInfoEmail.setOnClickListener(this);

		SharedPreferences sp = getSharedPreferences("myProjectForSMU", 0);
		String userName = sp.getString("userName", null);
		Log.e("userName", userName);
		if (userName != null && !"".equals(userName)){
			try {
				JSONObject json = new JSONObject();
				json.put("userName", userName);
				//创建一个RequestQueue队列
				mQueue = Volley.newRequestQueue(getApplicationContext());
				//向服务端发送请求
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(PER_INFO_URL, json, 
						new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							tvPerInfoNickname.setText(response.getString("emp_nickname"));
							tvPerInfoName.setText(response.getString("emp_name"));
							tvPerInfoSex.setText(response.getString("emp_sex"));
							tvPerInfoAge.setText(response.getString("emp_age"));
							tvPerInfoPhoneNum.setText(response.getString("emp_phone_no"));
							tvPerInfoEmail.setText(response.getString("emp_email"));

							tvPerInfoEmpNo.setText(response.getString("emp_no"));
							tvPerInfoDepartment.setText(response.getString("emp_department"));
							tvPerInfoPosition.setText(response.getString("emp_position"));
							tvPerInfoEntryDate.setText(response.getString("emp_entry_date"));

							tvPerInfoBirthday.setText(response.getString("emp_borthday"));
							tvPerInfoNation.setText(response.getString("emp_nation"));
							tvPerInfoCity.setText(response.getString("emp_city"));
							tvPerInfoAddress.setText(response.getString("emp_address"));
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

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		//打开拨号界面
		case R.id.tv_per_info_phone_num:
			Uri uri = Uri.parse("tel:" + tvPerInfoPhoneNum.getText().toString().trim());
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(intent);
			break;
			//打开邮件界面
		case R.id.tv_per_info_email:
			Uri uri1 = Uri.parse("mailto:" + tvPerInfoEmail.getText().toString().trim());   
			Intent it = new Intent(Intent.ACTION_SENDTO, uri1);   
			startActivity(it); 
			break;

		default:
			break;
		}
	}
	
	//ActionBar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.per_info, menu);
		return super.onCreateOptionsMenu(menu);
	}

	//ActionBar点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.per_info_edit:
//			Toast.makeText(SettingPerInfo.this, "修改个人信息", Toast.LENGTH_SHORT).show();
			EditPerInfo.startEditPerInfo(SettingPerInfo.this);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
