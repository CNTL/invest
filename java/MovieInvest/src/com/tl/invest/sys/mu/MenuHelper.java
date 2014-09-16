package com.tl.invest.sys.mu;

import java.util.ArrayList;
import java.util.List;

import com.tl.kernel.context.CacheReader;
import com.tl.kernel.context.Context;

public class MenuHelper {
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
	
	public static MenuManager getMenuManager(){
		MenuManager manager = (MenuManager)Context.getBean(MenuManager.class);
		if (manager == null)
			manager = new MenuManagerImpl();
		return manager;
	}
	
	public static MenuCache getCache()
	{
		return (MenuCache)CacheReader.find(MenuCache.class);
	}
	
	public static Menu[] sort(int pid,Menu[] menus){
		if(menus == null || menus.length<=0) return null;
		List<Menu> list = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if(menu.getPid() == pid){
				list.add(menu);
				list.addAll(sortSub(menu.getId(), menus));
			}
		}
		if(list == null || list.size()<=0) return null;
		return (Menu[]) list.toArray(new Menu[0]);
	}
	
	private static List<Menu> sortSub(int pid,Menu[] menus){
		List<Menu> list = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if(menu.getPid() == pid){
				list.add(menu);
				list.addAll(sortSub(menu.getId(), menus));
			}
		}
		return list;
	}
}
