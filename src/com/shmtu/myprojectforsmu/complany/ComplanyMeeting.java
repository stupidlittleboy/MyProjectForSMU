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

public class ComplanyMeeting extends Activity {

	private final static String MEETING_INFO_URL = Constant.URL + "meeting_info.php";
	private ListView lvCompanyMeeting;
	private CompanyMeetingAdapter companyMeetingAdapter;
	private RequestQueue mQueue = null;

	public static void startComplanyMeeting(Context context){
		Intent intent = new Intent(context, ComplanyMeeting.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_meeting);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getCompanyMeeting();
	}

	//初始化控件
	private void init() {
		lvCompanyMeeting = (ListView) findViewById(R.id.lv_company_meeting);
		companyMeetingAdapter = new CompanyMeetingAdapter(this);
	}

	private void getCompanyMeeting() {
		final ArrayList<HashMap<String, Object>> listCompanyMeeting = new ArrayList<HashMap<String,Object>>();
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MEETING_INFO_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				for (int i = 0; i < response.length(); i++) {

					try {
						JSONObject jObj = response.getJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("meeting_theme", jObj.get("meeting_theme").toString());
						map.put("meeting_content", jObj.get("meeting_content").toString());
						map.put("meeting_address", jObj.get("meeting_address").toString());
						map.put("meeting_start", jObj.get("meeting_start").toString());
						map.put("meeting_end", jObj.get("meeting_end").toString());
						listCompanyMeeting.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				companyMeetingAdapter.setItemList(listCompanyMeeting);
				lvCompanyMeeting.setAdapter(companyMeetingAdapter);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mQueue.add(jsonArrayRequest);
	}

	class CompanyMeetingAdapter extends BaseAdapter {

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listCompanyMeeting;

		public CompanyMeetingAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		public void setItemList(ArrayList<HashMap<String, Object>> listCompanyMeeting){
			this.listCompanyMeeting = listCompanyMeeting;
			Log.e("list2", this.listCompanyMeeting.toString());
		}

		@Override
		public int getCount() {
			return listCompanyMeeting.size();
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
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.complany_meeting_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvMeetingTheme = (TextView) convertView.findViewById(R.id.tv_meeting_theme);
				viewHolder.tvMeetingStarttime = (TextView) convertView.findViewById(R.id.tv_meeting_starttime);
				viewHolder.tvMeetingContent = (TextView) convertView.findViewById(R.id.tv_meeting_content);
				viewHolder.tvMeetingAddress = (TextView) convertView.findViewById(R.id.tv_meeting_address);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			if (listCompanyMeeting.size() > 0) {
				viewHolder.tvMeetingTheme.setText(listCompanyMeeting.get(position).get("meeting_theme").toString());
				viewHolder.tvMeetingStarttime.setHint(listCompanyMeeting.get(position).get("meeting_start").toString());
				viewHolder.tvMeetingContent.setHint(listCompanyMeeting.get(position).get("meeting_content").toString());
				viewHolder.tvMeetingAddress.setHint(listCompanyMeeting.get(position).get("meeting_address").toString());
				
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String meetingTheme = listCompanyMeeting.get(position).get("meeting_theme").toString();
						String meetingStarttime = listCompanyMeeting.get(position).get("meeting_start").toString();
						String meetingEndtime = listCompanyMeeting.get(position).get("meeting_end").toString();
						String meetingAddress = listCompanyMeeting.get(position).get("meeting_address").toString();
						String meetingContent = listCompanyMeeting.get(position).get("meeting_content").toString();
						ComplanyMeetingDetail.startComplanyMeetingDetail(ComplanyMeeting.this, meetingTheme, 
								meetingStarttime, meetingEndtime, meetingAddress, meetingContent);
						Toast.makeText(getApplicationContext(), "点击", Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				Toast.makeText(getApplicationContext(), "暂时无会议信息", Toast.LENGTH_SHORT).show();
			}
			return convertView;
		}

	}

	private class ViewHolder {
		TextView tvMeetingTheme;
		TextView tvMeetingStarttime;
		TextView tvMeetingContent;
		TextView tvMeetingAddress;
	}

}
