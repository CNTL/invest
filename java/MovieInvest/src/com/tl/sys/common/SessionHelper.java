package com.tl.sys.common;

import javax.servlet.http.HttpServletRequest;

import com.tl.kernel.web.SysSessionUser;

/** 
 * @created 2014��9��6�� ����11:48:28 
 * @author  leijj
 * ��˵�� �� ��ȡ��¼�û���Ϣ
 */
public class SessionHelper {
	/**
	 * ȡsession�б���ĵ�¼�û�����Ϣ
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
