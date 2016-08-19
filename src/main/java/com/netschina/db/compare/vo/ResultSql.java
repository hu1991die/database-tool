package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.List;

public class ResultSql implements Serializable{
	/**
	 * @Field: serialVersionUID:TODO
	 */
	private static final long 
	serialVersionUID = 1L;
	private List<ResultTableSql> baseTableSql;
	private List<ResultTableSql> goalTableSql;
	private List<ResultDataSql> baseInitSql;
	private List<ResultDataSql> goalInitSql;
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
