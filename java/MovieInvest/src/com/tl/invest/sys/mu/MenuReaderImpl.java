package com.tl.invest.sys.mu;

public class MenuReaderImpl implements MenuReader {

	@Override
	public Menu[] getMenus() {
		return MenuHelper.getCache().getMenus();
	}

	@Override
	public Menu getMenu(int id) {
		return MenuHelper.getCache().getMenu(id);
	}

	@Override
	public Menu[] getMenus(int position) {
		return MenuHelper.getCache().getMenus(position);
	}

	@Override
	public Menu[] getSubMenus(int pid) {
		return MenuHelper.getCache().getSubMenus(pid);
	}

}
