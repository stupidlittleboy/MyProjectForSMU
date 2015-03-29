package com.shmtu.myprojectforsmu.login_resgester;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.MainActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.utils.HttpUtils;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private Handler handler;
	private EditText username;
	private EditText password;
	private Button btn_login;
	private TextView tvBackpass;
	private TextView tvNewuser;
	private PopupWindow mPopupWindow;
	private String url = Constant.URL + "login.php";
	private String url1 = Constant.URL + "json_array.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					try {
						String res = msg.getData().getString("res");
						JSONObject result = new JSONObject(res);
						int success = Integer.parseInt(result.getString("success"));
						Toast.makeText(LoginActivity.this, res + ":\n" +result.toString(), Toast.LENGTH_LONG).show();
						// TODO Auto-generated catch block
						if(success == 0){
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							startActivity(intent);
						}else{
							Toast.makeText(LoginActivity.this, "输入的用户名或密码有错", Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				default:
					break;
				}
			}
		};

		Bundle bundle = this.getIntent().getExtras();

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		tvBackpass = (TextView) findViewById(R.id.tv_backpass);
		tvNewuser = (TextView) findViewById(R.id.tv_newuser);
		btn_login = (Button) findViewById(R.id.btn_login);

		btn_login.setOnClickListener(this);
		tvBackpass.setOnClickListener(this);
		tvNewuser.setOnClickListener(this);

		if(bundle != null){
			username.setText(bundle.getString("empNo"));
			password.setText(bundle.getString("pass"));
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
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						JSONObject json = new JSONObject();
						json.put("UserName", username.getText().toString().trim());
						json.put("PassWord", password.getText().toString().trim());
						//						httpPostMethod(json);
						HttpUtils.httpPostMethod(url, json, handler);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.d("json", "解析JSON出错");
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			break;

			//找回密码
		case R.id.tv_backpass:
			getPopupWindowInstance();
			mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 50);
			break;

			//注册点击事件
		case R.id.tv_newuser:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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

	/*public void popupWindow(){
		View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

		mPopupWindow.getContentView().setFocusableInTouchMode(true);
		mPopupWindow.getContentView().setFocusable(true);
		mPopupWindow.getContentView().setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					if (mPopupWindow != null && mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					return true;
				}
				return false;
			}
		});
		if (mPopupWindow == null && !mPopupWindow.isShowing()) {
			mPopupWindow.showAtLocation(findViewById(R.id.action_settings), Gravity.CENTER, 0, 0);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
			if (mPopupWindow != null && !mPopupWindow.isShowing()) {
				mPopupWindow.showAtLocation(findViewById(R.id.action_settings), Gravity.BOTTOM, 0, 0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}*/
	
	/*
	 * 获取PopupWindow实例
	 */
	private void getPopupWindowInstance() {
		if (null != mPopupWindow) {
			mPopupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		
		View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

		
	}
}
