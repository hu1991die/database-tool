package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 对比结果信息对应表对象
 * @author Ouyang
 *
 */
public class ResultTable implements Serializable {

	private String name;	//表名
	private String comment;		//注释
	private List<ResultColumn> moreResultColumns;   //表中增加的列集合
	private List<ResultColumn> lessResultColumns;	//表中减少的列集合
	private List<ResultColumn> modifyResultColumns;  //表中修改的列集合
//	private List<ResultColumn> identicalResultColumns;
	private List<ResultProperty> resultPropertys;	//存储修改表的相关属性
	private boolean modify = false;
	public String getName() {
		return name;
	}
//	public List<ResultColumn> getIdenticalResultColumns() {
//		return identicalResultColumns;
//	}
//	public void setIdenticalResultColumns(List<ResultColumn> identicalResultColumns) {
//		this.identicalResultColumns = identicalResultColumns;
//	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<ResultColumn> getMoreResultColumns() {
		return moreResultColumns;
	}
	public void setMoreResultColumns(List<ResultColumn> moreResultColumns) {
		this.moreResultColumns = moreResultColumns;
	}
	public List<ResultColumn> getLessResultColumns() {
		return lessResultColumns;
	}
	public void setLessResultColumns(List<ResultColumn> lessResultColumns) {
		this.lessResultColumns = lessResultColumns;
	}
	public List<ResultColumn> getModifyResultColumns() {
		return modifyResultColumns;
	}
	public void setModifyResultColumns(List<ResultColumn> modifyResultColumns) {
		this.modifyResultColumns = modifyResultColumns;
	}
	public List<ResultProperty> getResultPropertys() {
		return resultPropertys;
	}
	public void setResultPropertys(List<ResultProperty> resultPropertys) {
		this.resultPropertys = resultPropertys;
	}
	public boolean isModify() {
		return modify;
	}
	public void setModify(boolean modify) {
		this.modify = modify;
	}
	
}
