package com.netschina.db.xml;

import java.io.File;
import java.io.InputStream;

import com.thoughtworks.xstream.XStream;

/**
 * 使用Xstream对xml进行解析
 * @Author: Ouyang
 * @Date: 2014-12-30 下午03:54:29 
 * @ModifyUser: Ouyang
 * @ModifyDate: 2014-12-30 下午03:54:29 
 * @Version:V7.5
 */
public class XmlXStreamParser {

	/**
	 * 把java对象转换成字符串
	 * @param obj
	 * @return String
	 * @Author: Ouyang
	 * @Date: 2014-12-30 下午03:54:54
	 * @ModifyUser：Ouyang
	 * @ModifyDate: 2014-12-30 下午03:54:54
	 */
	public String toXML(Object obj) {
		XStream xStream = new XStream();
		return xStream.toXML(obj);
	}
	
	/**
	 * 把xml串转换成java对象
	 * @param xml
	 * @param cls
	 * @param set
	 * @return T
	 * @Author: Ouyang
	 * @Date: 2014-12-30 下午04:02:19
	 * @ModifyUser：Ouyang
	 * @ModifyDate: 2014-12-30 下午04:02:19
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXML(String xml, Class<T> cls, XmlSetCallBack set) {
		XStream xStream = new XStream();
		set.config(xStream);
		return (T) xStream.fromXML(xml);
	}
	
	/**
	 * 把xml文件转换成java对象
	 * @param file
	 * @param cls
	 * @param set
	 * @return T
	 * @Author: Ouyang
	 * @Date: 2014-12-30 下午04:02:54
	 * @ModifyUser：Ouyang
	 * @ModifyDate: 2014-12-30 下午04:02:54
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXML(File file, Class<T> cls, XmlSetCallBack set) {
		XStream xStream = new XStream();
		set.config(xStream);
		return (T) xStream.fromXML(file);
	}
	
	/**
	 * 把输入流转换成java对象
	 * @param is
	 * @param cls
	 * @param set
	 * @return T
	 * @Author: Ouyang
	 * @Date: 2014-12-30 下午04:04:27
	 * @ModifyUser：Ouyang
	 * @ModifyDate: 2014-12-30 下午04:04:27
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXML(InputStream is, Class<T> cls, XmlSetCallBack set) {
		XStream xStream = new XStream();
		set.config(xStream);
		return (T) xStream.fromXML(is);
	}
}
