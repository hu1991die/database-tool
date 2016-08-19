/**
  * 文件说明
  * @Description:扩展说明 
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.compare.vo;

import java.io.Serializable;

/**  
 * @Author: zhouhezhen
 * @Date: 2015年5月7日 下午3:46:23 
 * @ModifyUser: zhouhezhen
 * @ModifyDate: 2015年5月7日 下午3:46:23 
 * @Version:V6.0
 */
public class ResultDataSql implements Serializable{
	
	private String name;
	private String[] dataSql;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDataSql() {
		return dataSql;
	}
	public void setDataSql(String[] dataSql) {
		this.dataSql = dataSql;
	}
	
}
