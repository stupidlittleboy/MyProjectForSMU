package com.shmtu.myprojectforsmu.task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

public class TaskDetail extends BaseActivity implements OnClickListener {

	private final static String TASK_DETAIL_URL = Constant.URL + "get_task.php";
	private final static String CANCEL_TASK_URL = Constant.URL + "cancel_task.php";

	private TextView tvTaskDetailNo;
	private TextView tvTaskDetailName;
	private TextView tvTaskDetailSex;
	private TextView tvTaskDetailPhoneNo;
	private TextView tvTaskDetailDate;	//时间
	private TextView tvTaskDetailPeriod;
	private TextView tvTaskDetailAddress;
	private TextView tvTaskDetailComplete;
	private Button btnTaskDetailCancel;
	private Button btnTaskDetailOk;
	private RequestQueue mQueue = null;
	private JSONObject json = null;

	/**
	 * 
	 * @param context
	 * @param roomerNo	客户编号
	 * @param roomerName	客户姓名
	 * @param roometSex	客户性别
	 * @param roomerPhoneNo	客户联系方式
	 * @param roomerHouseNo	房源编号
	 * @param roomerDate	看房日期
	 * @param roomerPeriod	看房时间段
	 * @param roomerRent	出租或出售
	 * @param roomerComplete 交易是否完成
	 * @param roomerEmpNo	跟进人
	 * @param roomerHouseCity	房子所在城市
	 * @param roomerHouseAddress	房子详细地址
	 */
	public static void startTaskDetail(Context context, String roomerNo, String roomerName,
			String roometSex, String roomerPhoneNo, String roomerHouseNo, 
			String roomerDate, String roomerPeriod, String roomerRent, String roomerComplete, 
			String roomerEmpNo, String roomerHouseCity, String roomerHouseAddress){
		Intent intent = new Intent(context, TaskDetail.class);
		intent.putExtra("roomerNo", roomerNo);
		intent.putExtra("roomerName", roomerName);
		intent.putExtra("roometSex", roometSex);
		intent.putExtra("roomerPhoneNo", roomerPhoneNo);
		intent.putExtra("roomerHouseNo", roomerHouseNo);
		intent.putExtra("roomerDate", roomerDate);
		intent.putExtra("roomerPeriod", roomerPeriod);
		intent.putExtra("roomerRent", roomerRent);
		intent.putExtra("roomerComplete", roomerComplete);
		intent.putExtra("roomerEmpNo", roomerEmpNo);
		intent.putExtra("roomerHouseCity", roomerHouseCity);
		intent.putExtra("roomerHouseAddress", roomerHouseAddress);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail);
		mQueue = Volley.newRequestQueue(getApplicationContext());
		init();

	}

	private void init() {
		tvTaskDetailNo = (TextView) findViewById(R.id.tv_task_detail_no);
		tvTaskDetailName = (TextView) findViewById(R.id.tv_task_detail_name);
		tvTaskDetailSex = (TextView) findViewById(R.id.tv_task_detail_sex);
		tvTaskDetailPhoneNo = (TextView) findViewById(R.id.tv_task_detail_phone_no);
		tvTaskDetailDate = (TextView) findViewById(R.id.tv_task_detail_date);
		tvTaskDetailPeriod = (TextView) findViewById(R.id.tv_task_detail_period);
		tvTaskDetailAddress = (TextView) findViewById(R.id.tv_task_detail_address);
		tvTaskDetailComplete = (TextView) findViewById(R.id.tv_task_detail_complete);
		btnTaskDetailCancel = (Button) findViewById(R.id.btn_task_detail_cancel);
		btnTaskDetailOk = (Button) findViewById(R.id.btn_task_detail_ok);

		Intent intent = getIntent();
		Log.e("intent", intent.toString());
		tvTaskDetailNo.setText("客户编号：" + intent.getStringExtra("roomerNo"));
		tvTaskDetailName.setText("客户姓名：" + intent.getStringExtra("roomerName"));
		tvTaskDetailSex.setText("客户性别：" + intent.getStringExtra("roometSex"));
		tvTaskDetailPhoneNo.setText("联系方式：" + intent.getStringExtra("roomerPhoneNo"));
		tvTaskDetailDate.setText("看房日期：" + intent.getStringExtra("roomerDate"));
		switch (Integer.parseInt(intent.getStringExtra("roomerPeriod"))) {
		case 1:
			tvTaskDetailPeriod.setText("看房时间：9:30~11:30");
			break;

		case 2:
			tvTaskDetailPeriod.setText("看房时间：13:30~15:30");
			break;

		case 3:
			tvTaskDetailPeriod.setText("看房时间：15:30~17:30");
			break;

		case 4:
			tvTaskDetailPeriod.setText("看房时间：18:30~20:30");
			break;

		default:
			break;
		}
		tvTaskDetailAddress.setText("详细地址：" + intent.getStringExtra("roomerHouseAddress"));
		
		tvTaskDetailAddress.setOnClickListener(this);
		btnTaskDetailCancel.setOnClickListener(this);
		btnTaskDetailOk.setOnClickListener(this);
		if("".equals(intent.getStringExtra("roomerEmpNo"))){
			btnTaskDetailCancel.setVisibility(View.GONE);
		} else {
			tvTaskDetailComplete.setVisibility(View.VISIBLE);
			if (Integer.parseInt(intent.getStringExtra("roomerComplete").toString()) == 0) {
				tvTaskDetailComplete.setText("正在跟进中…");
			} else {
				tvTaskDetailComplete.setText("交易已完成！");
				btnTaskDetailCancel.setVisibility(View.GONE);
				btnTaskDetailOk.setVisibility(View.GONE);
			}
			btnTaskDetailOk.setVisibility(View.GONE);
		}
		try {
			json = new JSONObject();
			SharedPreferences sp =getSharedPreferences("myProjectForSMU", MODE_PRIVATE);
			json.put("userName", sp.getString("userName", ""));
			json.put("roomerNo", intent.getStringExtra("roomerNo"));
			json.put("roomerHouseNo", intent.getStringExtra("roomerHouseNo"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_task_detail_address:
			Toast.makeText(TaskDetail.this, "地图功能", Toast.LENGTH_SHORT).show();
			break;
		
		case R.id.btn_task_detail_cancel:
			JsonObjectRequest jsonObjectRequestCancel = new JsonObjectRequest(CANCEL_TASK_URL, json, 
					new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							try {
								if (Integer.parseInt(response.getString("success")) == 1) {
									Toast.makeText(TaskDetail.this, "任务已取消", Toast.LENGTH_SHORT).show();
									tvTaskDetailComplete.setVisibility(View.GONE);
									btnTaskDetailOk.setVisibility(View.VISIBLE);
									btnTaskDetailCancel.setVisibility(View.GONE);
								} else {
									Toast.makeText(TaskDetail.this, "取消任务失败，请稍后再试…", Toast.LENGTH_SHORT).show();
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
							Toast.makeText(TaskDetail.this, "任务取消失败，请耐心等待5秒后再试！", Toast.LENGTH_SHORT).show();
						}
					});
			mQueue.add(jsonObjectRequestCancel);
			break;

		case R.id.btn_task_detail_ok:
			JsonObjectRequest jsonObjectRequestOk = new JsonObjectRequest(TASK_DETAIL_URL, 
					json, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					try {
						if (Integer.parseInt(response.getString("success")) == 1) {
							Toast.makeText(TaskDetail.this, "你已成功领取任务", Toast.LENGTH_SHORT).show();
							tvTaskDetailComplete.setVisibility(View.VISIBLE);
							tvTaskDetailComplete.setText("正在跟进中…");
							btnTaskDetailOk.setVisibility(View.GONE);
							btnTaskDetailCancel.setVisibility(View.VISIBLE);
						} else {
							Toast.makeText(TaskDetail.this, "领取任务失败，请耐心等待5秒后再尝试！", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(TaskDetail.this, "网络连接失败", Toast.LENGTH_SHORT).show();
				}
			});
			mQueue.add(jsonObjectRequestOk);
			break;

		default:
			break;
		}
	}

}
