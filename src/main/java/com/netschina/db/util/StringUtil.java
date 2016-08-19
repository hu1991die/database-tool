package com.netschina.db.util;

public class StringUtil {

	/**
	 * 
	 * @Description: 判断是字符串是否为空
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午8:24:50
	 * @param str 判断字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		if(str == null){
			return true;
		}
		if(str.trim().length() <= 0){
			return true;
		}
		return false;
	}
	
}
