package com.shmtu.myprojectforsmu.complany;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shmtu.myprojectforsmu.R;

public class ComplanyNotices extends Activity {

	public static void startComplanyNotices(Context context){
		Intent intent = new Intent(context, ComplanyNotices.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complany_notices);
	}
	
}
