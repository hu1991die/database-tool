package com.netschina.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbUtil {
	private static List<CodeName> columnCodeNames= null;
//	private static Map<String, String> mapCodeName = null;
	static{
		columnCodeNames = new ArrayList<CodeName>();
		columnCodeNames.add(new CodeName("COLUMN_NAME", "名称"));
		columnCodeNames.add(new CodeName("DATA_TYPE", "类型"));
		columnCodeNames.add(new CodeName("COLUMN_TYPE", "最大长度"));
		columnCodeNames.add(new CodeName("COLUMN_KEY", "是否主键"));
		columnCodeNames.add(new CodeName("IS_NULLABLE", "是否为空"));
		columnCodeNames.add(new CodeName("COLUMN_COMMENT", "注释"));
//		mapCodeName = new HashMap<String, String>();
//		mapCodeName.put("name", "名称");
//		mapCodeName.put("dataType", "类型");
//		mapCodeName.put("maxLength", "最大长度");
//		mapCodeName.put("isPK", "是否主键");
//		mapCodeName.put("isNull", "是否为空");
//		mapCodeName.put("comment", "注释");
	}
	public static Connection getDbConnection(Properties prop){
		String dbDriver = (String)prop.get("db.driver");
		String dbUrl = (String)prop.get("db.url");
		String username = (String)prop.get("db.user");
		String password = (String)prop.get("db.password");
		return getConnection(dbDriver,dbUrl,username,password);
	}
	public static Connection getBaseDbConnection(Properties prop){
		String dbDriver = (String)prop.get("db.driver");
		String dbUrl = (String)prop.get("base.db.url");
		String username = (String)prop.get("base.db.user");
		String password = (String)prop.get("base.db.password");
		return getConnection(dbDriver,dbUrl,username,password);
	}
	public static Connection getGoalDbConnection(Properties prop){
		String dbDriver = (String)prop.get("db.driver");
		String dbUrl = (String)prop.get("goal.db.url");
		String username = (String)prop.get("goal.db.user");
		String password = (String)prop.get("goal.db.password");
		return getConnection(dbDriver,dbUrl,username,password);
	}
	public static List<CodeName> getColumnCodeNames(){
		return columnCodeNames;
	}
//	public static Map<String, String> getMapCodeName(){
//		return mapCodeName;
//	}
	//获得db数据库连接
	private static Connection getConnection(String driver, String url, String user, String pwd){
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭数据库资源链接
	  * @Discription:扩展说明
	  * @param rs
	  * @param pst
	  * @param conn
	  * @return void
	  * @Author: feizi
	  * @Date: 2015年6月5日 下午3:36:22
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月5日 下午3:36:22
	 */
	public static void close(ResultSet rs,PreparedStatement pst){
		try {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
