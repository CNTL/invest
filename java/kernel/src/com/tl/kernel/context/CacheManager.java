package com.tl.kernel.context;

import java.util.Hashtable;

import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.kernel.config.CacheConfig;
import com.tl.kernel.config.CacheInfo;
import com.tl.kernel.config.ConfigReader;

/**
 * ����Ĺ�����
 * ���ܰ��������õĻ������ˢ�¡����ֻ�����󡢷��ʻ������
 */
public class CacheManager extends CacheReader {
	private static Log log = Context.getLog("tl");
	/**
	 * ˢ�����л���
	 * ��������ȡ�������б���ÿ���������Cache�ӿ�
	 * ÿ�������඼����ʵ��Cache�ӿ�
	 */
	public synchronized static void refresh() throws Exception {
		if (classNames == null) return;
		
		for (int i = 0; i < classNames.length; i++){
			refresh(classNames[i]);
		}
	}

	/**
	 * ���������ˢ��
	 * ���ô������Cache�ӿ�
	 * ������ˢ���������÷�����������ˢ�·������еĻ����࣬
	 * ֻҪ����ʵ����Cache�ӿ�
	 * @param className Ҫˢ�µĻ����������������ʵ����Cache�ӿ�
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
	 * ���������ˢ��
	 * ���ô������Cache�ӿ�
	 * ������ˢ���������÷�����������ˢ�·������еĻ����࣬
	 * ֻҪ����ʵ����Cache�ӿ�
	 * @param clazz Ҫˢ�µĻ����࣬�������ʵ����Cache�ӿ�
	 */
	@SuppressWarnings("rawtypes")
	public synchronized static void refresh(Class clazz) throws Exception {
		refresh(clazz.getName());
	}
	
	/**
	 * ������ģ���еõ����õĻ������б�
	 * Ȼ������һ�εĻ���ˢ��
	 * �÷���ֻ��ϵͳ����ʱ����һ��
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
		
		//��ʼ��
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
		
		//�����Զ�ˢ���߳�
		if(autoRefresh)
			(new CacheAutoRefreshThread()).start();
	}
	
	/**
	 * �Զ�ˢ�»����̣߳�CacheManager��ʼ��ʱ�Զ�����
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
