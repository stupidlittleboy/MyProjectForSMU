package com.shmtu.myprojectforsmu.login_resgester;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cn.smssdk.SMSSDK;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.utils.SMSVerifyUtil;

public class SMSRegisterActivity extends BaseActivity implements OnClickListener {

	private Spinner spDepartment;
	private EditText etPhoneNo;
	private EditText etVerifycode;
	private Button btnGetcode;
	private Button btnNext;
	private Drawable bg;
	// 填写从短信SDK应用后台注册得到的APPKEY
	private static String APPKEY = "5f15f82e1a90";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "625ed9a4cb16e8215de824c52bb637b4";
	public String phString;
	public String code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_register);
		
		init();
		
		SMSVerifyUtil.initSMS(this, APPKEY, APPSECRET, handler);
	}
	
	private void init() {
		spDepartment = (Spinner) findViewById(R.id.sp_department);
		etPhoneNo = (EditText) findViewById(R.id.et_phone_no);
		etVerifycode = (EditText) findViewById(R.id.et_verifycode);
		btnGetcode = (Button) findViewById(R.id.btn_getcode);
		btnNext = (Button) findViewById(R.id.btn_next);
		bg = btnGetcode.getBackground();		//获取button背景样式
		
		btnGetcode.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getcode://获取验证码
			phString = etPhoneNo.getText().toString();
			if(!TextUtils.isEmpty(phString)){
				code = SMSVerifyUtil.subStrCountry(spDepartment.getSelectedItem().toString());
				SMSVerifyUtil.getCode(code, etPhoneNo, btnGetcode, bg);
				Log.e("phStrig", phString);
			}else {
				Toast.makeText(this, "电话不能为空", 1).show();
			}
			
			break;
		case R.id.btn_next://校验验证码
			if(!TextUtils.isEmpty(etVerifycode.getText().toString())){
				SMSVerifyUtil.submitCode(code, phString, etVerifycode.getText().toString());
//				SMSSDK.submitVerificationCode(code, phString, verEditText.getText().toString());
			}else {
				Toast.makeText(this, "验证码不能为空", 1).show();
			}
			break;
		default:
			break;
		}
	}

	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			Log.e("result", "result="+result);
			Log.e("data", "data="+data);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
					Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
					/*Intent intent = new Intent(SMSRegisterActivity.this, RegisterActivity.class);
					startActivity(intent);*/
					RegisterActivity.startRegisterActivity(SMSRegisterActivity.this, etPhoneNo.getText().toString().trim());
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
				}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
					Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
				}
			} else {
				((Throwable) data).printStackTrace();
				Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
			}
			
		}
		
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//必须有，否则会造成内存泄露
		SMSSDK.unregisterAllEventHandler();
	}
	
}
