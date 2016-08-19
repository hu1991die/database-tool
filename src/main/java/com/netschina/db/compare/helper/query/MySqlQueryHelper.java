package com.netschina.db.compare.helper.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.netschina.db.compare.helper.QueryHelper;
import com.netschina.db.compare.vo.Db;
import com.netschina.db.compare.vo.DbColumn;
import com.netschina.db.compare.vo.DbTable;
import com.netschina.db.compare.vo.DbTableDataInfo;
import com.netschina.db.compare.vo.DbTableRecordDataInfo;
import com.netschina.db.compare.vo.Result;
import com.netschina.db.compare.vo.ResultColumn;
import com.netschina.db.compare.vo.ResultTable;
import com.netschina.db.compare.vo.ResultTableDataInfo;
import com.netschina.db.compare.vo.ResultTableRecordDataInfo;
import com.netschina.db.util.DbUtil;

/**
 * 处理查询和包装信息类
 * 
 * @author Ouyang
 * 
 */
public class MySqlQueryHelper implements QueryHelper {

	// 查询数据库表
	public final static String QUERY_MYSQLDB_TABLE_NAME_SQL = "SELECT table_name AS tablename, table_comment AS tablecomment FROM "
			+ "information_schema.tables WHERE table_schema= ?";// 数据库实例'saasdbtest'
	// 查询数据库表字段
	public final static String QUERY_MYSQLDB_TABLE_STRUCTURE = "SELECT table_name,"
			+ "column_name,data_type,"
			+ "column_type,column_key,"
			+ "is_nullable, column_comment "
			+ "FROM "
			+ "information_schema.columns " + "WHERE table_schema = ? AND table_name = ?";// 数据库实例名称'saasdbtest'
	public final static String QUERY_MYSQLDB_DEFAULT_TABLE_STRUCTURE = "SELECT table_name,"
			+ "column_name,data_type,"
			+ "column_type,column_key,"
			+ "is_nullable, column_comment "
			+ "FROM "
			+ "information_schema.columns " + "WHERE table_schema = ? AND table_name = ? AND column_name='COMPANY_ID'";// 数据库实例名称'saasdbtest'
	//查询有initflag字段的表名及表注释
	public final static String QUERY_ALL_INIT_FLAG_TABLE = "SELECT table_name AS tableName, table_comment AS comment FROM information_schema.tables "
			+ "WHERE table_schema= ? AND TABLE_NAME IN (SELECT TABLE_NAME as tableName FROM information_schema.columns  "
			+ "where TABLE_SCHEMA=? and COLUMN_NAME=?)";
	public final static String QUERY_ALL_INIT_FLAG_TABLE_PK = "SELECT TABLE_NAME as tableName, COLUMN_NAME as columnName from information_schema.columns where COLUMN_KEY=? "
			+ "and TABLE_NAME in (SELECT TABLE_NAME FROM information_schema.columns  where TABLE_SCHEMA=? and COLUMN_NAME=?) order by TABLE_NAME, COLUMN_NAME";

	/**
	 * 方法说明
	 * @Discription:获取数据库信息base or goal Db
	 * @Author: zhouhezhen
	 * @Date: 2015年5月6日 上午9:57:47
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月6日 上午9:57:47
	 */
	public Db getDbInfoFromDb(Connection conn, String dbName, String companyId) throws Exception {
		Db db = new Db();
		getDbTableIntoDb(db, conn, dbName);
		getDbTableDataInfoIntoDbForAmong(db, conn, dbName, companyId);
		conn.close();
		return db;
	}
	
	private void getDbTableDataInfoIntoDbForAmong(Db db, Connection conn, String dbName, String companyId) throws Exception {
		getDbTableDataInfoIntoDb(db, conn, dbName, companyId, "db-among");
	}
	
	public Db getDbTableDataInfoIntoDbForInner(Connection conn, String dbName, String companyId) throws Exception {
		Db db = new Db();
		getDbTableDataInfoIntoDb(db, conn, dbName, companyId, "db-inner");
		return db;
	}
	
	/**
	 * 
	 * @Description: 获取数据库初始化数据信息
	 * @Author Ouyang
	 * @Date 2015年5月26日 下午6:32:51
	 * @param db	数据库信息对象
	 * @param conn  数据库连接
	 * @param dbName  数据库实例名
	 * @param companyId 公司id
	 * @param type	类型，有库间：db-among 库内：db-inner
	 * @throws Exception 
	 * @return void
	 */
	private void getDbTableDataInfoIntoDb(Db db, Connection conn, String dbName, String companyId, String type) throws Exception {
		//表初始化数据集合 map
		Map<String, DbTableDataInfo> mapTableDataInfo = new HashMap<String, DbTableDataInfo>(); 
		db.setMapTableDataInfo(mapTableDataInfo);
		//表初始化数据集合 list
		List<DbTableDataInfo> tableDataInfos = new ArrayList<DbTableDataInfo>(); 
		db.setTableDataInfos(tableDataInfos);
		//有初始化字段表的主键 用于对数据记录进行对比
		Map<String, List<String>> mapTablePks = getInitFlagTablePk(conn, dbName);
		db.setMapTablePks(mapTablePks);
		
		PreparedStatement ps = conn.prepareStatement(QUERY_ALL_INIT_FLAG_TABLE);
		ps.setFetchSize(100);
		ps.setString(1, dbName);
		ps.setString(2, dbName);
		ps.setString(3, "INIT_FLAG");
		ResultSet rs = ps.executeQuery();
		String tableName = null;
		while (rs.next()) {
			try{
				DbTableDataInfo table = new DbTableDataInfo();
				tableName = rs.getString("tableName");
				table.setName(tableName);
				table.setComment(rs.getString("comment"));
				//根据init_flag和company字段查询所有的表
				PreparedStatement dataPs = conn.prepareStatement("select * from "+tableName+" where init_flag=? and company_id=?");
				dataPs.setFetchSize(10);
				dataPs.setString(1, "Y");
				dataPs.setString(2, companyId);
				ResultSet dataRs = dataPs.executeQuery();
				ResultSetMetaData rsmd = dataPs.getMetaData();
				//字段集合
				List<String> fields = new ArrayList<String>();
				table.setFields(fields);
				int count = rsmd.getColumnCount();
				for(int i=0; i<count; i++){
					if("db-inner".equals(type) && ("COMPANY_ID").equals(rsmd.getColumnName(i+1))){ //库内则不对比company_id字段
						continue;
					}
					fields.add(rsmd.getColumnName(i+1));
				}
				
				transferRecordResultSet(dataRs, table, fields, mapTablePks.get(tableName));
				
				dataRs.close();
				dataPs.close();
				tableDataInfos.add(table);
				mapTableDataInfo.put(tableName, table);
			} catch (Exception e){
				System.out.println("表名："+tableName+" 没有company_id字段..............................");
			}
		}
		rs.close();
		ps.close();
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取含有InitFlag字段的表主键
	  * @return Map<String,List<String>>
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 上午9:02:20
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 上午9:02:20
	 */
	private Map<String, List<String>> getInitFlagTablePk(Connection conn, String dbName) throws Exception {
		PreparedStatement ps = conn.prepareStatement(QUERY_ALL_INIT_FLAG_TABLE_PK);
		ps.setFetchSize(100);
		ps.setString(1, "PRI");
		ps.setString(2, dbName);
		ps.setString(3, "INIT_FLAG");
		ResultSet rs = ps.executeQuery();
		Map<String, List<String>> mapTablePks = new HashMap<String, List<String>>();
		while(rs.next()){
			String tableName = rs.getString("tableName");
			String columnName = rs.getString("columnName");
			List<String> pks = mapTablePks.get(tableName);
			if(pks == null){
				pks = new ArrayList<String>();
				mapTablePks.put(tableName, pks);
			}
			pks.add(columnName);
		}
		return mapTablePks;
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取表中记录,用key来标识每条记录
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 下午1:04:13
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 下午1:04:13
	 */
	private void transferRecordResultSet(ResultSet dataRs,
			DbTableDataInfo table, List<String> fields, List<String> pks) throws Exception {
		List<DbTableRecordDataInfo> records = new ArrayList<DbTableRecordDataInfo>();
		Map<String, DbTableRecordDataInfo> mapRecord = new HashMap<String, DbTableRecordDataInfo>();
		table.setRecords(records);
		table.setMapRecord(mapRecord);
		while(dataRs.next()){
			DbTableRecordDataInfo record = new DbTableRecordDataInfo();
			Map<String, String> mapField = new HashMap<String, String>();
			for(String field: fields){
				mapField.put(field, dataRs.getString(field));
			}
			record.setMapField(mapField);
			records.add(record);
			String key = "";
			//遍历所有主键，将所有主键拼接作为key,key如果为null，则将所有字段拼接作为key
			if(pks != null){
				for(String pk : pks){
					key += mapField.get(pk) + "_";
				}
			} else {
				for(String field: fields){
					key += mapField.get(field) + "_";
				}
			}
			mapRecord.put(key, record);
		}
	}
	
	
	/**
	  * <li>获取所有表</li>
	  * <li>循环获取每个表的所有字段信息</li>
	  * @Discription:获取所有表
	  * @Author: zhouhezhen
	  * @Date: 2015年5月14日 下午6:20:48
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月14日 下午6:20:48
	 */
	private void getDbTableIntoDb(Db db, Connection conn, String dbName) throws Exception {
		String table_sql =QUERY_MYSQLDB_TABLE_NAME_SQL;
		String column_sql = QUERY_MYSQLDB_TABLE_STRUCTURE;
		System.out.println(dbName);
		//对应表与表信息
		Map<String, DbTable> mapTable = new HashMap<String, DbTable>();
		//表信息集合
		List<DbTable> dbTable = new ArrayList<DbTable>();		
		db.setMapTable(mapTable);
		db.setTables(dbTable);
		String tableName = null;
		String tableComment = null;
		PreparedStatement ps = conn.prepareStatement(table_sql);
		ps.setFetchSize(100);
		ps.setString(1, dbName);
		ResultSet rs = ps.executeQuery();
		//获取sql查询的属性列
		String[] columnNames = null;		
		while (rs.next()) {
			DbTable table = new DbTable();
			tableName = rs.getString("tablename");
			table.setName(tableName);
			tableComment = rs.getString("tablecomment");
			table.setComment(tableComment);
			PreparedStatement columnPs = conn.prepareStatement(column_sql);
			columnPs.setFetchSize(100);
			columnPs.setString(1, dbName);
			columnPs.setString(2, tableName);
			ResultSet columnRs = columnPs.executeQuery();
			if(columnNames == null){
				//得到结果集（columnPs）的结构信息	
				ResultSetMetaData rsmd = columnPs.getMetaData(); 
				int count = rsmd.getColumnCount(); 
				columnNames = new String[count];
				for(int i=0; i<count; i++){
					columnNames[i] = rsmd.getColumnName(i+1);
				}
			}
			table = tranferColumnResultSet(columnRs, table, columnNames);
			columnRs.close();
			columnPs.close();
			dbTable.add(table);
			mapTable.put(tableName, table);
		}
		rs.close();
		ps.close();
	}

	/**
	 * @Discription:取得所有表字段 包装到表中
	 * @param rs
	 * @param table
	 * @throws SQLException
	 * @return DbTable
	 * @Author: zhouhezhen
	 * @Date: 2015年5月6日 上午9:58:55
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月6日 上午9:58:55
	 */
	private DbTable tranferColumnResultSet(ResultSet rs, DbTable table, String[] columnNames)
			throws SQLException {
		Map<String, DbColumn> mapDbColumn = new HashMap<String, DbColumn>();
		List<DbColumn> columnList = new ArrayList<DbColumn>();
		while (rs.next()) {
			DbColumn dbColumn = new DbColumn();
			for(int i=0; i<columnNames.length; i++){
				dbColumn.add(columnNames[i], rs.getString(columnNames[i]));
			}
			columnList.add(dbColumn);
			dbColumn.setName(rs.getString("COLUMN_NAME"));
			mapDbColumn.put(dbColumn.getName(), dbColumn);
		}
		table.setColumns(columnList);
		table.setMapColumn(mapDbColumn);
		return table;
	}


	public List<ResultTableDataInfo> getInitCompanyId(Result resultDb,
			String goalDbName, Properties prop, String companyId) {
		List<ResultTableDataInfo> tableInfoList = new ArrayList<ResultTableDataInfo>();
		Connection conn = DbUtil.getGoalDbConnection(prop);
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return tableInfoList;
	}
}
