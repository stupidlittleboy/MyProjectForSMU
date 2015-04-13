package com.shmtu.myprojectforsmu.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmtu.myprojectforsmu.R;

public class CompanyContactsAdapter extends BaseExpandableListAdapter {

	private LayoutInflater father_Inflater=null;
	private LayoutInflater son_Inflater=null;

	private ArrayList<HashMap<String, Object>> father_array;//父层
	private ArrayList<HashMap<String, Object>> son_array;//子层
	private ExpandableListView elvCompanyContacts;

	public CompanyContactsAdapter(Context context, ArrayList<HashMap<String, Object>> father_array,
			ArrayList<HashMap<String, Object>> son_array, ExpandableListView elvCompanyContacts){
		father_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		son_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.father_array = father_array;
		this.son_array = son_array;
		this.elvCompanyContacts = elvCompanyContacts;
	}

	public void setItemFatherArray(ArrayList<HashMap<String, Object>> father_array) {
		this.father_array = father_array;
	}

	public void setItemSonArray(ArrayList<HashMap<String, Object>> son_array) {
		this.son_array = son_array;
	}

	public ArrayList<ArrayList<HashMap<String, Object>>> itjj(ArrayList<HashMap<String, Object>> father_array,
			ArrayList<HashMap<String, Object>> son_array) {
		ArrayList<ArrayList<HashMap<String, Object>>> son_list = new ArrayList<ArrayList<HashMap<String,Object>>>();
		for (int i = 0; i < father_array.size(); i++) {
			ArrayList<HashMap<String, Object>> son = new ArrayList<HashMap<String,Object>>();
			for (int j = 0; j < son_array.size(); j++) {
				if (son_array.get(j).get("contactsDepartment").equals(father_array.get(i).get("contactsDepartment"))) {
					son.add(son_array.get(j));
				}
			}
			son_list.add(son);
		}
		return son_list;
	}

	//获取父层的大小
	@Override
	public int getGroupCount() {
		return father_array.size();
	}

	//于获取父层中其中一层的子数目
	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<ArrayList<HashMap<String, Object>>> list = itjj(father_array, son_array);
		return list.get(groupPosition).size();
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
		if(isExpanded == true){//展开状态
			groupViewHolder.ivContactsHead.setImageResource(R.drawable.go_up);
		}else{//收起状态
			groupViewHolder.ivContactsHead.setImageResource(R.drawable.go_down);
		}
//		groupViewHolder.ivContactsHead.setImageResource(R.drawable.expandable_listview);
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
		ArrayList<ArrayList<HashMap<String, Object>>> list = itjj(father_array, son_array);
		childViewHolder.tvContactsChildName.setText(list.get(groupPosition).get(childPosition).get("contactsChildName").toString().trim());
		childViewHolder.tvContactsChildEmpNo.setText(list.get(groupPosition).get(childPosition).get("contactsChildEmpNo").toString().trim());
		childViewHolder.tvContactsChildPhoneNo.setText(list.get(groupPosition).get(childPosition).get("contactsChildPhoneNo").toString().trim());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
		for (int i = 0; i < father_array.size(); i++) {    
			if (groupPosition != i) {    
				elvCompanyContacts.collapseGroup(i);    
			}    
		}    
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
