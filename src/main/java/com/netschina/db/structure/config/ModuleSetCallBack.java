package com.netschina.db.structure.config;

import com.netschina.db.xml.XmlSetCallBack;
import com.thoughtworks.xstream.XStream;

public class ModuleSetCallBack implements XmlSetCallBack {

	public void config(Object obj) {
		XStream xStream = (XStream)obj;
		xStream.alias("modules", Modules.class);
		xStream.alias("module", Module.class);
		xStream.aliasField("module", Modules.class, "modules");
		xStream.addImplicitCollection(Modules.class, "modules");
		xStream.useAttributeFor("name", String.class);
		xStream.useAttributeFor("type", String.class);
		xStream.useAttributeFor("code", String.class);
		xStream.useAttributeFor("table", String.class);
	}

}
