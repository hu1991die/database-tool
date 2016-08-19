package com.netschina.db.util;

public class CodeName {

	private String code;	//约束
	private String name;  //字段名
	public CodeName(){
		
	}
	public CodeName(String code, String name){
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
