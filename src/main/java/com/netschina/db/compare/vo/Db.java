package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据库对应所有表和字段信息 用于查询结果包装
 * @author Ouyang
 *
 */
public class Db implements Serializable {

	private List<DbTable> tables;    //表集合
	private Map<String, DbTable> mapTable;	//表集合map方式 Map<tableName, DbTable>
	private List<DbTableDataInfo> tableDataInfos; //表数据信息
	private Map<String, DbTableDataInfo> mapTableDataInfo; //表数据信息map方式
	private Map<String, List<String>> mapTablePks;	//存储init_flag表的主键
	public Map<String, List<String>> getMapTablePks() {
		return mapTablePks;
	}
	public void setMapTablePks(Map<String, List<String>> mapTablePks) {
		this.mapTablePks = mapTablePks;
	}
	public List<DbTable> getTables() {
		return tables;
	}
	public void setTables(List<DbTable> tables) {
		this.tables = tables;
	}
	public Map<String, DbTable> getMapTable() {
		return mapTable;
	}
	public void setMapTable(Map<String, DbTable> mapTable) {
		this.mapTable = mapTable;
	}
//	public static void main(String[] args) {
//		List<Table> goalTables = new ArrayList<Table>();
//		Map<String, Table> mapTable = new HashMap<String, Table>();
//		for(Table tb: goalTables){
//			Table tb2 = mapTable.get(tb.getName());
//			if(tb2 != null){
//				com
//			}
//		}
//	}
	public List<DbTableDataInfo> getTableDataInfos() {
		return tableDataInfos;
	}
	public void setTableDataInfos(List<DbTableDataInfo> tableDataInfos) {
		this.tableDataInfos = tableDataInfos;
	}
	public Map<String, DbTableDataInfo> getMapTableDataInfo() {
		return mapTableDataInfo;
	}
	public void setMapTableDataInfo(Map<String, DbTableDataInfo> mapTableDataInfo) {
		this.mapTableDataInfo = mapTableDataInfo;
	}
}
