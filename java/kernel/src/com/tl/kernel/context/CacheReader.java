package com.tl.kernel.context;

import java.util.List;
import java.util.Map;

/**
 * 缓存读取类
 * 管理系统中所有的缓存，其他模块访问缓存时都通过这个类的静态方法进行
 */
public class CacheReader {
	/**
	 * 是否自动刷新缓存
	 * 在配置文件中配置
	 */
	protected static boolean autoRefresh = false;
	/**
	 * 刷新缓存时间间隔，单位秒，默认900秒
	 */
	protected static int refreshInterval = 900;
	/**
	 * 保存每个缓存对象的HashTable(value可以是null)
	 * 用类名做key，缓存的对象作为value
	 */
	@SuppressWarnings("rawtypes")
	protected static Map caches;
	/**
	 * 记录缓存配置名的数组
	 * 可提供给缓存刷新界面部分
	 */
	protected static String[] cacheNames;
	
	/**
	 * 记录缓存配置类名的数组
	 * 可用于缓存刷新界面
	 */
	protected static String[] classNames;
	/**
	 * 应用服务器的URL
	 * 多应用服务器时维护这个列表
	 */
	@SuppressWarnings("rawtypes")
	protected static List servers;
	
	/**
	 * 取一个缓存对象
	 * 
	 * @param className 缓存对象的类名
	 */
	public static Object find(String className){
		if (caches == null) return null;
		
		Object obj = caches.get(className);
		if (obj != null) return obj;
		
		/**
		 * 缓存类做了扩展时，e5中各处引用Cache的地方会找不到Cache。
		 * 此时根据类名（不带包名）查找。
		 * 要求继承的Cache类与原Cache类同名。
		 */
		String myName = getName(className);
		for (String name : classNames) {
			if (name.endsWith(myName)) {
				return caches.get(name);
			}
		}
		return null;
	}
	private static String getName(String className) {
		int pos = className.lastIndexOf(".");
		if (pos <= 0) return className;
		
		return className.substring(pos);//".PermissionCache", for "endsWith"
	}

	/**
	 * 取一个缓存对象
	 * 
	 * @param clazz 缓存对象类
	 */
	@SuppressWarnings("rawtypes")
	public static Object find(Class clazz){
		return find(clazz.getName());
	}
	/**
	 * 取所有缓存配置项名称的列表
	 * 在缓存界面中使用
	 */
	public static String[] getCacheNames(){
		return cacheNames;
	}

	/**
	 * 取所有缓存配置项类的名称的列表
	 * 在缓存界面中使用
	 */
	public static String[] getCacheClasses(){
		return classNames;
	}
	/**
	 * 读系统的配置，判断是否自动刷新缓存
	 * 返回autoRefresh的值
	 */
	public static boolean isAutoRefresh(){
		return autoRefresh;
	}
}
