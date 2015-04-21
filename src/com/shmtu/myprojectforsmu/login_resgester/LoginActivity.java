package com.shmtu.myprojectforsmu.login_resgester;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.MainActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private final static String LOGIN_URL = Constant.URL + "login.php";
	
	private EditText tvUsername;
	private EditText tvPassword;
	private Button btn_login;
	private TextView tvBackpass;
	private TextView tvNewuser;
	private RequestQueue mQueue = null;
	private JSONObject json = new JSONObject();

	public static void startLoginActivity (Context context, 
			String username, String password) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		tvUsername = (EditText) findViewById(R.id.username);
		tvPassword = (EditText) findViewById(R.id.password);
		tvBackpass = (TextView) findViewById(R.id.tv_backpass);
		tvNewuser = (TextView) findViewById(R.id.tv_newuser);
		btn_login = (Button) findViewById(R.id.btn_login);

		btn_login.setOnClickListener(this);
		tvBackpass.setOnClickListener(this);
		tvNewuser.setOnClickListener(this);
		
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");

		if(username != null && password != null){
			tvUsername.setText(username);
			tvPassword.setText(password);
		}
	}

	public void onClick(View v){
		int id = v.getId();
		switch(id){
		//登陆按钮点击事件
		case R.id.btn_login:
			/*//test
			Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent1);*/
			//将数据封装成json格式
			String userName = tvUsername.getText().toString().trim();
			String passWord = tvPassword.getText().toString().trim();
			try {
				json.put("UserName", userName);
				json.put("PassWord", passWord);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
//			SharePreferenceUtil spu = new SharePreferenceUtil(this, "login");
//			spu.saveSharedPreferences("userName", userName);
//			spu.saveSharedPreferences("passWord", passWord);
			
			//将用户名，密码信息保存
			SharedPreferences sp = getSharedPreferences("myProjectForSMU", MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("userName", userName);
			editor.putString("passWord", passWord);
			editor.commit();
			
			//创建一个RequestQueue队列
			mQueue = Volley.newRequestQueue(getApplicationContext());
			//向服务端发送请求
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, LOGIN_URL, json,  
					new Response.Listener<JSONObject>() {  
				@Override  
				public void onResponse(JSONObject response) {  
					Log.d("TAG", response.toString());  
//					Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
					try {
						int success = Integer.parseInt(response.getString("success"));
						if(success == 0){
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							startActivity(intent);
						}else{
							Toast.makeText(LoginActivity.this, "输入的用户名或密码有错", Toast.LENGTH_LONG).show();
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error);  
					Toast.makeText(LoginActivity.this, "网络连接出错，请检查网络状况！", Toast.LENGTH_LONG).show();
				}  
			});  

			mQueue.add(jsonObjectRequest);
			break;

			//找回密码
		case R.id.tv_backpass:
			break;

			//注册点击事件
		case R.id.tv_newuser:
			Intent intent = new Intent(LoginActivity.this, SMSRegisterActivity.class);
//			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
