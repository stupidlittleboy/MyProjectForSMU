package com.shmtu.myprojectforsmu.complany;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class ComplanyNotices extends Activity {

	private final static String NOTICE_URL = Constant.URL + "company_notice.php";
	private ListView lvNoticesInfo;
	private NoticesInfoAdapter noticeAdapter;
	private RequestQueue mQueue = null;

	public static void startComplanyNotices(Context context){
		Intent intent = new Intent(context, ComplanyNotices.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_notices);

		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getCompanyNotices();
	}
	
	//初始化控件
	private void init() {
		lvNoticesInfo = (ListView) findViewById(R.id.lv_notices_info);
		noticeAdapter = new NoticesInfoAdapter(this);
	}

	private void getCompanyNotices() {
		/*
		 * 想服务端发出请求
		 */
		//创建一个RequestQueue队列
		final ArrayList<HashMap<String, Object>> listNotice = new ArrayList<HashMap<String,Object>>();
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(NOTICE_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				/**
				 * 设置数据
				 */
				for (int i = 0; i < response.length(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					try {
						JSONObject jObj = (JSONObject) response.get(i);
						map.put("notice_theme", jObj.get("notice_theme"));
						map.put("notice_date", jObj.get("notice_date"));
						map.put("notice_content", jObj.get("notice_content"));
						map.put("notice_emp_no", jObj.get("notice_emp_no"));
						listNotice.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				noticeAdapter.setItemList(listNotice);
				noticeAdapter.notifyDataSetChanged();
				lvNoticesInfo.setAdapter(noticeAdapter);
				Log.e("TAG", response.toString());
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ComplanyNotices.this, "获取数据失败", Toast.LENGTH_SHORT).show();
			}
		});
		mQueue.add(jsonArrayRequest);
	}

	class NoticesInfoAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listNotice;

		public NoticesInfoAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
		}

		public void setItemList(ArrayList<HashMap<String, Object>> listNotice){
			this.listNotice = listNotice;
			Log.e("list2", this.listNotice.toString());
		}

		@Override
		public int getCount() {
			return listNotice.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder;
			if (convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.complany_notices_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvNoticesTitle = (TextView) convertView.findViewById(R.id.tv_notices_title);
				viewHolder.tvNoticesDate = (TextView) convertView.findViewById(R.id.tv_notices_date);
				viewHolder.tvNoticesContent = (TextView) convertView.findViewById(R.id.tv_notices_content);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tvNoticesTitle.setText(listNotice.get(position).get("notice_theme").toString());
			viewHolder.tvNoticesDate.setText(listNotice.get(position).get("notice_date").toString());
			viewHolder.tvNoticesContent.setText(listNotice.get(position).get("notice_content").toString());
			//给ListView的Item点击事件
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String noticeTheme = listNotice.get(position).get("notice_theme").toString();
					String noticeDate = listNotice.get(position).get("notice_date").toString();
					String noticeContent = listNotice.get(position).get("notice_content").toString();
					String noticeEmpNo = listNotice.get(position).get("notice_emp_no").toString();
					ComplanyNoticesDetial.startComplanyNoticesDetial(ComplanyNotices.this, noticeTheme, noticeDate, noticeContent, noticeEmpNo);
				}
			});
			return convertView;
		}

	}

	public final class ViewHolder{
		TextView tvNoticesTitle;
		TextView tvNoticesDate;
		TextView tvNoticesContent;
	}

}
