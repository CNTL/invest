package com.tl.kernel.context;

import java.util.Hashtable;

import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.kernel.config.CacheConfig;
import com.tl.kernel.config.CacheInfo;
import com.tl.kernel.config.ConfigReader;

/**
 * 缓存的管理类
 * 功能包括对配置的缓存进行刷新、保持缓存对象、访问缓存对象
 */
public class CacheManager extends CacheReader {
	private static Log log = Context.getLog("tl");
	/**
	 * 刷新所有缓存
	 * 从配置中取出缓存列表，对每个类访问其Cache接口
	 * 每个缓存类都必须实现Cache接口
	 */
	public synchronized static void refresh() throws Exception {
		if (classNames == null) return;
		
		for (int i = 0; i < classNames.length; i++){
			refresh(classNames[i]);
		}
	}

	/**
	 * 单个缓存的刷新
	 * 调用传入类的Cache接口
	 * 不检验刷新类名。该方法可以用来刷新非配置中的缓存类，
	 * 只要该类实现了Cache接口
	 * @param className 要刷新的缓存类名，该类必须实现了Cache接口
	 */
	@SuppressWarnings("unchecked")
	public synchronized static void refresh(String className) throws Exception {
		log.info("[Refresh]" + className);

		Object cacheObj = Class.forName(className).newInstance();
		Cache cache = (Cache)cacheObj;
		cache.refresh();
		caches.put(className, cacheObj);

		log.info("[Refresh]ok");
	}

	/**
	 * 单个缓存的刷新
	 * 调用传入类的Cache接口
	 * 不检验刷新类名。该方法可以用来刷新非配置中的缓存类，
	 * 只要该类实现了Cache接口
	 * @param clazz 要刷新的缓存类，该类必须实现了Cache接口
	 */
	@SuppressWarnings("rawtypes")
	public synchronized static void refresh(Class clazz) throws Exception {
		refresh(clazz.getName());
	}
	
	/**
	 * 从启动模块中得到配置的缓存类列表
	 * 然后做第一次的缓存刷新
	 * 该方法只在系统启动时调用一次
	 */
	@SuppressWarnings("rawtypes")
	public synchronized static void init() throws Exception {
		log.info("[Refresh]Init");

		if (cacheNames != null){
			log.info("[Refresh]Cache already loaded.");
			return;
		}
		
		ConfigReader reader = ConfigReader.getInstance();

		//servers = reader.getServers();
		CacheConfig cacheConfig = reader.getCacheConfig();
		if (cacheConfig == null){
			log.info("[Refresh]No cache config.");
			return;
		}
		
		autoRefresh = "true".equals(cacheConfig.getAutoRefresh());
		if (log.isInfoEnabled())
			log.info("[Refresh]AutoRefresh:" + autoRefresh);
		
		String configInterval = cacheConfig.getRefreshInterval();
		if(StringUtils.isNotEmpty(configInterval)){
			try{
				refreshInterval = Integer.parseInt(configInterval);
			}catch(Exception ex){
				log.error(ex);
			}
		}
		
		if (log.isInfoEnabled())
			log.info("[Refresh]RefreshInterval:" + refreshInterval + " s");
		
		CacheInfo[] cacheArr  = cacheConfig.getCaches();
		if (cacheArr == null) return;
		
		//初始化
		int size = cacheArr.length;
		caches = new Hashtable(size);

		cacheNames = new String[size];
		classNames = new String[size];
		
		for (int i = 0; i < cacheArr.length; i++)
		{
			cacheNames[i] = cacheArr[i].getName();
			classNames[i] = cacheArr[i].getInvokeClass();
		}
		refresh();
		
		//启动自动刷新线程
		if(autoRefresh)
			(new CacheAutoRefreshThread()).start();
	}
	
	/**
	 * 自动刷新缓存线程，CacheManager初始化时自动加载
	 * 
	 */
	static class CacheAutoRefreshThread extends Thread{
		public CacheAutoRefreshThread(){
			super("CacheAutoRefreshThread");
			if(log.isInfoEnabled())
				log.info("CacheAutoRefreshThread started");
		}
		
		public void run(){
			long mill = refreshInterval * 1000;
			while(true)
			{
				try{
					Thread.sleep(mill);
					
					if(log.isInfoEnabled())
						log.info("Auto refresh cache.");
					
					refresh();
				}catch(Exception ex){
					log.error("[CacheAutoRefreshThread] error:",ex);
				}
			}
		}
	}
}
