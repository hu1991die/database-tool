package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库对应表信息，包括字段信息   用于查询结果包装
 * @author Ouyang
 *
 */
public class DbTable implements Serializable {

	private String name;	//表名称
	private String comment;		//注释，说明
	private int columnCount;	//表总字段数 暂无用
	private List<DbColumn> columns = new ArrayList<DbColumn>();		//定义字段集合
	private Map<String, DbColumn> mapColumn = new HashMap<String, DbColumn>();	//字段集合map方式
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getColumnCount() {
		this.columnCount = columns.size();
		return this.columnCount;
	}
	public List<DbColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DbColumn> columns) {
		this.columns = columns;
	}
	public Map<String, DbColumn> getMapColumn() {
		return mapColumn;
	}
	public void setMapColumn(Map<String, DbColumn> mapColumn) {
		this.mapColumn = mapColumn;
	}
//	public void addColumn(DbColumn column){
//		this.columns.add(column);
//		this.mapColumn.put(column.getName(), column);
//	}
	
}
