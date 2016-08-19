/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.netschina.db.structure.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据表基本信息
 * 
 * @Author: feizi
 * @Date: 2015年6月9日 上午10:42:06
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月9日 上午10:42:06
 * @Version:V6.0
 */
public class TableInfo implements Serializable {
	private static final long serialVersionUID = 7103083215470559857L;

	// 表名称
	private String name;
	// 表注释
	private String comment;
	// 表结构字段集合
	private List<TableColumnInfo> columnList = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<TableColumnInfo> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<TableColumnInfo> columnList) {
		this.columnList = columnList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
