package com.netschina.db.main;

import com.netschina.db.compare.handler.DbCompareExportHandler;
import com.netschina.db.util.BeanFactoryUtil;
import com.netschina.db.util.PropertiesUtil;

/**
 * 数据库对比工具场导出入口main
 * @author Ouyang
 * 2015年5月26日 下午9:22:45
 */
public class DbCompareExportMain {
	
	public static void main(String[] args) throws Exception {
		//获取文件名
		String fileName ="compare-db-info";  
		long start = System.currentTimeMillis();
		//初始化配置文件 全局都可以获取
		PropertiesUtil.init("/compare", fileName); 
		//初始化DbCompareExportHandler
		DbCompareExportHandler dbCompareExportHandler = BeanFactoryUtil.getDbCompareExportHandler();
		System.out.println("执行中...................");
		executeDbCompareExportHandler(fileName, dbCompareExportHandler);
		System.out.println("执行成功，总共耗时："+((System.currentTimeMillis()-start)/1000)
				+"秒，请查看输出结果文件夹:"+PropertiesUtil.getProp().getProperty("export.folder"));
	}

	/**
	 * 执行Handler类
	 * @param fileName
	 * @param dbCompareExportHandler
	 * @param prop
	 */
	private static void executeDbCompareExportHandler(String fileName,
			DbCompareExportHandler dbCompareExportHandler) {
		
		if ("compare-db-info".equals(fileName)) {
			//表结构对比
			dbCompareExportHandler.exportCompareDbInfo();
		}else if("compare-company-init-info".equals(fileName)) {
			//初始数据对比(库间默认公司数据对比)
			dbCompareExportHandler.exportCompareCompanyInitInfo();
		}else if ("export-info-data".equals(fileName)) {
			//初始数据对比(库内默认公司与新公司对比)
			dbCompareExportHandler.exportInfoData();
		} else if ("export-sql".equals(fileName)) {
			//导出初始化表及数据
			dbCompareExportHandler.exportSql();
		} else {
			throw new RuntimeException("配置执行类型有错误，请检查！");
		}
	}

}
