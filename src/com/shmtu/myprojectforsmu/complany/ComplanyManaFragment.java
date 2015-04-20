package com.shmtu.myprojectforsmu.complany;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.task.TaskDetail;

public class ComplanyManaFragment extends Fragment implements OnClickListener {

	private final static String DEPARTMENT_URL = Constant.URL + "get_department.php";
	
	private ArrayList<HashMap<String,Object>> father_array = new ArrayList<HashMap<String,Object>>();
	private RequestQueue mQueueFather = null;
	private LinearLayout complanyMeeting;
	private LinearLayout complanyContacts;
	private LinearLayout complanyNotices;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.complany_mana_layout,
				container, false);
		return contactsLayout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		
	}
	
	
	private void init(){
		complanyMeeting = (LinearLayout) getActivity().findViewById(R.id.layout_complany_meeting);
		complanyContacts = (LinearLayout) getActivity().findViewById(R.id.layout_complany_contacts);
		complanyNotices = (LinearLayout) getActivity().findViewById(R.id.layout_complany_noticts);
		
		complanyMeeting.setOnClickListener(this);
		complanyContacts.setOnClickListener(this);
		complanyNotices.setOnClickListener(this);
	}

	
	//获取父节点的值
		private void getFatherArrry(){
//			father_array = new ArrayList<HashMap<String,Object>>();

			//创建一个RequestQueue队列
			mQueueFather = Volley.newRequestQueue(getActivity().getApplicationContext());
			//向服务端发送请求
			JsonArrayRequest jsonArrayRequestFather = new JsonArrayRequest(DEPARTMENT_URL, 
					new Listener<JSONArray>() {

				@Override
				public void onResponse(JSONArray response) {
					for (int i = 0; i < response.length(); i++) {
						try {
							JSONObject jsonObj = response.getJSONObject(i);
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("contactsDepartment", jsonObj.getString("emp_department"));
							father_array.add(map);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			},  
			new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG111", error.getMessage(), error);  
					//				Toast.makeText(LoginActivity.this, "网络连接出错，请检查网络状况！", Toast.LENGTH_LONG).show();
				}  
			});  
			mQueueFather.add(jsonArrayRequestFather);
			Log.e("father", father_array.toString());
		}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_complany_meeting:
			ComplanyMeeting.startComplanyMeeting(getActivity());
//			TaskDetail.startTaskDetail(getActivity());
			Toast.makeText(getActivity(), "会议信息", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_complany_contacts:
			ComplanyContacts.startComplanyContacts(getActivity());
			Toast.makeText(getActivity(), "企业通讯录", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.layout_complany_noticts:
			ComplanyNotices.startComplanyNotices(getActivity());
			Toast.makeText(getActivity(), "企业公告", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
}
