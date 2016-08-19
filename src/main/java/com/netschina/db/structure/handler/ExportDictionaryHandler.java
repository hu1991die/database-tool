/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.structure.handler;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.netschina.db.structure.config.Module;
import com.netschina.db.structure.helper.HtmlTemplateHelper;
import com.netschina.db.structure.helper.QueryDirectionaryHelper;
import com.netschina.db.structure.vo.TableColumnInfo;
import com.netschina.db.structure.vo.TableInfo;
import com.netschina.db.util.BeanFactoryUtil;
import com.netschina.db.util.DbUtil;
import com.netschina.db.util.PropertiesUtil;

/**  
 * 导出数据字典
 * @Author: feizi
 * @Date: 2015年6月5日 下午1:02:41 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月5日 下午1:02:41 
 * @Version:V6.0
 */
public class ExportDictionaryHandler {
	//实例化查询对象（通过工厂类实例化）
	private QueryDirectionaryHelper queryDictionaryHelper = BeanFactoryUtil.getQueryDirectionaryHelper();
	//实例化导出对象
	private HtmlTemplateHelper exportHtmlTemplate = new HtmlTemplateHelper();
	//属性对象
	private Properties prop = PropertiesUtil.getProp();
	//数据库连接对象
	private Connection conn = DbUtil.getDbConnection(prop);
	
	/**
	 * 获取实例名称
	  * @Discription:扩展说明
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年6月9日 下午12:38:10
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月9日 下午12:38:10
	 */
	public String getDbName(){
		return prop.getProperty("db.name");
	}
	
	/**
	 * 获取导出的路径
	  * @Discription:扩展说明
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年6月9日 下午12:38:15
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月9日 下午12:38:15
	 */
	public String getFilePath(){
		return prop.getProperty("export.folder");
	}
	
	/**
	 * 导出数据字典
	  * @Discription:扩展说明
	  * @return void
	  * @Author: feizi
	  * @Date: 2015年6月5日 上午10:06:18
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月5日 上午10:06:18
	 */
	public void exportDictionary(String htmlTemplateFile,Module module){
		long start = System.currentTimeMillis();
		//文件名称
		String fileName = module.getName();
		
		//查询表集合
		List<TableInfo> tableList = queryDictionaryHelper.queryTable(conn, getDbName(), module);
		
		//判断表集合
		if(null != tableList && tableList.size() > 0){
			for (TableInfo tableInfo : tableList) {
				String tableName = tableInfo.getName();
				//根据数据表名称查询表结构集合
				List<TableColumnInfo> columnList = queryDictionaryHelper.queryTableStructor(conn, getDbName(), tableName);
				tableInfo.setColumnList(columnList);
			}
		}
		
		/*for (TableInfo tableInfo : tableList) {
			System.out.println("表名\t\t注释");
			System.out.println(tableInfo.getName()+"\t\t"+tableInfo.getComment());
			
			System.out.println("字段名称\t数据类型\t字段描述\t是否主键\t长度\t可空");
			for (TableColumnInfo columnInfo : tableInfo.getColumnList()) {
				System.out.println(columnInfo.getColumn_name()+"\t"+columnInfo.getData_type()+"\t"+columnInfo.getColumn_comment()
						+"\t"+columnInfo.getColumn_key()+"\t"+columnInfo.getColumn_type()+"\t"+columnInfo.getIs_nullable());
			}
			System.out.println("======================================================================================");
		}*/
		
		//上下文
		VelocityContext context = new VelocityContext();
		//模块名称
		context.put("module", module);
		//数据表集合
		context.put("tableList", tableList);
		
		try {
			//获取velocity模板
			Template htmlTemplate = exportHtmlTemplate.getTemplate(htmlTemplateFile);
			
			//根据velocity模板导出html页面
			exportHtmlTemplate.exportHtmlTemplate(htmlTemplate, context, (getFilePath() + "/" + fileName +".html"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("执行成功，总共耗时："+((end - start)/1000)
				+"秒，请查看输出结果文件夹:"+prop.getProperty("export.folder"));
	}
}
