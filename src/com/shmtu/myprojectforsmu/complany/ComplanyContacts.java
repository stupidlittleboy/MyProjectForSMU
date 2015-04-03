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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;

public class ComplanyContacts extends Activity {

	private final static String CONTACTS_URL = Constant.URL + "company_contacts.php"; 
	private ListView lvCompanyContacts;
	private RequestQueue mQueue = null;
	private ArrayList<HashMap<String, Object>> listContacts = new ArrayList<HashMap<String,Object>>();
	private JSONObject json;

	public static void startComplanyContacts(Context context){
		Intent intent = new Intent(context, ComplanyContacts.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_contacts);

		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(CONTACTS_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				for (int i = 0; i < response.length(); i++) {
					try {
						json = response.getJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("contact_name", json.get("contact_name"));
						map.put("contact_emp_no", json.get("contact_emp_no"));
						map.put("contact_phone_no", json.get("contact_phone_no"));
						listContacts.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mQueue.add(jsonArrayRequest);
		/*lvCompanyContacts = (ListView) findViewById(R.id.lv_company_contacts);
		CompanyContactAdapter companyContactAdapter = new CompanyContactAdapter(this, listContacts);
		lvCompanyContacts.setAdapter(companyContactAdapter);*/

	}

	class CompanyContactAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		private ArrayList<HashMap<String, Object>> listContacts;

		public CompanyContactAdapter(Context context, ArrayList<HashMap<String, Object>> listContacts){
			mLayoutInflater = LayoutInflater.from(context);
			this.listContacts = listContacts;
		}

		@Override
		public int getCount() {
			return listContacts.size();
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
			viewHolder.tvNoticesTitle.setText(listContacts.get(position).get("contact_name").toString());
			viewHolder.tvNoticesDate.setText(listContacts.get(position).get("contact_emp_no").toString());
			viewHolder.tvNoticesContent.setText(listContacts.get(position).get("contact_phone_no").toString());
			return convertView;
		}
	}

	public final class ViewHolder{
		TextView tvNoticesTitle;
		TextView tvNoticesDate;
		TextView tvNoticesContent;
	}
}