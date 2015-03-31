package com.shmtu.myprojectforsmu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SMSVerifyUtil {

	/**
	 * 短信验证功能初始化以及注册回调
	 * @param context	上下文
	 * @param appKey	短信验证的App Key
	 * @param appSecret	短信验证的App Secret
	 * @param handler	消息处理
	 */
	public static void initSMS(Context context, String appKey, String appSecret, final Handler handler){
		SMSSDK.initSDK(context, appKey, appSecret);
		EventHandler eh=new EventHandler(){

			@Override
			public void afterEvent(int event, int result, Object data) {

				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}

		};
		SMSSDK.registerEventHandler(eh);
	}

	/**
	 * 获取验证码
	 * @param code	国家代码
	 * @param phonEditText	电话号码
	 * @param sensmsButton	按钮
	 * @param phString	存储电话号码
	 * @param bg	背景样式
	 */
	public static void getCode(String code, TextView phonEditText, TextView sensmsButton, Drawable bg) {
		SMSSDK.getVerificationCode(code,phonEditText.getText().toString());
		CountDown mc = new CountDown(6000, 1000, sensmsButton, bg);
		mc.start();
	}

	/**
	 * 
	 * @param code	国家代码
	 * @param phString	电话号码
	 * @param verEditText	
	 */
	public static void submitCode(String code, String phString, String str){
		SMSSDK.submitVerificationCode(code, phString, str);
	}
	
	/**
	 * 截取字符串，获取字符串中的code
	 * @param s
	 * @return
	 */
	public static String subStrCountry(String s){
		int index = s.lastIndexOf("+");
		if(index > 0){
			s = s.substring(index+1);
		}
		return s;
	}
	
	static class CountDown extends CountDownTimer{

		private TextView textView = null;
		private Drawable bg;
		
		public CountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		/**
		 * 
		 * @param millisInFuture	倒计时开始时间
		 * @param countDownInterval	时间间隔
		 * @param textView	文本框
		 * @param bg	文本样式
		 */
		public CountDown(long millisInFuture, long countDownInterval, TextView textView, Drawable bg){
			super(millisInFuture, countDownInterval);
			this.textView = textView;
			this.bg = bg;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			textView.setText(millisUntilFinished/1000 + "秒后重新获取验证码");
			textView.setClickable(false);
			textView.setBackgroundColor(Color.GRAY);
		}

		@SuppressLint("NewApi")
		@Override
		public void onFinish() {
			textView.setText("重新获取验证码");
			textView.setBackground(bg);
			textView.setClickable(true);
		}
		
	}
}
