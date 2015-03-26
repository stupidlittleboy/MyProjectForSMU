package com.shmtu.myprojectforsmu.complany;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class ComplanyMeeting extends Activity {

	public static void startComplanyMeeting(Context context){
		Intent intent = new Intent(context, ComplanyMeeting.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_meeting);
	}
	
}
