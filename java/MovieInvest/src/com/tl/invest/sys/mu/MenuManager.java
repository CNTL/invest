package com.tl.invest.sys.mu;

import org.hibernate.Session;

public interface MenuManager extends MenuReader {
	public void save(Menu mu);
	public void save(Menu mu,Session s);
	public int delete(int id);
	public int delete(int id,Session s);
	
	public Menu[] getMenus(Session s);
	public Menu getMenu(int id,Session s);
	public Menu[] getMenus(int position,Session s);
	public Menu[] getSubMenus(int pid,Session s);
}
