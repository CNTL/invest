package com.tl.invest.sys.mu;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.tl.common.log.Log;
import com.tl.invest.constant.TableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;

public class MenuManagerImpl implements MenuManager {
	Log log = Context.getLog("invest");
	@Override
	public void save(Menu mu) {
		DAO d = new DAO();
	    try {
			if(mu.getId()>0){
				d.update(mu);
			}else {
				mu.setId((int)TBID.getID(TableLibs.TB_SYS_MENUES.getTableCode()));
				d.save(mu);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void save(Menu mu, Session s) {
		DAO d = new DAO();		
	    try {
			if(mu.getId()>0){
				d.update(mu,s);
			}else {
				mu.setId((int)TBID.getID(TableLibs.TB_SYS_MENUES.getTableCode()));
				d.save(mu,s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public int delete(int id) {
		return delete(id,null);
	}
	
	@Override
	public int delete(int id,Session s){
		Menu mu = s == null ? getMenu(id) : getMenu(id, s);
		if(mu == null){
			log.error("id="+id+"的菜单不存在");
			return 0;
		}
		DAO d = new DAO();	
		if(s == null){
			return d.delete(mu);
		}else {
			return d.delete(mu, s);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Menu getMenu(int id) {
		Menu mu = null;
		DAO d = new DAO();
		List mus = d.find("select a from com.tl.invest.sys.mu.Menu as a where a.id = :id", new Object[]{id});
		if(!mus.isEmpty()){
			mu = (Menu)mus.get(0);
		}
		return mu;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Menu getMenu(int id,Session s) {
		Menu mu = null;
		DAO d = new DAO();
		List mus = d.find("select a from com.tl.invest.sys.mu.Menu as a where a.id = :id", new Object[]{id}, s);
		if(!mus.isEmpty()){
			mu = (Menu)mus.get(0);
		}
		return mu;
	}

	@Override
	public Menu[] getMenus(int position) {		
		return getMenus(position,null);
	}
	
	@Override
	public Menu[] getMenus(int position, Session s) {
		Menu[] allMenus = getMenus(s);
		if(allMenus == null || allMenus.length<=0) return null;
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu menu : allMenus) {
			if(menu.getPosition() == position){
				menus.add(menu);
			}
		}
		if(menus.size()<=0) return null;
		
		return MenuHelper.sort(0,(Menu[]) menus.toArray(new Menu[0]));
	}

	@Override
	public Menu[] getSubMenus(int pid) {
		return getSubMenus(pid, null);
	}

	@Override
	public Menu[] getSubMenus(int pid, Session s) {
		Menu[] allMenus = getMenus(s);
		if(allMenus == null || allMenus.length<=0) return null;
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu menu : allMenus) {
			if(menu.getPid() == pid){
				menus.add(menu);
			}
		}
		if(menus.size()<=0) return null;
		
		return MenuHelper.sort(pid, (Menu[]) menus.toArray(new Menu[0])) ;
	}

	@Override
	public Menu[] getMenus() {
		return getMenus(null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Menu[] getMenus(Session s) {
		DAO d = new DAO();
		String hql = "select a from com.tl.invest.sys.mu.Menu as a order by a.order";		
		List list = s==null ? d.find(hql) : d.find(hql, s);		
		return MenuHelper.sort(0,(Menu[]) list.toArray(new Menu[0]));
	}
}
