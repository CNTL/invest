package com.tl.invest.sys.mu;

import java.util.ArrayList;
import java.util.List;

import com.tl.common.log.Log;
import com.tl.kernel.context.Cache;
import com.tl.kernel.context.Context;

public class MenuCache implements Cache{
	Log log = Context.getLog("invest");
	private Menu[] menus = null;
	/**
	 * 缓存类主要用在Reader实现类中<br>
	 * 在引用时，注意不要使用构造方法创建缓存类的实例<br>
	 * 请使用如下形式：<br>
	 * <code>
	 * XXXCache cache = (XXXCache)(CacheReader.find(XXXCache.class));
	 * if (cache != null)
	 * 		return cache.get(...);
	 * </code>
	 * 其中XXXCache代表具体的缓存类
	 */
	public MenuCache(){
	}
	
	@Override
	public void refresh() throws Exception {
		MenuManager mgr = MenuHelper.getMenuManager();
		menus = mgr.getMenus();
		log.info("缓存更新：成功获取"+(menus==null ? "0" : String.valueOf(menus.length))+"个系统菜单资源。");
	}

	@Override
	public void reset() {
		
	}

	public Menu[] getMenus(){
		return menus;
	}
	
	public Menu getMenu(int id){
		if(menus==null ||menus.length<=0) return null;
		for (Menu menu : menus) {
			if(menu.getId() == id){
				return menu;
			}
		}
		return null;
	}
	
	public Menu[] getMenus(int position){
		if(menus==null ||menus.length<=0) return null;
		List<Menu> list = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if(position == menu.getPosition()){
				list.add(menu);
			}
		}
		if(list.size()>0) return MenuHelper.sort(0, (Menu[]) list.toArray(new Menu[0])) ;
		return null;
	}
	
	public Menu[] getSubMenus(int pid){
		if(menus==null ||menus.length<=0) return null;
		List<Menu> list = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if(pid == menu.getPid()){
				list.add(menu);
			}
		}
		if(list.size()>0) return MenuHelper.sort(pid, (Menu[]) list.toArray(new Menu[0])) ;
		return null;
	}
}
