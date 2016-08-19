/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.netschina.db.structure.vo;

import java.io.Serializable;

/**
 * 表结构实体类
 * @Author: feizi
 * @Date: 2015年6月4日 下午5:44:21
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月4日 下午5:44:21
 * @Version:V6.0
 */
public class TableColumnInfo implements Serializable{
	
	private static final long serialVersionUID = -1269680941420761352L;
	
	// 字段名称
	private String column_name;
	// 数据类型
	private String data_type;
	// 字段描述
	private String column_comment;
	// 是否主键
	private String column_key;
	// 字段长度
	private String column_type;
	// 是否为空
	private String is_nullable;

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getColumn_comment() {
		return column_comment;
	}

	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}

	public String getColumn_key() {
		return column_key;
	}

	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}

	public String getColumn_type() {
		return column_type;
	}

	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}

	public String getIs_nullable() {
		return is_nullable;
	}

	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}
}
