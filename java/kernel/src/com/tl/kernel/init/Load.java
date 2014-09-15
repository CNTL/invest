package com.tl.kernel.init;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServlet;

import com.tl.common.log.Log;
import com.tl.kernel.config.ConfigReader;
import com.tl.kernel.config.Locale;
import com.tl.kernel.config.StartClass;
import com.tl.kernel.context.Context;

@SuppressWarnings("serial")
public class Load extends HttpServlet{
	private Log log = Context.getLog("tl");
	private final String configFile = "conf/sys-config.xml";
	private boolean isWebProject = false;
	
	public void setIsWebProject(boolean isWebProject){
		this.isWebProject = isWebProject;
	}
	
	public void init(){
		init(configFile);
	}
	
	public void init(String cfgFile){
		log.info("--------- rengy VERSION:" + Context.getVersion() + " ---------");
		log.info("[tl LoadServlet]");
		 //调用ConfigReader读取所有配置信息
        ConfigReader reader = ConfigReader.getInstance();
		boolean gotConfigure = reader.getConfigure(cfgFile);
		log.info("[tl LoadServlet]Configuration file loaded.");
		try{
			if (gotConfigure){
				load();
			}
			log.info("[tl LoadServlet]Initialization finished.");
		}
		catch (Exception e){
			log.error("[tl LoadServlet]", e);
		}
	}
	
	private void load(){
		ConfigReader reader = ConfigReader.getInstance();
		StartClass[] starts = (StartClass[])reader.getStartClasses().toArray(new StartClass[0]);
		if (starts == null) return;
		
		 //先设置Locale信息
        Locale infoLocale = reader.getLocale();
        java.util.Locale locale;
        if (infoLocale != null)
        {
        	if (infoLocale.getVariant() != null)
        		locale = new java.util.Locale(infoLocale.getLanguage(), infoLocale.getCountry(),
        				infoLocale.getVariant());
        	else if (infoLocale.getCountry() != null)
        		locale = new java.util.Locale(infoLocale.getLanguage(), infoLocale.getCountry());
        	else
        		locale = new java.util.Locale(infoLocale.getLanguage());
        }
        else
        	locale = java.util.Locale.CHINA;
        Context.setLocale(locale);
        
        //系统启动加载项
        Class<?>[] paramTypes = null;
		Object[] params = null;
		for (int i = 0;  i < starts.length; i++)
	    {
	    	//若只在WEB环境运行，而当前是独立执行程序环境，则不启动
			if (isWebProject && "true".equals(starts[i].getOnlyWeb())) continue;
			
			if (log.isInfoEnabled())
			{
				log.info("[tl LoadServlet]init:" + starts[i].getInvokeClass()+".init()");
			}
			try {
				Class<?> myClass = Class.forName(starts[i].getInvokeClass());
				Method method = myClass.getMethod(starts[i].getInvokeMethod(), paramTypes);
				if (Modifier.isStatic(method.getModifiers()))
				    method.invoke(null, params);
				else
				    method.invoke(myClass.newInstance(), params);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}		   
			log.info("[tl LoadServlet]init ok.");
		}
		
//		 //DBSession Init
//		log.info("[rengy LoadServlet]DBSession registering...");
//        for (int i = 0; i < reader.getDBSessions().size(); i++)
//		{
//			InfoDBSession db = (InfoDBSession)reader.getDBSessions().get(i);
//	        DBSessionFactory.registerDB(db.getName(), db.getImplementation());
//		}
//		log.info("[rengy LoadServlet]DBSession registered.");
//        //ID Init
//        for (int i = 0; i < reader.getIDs().size(); i++)
//		{
//			InfoID id = (InfoID)reader.getIDs().get(i);
//	        EUID.registerID(id.getName(), id.getType(), id.getParam());
//		}
//		log.info("[rengy LoadServlet]EUID registered.");
	}
}
