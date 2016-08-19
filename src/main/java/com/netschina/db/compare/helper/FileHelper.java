package com.netschina.db.compare.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.netschina.db.compare.vo.Result;
import com.netschina.db.compare.vo.ResultColumn;
import com.netschina.db.compare.vo.ResultDataSql;
import com.netschina.db.compare.vo.ResultProperty;
import com.netschina.db.compare.vo.ResultTable;
import com.netschina.db.compare.vo.ResultTableDataInfo;
import com.netschina.db.compare.vo.ResultTableRecordDataInfo;
import com.netschina.db.compare.vo.ResultTableSql;
import com.netschina.db.util.StringUtil;

/**
 * 专门读写文件的类
 * @author Ouyang
 *
 */
public class FileHelper {

	/**
	 * 
	  * 方法说明
	  * @Discription:表结构对比输出到txt文件
	  * @Author: zhouhezhen
	  * @Date: 2015年5月7日 上午10:04:59
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月7日 上午10:04:59
	 */
	public void writeCompareDbInfoResult(Result result, String filePath) {
		List<ResultTable> moreResultTables = result.getMoreResultTables();
		List<ResultTable> lessResultTables = result.getLessResultTables();
		List<ResultTable> modifyResultTables = result.getModifyResultTables();
		List<ResultTableDataInfo> resultTableInfos = result.getResultTableInfos();
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, false);
			if((moreResultTables!=null && moreResultTables.size()>0) 
					|| (lessResultTables!=null && lessResultTables.size()>0) 
					|| (modifyResultTables!=null && modifyResultTables.size()>0)){
				fw.write("====================表结构变化如下========================\r\n");
			} else {
				fw.write("====================表结构无任何变化========================\r\n");
			}
			if(moreResultTables!=null && moreResultTables.size()>0){
				fw.write("==========多了表==============\r\n");
				for(ResultTable table: moreResultTables){
					fw.write("表："+table.getName()+"\t注释："+table.getComment()+"\r\n");
				}
			}
			if(lessResultTables!=null && lessResultTables.size()>0){
				fw.write("==========少了表==============\r\n");
				for(ResultTable table: lessResultTables){
					fw.write("表："+table.getName()+"\t注释："+table.getComment()+"\r\n");
				}
			}
			if(modifyResultTables!=null && modifyResultTables.size()>0){
				fw.write("==========修改了表==============\r\n");
				for(ResultTable table: modifyResultTables){
					fw.write("表："+table.getName()+"\t注释："+table.getComment()+"\t");
					List<ResultProperty> resultPropertys = table.getResultPropertys();
					if(resultPropertys != null){
						for(ResultProperty resultProperty : resultPropertys){
							fw.write(resultProperty.getName()+"\t新值："+resultProperty.getNewValue()+"\t旧值："+resultProperty.getOldValue());
						}
					}
					List<ResultColumn> moreResultColumns = table.getMoreResultColumns();
					if(moreResultColumns != null && moreResultColumns.size() > 0){
						fw.write("\r\n  多了：\r\n");
						for(ResultColumn resultColumn : moreResultColumns){
							fw.write("    字段："+resultColumn.getName());
						}
						fw.write("\r\n");
					}
					List<ResultColumn> lessResultColumns = table.getLessResultColumns();
					if(lessResultColumns != null && lessResultColumns.size() > 0){
						fw.write("\r\n  少了：\r\n");
						for(ResultColumn resultColumn : lessResultColumns){
							fw.write("    字段："+resultColumn.getName());
						}
						fw.write("\r\n");
					}
					List<ResultColumn> modifyResultColumns = table.getModifyResultColumns();
					if(modifyResultColumns != null && modifyResultColumns.size() > 0){
						fw.write("\r\n  修改：\r\n");
						for(ResultColumn resultColumn : modifyResultColumns){
							fw.write("    字段："+resultColumn.getName());
							resultPropertys = resultColumn.getResultPropertys();
							for(ResultProperty resultProperty: resultPropertys){
								fw.write("\t"+resultProperty.getName()+"\t新值:"+resultProperty.getNewValue()+"\t旧值:"+resultProperty.getOldValue()+"\r\n");
							}
						}
					}
				}
			}
			if(resultTableInfos != null && resultTableInfos.size() > 0){
				fw.write("====================初始化表数据变化如下========================\r\n");
				for(ResultTableDataInfo tableDataInfo: resultTableInfos) {
					fw.write("表："+tableDataInfo.getName()+"\t注释："+tableDataInfo.getComment()+"\r\n");
					List<ResultTableRecordDataInfo> moreRecords = tableDataInfo.getMoreRecords();
					List<String> fields = tableDataInfo.getFields();
					if(moreRecords!=null && moreRecords.size()>0){
						fw.write("  多了记录：\r\n");
						for(ResultTableRecordDataInfo record: moreRecords){
							Map<String, String> mapField = record.getMapField();
							for(String field : fields){
								fw.write("\t"+field+": "+mapField.get(field));
							}
							fw.write("\r\n");
						}
					}
					List<ResultTableRecordDataInfo> lessRecords = tableDataInfo.getLessRecords();
					if(lessRecords!=null && lessRecords.size()>0){
						fw.write("  少了记录：\r\n");
						for(ResultTableRecordDataInfo record: lessRecords){
							Map<String, String> mapField = record.getMapField();
							for(String field : fields){
								fw.write("\t"+field+": "+mapField.get(field));
							}
							fw.write("\r\n");
						}
					}
					List<ResultTableRecordDataInfo> modifyRecords = tableDataInfo.getModifyRecords();
					if(modifyRecords!=null && modifyRecords.size()>0){
						fw.write("  修改了记录：\r\n");
						for(ResultTableRecordDataInfo record: modifyRecords){
							List<ResultProperty> resultPropertys = record.getResultPropertys();
							String primaryKey = record.getPrimaryKey();
							if(!StringUtil.isEmpty(primaryKey)){
								fw.write("    主键ID："+primaryKey+"\t");
							}else{
								fw.write("    ");
							}
							for(ResultProperty resultProperty : resultPropertys){
								fw.write("字段："+resultProperty.getName()+"\t新值："+resultProperty.getNewValue()+"\t旧值："+resultProperty.getOldValue());
							}
							fw.write("\r\n");
						}
					}
				}
			} else {
				fw.write("====================初始化表数据无任何变化========================\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	  * 方法说明
	  * @Discription:扩展说明
	  * @param resultDataSql
	  * @param string
	  * @return void
	  * @Author: zhouhezhen
	  * @Date: 2015年5月5日 下午4:32:43
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月5日 下午4:32:43 
	  */
	public void writeExportDbSql(Result result, String filePath) {
		try{
			FileWriter baseSqlfw = new FileWriter(filePath+"\\base-table.sql", false);
			FileWriter goalSqlfw = new FileWriter(filePath+"\\goal-table.sql", false);
			FileWriter initDatafw = new FileWriter(filePath+"\\base-init-data.sql", false);
			FileWriter goalDatafw = new FileWriter(filePath+"\\goal-init-data.sql", false);
			if(null != result.getBaseTableSql() && result.getBaseTableSql().size() > 0){
				baseSqlfw.write("====================baseDb初始化表如下========================\r\n");
				for (ResultTableSql resultTableSql : result.getBaseTableSql()) {
					baseSqlfw.write(resultTableSql.getName()+"表Sql语句：\r\n");
					baseSqlfw.write(resultTableSql.getSql()+"\r\n\n");
				}
				baseSqlfw.write("===================END=============================="+"\r\n");
			}else {
				baseSqlfw.write("====================没有初始化表========================\r\n");
			}
			baseSqlfw.close();
			if(null != result.getGoalTableSql() && result.getGoalTableSql().size() > 0){
				goalSqlfw.write("====================goalDb初始化表如下========================\r\n");
				for (ResultTableSql resultTableSql : result.getGoalTableSql()) {
					goalSqlfw.write(resultTableSql.getName()+"表Sql语句：\r\n");
					goalSqlfw.write(resultTableSql.getSql()+"\r\n\n");
				}
				goalSqlfw.write("===================END=============================="+"\r\n");
			}else {
				goalSqlfw.write("====================没有初始化表========================\r\n");
			}
			goalSqlfw.close();
			if(null != result.getBaseInitSql() && result.getBaseInitSql().size() > 0){
				
				for (ResultDataSql resultInfos : result.getBaseInitSql()) {
					initDatafw.write("base库中的数据:"+resultInfos.getName()+"=============================\r\n");
					for (int i = 0; i < resultInfos.getDataSql().length; i++) {
						String[] str = resultInfos.getDataSql();
							initDatafw.write(str[i]+"   ");
						initDatafw.write("\r\n");
					}
					initDatafw.write("\r\n");
				}
				initDatafw.write("===================END=============================="+"\r\n");
			}else{
				initDatafw.write("===================END=============================="+"\r\n");
			}
			initDatafw.close();
			if(null != result.getGoalInitSql() && result.getGoalInitSql().size() > 0){
				
				for (ResultDataSql resultInfos : result.getGoalInitSql()) {
					goalDatafw.write("goal库中的数据:"+resultInfos.getName()+"=============================\r\n");
					for (int i = 0; i < resultInfos.getDataSql().length; i++) {
						String[] str = resultInfos.getDataSql();
						goalDatafw.write(str[i]+"   ");
						goalDatafw.write("\r\n");
					}
					goalDatafw.write("\r\n");
				}
				goalDatafw.write("===================END=============================="+"\r\n");
			}else{
				goalDatafw.write("===================END=============================="+"\r\n");
			}
			}catch(Exception e){
				throw new RuntimeException("txt文件写入异常!");
			}
		
		
	}
	
	public Object readFileForObject(String filePath) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filePath);
			ois = new ObjectInputStream(fis);
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeInputStream(ois);
			closeInputStream(fis);
		}
		return null;
	}
	
	public void writeFileForObject(Object obj, String filePath){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filePath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeOutputStream(fos);
			closeOutputStream(fos);
		}
	}
	
	private void closeInputStream(InputStream is) {
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void closeOutputStream(OutputStream os) {
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
