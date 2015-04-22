package com.shmtu.myprojectforsmu.complany;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;

public class ComplanyMeetingDetail extends BaseActivity {
	
	private TextView tvMeetingDetailTheme;
	private TextView tvMeetingDetailStarttime;
	private TextView tvMeetingDetailEndtime;
	private TextView tvMeetingDetailAddress;
	private TextView tvMeetingDetailContent;

	public static void startComplanyMeetingDetail(Context context, String meetingTheme, String meetingStarttime, 
			String meetingEndtime, String meetingAddress, String meetingContent) {
		Intent intent = new Intent(context, ComplanyMeetingDetail.class);
		intent.putExtra("meetingTheme", meetingTheme);
		intent.putExtra("meetingStarttime", meetingStarttime);
		intent.putExtra("meetingEndtime", meetingEndtime);
		intent.putExtra("meetingAddress", meetingAddress);
		intent.putExtra("meetingContent", meetingContent);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_meeting_detail);
		
		init();
	}
	
	//初始化控件
	private void init() {
		tvMeetingDetailTheme = (TextView) findViewById(R.id.tv_meeting_detail_theme);
		tvMeetingDetailStarttime = (TextView) findViewById(R.id.tv_meeting_detail_starttime);
		tvMeetingDetailEndtime = (TextView) findViewById(R.id.tv_meeting_detail_endtime);
		tvMeetingDetailAddress = (TextView) findViewById(R.id.tv_meeting_detail_address);
		tvMeetingDetailContent = (TextView) findViewById(R.id.tv_meeting_detail_content);
		
		Intent intent = getIntent();
		tvMeetingDetailTheme.setText(intent.getStringExtra("meetingTheme"));
		tvMeetingDetailStarttime.setText("开始时间：" + intent.getStringExtra("meetingStarttime"));
		tvMeetingDetailEndtime.setText("结束时间：" + intent.getStringExtra("meetingEndtime"));
		tvMeetingDetailAddress.setText("开会地址：" + intent.getStringExtra("meetingAddress"));
		tvMeetingDetailContent.setText(intent.getStringExtra("meetingContent"));
	}
	
}
