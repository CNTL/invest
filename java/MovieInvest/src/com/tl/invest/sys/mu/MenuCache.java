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
	 * ��������Ҫ����Readerʵ������<br>
	 * ������ʱ��ע�ⲻҪʹ�ù��췽�������������ʵ��<br>
	 * ��ʹ��������ʽ��<br>
	 * <code>
	 * XXXCache cache = (XXXCache)(CacheReader.find(XXXCache.class));
	 * if (cache != null)
	 * 		return cache.get(...);
	 * </code>
	 * ����XXXCache�������Ļ�����
	 */
	public MenuCache(){
	}
	
	@Override
	public void refresh() throws Exception {
		MenuManager mgr = MenuHelper.getMenuManager();
		menus = mgr.getMenus();
		log.info("������£��ɹ���ȡ"+(menus==null ? "0" : String.valueOf(menus.length))+"��ϵͳ�˵���Դ��");
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
