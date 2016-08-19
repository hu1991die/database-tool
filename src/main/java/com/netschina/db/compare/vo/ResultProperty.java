package com.netschina.db.compare.vo;

/**
 * 对比结果信息对应属性修改信息对象
 * @author Ouyang
 *
 */
public class ResultProperty {

	private String name;	//字段名
	private String oldValue;	//旧值
	private String newValue;	//新值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}
