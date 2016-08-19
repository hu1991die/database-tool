package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对比结果信息对应字段
 * @author Ouyang
 *
 */
public class ResultColumn implements Serializable {
	private List<ResultProperty> resultPropertys;
	/**存储表属性所有特征*/
	private Map<String, String> mapProperty = new HashMap<String, String>();
	private String name;

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
	public List<ResultProperty> getResultPropertys() {
		return resultPropertys;
	}
	public void setResultPropertys(List<ResultProperty> resultPropertys) {
		this.resultPropertys = resultPropertys;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
