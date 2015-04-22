package com.shmtu.myprojectforsmu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class DateTimePickerDialog {

	/**
	 * 日期样式一
	 * 可以滚动选择年、月、日，也可以通过点击日历上的日期获得选择值
	 * @param context	上下文
	 * @param calendar	实例化的日历对象
	 * @param editText	用于存储结果的输入框
	 */
	
	public static void dateDialogAll(Context context, Calendar calendar, 
			final EditText editText) {
		DatePickerDialog.OnDateSetListener dateListener = 
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, 
					int year, int month, int dayOfMonth) {
				//Calendar月份是从0开始,所以month要加1
				CharSequence date = year + "-" + (month+1) + "-" + dayOfMonth;
				Date dateFormat = null;
				try {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				editText.setText(new SimpleDateFormat("yyyy-MM-dd").format(dateFormat));
			}
		};
		new DatePickerDialog(context,
				dateListener,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}
	
	/**
	 * 日期样式一
	 * 只可以通过点击日历上的日期获得选择值
	 * @param context	上下文
	 * @param calendar	实例化的日历对象
	 * @param editText	用于存储结果的输入框
	 */
	
	public static void dateDialogOnlyScollSelect(Context context, Calendar calendar, 
			final EditText editText) {
		DatePickerDialog.OnDateSetListener dateListener = 
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, 
					int year, int month, int dayOfMonth) {
				//Calendar月份是从0开始,所以month要加1
				StringBuffer date = new StringBuffer();
				date = date.append(year).append("-").append(month+1)
						.append("-").append(dayOfMonth);
				Date dateFormat = null;
				try {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				editText.setText(new SimpleDateFormat("yyyy-MM-dd").format(dateFormat));
			}
		};
		DatePickerDialog date = new DatePickerDialog(context,
				dateListener,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		//设置日期控件右边的日历不显示
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	/**
	 * 日期样式五
	 * 只可以滚动选择年、月、日
	 * @param context	上下文
	 * @param calendar	实例化的日历对象
	 * @param editText	用于存储结果的输入框
	 */
	public static void dateDialogOnlyCalendar(Context context, Calendar calendar, 
			final EditText editText) {
		DatePickerDialog.OnDateSetListener dateListener = 
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, 
					int year, int month, int dayOfMonth) {
				//Calendar月份是从0开始,所以month要加1
				StringBuffer date = new StringBuffer();
				date = date.append(year).append("-").append(month+1)
						.append("-").append(dayOfMonth);
				Date dateFormat = null;
				try {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				editText.setText(new SimpleDateFormat("yyyy-MM-dd").format(dateFormat));
			}
		};
		DatePickerDialog date = new DatePickerDialog(context,
				dateListener,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		//设置日期控件右边的日历不显示
		date.getDatePicker().setSpinnersShown(false);
		date.show();
	}
	
	/**
	 * 日期样式
	 * 只可以滚动/点击按钮选择时间
	 * @param context	上下文
	 * @param calendar	实例化的日历对象
	 * @param editText	用于存储结果的输入框
	 */
	
	public static void timeDialogScollSelect(Context context, Calendar calendar, 
			final EditText editText) {
		TimePickerDialog.OnTimeSetListener timeListener = 
				new TimePickerDialog.OnTimeSetListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onTimeSet(TimePicker timerPicker,
					int hourOfDay, int minute) {
				StringBuffer time = new StringBuffer();
				time = time.append(hourOfDay).append(":").append(minute);
				Date timeFor = null;
				try {
					timeFor = new SimpleDateFormat("HH:ss").parse(time.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editText.setText(new SimpleDateFormat("HH:ss").format(timeFor));
			}
		};
		new TimePickerDialog(context, timeListener,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE),
				true).show();   //是否为二十四制，如果是false则为12小时制
	}
	
}
