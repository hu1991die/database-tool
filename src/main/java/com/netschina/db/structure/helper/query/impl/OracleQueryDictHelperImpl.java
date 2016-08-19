/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.netschina.db.structure.helper.query.impl;

import java.sql.Connection;
import java.util.List;

import com.netschina.db.structure.config.Module;
import com.netschina.db.structure.helper.query.AbstractQueryDictHelper;
import com.netschina.db.structure.vo.TableColumnInfo;
import com.netschina.db.structure.vo.TableInfo;

/**
 * 数据查询接口实现类（Oracle数据库）
 * @Author: feizi
 * @Date: 2015年6月4日 下午6:08:48
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月4日 下午6:08:48
 * @Version:V6.0
 */
public class OracleQueryDictHelperImpl extends AbstractQueryDictHelper {

	/**
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月9日 上午11:05:37
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月9日 上午11:05:37
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTable(java.sql.Connection, java.lang.String, java.lang.String)
	  */
	public List<TableInfo> queryTable(Connection conn, String dbName,
			Module module) {
		return null;
	}

	/**
	  * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月9日 上午11:05:37
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月9日 上午11:05:37
	  * @see com.netschina.db.structure.helper.QueryDirectionaryHelper#queryTableStructor(java.sql.Connection, java.lang.String, java.lang.String)
	  */
	public List<TableColumnInfo> queryTableStructor(Connection conn,
			String dbName, String tableName) {
		return null;
	}
}
