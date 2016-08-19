package com.netschina.db.main;

import com.netschina.db.structure.config.Module;
import com.netschina.db.structure.config.Modules;
import com.netschina.db.structure.config.TableParser;
import com.netschina.db.structure.handler.ExportDictionaryHandler;
import com.netschina.db.util.PropertiesUtil;


/**
 * 数据库结构导出入口main
 * <li>每个模块一个配置文件,解释配置文件</li>
 * <li>查询数据库表结构数据字典</li>
 * <li>包装对象导出</li>
 * <li>写模板页面</li>
 * @author Ouyang
 * 2015年5月26日 下午9:23:18
 */
public class DbStructureExportMain {
	
	static{
		//初始化配置文件
		PropertiesUtil.init("/structure", "db-info"); 
	}
	
	public static void main(String[] args) {
		//定义velocity的模板路径
		String htmlTemplateFile = "/structure/html-template.vm";
		
		//解析配置文件
		Modules modules = TableParser.getModules();
		
		//模块
		Module module = null;
		if(null != modules){
			module  = modules.getModules().get(0);
		}
		
		new ExportDictionaryHandler().exportDictionary(htmlTemplateFile,module);
	}
	
}
