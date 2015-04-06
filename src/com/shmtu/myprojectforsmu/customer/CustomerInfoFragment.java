package com.shmtu.myprojectforsmu.customer;

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
	private ArrayList<HashMap<String, Object>> listCustomerInfo;
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
		listCustomerInfo = new ArrayList<HashMap<String,Object>>();
		lvCustomerInfo = (ListView) getActivity().findViewById(R.id.lv_customer_info);
		customerInfoAdapter = new CustomerInfoAdapter(getActivity());

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
						map.put("customer_no", json.get("customer_no"));
						map.put("customer_name", json.get("customer_name"));
						map.put("customer_phone_no", json.get("customer_phone_no"));
						map.put("customer_date", json.get("customer_date"));
						map.put("customer_level", json.get("customer_level"));
						listCustomerInfo.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Log.e("list", listCustomerInfo.toString());
				customerInfoAdapter.setItemList(listCustomerInfo);
				lvCustomerInfo.setAdapter(customerInfoAdapter);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mQueue.add(jsonArrayRequest);
		/*
		 * 用于在非UI线程中更新UI
		 */
		/*handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:

					String res = msg.getData().getString("res");
					listItem = new ArrayList<HashMap<String,Object>>();
					try {
						jsonArray = new JSONArray(res);
						Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						e.printStackTrace();
					}

					for(int i = 0; i < jsonArray.length(); i++){
						try {
							JSONObject jObj = (JSONObject) jsonArray.get(i);
							HashMap<String, Object> map = new HashMap<String, Object>();
//							Toast.makeText(getActivity(), jObj.toString(), Toast.LENGTH_SHORT).show();
							map.put(Preferences.CUS_NAME, jObj.getString("name"));
							map.put(Preferences.CUS_PHONE, jObj.getString("email"));
							map.put(Preferences.CUS_DATE, jObj.getString("description"));
							listItem.add(map);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					SimpleAdapter mSimpleAdapter = new SimpleAdapter(getActivity(), listItem, 
							R.layout.customer_list_item, 
							new String[] {Preferences.CUS_NAME,Preferences.CUS_PHONE,Preferences.CUS_DATE}, 
							new int[] {R.id.customer_name, R.id.customer_phone, R.id.customer_date});

					lv.setAdapter(mSimpleAdapter);

					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							@SuppressWarnings("unchecked")
							HashMap<string, Object> map = (HashMap<string, Object>) lv.getItemAtPosition(position);
							Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString(Preferences.CUS_NAME, map.get(Preferences.CUS_NAME) + "");
							bundle.putString(Preferences.CUS_PHONE, map.get(Preferences.CUS_PHONE) + "");
							bundle.putString(Preferences.CUS_DATE, map.get(Preferences.CUS_DATE) + "");
							intent.putExtras(bundle);
							startActivity(intent);
							String name = map.get(Preferences.CUS_NAME) + "";
							String phone = map.get(Preferences.CUS_PHONE) + "";
							String date = map.get(Preferences.CUS_DATE) + "";
							Toast.makeText(getActivity(), "姓名：" + name + "\n手机：" + phone + "\n日期：" + date, Toast.LENGTH_SHORT).show();
						}
					});

					Toast.makeText(getActivity(), listItem.toString(), Toast.LENGTH_SHORT).show();
					for(int i = 0; i < listItem.size(); i++){
						Toast.makeText(getActivity(), listItem.get(i).toString(), Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}
			}
		};

		 *//**
		 * 从服务端获取用户基本信息
		 *//*
		new Thread(){
			@Override
			public void run() {
				super.run();
				json = new JSONObject();
				try {
					HttpUtils.httpPostMethod(Constant.URL + "cus_info.php", json, handler);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}.start();*/

		Log.d("list", listCustomerInfo.toString());
	}

	class CustomerInfoAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listCustomerInfo;

		public CustomerInfoAdapter (Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		public void setItemList(ArrayList<HashMap<String, Object>> listCustomerInfo){
			this.listCustomerInfo = listCustomerInfo;
			Log.e("list2", this.listCustomerInfo.toString());
		}

		@Override
		public int getCount() {
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.customer_list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvCustomerName = (TextView) getActivity().findViewById(R.id.tv_customer_name);
				viewHolder.tvCustomerPhoneNo = (TextView) getActivity().findViewById(R.id.tv_customer_phone_no);
				viewHolder.tvCustomerDate = (TextView) getActivity().findViewById(R.id.tv_customer_date);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (listCustomerInfo.size() == 0){
				viewHolder.tvCustomerName.setText(listCustomerInfo.get(position).get("customer_name").toString());
				viewHolder.tvCustomerPhoneNo.setText(listCustomerInfo.get(position).get("customer_phone_no").toString());
				viewHolder.tvCustomerDate.setText(listCustomerInfo.get(position).get("customer_date").toString());

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(), "点击查看明细", 0).show();
					}
				});
			} else {
				Toast.makeText(getActivity(), "暂时没有客户信息", 0).show();
			}
			return convertView;
		}

		private class ViewHolder {
			TextView tvCustomerName;
			TextView tvCustomerPhoneNo;
			TextView tvCustomerDate;
		}

	}

}
