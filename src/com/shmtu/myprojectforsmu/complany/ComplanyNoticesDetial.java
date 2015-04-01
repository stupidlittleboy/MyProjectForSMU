package com.shmtu.myprojectforsmu.complany;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.R;

public class ComplanyNoticesDetial extends Activity implements OnClickListener{
	
	private TextView tvNoticesDetailTitle;
	private TextView tvNoticesDetailDate;
	private TextView tvNoticesDetailContent;
	private RelativeLayout layoutNoticesDetailEmp;
	private TextView tvNoticesDetailEmp;
	
	public static void startComplanyNoticesDetial(Context context, String noticeTheme, 
			String noticeDate, String noticeContent, String noticeEmpNo){
		Intent intent = new Intent(context, ComplanyNoticesDetial.class);
		intent.putExtra("noticeTheme", noticeTheme);
		intent.putExtra("noticeDate", noticeDate);
		intent.putExtra("noticeContent", noticeContent);
		intent.putExtra("noticeEmpNo", noticeEmpNo);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_notices_detial);
		
		init();
	}
	
	private void init(){
		tvNoticesDetailTitle = (TextView) findViewById(R.id.tv_notices_detail_title);
		tvNoticesDetailDate = (TextView) findViewById(R.id.tv_notices_detail_date);
		tvNoticesDetailContent = (TextView) findViewById(R.id.tv_notices_detail_content);
		layoutNoticesDetailEmp = (RelativeLayout) findViewById(R.id.layout_notices_detail_emp);
		tvNoticesDetailEmp = (TextView) findViewById(R.id.tv_notices_detail_emp);
		
		layoutNoticesDetailEmp.setOnClickListener(this);
		Intent intent = getIntent();
		tvNoticesDetailTitle.setText(intent.getStringExtra("noticeTheme"));
		tvNoticesDetailDate.setText(intent.getStringExtra("noticeDate"));
		tvNoticesDetailContent.setText(intent.getStringExtra("noticeContent"));
		tvNoticesDetailEmp.setText(intent.getStringExtra("noticeEmpNo"));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.layout_notices_detail_emp:
			Toast.makeText(ComplanyNoticesDetial.this, tvNoticesDetailEmp.getText(), Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
}
