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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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

public class ComplanyNotices extends Activity implements OnItemClickListener {

	private final static String NOTICE_URL = Constant.URL + "company_notice.php";
	private ListView lvNoticesInfo;
	private NoticesInfoAdapter noticeAdapter;
	private RequestQueue mQueue = null;
	private JSONArray jsonArray = null;
	private ArrayList<HashMap<String, Object>> listNotice;
	
	public static void startComplanyNotices(Context context){
		Intent intent = new Intent(context, ComplanyNotices.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_notices);
		
		/*
		 * 想服务端发出请求
		 */
		//创建一个RequestQueue队列
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(NOTICE_URL, 
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						jsonArray = response;
						Toast.makeText(ComplanyNotices.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
						Log.e("TAG", jsonArray.toString());
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(ComplanyNotices.this, "获取数据失败", Toast.LENGTH_SHORT).show();
					}
				});
		mQueue.add(jsonArrayRequest);
		
		lvNoticesInfo = (ListView) findViewById(R.id.lv_notices_info);
		lvNoticesInfo.setOnItemClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		noticeAdapter = new NoticesInfoAdapter(getApplicationContext(), R.layout.complany_notices_item, jsonArray);
		lvNoticesInfo.setAdapter(noticeAdapter);
	}
	
	
	class NoticesInfoAdapter extends ArrayAdapter<Object>{

		private JSONArray jsonNotice;
		private int textViewResourceId;
		public NoticesInfoAdapter(Context context, int textViewResourceId,
				JSONArray jsonNotice) {
			super(context, textViewResourceId);
			this.textViewResourceId = textViewResourceId;
			this.jsonNotice = jsonNotice;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null){
				convertView = LayoutInflater.from(getApplicationContext()).inflate(textViewResourceId, null);
				viewHolder = new ViewHolder();
				viewHolder.tvNoticesTitle = (TextView) convertView.findViewById(R.id.tv_notices_title);
				viewHolder.tvNoticesDate = (TextView) convertView.findViewById(R.id.tv_notices_date);
				viewHolder.tvNoticesContent = (TextView) convertView.findViewById(R.id.tv_notices_content);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			/**
			 * 设置数据
			 */
			for (int i = 0; i < jsonNotice.length(); i++) {
				try {
					JSONObject jObj = (JSONObject) jsonNotice.get(i);
					viewHolder.tvNoticesTitle.setText(jObj.getString("notice_theme"));
					viewHolder.tvNoticesDate.setText(jObj.getString("notice_date"));
					viewHolder.tvNoticesContent.setText(jObj.getString("notice_content"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return convertView;
		}
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	class ViewHolder{
		TextView tvNoticesTitle;
		TextView tvNoticesDate;
		TextView tvNoticesContent;
	}
	
}
