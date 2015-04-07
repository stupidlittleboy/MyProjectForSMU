package com.shmtu.myprojectforsmu.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;

public class CustomerDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvCustomerDetailNo;
	private TextView tvCustomerDetailName;
	private TextView tvCustomerDetailPhoneNo;
	private TextView tvCustomerDetailEmail;
	private TextView tvCustomerDetailDate;
	private RatingBar tbCustomerDetailLevel;
	
	public static void startCustomerDetailActivity(Context context, String customerNo, String customerName, 
			String customerPhoneNo, String customerEmail, String customerDate, int customerLevel) {
		Intent intent = new Intent(context, CustomerDetailActivity.class);
		intent.putExtra("customerNo", customerNo);
		intent.putExtra("customerName", customerName);
		intent.putExtra("customerPhoneNo", customerPhoneNo);
		intent.putExtra("customerEmail", customerEmail);
		intent.putExtra("customerDate", customerDate);
		intent.putExtra("customerLevel", customerLevel);
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
		tbCustomerDetailLevel = (RatingBar) findViewById(R.id.rb_customer_datail_level);
		
		Intent intent = getIntent();
		tvCustomerDetailNo.setText("客户编号：" + intent.getStringExtra("customerNo"));
		tvCustomerDetailName.setText("客户名称：" + intent.getStringExtra("customerName"));
		tvCustomerDetailPhoneNo.setText("联系方式：" + intent.getStringExtra("customerPhoneNo"));
		tvCustomerDetailEmail.setText("电子邮件：" + intent.getStringExtra("customerEmail"));
		tvCustomerDetailDate.setText("日期：" + intent.getStringExtra("customerDate"));
		tbCustomerDetailLevel.setRating(intent.getIntExtra("customerLevel", 0));
		
		tvCustomerDetailPhoneNo.setOnClickListener(this);
		tvCustomerDetailEmail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}
}
