package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//对应记录对象
public class ResultTableRecordDataInfo implements Serializable{
	
	private Map<String, String> mapField;		//存储字段的值 Map<field, value>
	private List<ResultProperty> resultPropertys;	//存储修改过属性的对象 字段名,旧值，新值
	private String primaryKey;		//主键值，即为ID字段的值，如果没有则为空
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public Map<String, String> getMapField() {
		return mapField;
	}
	public void setMapField(Map<String, String> mapField) {
		this.mapField = mapField;
	}
	public List<ResultProperty> getResultPropertys() {
		return resultPropertys;
	}
	public void setResultPropertys(List<ResultProperty> resultPropertys) {
		this.resultPropertys = resultPropertys;
	}
	
}
