package com.tl.invest.sys.mu;

/**
 * �˵���ȡ��,���ڶ�ȡ�˵�<br>
 * ��ȡʵ���ķ�����MenuReader reader = (MenuReader)Context.getBean("MenuReader");
 */
public interface MenuReader {
	public Menu[] getMenus();
	public Menu getMenu(int id);
	public Menu[] getMenus(int position);
	public Menu[] getSubMenus(int pid);
}
