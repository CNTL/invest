package com.tl.kernel.config;

import java.util.ArrayList;
import java.util.List;

public class CacheGroup {
	private String name;
	private String cacheIndexes; //���а�����Cache���������л��水˳�򱣴���CacheConfig�ÿ���¼�����ڻ����˳���

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
	
	//Digester��ʹ�ã�������public
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
