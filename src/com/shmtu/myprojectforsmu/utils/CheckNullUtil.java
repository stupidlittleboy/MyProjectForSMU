package com.shmtu.myprojectforsmu.utils;

import android.widget.TextView;

public class CheckNullUtil {
	
	/**
	 * 判断控件内容是否为空
	 * @param view
	 * @return
	 */
	public static boolean checkIsNull (TextView view) {
		if ("".equals(view.getText().toString().trim()) || view.getText() == null) {
			return true;
		}
		return false;
	}

}
