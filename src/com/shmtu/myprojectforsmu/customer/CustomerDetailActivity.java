package com.shmtu.myprojectforsmu.customer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;
import com.shmtu.myprojectforsmu.setting.SettingMap;

public class CustomerDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvCustomerDetailNo;
	private TextView tvCustomerDetailName;
	private TextView tvCustomerDetailPhoneNo;
	private TextView tvCustomerDetailEmail;
	private TextView tvCustomerDetailDate;
	private RatingBar rbCustomerDetailLevel;
	private TextView tvCustomerDetailAddress;
	private TextView tvCustomerDetailCity;
	
	public static void startCustomerDetailActivity(Context context, String customerNo, 
			String customerName, String customerPhoneNo, String customerEmail, 
			String customerDate, int customerLevel, String customerCity, String customerAddress) {
		Intent intent = new Intent(context, CustomerDetailActivity.class);
		intent.putExtra("customerNo", customerNo);
		intent.putExtra("customerName", customerName);
		intent.putExtra("customerPhoneNo", customerPhoneNo);
		intent.putExtra("customerEmail", customerEmail);
		intent.putExtra("customerDate", customerDate);
		intent.putExtra("customerLevel", customerLevel);
		intent.putExtra("customerCity", customerCity);
		intent.putExtra("customerAddress", customerAddress);
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
		tvCustomerDetailName = (TextView) findViewById(R.id.tv_customer_detail_name);
		tvCustomerDetailPhoneNo = (TextView) findViewById(R.id.tv_customer_detail_phone_no);
		tvCustomerDetailEmail = (TextView) findViewById(R.id.tv_customer_detail_email);
		tvCustomerDetailDate = (TextView) findViewById(R.id.tv_customer_detail_date);
		rbCustomerDetailLevel = (RatingBar) findViewById(R.id.rb_customer_datail_level);
		tvCustomerDetailCity = (TextView) findViewById(R.id.tv_customer_detail_city);
		tvCustomerDetailAddress = (TextView) findViewById(R.id.tv_customer_detail_address);
		
		Intent intent = getIntent();
		tvCustomerDetailNo.setText("客户编号：" + intent.getStringExtra("customerNo"));
		tvCustomerDetailName.setText("客户名称：" + intent.getStringExtra("customerName"));
		tvCustomerDetailPhoneNo.setText("联系方式：" + intent.getStringExtra("customerPhoneNo"));
		tvCustomerDetailEmail.setText("电子邮件：" + intent.getStringExtra("customerEmail"));
		tvCustomerDetailDate.setText("日期：" + intent.getStringExtra("customerDate"));
		rbCustomerDetailLevel.setRating(intent.getIntExtra("customerLevel", 0));
		tvCustomerDetailCity.setText("所在城市：" + intent.getStringExtra("customerCity"));
		tvCustomerDetailAddress.setText("详细地址：" + intent.getStringExtra("customerAddress"));
		
		tvCustomerDetailPhoneNo.setOnClickListener(this);
		tvCustomerDetailEmail.setOnClickListener(this);
		tvCustomerDetailAddress.setOnClickListener(this);
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
