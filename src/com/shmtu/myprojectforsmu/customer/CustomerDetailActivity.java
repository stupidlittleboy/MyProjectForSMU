package com.shmtu.myprojectforsmu.customer;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.commons.Constant;
import com.shmtu.myprojectforsmu.setting.SettingMap;

public class CustomerDetailActivity extends BaseActivity implements OnClickListener {

	private final static String CUSTOMER_DETAIL_URL = Constant.URL + "get_customer_detail.php";
	
	private TextView tvCustomerDetailNo;
	private TextView tvCustomerDetailNeed;
	private TextView tvCustomerDetailName;
	private TextView tvCustomerDetailSex;
	private TextView tvCustomerDetailPhoneNo;
	private TextView tvCustomerDetailEmail;
	private TextView tvCustomerDetailDate;
	private TextView tvCustomerDetailPeriod;
	private TextView tvCustomerDetailCity;
	private TextView tvCustomerDetailAddress;
	private TextView tvCustomerDetailType;
	private TextView tvCustomerDetailArea;
	private TextView tvCustomerDetailPrice;
	private TextView tvCustomerDetailGreenRating;
	private TextView tvCustomerDetailProperty;
	private TextView tvCustomerDetailOwnerName;
	private TextView tvCustomerDetailOwnerPhoneNo;
	private TextView tvCustomerDetailIsEmpty;
	private TextView tvCustomerDetailIsCompleted;
	private RatingBar rbCustomerDetailLevel;
	
	private RequestQueue mQueue;
	
	public static void startCustomerDetailActivity(Context context, String roomerNo) {
		Intent intent = new Intent(context, CustomerDetailActivity.class);
		intent.putExtra("roomerNo", roomerNo);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_detail);
		
		init();
	}

	private void init() {
		tvCustomerDetailNo = (TextView) findViewById(R.id.tv_customer_detail_no);
		tvCustomerDetailNeed = (TextView) findViewById(R.id.tv_customer_detail_need);
		tvCustomerDetailName = (TextView) findViewById(R.id.tv_customer_detail_name);
		tvCustomerDetailSex = (TextView) findViewById(R.id.tv_customer_detail_sex);
		tvCustomerDetailPhoneNo = (TextView) findViewById(R.id.tv_customer_detail_phone_no);
		tvCustomerDetailEmail = (TextView) findViewById(R.id.tv_customer_detail_email);
		tvCustomerDetailDate = (TextView) findViewById(R.id.tv_customer_detail_date);
		tvCustomerDetailPeriod = (TextView) findViewById(R.id.tv_customer_detail_period);
		tvCustomerDetailCity = (TextView) findViewById(R.id.tv_customer_detail_city);
		tvCustomerDetailAddress = (TextView) findViewById(R.id.tv_customer_detail_address);
		tvCustomerDetailType = (TextView) findViewById(R.id.tv_customer_detail_type);
		tvCustomerDetailArea = (TextView) findViewById(R.id.tv_customer_detail_area);
		tvCustomerDetailPrice = (TextView) findViewById(R.id.tv_customer_detail_price);
		tvCustomerDetailGreenRating = (TextView) findViewById(R.id.tv_customer_detail_green_rating);
		tvCustomerDetailProperty = (TextView) findViewById(R.id.tv_customer_detail_property);
		tvCustomerDetailOwnerName = (TextView) findViewById(R.id.tv_customer_detail_owner_name);
		tvCustomerDetailOwnerPhoneNo = (TextView) findViewById(R.id.tv_customer_detail_owner_phone_no);
		tvCustomerDetailIsEmpty = (TextView) findViewById(R.id.tv_customer_detail_is_empty);
		tvCustomerDetailIsCompleted = (TextView) findViewById(R.id.tv_customer_detail_is_completed);
		
		rbCustomerDetailLevel = (RatingBar) findViewById(R.id.rb_customer_datail_level);
		
		tvCustomerDetailPhoneNo.setOnClickListener(this);
		tvCustomerDetailEmail.setOnClickListener(this);
		tvCustomerDetailAddress.setOnClickListener(this);
		
		Intent intent = getIntent();
		String roomerNo = intent.getStringExtra("roomerNo");
		JSONObject json = new JSONObject();
		try {
			json.put("roomerNo", roomerNo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		getCustomerDetail(roomerNo, json);
	}
	
	private void getCustomerDetail(String roomerNo, JSONObject json) {
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(CUSTOMER_DETAIL_URL, json, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.e("customer_detail", response.toString());
				try {
					tvCustomerDetailNo.setText("客户编号：" + response.getString("roomer_no").toString());
					if (Integer.parseInt(response.getString("roomer_rent").toString()) == 0) {
						tvCustomerDetailNeed.setText("客户需求：租房");
					} else {
						tvCustomerDetailNeed.setText("客户需求：买房");
					}
					tvCustomerDetailName.setText("客户名称：" + response.getString("roomer_name").toString());
					tvCustomerDetailSex.setText("客户性别：" + response.getString("roomer_sex").toString());
					tvCustomerDetailPhoneNo.setText("联系方式：" + response.getString("roomer_phone_no").toString());
					tvCustomerDetailEmail.setText("电子邮件：" + response.getString("roomer_email").toString());
					tvCustomerDetailDate.setText("预约日期：" + response.getString("roomer_date").toString());
					tvCustomerDetailPeriod.setText("预约时间：" + response.getString("roomer_period").toString());
					tvCustomerDetailCity.setText("所在城市：" + response.getString("house_city").toString());
					tvCustomerDetailAddress.setText("看房地址：" + response.getString("house_address").toString());
					tvCustomerDetailType.setText("房子类型：" + response.getString("house_type").toString());
					tvCustomerDetailArea.setText("房子面积：" + response.getString("house_area").toString());
					tvCustomerDetailPrice.setText("房子价格：" + response.getString("house_price").toString());
					tvCustomerDetailGreenRating.setText("绿化面积：" + response.getString("house_green_rating").toString());
					tvCustomerDetailProperty.setText("所属物业：" + response.getString("house_property").toString());
					tvCustomerDetailOwnerName.setText("房东姓名：" + response.getString("house_owner_name").toString());
					tvCustomerDetailOwnerPhoneNo.setText("房东电话：" + response.getString("house_owner_phone_no").toString());
					if (Integer.parseInt(response.getString("house_out_flag").toString()) == 0) {
						tvCustomerDetailIsEmpty.setText("现阶段房子空闲中，可以交易…");
					} else {
						tvCustomerDetailIsEmpty.setText("对不起，该套房子已经没有了，请重新选择！");
					}
					if (Integer.parseInt(response.getString("roomer_complete").toString()) == 0) {
						tvCustomerDetailIsCompleted.setText("正在跟进中…");
					} else {
						tvCustomerDetailIsCompleted.setText("交易已完成！");
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		mQueue.add(jsonObjectRequest);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.tv_customer_detail_phone_no:
			Uri uri = Uri.parse("tel:" + tvCustomerDetailPhoneNo.getText().toString().trim());
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(intent);
			break;

		case R.id.tv_customer_detail_email:
			Uri uri1 = Uri.parse("mailto:" + tvCustomerDetailEmail.getText().toString().trim());   
			Intent it = new Intent(Intent.ACTION_SENDTO, uri1);   
			startActivity(it);
			break;
			
		case R.id.tv_customer_detail_address:
			String city = tvCustomerDetailCity.getText().toString().trim();
			String address = tvCustomerDetailAddress.getText().toString().trim();
			SettingMap.startSettingMapSearch(CustomerDetailActivity.this, city, address);
			break;
			
		default:
			break;
		}
	}
}
