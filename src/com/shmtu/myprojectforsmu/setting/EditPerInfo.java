package com.shmtu.myprojectforsmu.setting;

import com.shmtu.myprojectforsmu.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EditPerInfo extends Activity {

	private EditText etPerInfoNickname;
	private EditText etPerInfoName;
	private RadioGroup radioPerInfoSex;
	private RadioButton radioPerInfoMale;
	private RadioButton radioPerInfoFemale;
	private EditText etPerInfoAge;
	private EditText etPerInfoPhoneNo;
	private EditText etPerInfoEmail;
	private TextView etPerInfoEmpNo;
	private TextView etPerInfoDepartment;
	private TextView etPerInfoPosition;
	private TextView etPerInfoEntryDate;
	private EditText etPerInfoBirthday;
	private EditText etPerInfoNation;
	private EditText etPerInfoCity;
	private EditText etPerInfoAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_per_info);
		
		init();
	}
	
	private void init() {
		etPerInfoNickname = (EditText) findViewById(R.id.et_per_info_nickname);
		etPerInfoName = (EditText) findViewById(R.id.et_per_info_name);
		radioPerInfoSex = (RadioGroup) findViewById(R.id.radio_per_info_sex);
		radioPerInfoMale = (RadioButton) findViewById(R.id.radio_per_info_male);
		radioPerInfoFemale = (RadioButton) findViewById(R.id.radio_per_info_female);
		etPerInfoAge = (EditText) findViewById(R.id.et_per_info_age);
		etPerInfoPhoneNo = (EditText) findViewById(R.id.et_per_info_phone_no);
		etPerInfoEmail = (EditText) findViewById(R.id.et_per_info_email);
		etPerInfoEmpNo = (TextView) findViewById(R.id.et_per_info_emp_no);
		etPerInfoDepartment = (TextView) findViewById(R.id.et_per_info_department);
		etPerInfoPosition = (TextView) findViewById(R.id.et_per_info_position);
		etPerInfoEntryDate = (TextView) findViewById(R.id.et_per_info_entry_date);
		etPerInfoBirthday = (EditText) findViewById(R.id.et_per_info_birthday);
		etPerInfoNation = (EditText) findViewById(R.id.et_per_info_nation);
		etPerInfoCity = (EditText) findViewById(R.id.et_per_info_city);
		etPerInfoAddress = (EditText) findViewById(R.id.et_per_info_address);
	}
	
}
