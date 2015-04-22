package com.shmtu.myprojectforsmu.setting;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.shmtu.myprojectforsmu.login_resgester.LoginActivity;

public class SettingChangePass extends Activity implements
OnClickListener{

	private final static String CHANGE_PASS_URL = Constant.URL + "change_pass.php";

	private EditText etChangePassUsername;
	private EditText etChangePassOldPass;
	private EditText etChangePassNewPass;
	private EditText etChangePassConfirmPass;
	private Button btnChangePassSubmit;
	private Button btnChangePassReset;

	private RequestQueue mQueue = null;

	public static void startSettingChangePass (Context context) {
		Intent intent = new Intent(context, SettingChangePass.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_change_pass);

		init();
	}

	private void init () {
		etChangePassUsername = (EditText) findViewById(R.id.et_chang_pass_username);
		etChangePassOldPass = (EditText) findViewById(R.id.et_chang_pass_old_pass);
		etChangePassNewPass = (EditText) findViewById(R.id.et_chang_pass_new_pass);
		etChangePassConfirmPass = (EditText) findViewById(R.id.et_chang_pass_confirm_pass);
		btnChangePassSubmit = (Button) findViewById(R.id.btn_change_pass_submit);
		btnChangePassReset = (Button) findViewById(R.id.btn_change_pass_reset);

		SharedPreferences sp = getSharedPreferences("myProjectForSMU", MODE_PRIVATE);
		String username = sp.getString("userName", null);
		Log.e("sp", username + ":");

		if (!(username == null || "".equals(username))) {
			etChangePassUsername.setText(username);
			etChangePassOldPass.requestFocus();
		}

		btnChangePassSubmit.setOnClickListener(this);
		btnChangePassReset.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_change_pass_submit:
			final String username = etChangePassUsername.getText().toString().trim();
			final String password = etChangePassNewPass.getText().toString().trim();
			if (!password.
					equals(etChangePassConfirmPass.getText().toString().trim())) {
				Toast.makeText(SettingChangePass.this, "两次输入的密码不正确，请重新输入！", 
						Toast.LENGTH_SHORT).show();
				etChangePassNewPass.getText().clear();
				etChangePassConfirmPass.getText().clear();
				etChangePassNewPass.requestFocus();
				break;
			} 
			try {
				JSONObject json = new JSONObject();
				json.put("userName", username);
				json.put("oldPass", etChangePassOldPass.getText().toString().trim());
				json.put("newPass", password);

				mQueue = Volley.newRequestQueue(getApplicationContext());
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(CHANGE_PASS_URL, json, 
						new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							int success = Integer.parseInt(response.getString("success").toString().trim());
							if (success == 1) {
								dialog(username, password);
							} else {
								Toast.makeText(SettingChangePass.this, "请仔细核对用户名和密码是否正确！", Toast.LENGTH_SHORT).show();
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		case R.id.btn_change_pass_reset:
			etChangePassOldPass.getText().clear();
			etChangePassNewPass.getText().clear();
			etChangePassConfirmPass.getText().clear();
			etChangePassOldPass.requestFocus();
			break;

		default:
			break;
		}
	}

	protected void dialog(final String username, final String password) {
		AlertDialog.Builder builder = new Builder(SettingChangePass.this);
		builder.setMessage("密码已修改成功，请重新登录！");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				LoginActivity.startLoginActivity(SettingChangePass.this, username, password);
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

}
