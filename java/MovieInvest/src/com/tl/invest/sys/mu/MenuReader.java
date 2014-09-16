package com.tl.invest.sys.mu;

/**
 * 菜单读取器,用于读取菜单<br>
 * 获取实例的方法：MenuReader reader = (MenuReader)Context.getBean("MenuReader");
 */
public interface MenuReader {
	public Menu[] getMenus();
	public Menu getMenu(int id);
	public Menu[] getMenus(int position);
	public Menu[] getSubMenus(int pid);
}
