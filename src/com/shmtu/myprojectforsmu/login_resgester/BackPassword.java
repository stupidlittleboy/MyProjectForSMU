package com.shmtu.myprojectforsmu.login_resgester;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class BackPassword extends Activity implements OnClickListener {

	private final static String BACK_PASSWORD_URL = Constant.URL + "back_password.php";
	
	private EditText etBackEmpNo;
	private EditText etBackPhoneNo;
	private EditText etBackIdentify;
	private Button btnBackSubmit;
	private Button btnBackReset;
	
	private RequestQueue mQueue = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.back_password);
		
		init();
	}
	
	private void init () {
		etBackEmpNo = (EditText) findViewById(R.id.et_back_emp_no);
		etBackPhoneNo = (EditText) findViewById(R.id.et_back_phone_no);
		etBackIdentify = (EditText) findViewById(R.id.et_back_identity);
		btnBackSubmit = (Button) findViewById(R.id.btn_back_submit);
		btnBackReset = (Button) findViewById(R.id.btn_back_reset);
		
		btnBackSubmit.setOnClickListener(this);
		btnBackReset.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_back_submit:
			if (chkEdit()) {
				JSONObject json = new JSONObject();
				try {
					json.put("empNo", etBackEmpNo.getText().toString());
					json.put("phoneNo", etBackPhoneNo.getText().toString());
					json.put("identify", etBackIdentify.getText().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				mQueue = Volley.newRequestQueue(getApplicationContext());
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BACK_PASSWORD_URL,
						json, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							dialog(response.get("emp_password").toString());
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
			}
			
			break;
			
		case R.id.btn_back_reset:
			
			break;

		default:
			break;
		}
	}
	
	private boolean chkEdit () {
		if (etBackEmpNo.getText().toString() == null 
				|| "".equals(etBackEmpNo.getText().toString())) {
			etBackEmpNo.requestFocus();
			Toast.makeText(this, "员工号不能为空！", Toast.LENGTH_SHORT).show();
			return false;
		} else if (etBackPhoneNo.getText().toString() == null
				|| "".equals(etBackPhoneNo.getText().toString())) {
			etBackPhoneNo.requestFocus();
			Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
			return false;
		} else if (etBackIdentify.getText().toString() == null
				|| "".equals(etBackIdentify.getText().toString())) {
			etBackIdentify.requestFocus();
			Toast.makeText(this, "身份证号不能为空！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	/**
	 * 弹出对话框
	 * @param username	用户名
	 * @param password	密码
	 */
	protected void dialog(String password) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("您的登陆密码为：" + password);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(BackPassword.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
}
