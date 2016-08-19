package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对比结果信息对象
 * @author Ouyang
 *
 */
public class Result implements Serializable {

	private List<ResultTable> moreResultTables;		//多的表
	private List<ResultTable> lessResultTables;		//少的表
	private List<ResultTable> modifyResultTables;	//修改的表
	private Map<String, String> mapSameResultTable = new HashMap<String, String>();		//相同表的表名
	private List<ResultTableDataInfo> resultTableInfos;		//初始化表对比记录结果
	
	private List<ResultTableSql> baseTableSql;		//基础表初始化sql语句集合
	private List<ResultTableSql> goalTableSql;		//目标表初始化sql语句集合
	private List<ResultDataSql> baseInitSql;		//基础表数据初始化sql语句集合
	private List<ResultDataSql> goalInitSql;		//目标表数据初始化sql语句集合
	public Map<String, String> getMapSameResultTable() {
		return mapSameResultTable;
	}
	public void setMapSameResultTable(Map<String, String> mapSameResultTable) {
		this.mapSameResultTable = mapSameResultTable;
	}
	public List<ResultTable> getMoreResultTables() {
		return moreResultTables;
	}
	public void setMoreResultTables(List<ResultTable> moreResultTables) {
		this.moreResultTables = moreResultTables;
	}
	public List<ResultTable> getLessResultTables() {
		return lessResultTables;
	}
	public void setLessResultTables(List<ResultTable> lessResultTables) {
		this.lessResultTables = lessResultTables;
	}
	public List<ResultTable> getModifyResultTables() {
		return modifyResultTables;
	}
	public void setModifyResultTables(List<ResultTable> modifyResultTables) {
		this.modifyResultTables = modifyResultTables;
	}
	public List<ResultTableDataInfo> getResultTableInfos() {
		return resultTableInfos;
	}
	public void setResultTableInfos(List<ResultTableDataInfo> resultTableInfos) {
		this.resultTableInfos = resultTableInfos;
	}
	public List<ResultTableSql> getBaseTableSql() {
		return baseTableSql;
	}
	public void setBaseTableSql(List<ResultTableSql> baseTableSql) {
		this.baseTableSql = baseTableSql;
	}
	public List<ResultTableSql> getGoalTableSql() {
		return goalTableSql;
	}
	public void setGoalTableSql(List<ResultTableSql> goalTableSql) {
		this.goalTableSql = goalTableSql;
	}
	public List<ResultDataSql> getBaseInitSql() {
		return baseInitSql;
	}
	public void setBaseInitSql(List<ResultDataSql> baseInitSql) {
		this.baseInitSql = baseInitSql;
	}
	public List<ResultDataSql> getGoalInitSql() {
		return goalInitSql;
	}
	public void setGoalInitSql(List<ResultDataSql> goalInitSql) {
		this.goalInitSql = goalInitSql;
	}
	
	
}
