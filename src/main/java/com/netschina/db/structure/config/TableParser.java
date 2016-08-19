package com.netschina.db.structure.config;

import java.io.InputStream;

import com.netschina.db.util.PropertiesUtil;
import com.netschina.db.xml.XmlXStreamParser;

public class TableParser {

	private static Modules modules = null;
	
	public static Modules getModules(){
		if(modules != null){
			return modules;
		}
		XmlXStreamParser xmlXStreamParser = new XmlXStreamParser();
		InputStream is = PropertiesUtil.class.getResourceAsStream("/structure/table-info.xml");
		modules = xmlXStreamParser.fromXML(is, Modules.class, new ModuleSetCallBack());
		return modules;
	}
}
