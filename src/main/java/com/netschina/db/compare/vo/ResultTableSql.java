package com.netschina.db.compare.vo;

import java.io.Serializable;

public class ResultTableSql implements Serializable{
	private String name;
	private String sql;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
