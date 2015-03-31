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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_notices);
		
		lvNoticesInfo = (ListView) findViewById(R.id.lv_notices_info);
		noticeAdapter = new NoticesInfoAdapter(this, listNotice);
		lvNoticesInfo.setOnItemClickListener(this);

		/*
		 * 想服务端发出请求
		 */
		//创建一个RequestQueue队列
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(NOTICE_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				/**
				 * 设置数据
				 */
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < 10; i++) {
					try {
						JSONObject jObj = (JSONObject) response.get(i);
						/*map.put("notice_theme", jObj.get("notice_theme"));
								map.put("notice_date", jObj.get("notice_date"));
								map.put("notice_content", jObj.get("notice_content"));*/

						map.put("notice_theme", "这是主题"+i);
						map.put("notice_date", "2015-03-31");
						map.put("notice_content", "这是内容"+i);

						listNotice.add(map);
						//								map.clear();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				lvNoticesInfo.setAdapter(noticeAdapter);
				Toast.makeText(ComplanyNotices.this, response.toString(), Toast.LENGTH_SHORT).show();
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

		public NoticesInfoAdapter(Context context, ArrayList<HashMap<String, Object>> listNotice){
			mLayoutInflater = LayoutInflater.from(context);
			this.listNotice = listNotice;
		}

		@Override
		public int getCount() {
			return 0;
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
		public View getView(int position, View convertView, ViewGroup parent) {
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
			return convertView;
		}

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	public final class ViewHolder{
		TextView tvNoticesTitle;
		TextView tvNoticesDate;
		TextView tvNoticesContent;
	}

}
