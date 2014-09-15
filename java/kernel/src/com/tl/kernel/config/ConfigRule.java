package com.tl.kernel.config;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class ConfigRule extends RuleSetBase {

	public void addRuleInstances(Digester digester) {
		//====SysInfo======
		digester.addCallMethod("sys-config/system/name", "setSysName", 0);
	    digester.addCallMethod("sys-config/system/version", "setSysVersion", 0);
		//====StartClass======
        digester.addObjectCreate("sys-config/loader-config/action",
        		"com.tl.kernel.config.StartClass");
	    digester.addSetProperties("sys-config/loader-config/action");
	    digester.addSetNext("sys-config/loader-config/action", 
	    		"addInfo", "com.tl.kernel.config.StartClass");
	    //====database-config======
	    digester.addObjectCreate("sys-config/database-config/central-db",
				"com.tl.kernel.config.CentralDB");
		digester.addSetProperties("sys-config/database-config/central-db");
		digester.addSetNext("sys-config/database-config/central-db",
				"addInfo", "com.tl.kernel.config.CentralDB");
		//====locale-config=======
	    digester.addObjectCreate("sys-config/locale",
				"com.tl.kernel.config.Locale");
	    digester.addSetProperties("sys-config/locale");
	    digester.addSetNext("sys-config/locale",
	    		"addInfo", "com.tl.kernel.config.Locale");
	    //====cache-config=======
	    digester.addObjectCreate("sys-config/cache-config",	"com.tl.kernel.config.CacheConfig");
		digester.addSetProperties("sys-config/cache-config");
		digester.addSetNext("sys-config/cache-config","addInfo", "com.tl.kernel.config.CacheConfig");
		
	    digester.addObjectCreate("sys-config/cache-config/group", "com.tl.kernel.config.CacheGroup");
		digester.addSetProperties("sys-config/cache-config/group");
		digester.addSetNext("sys-config/cache-config/group", "addGroup", "com.tl.kernel.config.CacheGroup");
		
	    digester.addObjectCreate("sys-config/cache-config/group/action", "com.tl.kernel.config.Cache");
		digester.addSetProperties("sys-config/cache-config/group/action");
		digester.addSetNext("sys-config/cache-config/group/action", "addCache", "com.tl.kernel.config.Cache");
	}
}
