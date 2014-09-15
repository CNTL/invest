package com.tl.kernel.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置文件中的缓存信息类
 * 需要在应用启动时自动读取并在ConfigReader中保留一份实例
 * 系统中只有这一份实例
 * @author rengy
 *
 */
public class CacheConfig {
	private String autoRefresh;
	private String refreshInterval;
	
	private List<CacheInfo> caches = new ArrayList<CacheInfo>();
	private List<CacheGroup> groups = new ArrayList<CacheGroup>();
	
	/**
	 * @return Returns the autoRefresh.
	 */
	public String getAutoRefresh()
	{
		return autoRefresh;
	}
	/**
	 * @param autoRefresh The autoRefresh to set.
	 */
	public void setAutoRefresh(String autoRefresh)
	{
		this.autoRefresh = autoRefresh;
	}

	/**
	 * @return the refreshInterval
	 */
	public String getRefreshInterval()
	{
		return refreshInterval;
	}
	/**
	 * @param refreshInterval the refreshInterval to set
	 */
	public void setRefreshInterval(String refreshInterval)
	{
		this.refreshInterval = refreshInterval;
	}
	
	/**
     * 增加一个缓存加载项
     * @param info CacheInfo
     */
    public void addInfo(CacheInfo info)
    {
    	caches.add(info);
    }
    
	/**
	 * @return Returns the map.
	 */
	public CacheInfo[] getCaches()
	{
		return (CacheInfo[])caches.toArray(new CacheInfo[0]);
	}
	
	public String toString(){
		StringBuffer sbInfo = new StringBuffer(400);
		sbInfo.append("[autoRefresh:").append(autoRefresh);
		CacheInfo[] cacheArr = (CacheInfo[])caches.toArray(new CacheInfo[0]);
		if (caches != null)
			for (int i = 0; i < cacheArr.length; i++)
				sbInfo.append("\n").append(cacheArr[i].toString());
		sbInfo.append("\n]");
		return sbInfo.toString();
	}

	public void addGroup(CacheGroup group) {
		groups.add(group);
		
		//把group里的cache list 加到当前list里，给group标记索引号
		String index = "";
		int i = caches.size();
		for (CacheInfo cache : group.getCaches()) {
			addInfo(cache);
			
			if (index.length() == 0) 
				index = String.valueOf(i);
			else
				index += "," + i;
			i++;
		}
		group.setCacheIndexes(index);
		group.clearCaches();
	}
	
	/**
	 * 提供缓存分组情况。若缓存没有分组，则自动按照一个缓存一个组的方式提供
	 * @return
	 */
	public List<CacheGroup> getGroups() {
		if (groups.size() > 0)	return groups;
		
		groups = new ArrayList<CacheGroup>(caches.size());
		for (int i = 0; i < caches.size(); i++) {
			CacheInfo cache = caches.get(i);
			
			CacheGroup group = new CacheGroup();
			group.setName(cache.getName());
			group.setCacheIndexes(String.valueOf(i));
			
			groups.add(group);
		}
		return groups;
	}
}
