package com.tl.sys.common;

import javax.servlet.http.HttpServletRequest;

import com.tl.kernel.web.SysSessionUser;

/** 
 * @created 2014年9月6日 上午11:48:28 
 * @author  leijj
 * 类说明 ： 获取登录用户信息
 */
public class SessionHelper {
	/**
	 * 取session中保存的登录用户的信息
	 * @param request
	 * @return
	 */
	public static SysSessionUser getUser(HttpServletRequest request)
	{
		SysSessionUser sysUser = (SysSessionUser)request.getSession().getAttribute(SysSessionUser.sessionName);

		return sysUser;
	}
	public static String getUserCode(HttpServletRequest request)
	{
		SysSessionUser sysUser = getUser(request);
		
		if(sysUser == null) return "";

		return sysUser.getUserCode();
	}
	
	public static int getUserID(HttpServletRequest request)
	{
		SysSessionUser sysUser = getUser(request);
		
		if(sysUser == null) return 0;

		return sysUser.getUserID();
	}
}
