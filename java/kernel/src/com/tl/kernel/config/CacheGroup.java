package com.tl.kernel.config;

import java.util.ArrayList;
import java.util.List;

public class CacheGroup {
	private String name;
	private String cacheIndexes; //组中包含的Cache索引。所有缓存按顺序保存在CacheConfig里，每组记录本组内缓存的顺序号

	private List<CacheInfo> caches = new ArrayList<CacheInfo>();
	
	public String getCacheIndexes() {
		return cacheIndexes;
	}
	void setCacheIndexes(String cacheIndexes) {
		this.cacheIndexes = cacheIndexes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Digester里使用，必须是public
	public void addCache(CacheInfo cache) {
		caches.add(cache);
	}

	List<CacheInfo> getCaches() {
		return caches;
	}
	void clearCaches() {
		caches.clear();
	}
}
