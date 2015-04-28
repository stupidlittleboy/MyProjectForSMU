package com.shmtu.myprojectforsmu.setting;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.task.TaskDetail;

public class SettingTaskInfo extends BaseActivity {

	private final static String PER_TASK_INFO_URL = Constant.URL + "task_info.php";

	private ListView lvSettingTaskInfo;
	private SettingTaskManaAdapter settingTaskManaAdapter = null;
	private RequestQueue mQueue = null;


	public static void startSettingTaskInfo(Context context, String empNo){
		Intent intent = new Intent(context, SettingTaskInfo.class);
		intent.putExtra("empNo", empNo);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_task_info);

		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sendToServer(this.getApplicationContext());
	}

	private void init () {
		lvSettingTaskInfo = (ListView) findViewById(R.id.lv_setting_task_info);
		settingTaskManaAdapter = new SettingTaskManaAdapter(this);
	}

	private void sendToServer(Context context){

		final ArrayList<HashMap<String, Object>> listTask = new ArrayList<HashMap<String,Object>>();
		/*
		 * 想服务端发出请求
		 */
		//创建一个RequestQueue队列
		mQueue = Volley.newRequestQueue(context);
		try {
			Intent intent = getIntent();
			JSONObject json = new JSONObject();

			json.put("empNo", intent.getStringExtra("empNo"));

			JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(PER_TASK_INFO_URL, 
					json, new Listener<JSONArray>() {

				@Override
				public void onResponse(JSONArray response) {
					/**
					 * 设置数据
					 */
					Log.e("TAG", response.toString());
					for (int i = 0; i < response.length(); i++) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						try {
							JSONObject jObj = (JSONObject) response.get(i);
							map.put("roomer_no", jObj.get("roomer_no"));
							map.put("roomer_name", jObj.get("roomer_name"));
							map.put("roomer_sex", jObj.get("roomer_sex"));
							map.put("roomer_phone_no", jObj.get("roomer_phone_no"));
							map.put("roomer_house_no", jObj.get("roomer_house_no"));
							map.put("roomer_date", jObj.get("roomer_date"));
							map.put("roomer_period", jObj.get("roomer_period"));
							map.put("roomer_rent", jObj.get("roomer_rent"));
							map.put("roomer_complete", jObj.get("roomer_complete"));
							map.put("roomer_emp_no", jObj.get("roomer_emp_no"));
							map.put("house_city", jObj.get("house_city"));
							map.put("house_address", jObj.get("house_address"));
							listTask.add(map);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					settingTaskManaAdapter.setItemList(listTask);
					settingTaskManaAdapter.notifyDataSetChanged();
					lvSettingTaskInfo.setAdapter(settingTaskManaAdapter);
					//				Toast.makeText(ComplanyNotices.this, response.toString(), Toast.LENGTH_SHORT).show();
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(SettingTaskInfo.this, "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			});
			mQueue.add(jsonArrayRequest);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	class SettingTaskManaAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listTask;

		public SettingTaskManaAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
			//			taskManaAdapter.
		}

		public void setItemList(ArrayList<HashMap<String, Object>> listTask){
			this.listTask = listTask;
		}

		@Override
		public int getCount() {
			return listTask.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder;
			if (convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.task_mana_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvTaskRent = (TextView) convertView.findViewById(R.id.tv_task_rent);
				viewHolder.tvTaskDate = (TextView) convertView.findViewById(R.id.tv_task_date);
				viewHolder.tvTaskPeriod = (TextView) convertView.findViewById(R.id.tv_task_period);
				viewHolder.tvTaskAddress = (TextView) convertView.findViewById(R.id.tv_task_address);
//				viewHolder.tvTaskFlag = (TextView) convertView.findViewById(R.id.tv_task_flag);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			if (Integer.parseInt(listTask.get(position).get("roomer_rent").toString()) == 0) {
				viewHolder.tvTaskRent.setText("租房");
			} else {
				viewHolder.tvTaskRent.setText("买房");
			}
			viewHolder.tvTaskDate.setText("看房日期：" + listTask.get(position).get("roomer_date").toString());
			Log.e("rent", listTask.get(position).get("roomer_rent").toString());
			switch (Integer.parseInt(listTask.get(position).get("roomer_period").toString())) {
			case 1:
				viewHolder.tvTaskPeriod.setHint("9:30~11:30");
				break;

			case 2:
				viewHolder.tvTaskPeriod.setHint("13:30~15:30");
				break;

			case 3:
				viewHolder.tvTaskPeriod.setHint("15:30~17:30");
				break;

			case 4:
				viewHolder.tvTaskPeriod.setHint("18:30~20:30");
				break;

			default:
				break;
			}
			viewHolder.tvTaskAddress.setText(listTask.get(position).get("house_address").toString());
			/*if ("".equals(listTask.get(position).get("roomer_emp_no"))){
				viewHolder.tvTaskFlag.setText("可领取");
			} else {
				viewHolder.tvTaskFlag.setText("任务已被" + listTask.get(position).get("roomer_emp_no").toString().trim() + "领取，点击查看详情");
				//				viewHolder.tvTaskFlag.setBackgroundColor(R.color.gray_bg);
			}
*/
			//给ListView的Item点击事件
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					String roomerNo = listTask.get(position).get("roomer_no").toString();
					String roomerName = listTask.get(position).get("roomer_name").toString();
					String roomerSex = listTask.get(position).get("roomer_sex").toString();
					String roomerPhoneNo = listTask.get(position).get("roomer_phone_no").toString();
					String roomerHouseNo = listTask.get(position).get("roomer_house_no").toString();
					String roomerDate = listTask.get(position).get("roomer_date").toString();
					String roomerPeriod = listTask.get(position).get("roomer_period").toString();
					String roomerRent = listTask.get(position).get("roomer_rent").toString();
					String roomerComplete = listTask.get(position).get("roomer_complete").toString();
					String roomerEmpNo = listTask.get(position).get("roomer_emp_no").toString();
					String roomerHouseCity = listTask.get(position).get("house_city").toString();
					String roomerHouseAddress = listTask.get(position).get("house_address").toString();
					TaskDetail.startTaskDetail(SettingTaskInfo.this, roomerNo, roomerName, roomerSex, 
							roomerPhoneNo, roomerHouseNo, roomerDate, roomerPeriod, roomerRent, 
							roomerComplete, roomerEmpNo, roomerHouseCity, roomerHouseAddress);

				}
			});
			return convertView;
		}

	}

	public final class ViewHolder{
		TextView tvTaskRent;
		TextView tvTaskDate;
		TextView tvTaskPeriod;
		TextView tvTaskAddress;
		TextView tvTaskFlag;
	}

}
