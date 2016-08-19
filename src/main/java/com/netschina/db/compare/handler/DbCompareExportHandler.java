package com.netschina.db.compare.handler;

import java.util.Properties;

/**
 * 数据库对比和导出工具处理类接口
 * @author Ouyang
 *
 */
public interface DbCompareExportHandler {

	/**
	 * 导出库间表修改与字段信息与数据初始化对比结果compare-db-result.txt
	 * <li>获取数据库Db对象信息</li>
	 * <li>根据获取数据库Db信息对比表结构</li>
	 * <li>根据获取数据库Db信息对比数据</li>
	 * <li>对比结果输出</li>
	 * @author Ouyang
	 */
	public void exportCompareDbInfo();
	/**
	 * 导出库内公司间数据初始化对比结果compare-company-init-result.txt
	 * @author Ouyang
	 */
	public void exportCompareCompanyInitInfo();
	/**
	 * 导出表信息对象集合与数据信息集合 base.dat goal.dat
	 * @author Ouyang
	 */
	public void exportInfoData();
	/**
	 * 导出初始化表结构与数据sql base-table.sql,base-init-data.sql goal-table.sql,goal-init-data.sql
	 * @author Ouyang
	 */
	public void exportSql();
}
