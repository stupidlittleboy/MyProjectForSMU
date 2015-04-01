package com.shmtu.myprojectforsmu.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.commons.Constant;

public class CompanyContactsAdapter extends BaseExpandableListAdapter {

	private final static String DEPARTMENT_URL = Constant.URL + "get_department.php";
	private final static String CONTACTS_URL = Constant.URL + "get_contacts.php";

	private Context context;
	private LayoutInflater father_Inflater=null;
	private LayoutInflater son_Inflater=null;
	private RequestQueue mQueue = null;

	private ArrayList<String> father_array;//父层
	private ArrayList<List<String>> son_array;//子层

	public CompanyContactsAdapter(Context context){
		this.context = context;
		father_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		son_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	//获取父层的大小
	@Override
	public int getGroupCount() {
		return father_array.size();
	}

	//于获取父层中其中一层的子数目
	@Override
	public int getChildrenCount(int groupPosition) {
		return son_array.get(groupPosition).size();
	}

	//获取父层中的一项，返回的是父层的字符串类型
	@Override
	public Object getGroup(int groupPosition) {
		return father_array.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return son_array.get(groupPosition).get(childPosition);
	}

	//获取父层的位置
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	//获取子层中单项在子层中的位置
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		return null;
	}

	//获取子层视图
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private void init(){
		father_array=new ArrayList<String>();
		son_array=new ArrayList<List<String>>();

		//创建一个RequestQueue队列
		mQueue = Volley.newRequestQueue(context);
		//向服务端发送请求
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DEPARTMENT_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				for (int i = 0; i < response.length(); i++) {
					try {
						JSONObject jsonObj = response.getJSONObject(i);
						father_array.add(jsonObj.getString("contacts_department"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		},  
		new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				//				Toast.makeText(LoginActivity.this, "网络连接出错，请检查网络状况！", Toast.LENGTH_LONG).show();
			}  
		});  

		mQueue.add(jsonArrayRequest);
	}

}
