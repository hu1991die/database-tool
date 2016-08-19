package com.netschina.db.compare.vo;

import java.io.Serializable;
import java.util.Map;

public class DbTableRecordDataInfo implements Serializable {

	private Map<String, String> mapField;

	/**
	 *   
	 * @return
	 * 2015年5月25日
	 *
	 */
	public Map<String, String> getMapField() {
		return mapField;
	}

	public void setMapField(Map<String, String> mapField) {
		this.mapField = mapField;
	}
	
}
