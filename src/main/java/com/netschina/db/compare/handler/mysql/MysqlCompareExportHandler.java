package com.netschina.db.compare.handler.mysql;

import java.util.List;
import java.util.Properties;

import com.netschina.db.compare.handler.AbstractDbCompareExportHandler;
import com.netschina.db.compare.helper.DbCompareExportHandlerHelper;
import com.netschina.db.compare.helper.FileHelper;
import com.netschina.db.compare.vo.Db;
import com.netschina.db.compare.vo.Result;
import com.netschina.db.compare.vo.ResultDataSql;
import com.netschina.db.compare.vo.ResultTableSql;
import com.netschina.db.util.PropertiesUtil;

public class MysqlCompareExportHandler extends AbstractDbCompareExportHandler {

	DbCompareExportHandlerHelper helper = new DbCompareExportHandlerHelper();
	FileHelper fileWriter=new FileHelper();
	
	/**
	 * 
	  * 对比数据库信息(表结构与初始化数据)并导出
	  * @Discription:库间对比信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:47:13
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:47:13
	  * @see com.netschina.db.compare.handler.DbCompareExportHandler#exportCompareDbInfo(java.util.Properties)
	 */
	public void exportCompareDbInfo() {
		Properties prop = PropertiesUtil.getProp();
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		Result result = helper.compareDbStructure(baseDb, goalDb, null);	//2 对比数据库结构信息
		result= helper.compareDbInitData(baseDb, goalDb, result, "db-among");	//3 对比数据库初始化数据信息
		fileWriter.writeCompareDbInfoResult(result, prop.getProperty("export.folder")+"\\compare-db-result.txt");	//4 输出对比结果信息
	}
	

	/**
	  * @Discription:库内公司对比信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:50:21
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:50:21
	  * @see com.netschina.db.compare.handler.DbCompareExportHandler#exportCompareCompanyInitInfo(java.util.Properties)
	 */
	public void exportCompareCompanyInitInfo() {
		Properties prop = PropertiesUtil.getProp();
		Db defaultInitDataDb = helper.getDefaultCompanyInitData(prop);	//1获取库内默认公司初始化数据
		Db goalInitDataDb = helper.getGoalCompanyInitData(prop);  //1获取库内目标公司初始化数据
		Result result = new Result();
		result= helper.compareDbInitData(defaultInitDataDb, goalInitDataDb, result, "db-inner");	//2 对比数据库初始化数据信息
		fileWriter.writeCompareDbInfoResult(result, prop.getProperty("export.folder")+"\\compare-company-init-result.txt");
	}

	public void exportInfoData() {
		// TODO Auto-generated method stub
		Properties prop = PropertiesUtil.getProp();
		Db baseDb = helper.getBaseDbInfoFromDb(prop);	//获取base db数据库信息  从数据库中获取
		if(baseDb != null) { //不为空则代表存在 要输出基础库 以下为输出
			fileWriter.writeFileForObject(baseDb, prop.getProperty("export.folder")+"\\base.dat");
		}
		Db goalDb = helper.getGoalDbInfoFromDb(prop);   //获取goal db数据库信息  从数据库中获取
		if(goalDb != null) { //不为空则代表存在 要输出目标库 以下为输出
			fileWriter.writeFileForObject(goalDb, prop.getProperty("export.folder")+"\\goal.dat");
		}
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:导出初始化表结构与数据
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:50:48
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:50:48
	  * @see com.netschina.db.compare.handler.DbCompareExportHandler#exportSql(java.util.Properties)
	 */
	public void exportSql() {
		// TODO Auto-generated method stub
		Properties prop = PropertiesUtil.getProp();
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		Result result = new Result();
		List<ResultTableSql> baseTableSql = helper.getSqlList(baseDb.getTableDataInfos(),prop,"base");
		List<ResultTableSql> goalTableSql = helper.getSqlList(goalDb.getTableDataInfos(),prop,"goal");
		List<ResultDataSql> baseInitSql =helper.getInitData(baseDb.getTableDataInfos(),prop,"base");
		List<ResultDataSql> goalInitSql =helper.getInitData(goalDb.getTableDataInfos(),prop,"goal");
		result.setBaseTableSql(baseTableSql);
		result.setGoalTableSql(goalTableSql);
		result.setBaseInitSql(baseInitSql);
		result.setGoalInitSql(goalInitSql);
		System.out.println(baseTableSql.size());
		fileWriter.writeExportDbSql(result,(String)prop.get("export.folder"));
	}

}
