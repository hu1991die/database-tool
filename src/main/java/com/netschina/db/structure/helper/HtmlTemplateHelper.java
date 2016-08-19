/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.structure.helper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**  
 * Html模板帮助类
 * @Author: feizi
 * @Date: 2015年6月9日 上午10:17:29 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年6月9日 上午10:17:29 
 * @Version:V6.0
 */
public class HtmlTemplateHelper {

	/**
	 * 初始化模板
	 * @throws Exception 
	 * @throws ParseErrorException 
	 * @throws ResourceNotFoundException 
	 * @Discription:扩展说明
	 * @Author: feizi
	 * @Date: 2015年6月5日 上午9:38:23
	 * @ModifyUser：feizi
	 * @ModifyDate: 2015年6月5日 上午9:38:23
	 * @see com.netschina.db.structure.export.ExportHtmlTemplate#initTemplate()
	 */
	public Template getTemplate(String htmlTemplateFile) throws Exception {
		VelocityEngine ve = new VelocityEngine();
		Properties p = new Properties();
		p.put("file.resource.loader.class",
		"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");     
		ve.init(p);
		return ve.getTemplate(htmlTemplateFile);
	}

	/**
	 * 导出模板
	  * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @Discription:扩展说明
	  * @Author: feizi
	  * @Date: 2015年6月5日 上午9:55:34
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年6月5日 上午9:55:34
	  * @see com.netschina.db.structure.export.ExportHtmlTemplate#exportHtmlTemplate(org.apache.velocity.VelocityContext, java.lang.String)
	  */
	public void exportHtmlTemplate(Template template, VelocityContext context, String filePath) throws Exception {
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		System.out.println("===========filePath:" + filePath);
		
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
		out.write(writer.toString());
		out.close();
		
		System.out.println("ok...导出html格式数据字典成功................");
	}
}
