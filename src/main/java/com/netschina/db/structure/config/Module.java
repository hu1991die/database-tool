package com.netschina.db.structure.config;
/**
 * 模块类
 * @Author: feizi
 * @Date: 2015年6月9日 下午4:15:57 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月9日 下午4:15:57 
 * @Version:V6.0
 */
public class Module {

	//模块名称
	private String name;
	//导出类型
	private String type;
	//导出表
	private String table;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}
