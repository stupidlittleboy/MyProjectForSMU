package com.shmtu.myprojectforsmu.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.R;

public class TaskDetail extends Activity implements OnClickListener {

	private TextView tvTaskTitle;	//标题
	private TextView tvTaskDate;	//时间
	private TextView tvTaskContent;	//内容
	private LinearLayout layoutTaskFlag;	//任务领取标识
	private Button btnTaskFlag;		//领取任务
	
	public static void startTaskDetail(Context context){
		Intent intent = new Intent(context, TaskDetail.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail);
		
		tvTaskTitle = (TextView) findViewById(R.id.tv_task_title);
		tvTaskDate = (TextView) findViewById(R.id.tv_task_date);
		tvTaskContent = (TextView) findViewById(R.id.tv_task_content);
		layoutTaskFlag = (LinearLayout) findViewById(R.id.layout_task_detail_flag);
		btnTaskFlag = (Button) findViewById(R.id.btn_task_detail_flag);
		
		layoutTaskFlag.setOnClickListener(this);
		btnTaskFlag.setOnClickListener(this);
		int flag = 1;
		if(flag == 0){
			layoutTaskFlag.setVisibility(View.GONE);
		} else {
			btnTaskFlag.setVisibility(View.GONE);
		}
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_task_detail_flag:
			Toast.makeText(TaskDetail.this, "显示详细信息", 0).show();
			break;
			
		case R.id.btn_task_detail_flag:
			Toast.makeText(TaskDetail.this, "任务领取成功", 0).show();
			break;

		default:
			break;
		}
	}
	
}
