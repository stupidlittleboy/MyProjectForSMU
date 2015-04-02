package com.shmtu.myprojectforsmu.task;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.complany.ComplanyNotices;
import com.shmtu.myprojectforsmu.complany.ComplanyNoticesDetial;
import com.shmtu.myprojectforsmu.complany.ComplanyNotices.ViewHolder;

public class TaskManaFragment extends Fragment {

	private final static String NOTICE_URL = Constant.URL + "company_notice.php";
	
	private ListView lvTaskInfo;
	
	private RequestQueue mQueue = null;
	private ArrayList<HashMap<String, Object>> listTask = new ArrayList<HashMap<String,Object>>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.task_mana_layout, container,
				false);
		return newsLayout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		sendToServer(getActivity(), listTask);
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		lvTaskInfo = (ListView) getActivity().findViewById(R.id.lv_task_info);
	}
	
	private void sendToServer(Context context, final ArrayList<HashMap<String, Object>> listTask){
		
		final TaskManaAdapter taskManaAdapter = new TaskManaAdapter(context, listTask);
		/*
		 * 想服务端发出请求
		 */
		//创建一个RequestQueue队列
		mQueue = Volley.newRequestQueue(context);
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(NOTICE_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				/**
				 * 设置数据
				 */
				for (int i = 0; i < 10; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					try {
						JSONObject jObj = (JSONObject) response.get(i);
						map.put("notice_theme", jObj.get("notice_theme"));
						map.put("notice_date", jObj.get("notice_date"));
						map.put("notice_content", jObj.get("notice_content"));
						map.put("notice_emp_no", jObj.get("notice_emp_no"));

						/*map.put("notice_theme", "这是主题"+i);
						map.put("notice_date", "2015-03-31");
						map.put("notice_content", "这是内容"+i);
						 */
						listTask.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				lvTaskInfo.setAdapter(taskManaAdapter);
//				Toast.makeText(ComplanyNotices.this, response.toString(), Toast.LENGTH_SHORT).show();
				Log.e("TAG", response.toString());
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
			}
		});
		mQueue.add(jsonArrayRequest);
	}
	
	class TaskManaAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listTask;

		public TaskManaAdapter(Context context, ArrayList<HashMap<String, Object>> listTask){
			mLayoutInflater = LayoutInflater.from(context);
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

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder;
			if (convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.task_mana_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvTaskTitle = (TextView) convertView.findViewById(R.id.tv_task_title);
				viewHolder.tvTaskDate = (TextView) convertView.findViewById(R.id.tv_task_date);
				viewHolder.tvTaskContent = (TextView) convertView.findViewById(R.id.tv_task_content);
				viewHolder.tvTaskFlag = (TextView) convertView.findViewById(R.id.tv_task_flag);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tvTaskTitle.setText(listTask.get(position).get("notice_theme").toString());
			viewHolder.tvTaskDate.setText(listTask.get(position).get("notice_date").toString());
			viewHolder.tvTaskContent.setText(listTask.get(position).get("notice_content").toString());
			
			//给ListView的Item点击事件
			/*convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String noticeTheme = listTask.get(position).get("notice_theme").toString();
					String noticeDate = listTask.get(position).get("notice_date").toString();
					String noticeContent = listTask.get(position).get("notice_content").toString();
					String noticeEmpNo = listTask.get(position).get("notice_emp_no").toString();
				}
			});*/
			return convertView;
		}

	}

	public final class ViewHolder{
		TextView tvTaskTitle;
		TextView tvTaskDate;
		TextView tvTaskContent;
		TextView tvTaskFlag;
	}

}
