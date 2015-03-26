package com.shmtu.myprojectforsmu.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class TaskDetail extends Activity {

	public static void startTaskDetail(Context context){
		Intent intent = new Intent(context, TaskDetail.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail);
	}
	
}
