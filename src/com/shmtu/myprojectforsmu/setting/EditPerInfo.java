package com.shmtu.myprojectforsmu.setting;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.utils.DateTimePickerDialog;

public class EditPerInfo extends BaseActivity implements OnClickListener,
OnCheckedChangeListener{

	private final static String PER_INFO_URL = Constant.URL + "per_info.php";
	private final static String EDIT_PER_INFO_URL = Constant.URL + "edit_per_info.php";

	private EditText etPerInfoNickname;
	private EditText etPerInfoName;
	private RadioGroup radioPerInfoSex;
	private RadioButton radioPerInfoMale;
	private RadioButton radioPerInfoFemale;
	private RadioButton radioPerInfoSecret;
	private EditText etPerInfoAge;
	private EditText etPerInfoPhoneNo;
	private EditText etPerInfoEmail;
	private TextView etPerInfoEmpNo;
	private TextView etPerInfoDepartment;
	private TextView etPerInfoPosition;
	private TextView etPerInfoEntryDate;
	private EditText etPerInfoBirthday;
	private EditText etPerInfoNation;
	private EditText etPerInfoCity;
	private EditText etPerInfoAddress;
	private Button btnPerInfoSubmit;

	private Calendar calendar = Calendar.getInstance();
	private String sex = null;

	private RequestQueue mQueue = null;
	private RequestQueue mQueueEdit = null;

	public static void startEditPerInfo (Context context) {
		Intent intent = new Intent(context, EditPerInfo.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_per_info);

		init();
	}

	private void init() {
		etPerInfoNickname = (EditText) findViewById(R.id.et_per_info_nickname);
		etPerInfoName = (EditText) findViewById(R.id.et_per_info_name);
		radioPerInfoSex = (RadioGroup) findViewById(R.id.radio_per_info_sex);
		radioPerInfoMale = (RadioButton) findViewById(R.id.radio_per_info_male);
		radioPerInfoFemale = (RadioButton) findViewById(R.id.radio_per_info_female);
		radioPerInfoSecret = (RadioButton) findViewById(R.id.radio_per_info_secret);
		etPerInfoAge = (EditText) findViewById(R.id.et_per_info_age);
		etPerInfoPhoneNo = (EditText) findViewById(R.id.et_per_info_phone_no);
		etPerInfoEmail = (EditText) findViewById(R.id.et_per_info_email);
		etPerInfoEmpNo = (TextView) findViewById(R.id.et_per_info_emp_no);
		etPerInfoDepartment = (TextView) findViewById(R.id.et_per_info_department);
		etPerInfoPosition = (TextView) findViewById(R.id.et_per_info_position);
		etPerInfoEntryDate = (TextView) findViewById(R.id.et_per_info_entry_date);
		etPerInfoBirthday = (EditText) findViewById(R.id.et_per_info_birthday);
		etPerInfoNation = (EditText) findViewById(R.id.et_per_info_nation);
		etPerInfoCity = (EditText) findViewById(R.id.et_per_info_city);
		etPerInfoAddress = (EditText) findViewById(R.id.et_per_info_address);
		btnPerInfoSubmit = (Button) findViewById(R.id.btn_edit_per_info_submit);

		//获取数据库中已存在的信息
		SharedPreferences sp = getSharedPreferences("myProjectForSMU", 0);
		String userName = sp.getString("userName", null);
		Log.e("userName", userName);
		setPerInfo(userName);

		radioPerInfoSex.setOnCheckedChangeListener(this);
		etPerInfoBirthday.setOnClickListener(this);
		btnPerInfoSubmit.setOnClickListener(this);
	}

	private void setPerInfo(String userName) {
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
							etPerInfoNickname.setText(response.getString("emp_nickname"));
							etPerInfoName.setText(response.getString("emp_name"));

							if (radioPerInfoMale.getText().toString().trim().
									equals(response.get("emp_sex"))) {
								radioPerInfoMale.setChecked(true);
							} else if (radioPerInfoFemale.getText().toString().trim().
									equals(response.get("emp_sex"))) {
								radioPerInfoFemale.setChecked(true);
							} else {
								radioPerInfoSecret.setChecked(true);
							}

							//							tvPerInfoSex.setText(response.getString("emp_sex"));
							etPerInfoAge.setText(response.getString("emp_age"));
							etPerInfoPhoneNo.setText(response.getString("emp_phone_no"));
							etPerInfoEmail.setText(response.getString("emp_email"));

							etPerInfoEmpNo.setText(response.getString("emp_no"));
							etPerInfoDepartment.setText(response.getString("emp_department"));
							etPerInfoPosition.setText(response.getString("emp_position"));
							etPerInfoEntryDate.setText(response.getString("emp_entry_date"));

							etPerInfoBirthday.setText(response.getString("emp_borthday"));
							etPerInfoNation.setText(response.getString("emp_nation"));
							etPerInfoCity.setText(response.getString("emp_city"));
							etPerInfoAddress.setText(response.getString("emp_address"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
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
		case R.id.et_per_info_birthday:
			DateTimePickerDialog.dateDialogAll(EditPerInfo.this, calendar, etPerInfoBirthday);
			break;

		case R.id.btn_edit_per_info_submit:
			try {
				JSONObject json = new JSONObject();
				json.put("nickname", etPerInfoNickname.getText().toString().trim());
				json.put("name", etPerInfoName.getText().toString().trim());
				json.put("sex", sex);
				json.put("age", etPerInfoAge.getText().toString().trim());
				json.put("phoneNo", etPerInfoPhoneNo.getText().toString().trim());
				json.put("email", etPerInfoEmail.getText().toString().trim());
				json.put("empNo", etPerInfoEmpNo.getText().toString().trim());
				json.put("birthday", etPerInfoBirthday.getText().toString().trim());
				json.put("nation", etPerInfoNation.getText().toString().trim());
				json.put("city", etPerInfoCity.getText().toString().trim());
				json.put("address", etPerInfoAddress.getText().toString().trim());

				mQueueEdit = Volley.newRequestQueue(getApplicationContext());
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(EDIT_PER_INFO_URL, 
						json, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							int success = Integer.parseInt(response.getString("success"));
							if (success == 1) {
								dialog();
							} else {
								Toast.makeText(EditPerInfo.this, 
										"修改个人信息失败，请耐心等待5秒后再次尝试", Toast.LENGTH_SHORT).show();
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
				mQueueEdit.add(jsonObjectRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.radio_per_info_male) {
			// 把mRadio1的内容传到mTextView1
			sex = radioPerInfoMale.getText().toString();
		} else if (checkedId == R.id.radio_per_info_female) {
			// 把mRadio2的内容传到mTextView1
			sex = radioPerInfoFemale.getText().toString();
		} else {
			sex = radioPerInfoSecret.getText().toString();
		}
	}
	
	/**
	 * 弹出对话框
	 */
	protected void dialog() {
		final AlertDialog builder = new AlertDialog.Builder(this).create();
		builder.setMessage("个人信息已成功修改,对话框2秒后自动关闭！");
		builder.setTitle("提示");
		builder.setCancelable(false);
		builder.show();

		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				builder.dismiss();
				t.cancel();
				EditPerInfo.this.finish();
			}
		}, 2000);
	}
}
