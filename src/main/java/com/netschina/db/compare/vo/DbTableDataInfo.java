package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ouyang
 *
 */
public class DbTableDataInfo implements Serializable {

	private String name; //表名
	private String comment;  	//表注释
	private List<String> fields;  	//字段集合
	private List<String> pks;		//主键集合
	private List<DbTableRecordDataInfo> records;   //表记录
	private Map<String, DbTableRecordDataInfo> mapRecord;
	public List<String> getPks() {
		return pks;
	}
	public void setPks(List<String> pks) {
		this.pks = pks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public List<DbTableRecordDataInfo> getRecords() {
		return records;
	}
	public void setRecords(List<DbTableRecordDataInfo> records) {
		this.records = records;
	}
	public Map<String, DbTableRecordDataInfo> getMapRecord() {
		return mapRecord;
	}
	public void setMapRecord(Map<String, DbTableRecordDataInfo> mapRecord) {
		this.mapRecord = mapRecord;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
