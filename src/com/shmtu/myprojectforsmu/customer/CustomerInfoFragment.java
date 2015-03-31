package com.shmtu.myprojectforsmu.customer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.commons.Preferences;
import com.shmtu.myprojectforsmu.utils.HttpUtils;

public class CustomerInfoFragment extends Fragment {
	
	private ListView lv;
	private JSONObject json;
	private JSONArray jsonArray;
	private Handler handler;
	private AutoCompleteTextView et_search;
	private ArrayList<HashMap<String, Object>> listItem;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.customer_layout,
				container, false);
		return messageLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		lv = (ListView) getActivity().findViewById(R.id.listView);/*定义一个动态数组*/ 
		/*
		 * 用于在非UI线程中更新UI
		 */
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					
					String res = msg.getData().getString("res");
					listItem = new ArrayList<HashMap<String,Object>>();
					try {
						jsonArray = new JSONArray(res);
						Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
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
							// TODO Auto-generated method stub
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
					/*for(int i = 0; i < listItem.size(); i++){
						Toast.makeText(getActivity(), listItem.get(i).toString(), Toast.LENGTH_SHORT).show();
					}*/
					break;

				default:
					break;
				}
			}
		};
		
		/**
		 * 从服务端获取用户基本信息
		 */
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				json = new JSONObject();
				try {
					HttpUtils.httpPostMethod(Constant.URL + "cus_info.php", json, handler);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
	}
	
}
