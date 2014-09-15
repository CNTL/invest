package com.tl.kernel.sys.dic;

import com.tl.kernel.context.CacheReader;
import com.tl.kernel.context.Context;

class DictionaryHelper {
	/**
	 * 把一个数组拼接成一个字符串
	 * @param arr
	 * @param joiner 连接符号
	 * @return
	 */
	public static String join(int[] arr, char joiner){
		StringBuffer sb = new StringBuffer();
		sb.append(arr[0]);
		for (int i = 1; i < arr.length; i++)
			sb.append(joiner).append(arr[i]);
		return sb.toString();
	}
	
	static DictionaryManager getDictionaryManager()
	{
		DictionaryManager manager = (DictionaryManager)Context.getBean(DictionaryManager.class);
		if (manager == null)
			manager = new DictionaryManagerImpl();
		return manager;
	}

	static DictionaryCache getCache()
	{
		return (DictionaryCache)CacheReader.find(DictionaryCache.class);
	}
}
