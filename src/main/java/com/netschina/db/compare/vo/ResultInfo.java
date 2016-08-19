package com.netschina.db.compare.vo;

import java.io.Serializable;

public class ResultInfo implements Serializable{
	private static final long 
	serialVersionUID = 1L;
	private String info;
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String toString(){
		return info;
	}
}
