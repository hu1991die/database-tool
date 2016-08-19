/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.structure.helper.query.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.netschina.db.structure.config.Module;
import com.netschina.db.structure.helper.query.AbstractQueryDictHelper;
import com.netschina.db.structure.vo.TableColumnInfo;
import com.netschina.db.structure.vo.TableInfo;
import com.netschina.db.util.DbUtil;

/**  
 * 数据查询接口实现类（Mysql数据库）
 * @Author: feizi
 * @Date: 2015年6月4日 下午5:55:22 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月4日 下午5:55:22 
 * @Version:V6.0
 */
public class MysqlQueryDictHelperImpl extends AbstractQueryDictHelper{
	
	// 查询数据库表
	private final static String QUERY_MYSQLDB_TABLE_NAME_SQL = "SELECT table_name AS tablename, table_comment AS tablecomment FROM "
			+ "information_schema.tables WHERE table_schema= ?";// 数据库实例'saasdbtest'

	// 查询数据库表字段
	private final static String QUERY_MYSQLDB_TABLE_STRUCTURE = "SELECT table_name,"
			+ "column_name,data_type,"
			+ "column_type,column_key,"
			+ "is_nullable, column_comment "
			+ "FROM "
			+ "information_schema.columns " + "WHERE table_schema = ? AND table_name = ?";// 数据库实例名称'saasdbtest'
	
	//存放数据表
	private List<TableInfo> tableList = null;
	//存放数据表的表结构信息
	private List<TableColumnInfo> columnList = null;
	//预编译SQL语句对象
	private PreparedStatement pst = null;
	//结果集
	private ResultSet rs = null;
	
	/**
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月9日 上午11:12:15
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月9日 上午11:12:15
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTable(java.sql.Connection, java.lang.String, java.lang.String)
	  */
	public List<TableInfo> queryTable(Connection conn, String dbName,Module module) {
		//获取配置的导出类型
		String type = module.getType();
		
		//判断导出类型
		if("all".equals(type)){
			//查询所有的table表
			tableList = queryTables(conn, dbName);
		}else if("table".equals(type)){
			//表示按照指定的table表名查询
			
			//获取指定的表名称
			String[] tables = module.getTable().split(",");
			tableList = queryTable(conn, dbName, tables);
		}else if("prefix".equals(type)){
			//表示按照指定的table类型查询
			
			//获取表名前缀
			String tableType = module.getTable();
			tableList = queryTables(conn, dbName, tableType);
		}
		
		return tableList;
	}
	
	/**
	 * 按照指定的table表名查询
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月5日 上午9:01:27
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月5日 上午9:01:27
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTable(java.lang.String[])
	  */
	public List<TableInfo> queryTable(Connection conn,String dbName,String[] tables) {
		//查询的SQL语句
		StringBuffer table_sql = new StringBuffer(QUERY_MYSQLDB_TABLE_NAME_SQL);
		table_sql.append(" AND table_name in ( ");
		
		if(null != tables && tables.length > 0){
			int length = tables.length;
			for (int i = 0; i < length; i++) {
				if(i > 0){
					table_sql.append(", ");
				}
				table_sql.append("?");
			}
		}
		
		table_sql.append(" )");
		
		tableList = new ArrayList<TableInfo>();
		TableInfo tableInfo = null;
		
		try {
			pst = conn.prepareStatement(table_sql.toString());
			pst.setString(1, dbName);

			if(null != tables && tables.length > 0){
				for (int i = 0; i < tables.length; i++) {
					pst.setString(i + 2, tables[i]);
				}
			}
			
			rs = pst.executeQuery();
			while(rs.next()){
				tableInfo = new TableInfo();
				tableInfo.setName(rs.getString("tablename"));
				tableInfo.setComment(rs.getString("tablecomment"));
				
				tableList.add(tableInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, pst);
		}
		
		return tableList;
	}
	
	/**
	 * 按照指定的table类型（前缀）查询，比如说exam（考试类型）
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月5日 上午9:01:27
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月5日 上午9:01:27
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTables(java.lang.String, java.lang.String)
	  */
	public List<TableInfo> queryTables(Connection conn,String dbName, String tableType) {
		/** 指定表的类型查询  */
		StringBuffer table_sql = new StringBuffer(QUERY_MYSQLDB_TABLE_NAME_SQL);
		table_sql.append(" AND table_name LIKE ?");
		
		//查询数据库表
		tableList = new ArrayList<TableInfo>();
		TableInfo tableInfo = null;
		try {
			pst = conn.prepareStatement(table_sql.toString());
			pst.setString(1, dbName);
			pst.setString(2, tableType + "%");
			
			rs = pst.executeQuery();
			while(rs.next()){
				tableInfo = new TableInfo();
				tableInfo.setName(rs.getString("tablename"));
				tableInfo.setComment(rs.getString("tablecomment"));
				
				tableList.add(tableInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, pst);
		}
		
		return tableList;
	}
	
	/**
	 * 查询所有的table表集合
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月4日 下午7:42:58
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月4日 下午7:42:58
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTables()
	  */
	public List<TableInfo> queryTables(Connection conn,String dbName) {
		//查询数据库表
		String table_sql = QUERY_MYSQLDB_TABLE_NAME_SQL;
		tableList = new ArrayList<TableInfo>();
		TableInfo tableInfo = null;
		try {
			pst = conn.prepareStatement(table_sql);
			pst.setString(1, dbName);
			
			rs = pst.executeQuery();
			while(rs.next()){
				tableInfo = new TableInfo();
				tableInfo.setName(rs.getString("tablename"));
				tableInfo.setComment(rs.getString("tablecomment"));
				
				tableList.add(tableInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, pst);
		}
		
		return tableList;
	}

	/** 
	 * 根据表名查询该表的表结构信息
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月4日 下午5:55:34
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月4日 下午5:55:34
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTableStructor(java.lang.String)
	  */
	public List<TableColumnInfo> queryTableStructor(Connection conn,String dbName,String tableName) {
		// 查询数据库表字段
		String column_sql = QUERY_MYSQLDB_TABLE_STRUCTURE;
		
		columnList = new ArrayList<TableColumnInfo>();
		TableColumnInfo columnInfo = null;
		
		try {
			pst = conn.prepareStatement(column_sql.toString());
			pst.setString(1, dbName);
			pst.setString(2, tableName);
			
			rs = pst.executeQuery();
			while(rs.next()){
				columnInfo = new TableColumnInfo();
				columnInfo.setColumn_name(rs.getString("column_name"));
				columnInfo.setColumn_comment(rs.getString("column_comment"));
				columnInfo.setColumn_key(rs.getString("column_key"));
				columnInfo.setColumn_type(rs.getString("column_type"));
				columnInfo.setData_type(rs.getString("data_type"));
				columnInfo.setIs_nullable(rs.getString("is_nullable"));
				
				columnList.add(columnInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, pst);
		}
		
		return columnList;
	}
}
