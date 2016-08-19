package com.netschina.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author Ouyang
 *
 */
public class PropertiesUtil {
	private static String configFileName;
	private static String path;
	private static Properties prop = null;

	public static void init (String path, String configFileName) {
		PropertiesUtil.path = path;
		PropertiesUtil.configFileName = configFileName;
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取配置文件
	  * @return
	  * @return Properties
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:43:57
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:43:57
	 */
	public static Properties getProp() {
		if(prop != null){
			return prop;
		}
		InputStream is = null;
		try{
			prop = new Properties();
			is = PropertiesUtil.class.getResourceAsStream(PropertiesUtil.path+"/"+PropertiesUtil.configFileName+".properties");
			prop.load(is);
		} catch (Exception e){
			throw new RuntimeException("读取配置文件失败，请检查是否路径不对!");
			//关闭流
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}
