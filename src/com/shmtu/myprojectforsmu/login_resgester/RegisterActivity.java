package com.shmtu.myprojectforsmu.login_resgester;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.utils.CheckNullUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private final static String REGISTER_URL = Constant.URL + "register.php";

	private EditText etRegisterEmpNickname;
	private EditText etRegisterEmpNo;
	private EditText etRegisterEmpName;
	private EditText etRegisterPass;
	private EditText etRegisterPassConfirm;
	private Button btnRegisterSubmit;
	private Button btnRegisterReset;

	private RequestQueue mQueue = null;

	public static void startRegisterActivity (Context context, String phoneNo){
		Intent intent = new Intent(context, RegisterActivity.class);
		intent.putExtra("empPhoneNo", phoneNo);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		init();
	}

	private void init() {
		etRegisterEmpNickname = (EditText) findViewById(R.id.et_register_emp_nickname);
		etRegisterEmpNo = (EditText) findViewById(R.id.et_register_emp_no);
		etRegisterEmpName = (EditText) findViewById(R.id.et_register_emp_name);
		etRegisterPass = (EditText) findViewById(R.id.et_register_pass);
		etRegisterPassConfirm = (EditText) findViewById(R.id.et_register_pass_confirm);
		btnRegisterSubmit = (Button) findViewById(R.id.btn_register_submit);
		btnRegisterReset = (Button) findViewById(R.id.btn_register_reset);
		btnRegisterSubmit.setOnClickListener(this);
		btnRegisterReset.setOnClickListener(this);
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
		case R.id.btn_register_submit:

			if (CheckNullUtil.checkIsNull(etRegisterEmpNickname)) {
				Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
				break;
			} else if (CheckNullUtil.checkIsNull(etRegisterEmpNo)) {
				Toast.makeText(RegisterActivity.this, "员工号不能为空", Toast.LENGTH_SHORT).show();
				break;
			}else if (CheckNullUtil.checkIsNull(etRegisterEmpName)) {
				Toast.makeText(RegisterActivity.this, "员工姓名不能为空", Toast.LENGTH_SHORT).show();
				break;
			} else if (CheckNullUtil.checkIsNull(etRegisterPass)) {
				Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				break;
			} else if (CheckNullUtil.checkIsNull(etRegisterPassConfirm)) {
				Toast.makeText(RegisterActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
				break;
			} else if (!etRegisterPass.getText().toString().trim()
					.equals(etRegisterPassConfirm.getText().toString().trim())) {
				Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
				etRegisterPass.getText().clear();
				etRegisterPassConfirm.getText().clear();
				break;
			} else {
				try {
					JSONObject json = new JSONObject();
					Intent intent = getIntent();
					json.put("empPhoneNo", intent.getStringExtra("empPhoneNo"));
					json.put("empNickname", etRegisterEmpNickname.getText().toString().trim());
					json.put("empNo", etRegisterEmpNo.getText().toString().trim());
					json.put("empName", etRegisterEmpName.getText().toString().trim());
					json.put("pass", etRegisterPass.getText().toString().trim());
					empRegister(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			break;

		case R.id.btn_register_reset:
			etRegisterEmpNickname.getText().clear();
			etRegisterEmpNo.getText().clear();
			etRegisterPass.getText().clear();
			etRegisterPassConfirm.getText().clear();
			etRegisterEmpNickname.requestFocus();
			break;
		}
	}

	private void empRegister(final JSONObject json) {
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(REGISTER_URL, json, 
				new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				try {
					int	success = Integer.parseInt(response.getString("success"));
					if (success == 0) {
						String empNickname = json.getString("empNickname");
						String pass = json.getString("pass");
						LoginActivity.startLoginActivity(RegisterActivity.this, empNickname, pass);
						Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(RegisterActivity.this, "注册失败,请联系管理员…", Toast.LENGTH_SHORT).show();
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
				Log.e("TAG", error.getMessage(), error);
			}
		});
		mQueue.add(jsonObjectRequest);
	}
}
