package com.shmtu.myprojectforsmu.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class CompanyContactsAdapter extends BaseExpandableListAdapter {

	private final static String DEPARTMENT_URL = Constant.URL + "get_department.php";
	private final static String CONTACTS_URL = Constant.URL + "get_contacts.php";

	private Context context;
	private LayoutInflater father_Inflater=null;
	private LayoutInflater son_Inflater=null;
	private RequestQueue mQueue = null;

	private ArrayList<HashMap<String, Object>> father_array;//父层
	private ArrayList<HashMap<String, Object>> son_array;//子层

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
		GroupViewHolder groupViewHolder;
		if (convertView == null) {
			convertView = father_Inflater.inflate(R.layout.contacts_group_item, null);
			groupViewHolder  = new GroupViewHolder();
			groupViewHolder.ivContactsHead = (ImageView) convertView.findViewById(R.id.iv_contacts_head);
			groupViewHolder.tvContactsFatherDepartment = (TextView) convertView.findViewById(R.id.tv_contacts_father_department);
			convertView.setTag(groupViewHolder);
		} else {
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		groupViewHolder.ivContactsHead.setImageResource(R.drawable.ic_launcher);
		groupViewHolder.tvContactsFatherDepartment.setText(father_array.get(groupPosition).get("contactsDepartment").toString().trim());
		return convertView;
	}

	//获取子层视图
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder childViewHolder;
		if (convertView == null) {
			convertView = son_Inflater.inflate(R.layout.contacts_child_item, null);
			childViewHolder = new ChildViewHolder();
			childViewHolder.tvContactsChildName = (TextView) convertView.findViewById(R.id.tv_contacts_child_name);
			childViewHolder.tvContactsChildEmpNo = (TextView) convertView.findViewById(R.id.tv_contacts_child_emp_no);
			childViewHolder.tvContactsChildPhoneNo = (TextView) convertView.findViewById(R.id.tv_contacts_child_phone_no);
			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag(); 
		}
		childViewHolder.tvContactsChildName.setText(son_array.get(childPosition).get("contactsChildName").toString().trim());
		childViewHolder.tvContactsChildEmpNo.setText(son_array.get(childPosition).get("contactsChildEmpNo").toString().trim());
		childViewHolder.tvContactsChildPhoneNo.setText(son_array.get(childPosition).get("contactsChildPhoneNo").toString().trim());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	//获取父节点的值
	private void getFatherArrry(){
		father_array=new ArrayList<HashMap<String,Object>>();

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
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("contactsDepartment", jsonObj.getString("contacts_department"));
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
				Log.e("TAG", error.getMessage(), error);  
				//				Toast.makeText(LoginActivity.this, "网络连接出错，请检查网络状况！", Toast.LENGTH_LONG).show();
			}  
		});  

		mQueue.add(jsonArrayRequest);
	}
	
	private void getChildArray(){
		son_array=new ArrayList<HashMap<String,Object>>();
		mQueue = Volley.newRequestQueue(context);
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(CONTACTS_URL, 
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject jsonObj = response.getJSONObject(i);
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("contactsChildName", jsonObj.getString("contacts_name"));
								map.put("contactsChildEmpNo", jsonObj.getString("contacts_emp_no"));
								map.put("contactsChildPhoneNo", jsonObj.getString("contacts_phone_no"));
								son_array.add(map);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
	}
	
	class GroupViewHolder {
		ImageView ivContactsHead;
		TextView tvContactsFatherDepartment;
	}
	
	class ChildViewHolder {
		TextView tvContactsChildName;
		TextView tvContactsChildEmpNo;
		TextView tvContactsChildPhoneNo;
	}

}
