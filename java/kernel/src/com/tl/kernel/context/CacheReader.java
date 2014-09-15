package com.tl.kernel.context;

import java.util.List;
import java.util.Map;

/**
 * �����ȡ��
 * ����ϵͳ�����еĻ��棬����ģ����ʻ���ʱ��ͨ�������ľ�̬��������
 */
public class CacheReader {
	/**
	 * �Ƿ��Զ�ˢ�»���
	 * �������ļ�������
	 */
	protected static boolean autoRefresh = false;
	/**
	 * ˢ�»���ʱ��������λ�룬Ĭ��900��
	 */
	protected static int refreshInterval = 900;
	/**
	 * ����ÿ����������HashTable(value������null)
	 * ��������key������Ķ�����Ϊvalue
	 */
	@SuppressWarnings("rawtypes")
	protected static Map caches;
	/**
	 * ��¼����������������
	 * ���ṩ������ˢ�½��沿��
	 */
	protected static String[] cacheNames;
	
	/**
	 * ��¼������������������
	 * �����ڻ���ˢ�½���
	 */
	protected static String[] classNames;
	/**
	 * Ӧ�÷�������URL
	 * ��Ӧ�÷�����ʱά������б�
	 */
	@SuppressWarnings("rawtypes")
	protected static List servers;
	
	/**
	 * ȡһ���������
	 * 
	 * @param className ������������
	 */
	public static Object find(String className){
		if (caches == null) return null;
		
		Object obj = caches.get(className);
		if (obj != null) return obj;
		
		/**
		 * ������������չʱ��e5�и�������Cache�ĵط����Ҳ���Cache��
		 * ��ʱ�����������������������ҡ�
		 * Ҫ��̳е�Cache����ԭCache��ͬ����
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
	 * ȡһ���������
	 * 
	 * @param clazz ���������
	 */
	@SuppressWarnings("rawtypes")
	public static Object find(Class clazz){
		return find(clazz.getName());
	}
	/**
	 * ȡ���л������������Ƶ��б�
	 * �ڻ��������ʹ��
	 */
	public static String[] getCacheNames(){
		return cacheNames;
	}

	/**
	 * ȡ���л���������������Ƶ��б�
	 * �ڻ��������ʹ��
	 */
	public static String[] getCacheClasses(){
		return classNames;
	}
	/**
	 * ��ϵͳ�����ã��ж��Ƿ��Զ�ˢ�»���
	 * ����autoRefresh��ֵ
	 */
	public static boolean isAutoRefresh(){
		return autoRefresh;
	}
}
