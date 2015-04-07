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
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.adapter.CompanyContactsAdapter;
import com.shmtu.myprojectforsmu.commons.Constant;

public class ComplanyContacts extends Activity {

//	private final static String DEPARTMENT_URL = Constant.URL + "get_department.php";
	private final static String CONTACTS_URL = Constant.URL + "get_contacts.php";

	//	private final static String CONTACTS_URL = Constant.URL + "company_contacts.php"; 
	private ExpandableListView elvCompanyContacts;
	private RequestQueue mQueueFather = null;
	private RequestQueue mQueueSon = null;
	private CompanyContactsAdapter companyContactsAdapter;
	
	public static void startComplanyContacts(Context context){
		Intent intent = new Intent(context, ComplanyContacts.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_contacts);
		init();
	}

	private void init() {
		elvCompanyContacts = (ExpandableListView) findViewById(R.id.elv_company_contacts);
//		companyContactsAdapter = new CompanyContactsAdapter(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getCompanyContacts();
//		elvCompanyContacts.setAdapter(companyContactsAdapter);
	}
	
	/*//获取父节点的值
	private void getFatherArrry(){
		final ArrayList<HashMap<String,Object>> father_array=new ArrayList<HashMap<String,Object>>();

		//创建一个RequestQueue队列
		mQueueFather = Volley.newRequestQueue(getApplicationContext());
		//向服务端发送请求
		JsonArrayRequest jsonArrayRequestFather = new JsonArrayRequest(DEPARTMENT_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				Log.e("father", response.toString());
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
				Log.e("father", father_array.toString());
//				companyContactsAdapter.setItemFatherArray(father_array);
			}
		},  
		new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				//				Toast.makeText(LoginActivity.this, "网络连接出错，请检查网络状况！", Toast.LENGTH_LONG).show();
			}  
		});  

		mQueueFather.add(jsonArrayRequestFather);
	}
*/
	private void getCompanyContacts(){
		final ArrayList<HashMap<String,Object>> son_array=new ArrayList<HashMap<String,Object>>();
		mQueueSon = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequestSon = new JsonArrayRequest(CONTACTS_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				Log.e("response", response.toString());
				for (int i = 0; i < response.length(); i++) {
					try {
						JSONObject jsonObj = response.getJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("contactsChildName", jsonObj.getString("contact_name"));
						map.put("contactsChildEmpNo", jsonObj.getString("contact_emp_no"));
						map.put("contactsChildPhoneNo", jsonObj.getString("contact_phone_no"));
						son_array.add(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Log.e("son", son_array.toString());
//				companyContactsAdapter.set+ItemSonArray(son_array);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mQueueSon.add(jsonArrayRequestSon);
	}

}