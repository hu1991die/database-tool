/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.structure.helper;

import java.sql.Connection;
import java.util.List;

import com.netschina.db.structure.config.Module;
import com.netschina.db.structure.vo.TableColumnInfo;
import com.netschina.db.structure.vo.TableInfo;


/**  
 * 数据查询接口类
 * @Author: feizi
 * @Date: 2015年6月4日 下午5:40:43 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月4日 下午5:40:43 
 * @Version:V6.0
 */
public interface QueryDirectionaryHelper {
	
	/**
	 * 查询数据表集合
	  * @Discription:扩展说明
	  * @param tables
	  * @return
	  * @return List<DbTable>
	  * @Author: feizi
	  * @Date: 2015年6月5日 上午9:01:04
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月5日 上午9:01:04
	 */
	List<TableInfo> queryTable(Connection conn,String dbName,Module module);

	/**
	 * 根据表名查询该表的表结构集合
	  * @Discription:扩展说明
	  * @param tableName
	  * @return
	  * @return List<TableStructor>
	  * @Author: feizi
	  * @Date: 2015年6月4日 下午5:57:45
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月4日 下午5:57:45
	 */
	List<TableColumnInfo> queryTableStructor(Connection conn,String dbName,String tableName);
}
