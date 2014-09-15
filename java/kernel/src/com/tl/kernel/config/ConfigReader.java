package com.tl.kernel.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.tl.common.Utils;
import com.tl.common.log.Log;
import com.tl.kernel.context.Context;

public class ConfigReader {
	private static Log log = Context.getLog("rengy");
	
	private SysInfo sysInfo = new SysInfo();
	//系统启动时的自动加载项
    private List<StartClass> startClasses = new ArrayList<StartClass>();
    //数据源
    private CentralDB centralDB = new CentralDB();
    private Locale locale = new Locale();
    //缓存
    private CacheConfig cacheConfig = new CacheConfig();
	
	private static ConfigReader reader;
    private ConfigReader(){}
    public synchronized static ConfigReader getInstance(){
    	if (reader == null) reader = new ConfigReader();
    	return reader;
    }
    
    /**
	 * @return 返回系统启动时的加载项
	 */
	public List<StartClass> getStartClasses()
	{
		return startClasses;
	}
	
	public CentralDB getCentralDB(){
		return centralDB;
	}
	
	public Locale getLocale(){
		return locale;
	}
	
	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}
	
	/**
	 * 
	 * @return 返回系统信息
	 */
	public SysInfo getSysInfo(){
		return sysInfo;
	}
	
	
	//===========AddInfo Method================
	/**
	 * @param info
	 */
    public void addInfo(StartClass info)
    {
    	startClasses.clear();
    	startClasses.add(info);
    }
    public void addInfo(CentralDB info)
    {
    	centralDB = info;
    }
    
    public void addInfo(Locale info){
    	locale = info;    	
    }
    
    public void addInfo(CacheConfig info){
    	cacheConfig = info;
    }
    
    public void setSysName(String name){
    	sysInfo.setName(name);
    }
    
    public void setSysVersion(String version){
    	sysInfo.setVersion(version);
    }
	
	 /**
     * 解析sys-config.xml，取得所有配置信息
     * @param configFile 配置文件路径，用于WEB应用
     * @throws Exception
     * @return ConfigRestart
     */
    public synchronized boolean getConfigure(String configFile)
    {
    	boolean success = true;
    	File file = new File(Utils.getPath(configFile));
    	if(file==null || !file.exists()){
    		log.error("No configuration file!!" + configFile);
    		success = false;
    	}    	
        try{
            getConfigure(file);
		}catch(Exception e){
			success = false;
		}
		return success;
    }
   /**
     * 解析配置文件
     * @param doc
     */
    public synchronized void getConfigure(File file){
    	 Digester digester = new Digester();
         digester.setNamespaceAware(true);
         digester.setValidating(false);
         digester.setUseContextClassLoader(true);
         digester.push(this);
         digester.addRuleSet(new ConfigRule());

         try
 		{
 			digester.parse(file);
 		}
 		catch (IOException e)
 		{
 			log.error("Configuration file io exception!!! ", e);
 		}
 		catch (SAXException e)
 		{
 			log.error("Configuration file parse exception!!! ", e);
 		}
    }
}
