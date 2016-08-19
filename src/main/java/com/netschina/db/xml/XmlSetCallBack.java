package com.netschina.db.xml;

/**
 * 用于Xstream的元素属性设置 解析xml文件
 * @Author: Ouyang
 * @Date: 2014-12-30 下午03:36:33 
 * @ModifyUser: Ouyang
 * @ModifyDate: 2014-12-30 下午03:36:33 
 * @Version:V7.5
 */
public interface XmlSetCallBack {

	/**
	 * 配置xstream元素及属性
	 * @param obj
	 * @Author: Ouyang
	 * @Date: 2014-12-30 下午04:01:26
	 * @ModifyUser：Ouyang
	 * @ModifyDate: 2014-12-30 下午04:01:26
	 */
	void config(Object obj);
}
