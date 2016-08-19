package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库对应字段信息 用于查询结果包装
 * @author Ouyang
 *
 */
public class DbColumn implements Serializable {

	/**存储表属性所有特征*/
	private Map<String, String> mapProperty = new HashMap<String, String>();
	private String name; //表名

	public Map<String, String> getMapProperty() {
		return mapProperty;
	}
	public void setMapProperty(Map<String, String> mapProperty) {
		this.mapProperty = mapProperty;
	}
	public void add(String code, String value){
		mapProperty.put(code, value);
		if("name".equals(code)){
			setName(value);
		}
	}
	public String get(String code){
		return mapProperty.get(code);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
