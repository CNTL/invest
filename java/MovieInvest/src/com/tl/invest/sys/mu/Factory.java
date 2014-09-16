package com.tl.invest.sys.mu;


public class Factory {
	private static MenuReader menuReader;
	private static MenuManager menuManager;
	
	public static MenuReader buildMenuReader(){
		if (menuReader == null) menuReader = new MenuReaderImpl();
		return menuReader;
	}

	public static MenuManager buildMenuManager(){
		if (menuManager == null) menuManager = new MenuManagerImpl();
		return menuManager;
	}
}
