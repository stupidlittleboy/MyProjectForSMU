package com.shmtu.myprojectforsmu.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 搜集所有已经创建过的活动，便于一键退出程序
 * @author admin
 *
 */
public class ActivityCollector {
	
	public static List<Activity> activities = new ArrayList<Activity>();
	
	/**
	 * 添加Activity到list集合里
	 * @param activity
	 */
	public static void addActivity (Activity activity) {
		activities.add(activity);
	}
	
	/**
	 * 从list集合里删除一个Activity
	 * @param activity
	 */
	public static void removeActivity (Activity activity) {
		activities.remove(activity);
	}
	
	/**
	 * 删除所有的Activity
	 */
	public static void finishAll () {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}
