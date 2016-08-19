package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.List;

public class ResultTableDataInfo implements Serializable{
	
	private String name; //定义返回查询表的table名称
	private String comment;			//注释
	private List<String> fields;	//该表所有的字段名
	private List<ResultTableRecordDataInfo> moreRecords;	//多的记录
	private List<ResultTableRecordDataInfo> lessRecords;	//少的记录
	private List<ResultTableRecordDataInfo> modifyRecords;	//修改的记录
	private boolean modify = false;
	
	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	private List<ResultTableRecordDataInfo> detailList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ResultTableRecordDataInfo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ResultTableRecordDataInfo> detailList) {
		this.detailList = detailList;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<ResultTableRecordDataInfo> getMoreRecords() {
		return moreRecords;
	}

	public void setMoreRecords(List<ResultTableRecordDataInfo> moreRecords) {
		this.moreRecords = moreRecords;
	}

	public List<ResultTableRecordDataInfo> getLessRecords() {
		return lessRecords;
	}

	public void setLessRecords(List<ResultTableRecordDataInfo> lessRecords) {
		this.lessRecords = lessRecords;
	}

	public List<ResultTableRecordDataInfo> getModifyRecords() {
		return modifyRecords;
	}

	public void setModifyRecords(List<ResultTableRecordDataInfo> modifyRecords) {
		this.modifyRecords = modifyRecords;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
