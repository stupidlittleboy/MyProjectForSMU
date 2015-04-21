package com.shmtu.myprojectforsmu.customer;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
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

public class CustomerInfoFragment extends Fragment {

	private final static String CUSTOMER_INFO_URL = Constant.URL + "customer_info.php";

	private ListView lvCustomerInfo;
	private RequestQueue mQueue = null;
	private CustomerInfoAdapter customerInfoAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.customer_layout,
				container, false);
		return messageLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public void onResume() {
		super.onResume();
		getCustomerInfo();
	}

	//初始化ListView控件
	private void init() {
		lvCustomerInfo = (ListView) getActivity().findViewById(R.id.lv_customer_info);
		customerInfoAdapter = new CustomerInfoAdapter(getActivity());
	}

	//从服务器获取信息
	private void getCustomerInfo() {
		final ArrayList<HashMap<String, Object>> listCustomerInfo = new ArrayList<HashMap<String,Object>>();
		mQueue = Volley.newRequestQueue(getActivity());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(CUSTOMER_INFO_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				Log.e("jsonArray", response.toString());
				for (int i = 0; i < response.length(); i++) {
					try {
						JSONObject json = response.getJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("roomer_no", json.get("roomer_no"));
						map.put("roomer_name", json.get("roomer_name"));
						map.put("roomer_phone_no", json.get("roomer_phone_no"));
						map.put("roomer_date", json.get("roomer_date"));
						listCustomerInfo.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				//将list集合中的数据传入到自定义的adapter中
				customerInfoAdapter.setItemList(listCustomerInfo);
				customerInfoAdapter.notifyDataSetChanged();
				lvCustomerInfo.setAdapter(customerInfoAdapter);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mQueue.add(jsonArrayRequest);
	}

	//自定义adapter，优化ListView
	@SuppressLint("InflateParams")
	class CustomerInfoAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listCustomerInfo;

		public CustomerInfoAdapter (Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		//传入list内的数据
		public void setItemList(ArrayList<HashMap<String, Object>> listCustomerInfo){
			this.listCustomerInfo = listCustomerInfo;
			Log.e("list2", this.listCustomerInfo.toString());
		}

		@Override
		public int getCount() {
			Log.e("list2", this.listCustomerInfo.size()+"");
			return listCustomerInfo.size();
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
			Log.e("size", listCustomerInfo.size()+"");
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.customer_list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvCustomerName = (TextView) convertView.findViewById(R.id.tv_customer_name);
				viewHolder.tvCustomerPhoneNo = (TextView) convertView.findViewById(R.id.tv_customer_phone_no);
				viewHolder.tvCustomerDate = (TextView) convertView.findViewById(R.id.tv_customer_date);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (listCustomerInfo.size() > 0){
				viewHolder.tvCustomerName.setText(listCustomerInfo.get(position).get("roomer_name").toString());
				viewHolder.tvCustomerPhoneNo.setText(listCustomerInfo.get(position).get("roomer_phone_no").toString());
				viewHolder.tvCustomerDate.setText(listCustomerInfo.get(position).get("roomer_date").toString());

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(), "点击查看明细", Toast.LENGTH_SHORT).show();
						String roomerNo = listCustomerInfo.get(position).get("roomer_no").toString();
						CustomerDetailActivity.startCustomerDetailActivity(getActivity(), roomerNo);
					}
				});
			} else {
				Toast.makeText(getActivity(), "暂时没有客户信息", Toast.LENGTH_SHORT).show();
			}
			return convertView;
		}

		//辅助类，优化ListView性能
		private class ViewHolder {
			TextView tvCustomerName;
			TextView tvCustomerPhoneNo;
			TextView tvCustomerDate;
		}

	}

}
