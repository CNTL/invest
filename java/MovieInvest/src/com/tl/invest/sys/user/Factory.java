package com.tl.invest.sys.user;

public class Factory {
	private static SysUserReader userReader;
	private static SysUserManager userManager;
	
	public static SysUserReader buildUserReader(){
		if (userReader == null) userReader = new SysUserReaderImpl();
		return userReader;
	}

	public static SysUserManager buildUserManager(){
		if (userManager == null) userManager = new SysUserManagerImpl();
		return userManager;
	}
}
