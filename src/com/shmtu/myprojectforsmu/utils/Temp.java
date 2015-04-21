package com.shmtu.myprojectforsmu.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.login_resgester.LoginActivity;
import com.shmtu.myprojectforsmu.utils.DateUtil;
import com.shmtu.myprojectforsmu.utils.HttpUtils;

public class Temp extends BaseActivity implements
OnClickListener, OnCheckedChangeListener{

	Calendar calendar = Calendar.getInstance();
	private EditText et_empNo;						//员工号
	private EditText et_pass;							//密码
	private EditText et_passConfirm;			//确认密码
	private EditText et_name;							//姓名
	private EditText et_nation;						//民族
	private EditText et_borthdate;				//出生日期
	private EditText et_address;					//家庭住址
	private EditText et_phone;						//联系方式
	private EditText et_email;						//Email地址
	private EditText et_entrytime;				//入职时间
	private RadioGroup radio_sex;					//性别
	private RadioButton radio_male;				//男
	private RadioButton radio_female;			//女
	private Spinner spinner_department;	//部门
	private Spinner spinner_position;		//职位
	private Button btn_submit;						//提交
	private Button btn_reset;						//重置
	private String sex;
	private String position;
	private int status;
	private JSONObject json = new JSONObject();
	private Handler handler;
	private String url = "http://130.234.1.67/Test/register.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//		ButterKnife.inject(this);

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					try {
						String res = msg.getData().getString("res");
						JSONObject result = new JSONObject(res);
						int success = Integer.parseInt(result.getString("success"));
						Toast.makeText(Temp.this, res, Toast.LENGTH_LONG).show();
						if(success == 0){
							Intent intent = new Intent(Temp.this, LoginActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("empNo", json.getString("empNo"));
							bundle.putString("pass", json.getString("pass"));
							intent.putExtras(bundle);
							startActivity(intent);
						}else{
							Toast.makeText(Temp.this, "输入的用户名或密码有错", Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;

				default:
					break;
				}
			}
		};

		initViews();
	}

	private void initViews(){
		et_empNo = (EditText) findViewById(R.id.et_empNo);
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_passConfirm = (EditText) findViewById(R.id.et_passConfirm);
		et_name = (EditText) findViewById(R.id.et_name);
		et_nation = (EditText) findViewById(R.id.et_nation);
		et_borthdate = (EditText) findViewById(R.id.et_borthdate);
		et_address = (EditText) findViewById(R.id.et_address);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_email = (EditText) findViewById(R.id.et_email);
		et_entrytime = (EditText) findViewById(R.id.et_entrytime);
		radio_sex = (RadioGroup) findViewById(R.id.radio_sex);
		radio_male = (RadioButton) findViewById(R.id.radio_male);
		radio_female = (RadioButton) findViewById(R.id.radio_female);
		spinner_department = (Spinner) findViewById(R.id.spinner_department);
		spinner_position = (Spinner) findViewById(R.id.spinner_position);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_reset = (Button) findViewById(R.id.btn_reset);

		//设置性别初始值
		sex = radio_male.getText().toString();
		spinner_department.getSelectedItem().toString();//获取Spinner控件的当前选中的值

		et_borthdate.setOnClickListener(this);
		et_entrytime.setOnClickListener(this);
		radio_sex.setOnCheckedChangeListener(this);
		btn_submit.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
	}

	private void getDate(JSONObject json){
		position = spinner_position.getSelectedItem().toString();
		if("普通员工".equals(position)){
			status = 0;
		}else if("公司职员".equals(position)){
			status = 1;
		}else if ("部门主管".equals(position)) {
			status = 2;
		}else if("部门经理".equals(position)){
			status = 3;
		}else if("总经理".equals(position)){
			status = 4;
		}else if("区域总裁".equals(position)){
			status = 5;
		}
		try {
			json.put("empNo", et_empNo.getText().toString());
			json.put("pass", et_pass.getText().toString());
			json.put("name", et_name.getText().toString());
			json.put("sex", sex);
			json.put("nation", et_nation.getText().toString());
			json.put("borthdate", et_borthdate.getText().toString());
			json.put("address", et_address.getText().toString());
			json.put("phone", et_phone.getText().toString());
			json.put("email", et_email.getText().toString());
			json.put("department", spinner_department.getSelectedItem().toString());
			json.put("position", position);
			json.put("entrytime", et_entrytime.getText().toString());
			json.put("status", status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.et_borthdate:
			DateUtil.datePickerDialog(Temp.this, et_borthdate, calendar);
			break;

		case R.id.et_entrytime:
			DateUtil.datePickerDialog(Temp.this, et_entrytime, calendar);
			break;

		case R.id.btn_submit:
			getDate(json);
			new Thread(){
				@Override
				public void run() {
					try {
						HttpUtils.httpPostMethod(url, json, handler);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}.start();
			Log.e("json", json.toString());
			Toast.makeText(this, json.toString(), Toast.LENGTH_SHORT).show();
			break;

		case R.id.btn_reset:

			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.radio_male) {
			// 把mRadio1的内容传到mTextView1
			sex = radio_male.getText().toString();
		} else if (checkedId == R.id.radio_female) {
			// 把mRadio2的内容传到mTextView1
			sex = radio_female.getText().toString();
		}
	}
}
