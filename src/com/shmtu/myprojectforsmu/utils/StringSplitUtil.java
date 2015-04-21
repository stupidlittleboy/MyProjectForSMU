package com.shmtu.myprojectforsmu.utils;

public class StringSplitUtil {

	/**
	 * 截取字符串中所需要的内容
	 * @param str
	 */
	public static String interceptString(String str){
		int index1 = str.lastIndexOf(":");
		int index2 = str.lastIndexOf("：");
		if(index1 > 0){
			str = str.substring(index1+1);
		}else if (index2 > 0){
			str = str.substring(index2+1);
		}
		return str;
	}
}
