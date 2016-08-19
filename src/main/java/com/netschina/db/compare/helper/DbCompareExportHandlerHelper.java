package com.netschina.db.compare.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.netschina.db.compare.vo.Db;
import com.netschina.db.compare.vo.DbColumn;
import com.netschina.db.compare.vo.DbTable;
import com.netschina.db.compare.vo.DbTableDataInfo;
import com.netschina.db.compare.vo.DbTableRecordDataInfo;
import com.netschina.db.compare.vo.Result;
import com.netschina.db.compare.vo.ResultColumn;
import com.netschina.db.compare.vo.ResultDataSql;
import com.netschina.db.compare.vo.ResultProperty;
import com.netschina.db.compare.vo.ResultTable;
import com.netschina.db.compare.vo.ResultTableDataInfo;
import com.netschina.db.compare.vo.ResultTableRecordDataInfo;
import com.netschina.db.compare.vo.ResultTableSql;
import com.netschina.db.util.BeanFactoryUtil;
import com.netschina.db.util.CodeName;
import com.netschina.db.util.DbUtil;
import com.netschina.db.util.StringUtil;

/**
 * 辅助DbCompareExportHandler这个类来处理计算逻辑
 * 
 * @author Ouyang
 * 
 */
public class DbCompareExportHandlerHelper {
	
	private FileHelper fileHelper = new FileHelper();
	private QueryHelper queryHelper = BeanFactoryUtil.getQueryHelper();
	
	/**
	 * 获取基础库信息 从数据库中获取
	 * @Description: 
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午8:30:13
	 * @param prop
	 * @return
	 */
	public Db getBaseDbInfoFromDb(Properties prop) {
		String instanceName = prop.getProperty("base.db.name");
		String url = prop.getProperty("base.db.url");
		String user = prop.getProperty("base.db.user");
		//简单判断一下是否没有基础库的配置 没有则返回空
		if(StringUtil.isEmpty(instanceName) || StringUtil.isEmpty(url) || StringUtil.isEmpty(user)) {
			return null;
		}
		Connection conn = DbUtil.getBaseDbConnection(prop);
		if(conn == null){
			return null;
		}
		String defaultCompanyId = prop.getProperty("base.db.company.id");
		return getDbInfoFromDb(conn, instanceName, defaultCompanyId);
	}
	
	/**
	 * 获取目标库信息 从数据库中获取
	 * @Description: 
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午8:30:43
	 * @param prop
	 * @return
	 */
	public Db getGoalDbInfoFromDb(Properties prop) {
		String instanceName = prop.getProperty("goal.db.name");
		String url = prop.getProperty("goal.db.url");
		String user = prop.getProperty("goal.db.user");
		//简单判断一下是否没有基础库的配置 没有则返回空
		if(StringUtil.isEmpty(instanceName) || StringUtil.isEmpty(url) || StringUtil.isEmpty(user)) {
			return null;
		}
		Connection conn = DbUtil.getGoalDbConnection(prop);
		if(conn == null){
			return null;
		}
		String defaultCompanyId = prop.getProperty("goal.db.company.id");
		return getDbInfoFromDb(conn, instanceName, defaultCompanyId);
	}
	
	/**
	  * @Discription:获取数据库信息base Db，来源有可能是file or db
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 上午11:33:46
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 上午11:33:46
	 */
	public Db getBaseDbInfo(Properties prop) {
		if ("file".equals(prop.getProperty("base.source.type"))) {
			return getDbInfoFromFile(prop.getProperty("import.folder"),
					"base.dat");
		} else if ("db".equals((String) prop.getProperty("base.source.type"))) {
			// 获取数据库信息base Db
			String instanceName = prop.getProperty("base.db.name");
			Connection conn = DbUtil.getBaseDbConnection(prop);
			String defaultCompanyId = prop.getProperty("base.db.company.id");
			return getDbInfoFromDb(conn, instanceName, defaultCompanyId);
		}
		throw new RuntimeException(
				"配置文件compare-db-info.properties没有配置属性base.source.type或者配置不对！");
	}
	
	
	/**
	  * @Discription:获取数据库信息goal Db，来源有可能是file or db
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 上午11:33:46
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 上午11:33:46
	 */
	public Db getGoalDbInfo(Properties prop) {
		if ("file".equals(prop.getProperty("goal.source.type"))) {
			return getDbInfoFromFile(prop.getProperty("import.folder"),
					"goal.dat");
		} else if ("db".equals((String) prop.getProperty("goal.source.type"))) {
			// 获取数据库信息goal Db
			String instanceName = prop.getProperty("goal.db.name");
			Connection conn = DbUtil.getGoalDbConnection(prop);
			String defaultCompanyId = prop.getProperty("goal.db.company.id");
			return getDbInfoFromDb(conn, instanceName, defaultCompanyId);
		}
		throw new RuntimeException(
				"配置文件compare-db-info.properties没有配置属性base.source.type或者配置不对！");
	}
	
	/**
	 * 获取默认公司初始化数据
	 * @Description: 
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午9:13:45
	 * @param prop
	 * @return
	 */
	public Db getDefaultCompanyInitData(Properties prop){
		String instanceName = prop.getProperty("db.name");
		Connection conn = DbUtil.getDbConnection(prop);
		String companyId = prop.getProperty("default.company.id");
		try{
			return queryHelper.getDbTableDataInfoIntoDbForInner(conn, instanceName, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取目标公司初始化数据
	 * @Description: 
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午9:14:03
	 * @param prop
	 * @return
	 */
	public Db getGoalCompanyInitData(Properties prop){
		String instanceName = prop.getProperty("db.name");
		Connection conn = DbUtil.getDbConnection(prop);
		String companyId = prop.getProperty("goal.company.id");
		try{
			return queryHelper.getDbTableDataInfoIntoDbForInner(conn, instanceName, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Discription:对比数据库结构信息
	 * @Author: zhouhezhen
	 * @Date: 2015年5月6日 上午9:59:46
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月6日 上午9:59:46
	 */
	public Result compareDbStructure(Db baseDb, Db goalDb, Result result) {
		if (result == null) {
			result = new Result();
		}
		// 增加的表集合
		List<ResultTable> moreResultTables = new ArrayList<ResultTable>();
		// 减少的表集合
		List<ResultTable> lessResultTables = new ArrayList<ResultTable>();
		// 修改的表集合
		List<ResultTable> modifyResultTables = new ArrayList<ResultTable>();
		// 保持不变的表集合
		Map<String, String> mapSameResultTable = new HashMap<String, String>();
		//对比baseDb和goalDb获取增加的表，修改的个表，相同的表
		compareMoreAndModifyTable(baseDb, goalDb, moreResultTables, modifyResultTables, mapSameResultTable);
		//对比减少的表
		compareLessTable(baseDb, goalDb, lessResultTables);
		
		result.setMoreResultTables(moreResultTables);
		result.setLessResultTables(lessResultTables);
		result.setModifyResultTables(modifyResultTables);
		result.setMapSameResultTable(mapSameResultTable);
		return result;
	}


	/**
	 * 获取减少的表 比较的是表结构
	 * @param baseDb
	 * @param goalDb
	 * @param lessResultTables
	 */
	private void compareLessTable(Db baseDb, Db goalDb,
			List<ResultTable> lessResultTables) {
		List<DbTable> baseTables = baseDb.getTables();
		Map<String, DbTable> mapGoalTables = goalDb.getMapTable();
		String tempName = null;
		for (DbTable baseTable : baseTables) {
			tempName = baseTable.getName();
			if (!mapGoalTables.containsKey(tempName)) {
				ResultTable resultTable = new ResultTable();
				resultTable.setName(baseTable.getName());
				resultTable.setComment(baseTable.getComment());
				lessResultTables.add(resultTable);
			}

		}
	}


	/**
	 * 对比baseDb和goalDb获取增加的表，修改的个表，相同的表  比较的是表结构
	 * @param baseDb
	 * @param goalDb
	 * @param moreResultTables
	 * @param modifyResultTables
	 * @param map
	 */
	private void compareMoreAndModifyTable(Db baseDb, Db goalDb,
			List<ResultTable> moreResultTables,
			List<ResultTable> modifyResultTables, Map<String, String> mapSameResultTable) {
		//目标表集合
		List<DbTable> goalTables = goalDb.getTables();
		Map<String, DbTable> mapBaseTables = baseDb.getMapTable();
		String tempName = null;
		for (DbTable goalTable : goalTables) {
			tempName = goalTable.getName();
			// true,即存在相同的表,false,存在增加的表
			if (mapBaseTables.containsKey(tempName)) {
				DbTable baseTable = mapBaseTables.get(tempName);
				//对比修改的表
				ResultTable modifyTableResult = compareModifyTable(baseTable, goalTable);
				
				if (modifyTableResult != null && modifyTableResult.isModify()) {
					//添加修改过的表
					modifyResultTables.add(modifyTableResult);
				} else {
					//保持不变的表
					mapSameResultTable.put(tempName, "");
				}
			} 
			/**增加的表*/
			else {
				ResultTable resultTable = new ResultTable();
				resultTable.setName(tempName);
				resultTable.setComment(goalTable.getComment());
				moreResultTables.add(resultTable);
			}
		}
	}



	/**
	 * 比较修改的表  字段对比	比较的是表结构
	 * @param baseTable	基础表
	 * @param goalTable 目标表
	 * @return
	 */
	private ResultTable compareModifyTable(DbTable baseTable, DbTable goalTable) {

		ResultTable modifyTable = new ResultTable();
		List<ResultColumn> modifyResultColumns = new ArrayList<ResultColumn>();
		List<ResultColumn> moreResultColumns = new ArrayList<ResultColumn>();
		List<ResultColumn> lessResultColumns = new ArrayList<ResultColumn>();
		modifyTable.setLessResultColumns(lessResultColumns);
		modifyTable.setModifyResultColumns(modifyResultColumns);
		modifyTable.setMoreResultColumns(moreResultColumns);
		modifyTable.setName(goalTable.getName());
		modifyTable.setComment(goalTable.getComment());
		//表修改特性
		List<ResultProperty> tResultPropertys = new ArrayList<ResultProperty>();
		modifyTable.setResultPropertys(tResultPropertys);
		
		Map<String, DbColumn> mapColumn = baseTable.getMapColumn();

		for (DbColumn goalColumn : goalTable.getColumns()) {
			/**判断是否有修改字段*/
			if (mapColumn.containsKey(goalColumn.getName())) {
				List<ResultProperty> resultPropertys = new ArrayList<ResultProperty>();
				DbColumn baseColumn = mapColumn.get(goalColumn.getName());
				//判断是否修改了字段
				for(CodeName codeName: DbUtil.getColumnCodeNames()){
					boolean isModifyColumn = false;
					String value = goalColumn.get(codeName.getCode());
					if(value == null){
						if(baseColumn.get(codeName.getCode()) != null){
							isModifyColumn = true;
						}
					} else {
						if(!value.equals(baseColumn.get(codeName.getCode()))){
							isModifyColumn = true;
						}
					}
					/**字段被修改，存储修改前的值，修改后的值*/
					if(isModifyColumn){
						ResultProperty resultProperty = new ResultProperty();
						resultProperty.setName(codeName.getName());
						resultProperty.setOldValue(baseColumn.get(codeName.getCode()));
						resultProperty.setNewValue(goalColumn.get(codeName.getCode()));
						resultPropertys.add(resultProperty);
					}
				}
				/**有修改字段*/
				if(resultPropertys.size()>0){
					ResultColumn modifyColumn = new ResultColumn();
					modifyColumn.setName(goalColumn.getName());
					modifyColumn.setResultPropertys(resultPropertys);
					modifyResultColumns.add(modifyColumn);
					modifyTable.setModify(true);
				}
			}
			/**有多字段*/
			else {
				modifyTable.setModify(true);
				ResultColumn resultColumn = new ResultColumn();
				resultColumn.setMapProperty(goalColumn.getMapProperty());
				resultColumn.setName(goalColumn.getName());
				moreResultColumns.add(resultColumn);
			}
		}
		/**计算少字段的情况*/
		mapColumn = goalTable.getMapColumn();
		for (DbColumn baseColumn : baseTable.getColumns()) {
			if(!mapColumn.containsKey(baseColumn.getName())){/**有少字段*/
				modifyTable.setModify(true);
				ResultColumn resultColumn = new ResultColumn();
				resultColumn.setMapProperty(baseColumn.getMapProperty());
				resultColumn.setName(baseColumn.getName());
				lessResultColumns.add(resultColumn);
			}
		}
		
		/**计算修改表注释的情况*/
		String comment = goalTable.getComment();
		boolean isModifyComment = false;
		if(comment == null){
			if(baseTable.getComment() != null){
				isModifyComment = true;
			}
		} else {
			if(!comment.equals(baseTable.getComment())){
				isModifyComment = true;
			}
		}
		if(isModifyComment) {
			modifyTable.setModify(true);
			ResultProperty resultProperty = new ResultProperty();
			resultProperty.setName("注释");
			resultProperty.setOldValue(baseTable.getComment());
			resultProperty.setNewValue(goalTable.getComment());
			tResultPropertys.add(resultProperty);
		}
		
		return modifyTable;
	}

	/**
	 * 对比数据库初始化数据信息
	 * @param baseDb 基础库信息
	 * @param goalDb 目标库信息
	 * @param result 结果对象 用于包装输出结果
	 * @param type 类型，有库间：db-among 库内：db-inner
	 * @return Result 结果对象 用于包装输出结果
	 */
	public Result compareDbInitData(
			Db baseDb, Db goalDb, Result result, String type) {

		if(result == null){
			throw new RuntimeException("对比表结构信息没有出结果！");
		}
		Map<String, String> mapSameResultTable = result.getMapSameResultTable();
		List<ResultTableDataInfo> resultTableInfos = new ArrayList<ResultTableDataInfo>();
		result.setResultTableInfos(resultTableInfos);
		
		List<DbTableDataInfo> goalTableDataInfos = goalDb.getTableDataInfos();
		Map<String, DbTableDataInfo> mapBaseTableDataInfo = baseDb.getMapTableDataInfo();
		Map<String, List<String>> mapTablePks = goalDb.getMapTablePks();
		for(DbTableDataInfo goalTableDataInfo: goalTableDataInfos){
			String tableName = goalTableDataInfo.getName();
			//库间情况：相同的表中存在目标表，则对比base库和goal库中初始化数据信息
			//或者库内情况：本身就是相同的表所以不要管相同的表
			if(mapSameResultTable.containsKey(tableName) || "db-inner".equals(type)){	
				ResultTableDataInfo resultTableInfo = compareInitData(goalTableDataInfo, mapBaseTableDataInfo.get(tableName), mapTablePks.get(tableName));
				//被修改，则添加
				if(resultTableInfo != null && resultTableInfo.isModify()){
					resultTableInfos.add(resultTableInfo);
				}
			}
		}
		return result;
	}
	
	/**
	  * @Discription:对比有初始化数据的相同表的数据信息
	  * @return ResultTableDataInfo
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 下午1:44:09
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 下午1:44:09
	 */
	private ResultTableDataInfo compareInitData(DbTableDataInfo goalTableDataInfo,
			DbTableDataInfo baseTableDataInfo, List<String> pks) {
		ResultTableDataInfo resultTableInfo = new ResultTableDataInfo();
		resultTableInfo.setName(goalTableDataInfo.getName());
		resultTableInfo.setFields(goalTableDataInfo.getFields());
		resultTableInfo.setComment(goalTableDataInfo.getComment());
		
		List<DbTableRecordDataInfo> baseRecords = baseTableDataInfo.getRecords();
		Map<String, DbTableRecordDataInfo> mapBaseRecord = baseTableDataInfo.getMapRecord();
		List<DbTableRecordDataInfo> goalRecords = goalTableDataInfo.getRecords();
		Map<String, DbTableRecordDataInfo> mapGoalRecord = goalTableDataInfo.getMapRecord();
		//对比并存储增加和修改的表记录数据
		compareMoreAndModifyRecord(goalRecords, mapBaseRecord, resultTableInfo, pks);
		//对比并存储减少的表记录数据
		compareLessRecord(baseRecords, mapGoalRecord, resultTableInfo, pks);
		
		return resultTableInfo;
	}

	/**
	  * @Discription:对比存储减少的记录数据信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 下午1:54:37
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 下午1:54:37
	 */
	private void compareLessRecord(List<DbTableRecordDataInfo> baseRecords,
			Map<String, DbTableRecordDataInfo> mapGoalRecord,
			ResultTableDataInfo resultTableInfo, List<String> pks) {
		List<ResultTableRecordDataInfo> lessRecords = new ArrayList<ResultTableRecordDataInfo>();
		resultTableInfo.setLessRecords(lessRecords);
		for(DbTableRecordDataInfo record : baseRecords){
			String key = "";
			Map<String, String> mapField = record.getMapField();
			if(pks != null){
				for(String pk : pks){
					key += mapField.get(pk) + "_";
				}
			} else {
				List<String> fields = resultTableInfo.getFields();
				for(String field : fields){
					key += mapField.get(field) + "_";
				}
			}
			/*** 有减少的记录*/
			if(!mapGoalRecord.containsKey(key)) {
				resultTableInfo.setModify(true);
				ResultTableRecordDataInfo resultRecord = new ResultTableRecordDataInfo();
				resultRecord.setMapField(record.getMapField());
				lessRecords.add(resultRecord);
			}
		}
	}
	
	/**
	  * @Discription:获取修改的和增加的表数据记录信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 下午1:46:24
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 下午1:46:24
	 */
	private void compareMoreAndModifyRecord(
			List<DbTableRecordDataInfo> goalRecords,
			Map<String, DbTableRecordDataInfo> mapBaseRecord,
			ResultTableDataInfo resultTableInfo, List<String> pks) {
		List<ResultTableRecordDataInfo> moreRecords = new ArrayList<ResultTableRecordDataInfo>();
		List<ResultTableRecordDataInfo> modifyRecords = new ArrayList<ResultTableRecordDataInfo>();
		resultTableInfo.setMoreRecords(moreRecords);
		resultTableInfo.setModifyRecords(modifyRecords);
		for(DbTableRecordDataInfo record : goalRecords){
			//获取每条记录的主键，拼接起来作为唯一标识，没有主键则拼接所有字段作为标识
			String key = "";
			Map<String, String> mapField = record.getMapField();
			if(pks != null){
				for(String pk : pks){
					key += mapField.get(pk) + "_";
				}
			} else {
				List<String> fields = resultTableInfo.getFields();
				for(String field : fields){
					key += mapField.get(field) + "_";
				}
			}
			//是否有修改记录
			if(mapBaseRecord.containsKey(key)) {
				DbTableRecordDataInfo baseRecord = mapBaseRecord.get(key);
				Map<String, String> mapBaseField = baseRecord.getMapField();
				List<String> fields = resultTableInfo.getFields();
				List<ResultProperty> resultPropertys = new ArrayList<ResultProperty>();
				for(String field : fields){
					String oldValue = mapBaseField.get(field);
					String newValue = mapField.get(field);
					boolean diff = false;
					if(oldValue == null){
						if(newValue != null){
							diff = true;
						}
					}else{
						if(!oldValue.equals(newValue)){
							diff = true;
						}
					}
					/**被修改，存储修改前的值，修改后的值*/
					if(diff){
						resultTableInfo.setModify(true);
						ResultProperty resultProperty = new ResultProperty();
						resultProperty.setName(field);
						resultProperty.setOldValue(oldValue);
						resultProperty.setNewValue(newValue);
						resultPropertys.add(resultProperty);
					}
				}
				/**有修改记录*/
				if(resultPropertys.size() > 0){
					resultTableInfo.setModify(true);
					ResultTableRecordDataInfo resultRecord = new ResultTableRecordDataInfo();
					String primaryKey = mapField.get("ID");
					if(!StringUtil.isEmpty(primaryKey)){
						resultRecord.setPrimaryKey(primaryKey);
					}
					resultRecord.setResultPropertys(resultPropertys);
					modifyRecords.add(resultRecord);
				}
			}
			//增加的记录
			else {
				resultTableInfo.setModify(true);
				ResultTableRecordDataInfo resultRecord = new ResultTableRecordDataInfo();
				resultRecord.setMapField(record.getMapField());
				moreRecords.add(resultRecord);
			}
		}
	}


	/**
	 * @Discription:获取数据库信息 从数据库中获取
	 * @param conn			
	 * @param instanceName 数据库实例名
	 * @param companyId	公司id
	 * @return Db
	 * @Author: zhouhezhen
	 * @Date: 2015年5月6日 上午9:53:35
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月6日 上午9:53:35
	 */
	private Db getDbInfoFromDb(Connection conn, String instanceName, String companyId) {
		try {
			return queryHelper.getDbInfoFromDb(conn, instanceName, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取数据库信息 从文件中获取
	 * @Description: 
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午9:18:20
	 * @param folder
	 * @param fileName
	 * @return
	 */
	private Db getDbInfoFromFile(String folder, String fileName) {
		// TODO Auto-generated method stub
		return (Db) fileHelper.readFileForObject(folder + "/" + fileName);
	}

	/**
	 * 
	 * 方法说明
	 * 
	 * @Discription:获取初始化表sql
	 * @Author: zhouhezhen
	 * @Date: 2015年5月5日 下午2:36:38
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月5日 下午2:36:38
	 */
	public List<ResultTableSql> getSqlList(List<DbTableDataInfo> dbTableDataInfo,
			Properties prop, String dbType) {
		List<ResultTableSql> resultSqlList = new ArrayList<ResultTableSql>();
		Connection conn = null;
		Statement statment = null;
		ResultSet rs = null;
		if (("base").equals(dbType)) {
			conn = DbUtil.getBaseDbConnection(prop);
		} else {
			conn = DbUtil.getGoalDbConnection(prop);
		}
		try {
			
//			statment = conn.createStatement();
			for (DbTableDataInfo dbTableData : dbTableDataInfo) {
				String sql = "show create table " + dbTableData.getName();
				PreparedStatement ps = conn.prepareStatement("show create table "+dbTableData.getName());
				ps.setFetchSize(10);
				ResultTableSql resultSql = new ResultTableSql();
//				rs = statment.executeQuery(sql);
				rs=ps.executeQuery();
				while (rs.next()) {
					resultSql.setName(dbTableData.getName());
					resultSql.setSql(rs.getString(2));
				}
				resultSqlList.add(resultSql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				statment.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return resultSqlList;
	}

	/**
	 * 方法说明
	 * 
	 * @Discription:扩展说明
	 * @param tables
	 * @param prop
	 * @param string
	 * @return
	 * @return List<ResultTableInfo>
	 * @Author: zhouhezhen
	 * @Date: 2015年5月5日 下午3:45:48
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月5日 下午3:45:48
	 */
	public List<ResultDataSql> getInitData(List<DbTableDataInfo> dbTableDataInfo,
			Properties prop, String dbType) {
		List<ResultDataSql> resultSqlList = new ArrayList<ResultDataSql>();
		
		Connection conn = null;
		Statement statment = null;
		ResultSet rs = null;
		String company_id=null;
		if (("base").equals(dbType)) {
			conn = DbUtil.getBaseDbConnection(prop);
			company_id=prop.getProperty("base.db.default.company.id");
		} else {
			conn = DbUtil.getGoalDbConnection(prop);
			company_id=prop.getProperty("goal.db.default.company.id");
		}
		int count = 0;
		
		try {
//			statment = conn.createStatement();
			for (DbTableDataInfo dbTableData  : dbTableDataInfo) {
				ResultDataSql resultSql = new ResultDataSql();
				count += 1;
				PreparedStatement ps = conn.prepareStatement("select * from "+dbTableData.getName()+" where init_flag=? and company_id=?");
//				rs = statment.executeQuery(sql);
				ps.setFetchSize(10);
				ps.setString(1,"Y");
				ps.setString(2,company_id);
				rs = ps.executeQuery();
				rs.last();
				String[] str =null;
//				if(rs.getRow()>0){
//					 str = new String[rs.getRow()-1];
//				}else{
//					 str = new String[rs.getRow()];
//				}
				 str =new String[rs.getRow()];
				// System.out.println(str.length);
				int j = 0;
				rs.first();
				if(str.length>0){
				 do{
					String resultSql_Start = "insert into " + dbTableData.getName()
							+ " values (";
					String resultSql_End = ");";
					for (int i = 0; i < dbTableData.getFields().size(); i++) {
//						System.out.println(dbTable.getColumns().get(i).getName());
						resultSql_Start = resultSql_Start +"'"+ rs.getString(i + 1) +"'"
								+ " ,";
					}
					resultSql_Start = resultSql_Start.substring(0,
							resultSql_Start.length() - 1);
					resultSql_End = resultSql_Start + resultSql_End;
					str[j] = resultSql_End;
					resultSql_Start = null;
					resultSql_End = null;
					j++;

				}while(rs.next());
				}
				resultSql.setDataSql(str);
				resultSql.setName(dbTableData.getName());
				resultSqlList.add(resultSql);
			
			}

		} catch (Exception e) {
			System.out.println("在分析===========" + count
					+ "表中,由于数据初始化异常，程序已经终止!");
			e.printStackTrace();
		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != statment)
					statment.close();
				if (null != conn)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSqlList;
	}

}
