package com.shmtu.myprojectforsmu.task;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class TaskDetail extends Activity implements OnClickListener {

	private final static String TASK_DETAIL_URL = Constant.URL + "get_task.php";

	private TextView tvTaskDetailTitle;	//标题
	private TextView tvTaskDetailDate;	//时间
	private TextView tvTaskDetailContent;	//内容
	private TextView tvTaskDetailNo;
	private TextView tvTaskDetailCustomerNo;
	private TextView tvTaskDetailEmp;
	private LinearLayout layoutTaskFlag;	//任务领取标识
	private Button btnTaskFlag;		//领取任务
	private RequestQueue mQueue = null;
	private JSONObject json = null;

	public static void startTaskDetail(Context context, String taskNo, String taskTitle,
			String taskContent, String taskCustomerNo, String taskFlag, 
			String taskEmpNo, String taskDate){
		Intent intent = new Intent(context, TaskDetail.class);
		intent.putExtra("task_no", taskNo);
		intent.putExtra("task_title", taskTitle);
		intent.putExtra("task_content", taskContent);
		intent.putExtra("task_customer_no", taskCustomerNo);
		intent.putExtra("task_flag", taskFlag);
		intent.putExtra("task_emp_no", taskEmpNo);
		intent.putExtra("task_date", taskDate);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail);
		
		mQueue = Volley.newRequestQueue(getApplicationContext());

		tvTaskDetailTitle = (TextView) findViewById(R.id.tv_task_detail_title);
		tvTaskDetailDate = (TextView) findViewById(R.id.tv_task_detail_date);
		tvTaskDetailContent = (TextView) findViewById(R.id.tv_task_detail_content);
		tvTaskDetailNo = (TextView) findViewById(R.id.tv_task_detail_no);
		tvTaskDetailCustomerNo = (TextView) findViewById(R.id.tv_task_detail_customer_no);
		tvTaskDetailEmp = (TextView) findViewById(R.id.tv_task_detail_emp);
		layoutTaskFlag = (LinearLayout) findViewById(R.id.layout_task_detail_flag);
		btnTaskFlag = (Button) findViewById(R.id.btn_task_detail_flag);

		layoutTaskFlag.setOnClickListener(this);
		btnTaskFlag.setOnClickListener(this);
		Intent intent = getIntent();
		Log.e("intent", intent.toString());
		tvTaskDetailTitle.setText(intent.getStringExtra("task_title"));
		tvTaskDetailDate.setText("任务发布日期：" + intent.getStringExtra("task_date"));
		tvTaskDetailContent.setText(intent.getStringExtra("task_content"));
		tvTaskDetailNo.setText("任务编号：" + intent.getStringExtra("task_no"));
		tvTaskDetailCustomerNo.setText("客户编号:" + intent.getStringExtra("task_customer_no"));
		tvTaskDetailEmp.setText("任务领取人：" + intent.getStringExtra("task_emp_no"));

		if("0".equals(intent.getStringExtra("task_flag"))){
			layoutTaskFlag.setVisibility(View.GONE);
		} else {
			btnTaskFlag.setVisibility(View.GONE);
		}
		try {
			json = new JSONObject();
			SharedPreferences sp = getSharedPreferences("myProjectForSMU", 0);
			json.put("taskNo", intent.getStringExtra("task_no"));
			json.put("userName", sp.getString("userName", null));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_task_detail_flag:
			Toast.makeText(TaskDetail.this, "显示详细信息", 0).show();
			break;

		case R.id.btn_task_detail_flag:
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(TASK_DETAIL_URL, 
					json, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					try {
						if (Integer.parseInt(response.getString("success")) == 1) {
							Toast.makeText(TaskDetail.this, "你已成功领取任务", 0).show();
							btnTaskFlag.setVisibility(View.GONE);
							layoutTaskFlag.setVisibility(View.VISIBLE);
							tvTaskDetailEmp.setText("任务领取人：" + response.getString("emp_no"));
						} else {
							Toast.makeText(TaskDetail.this, "领取任务失败，请耐心等待5秒后再尝试！", 0).show();
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
					Toast.makeText(TaskDetail.this, "网络连接失败", 0).show();
				}
			});
			mQueue.add(jsonObjectRequest);
			break;

		default:
			break;
		}
	}

}
