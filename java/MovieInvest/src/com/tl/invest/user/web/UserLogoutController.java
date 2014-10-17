package com.tl.invest.user.web;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;

 

public class UserLogoutController extends BaseController 
{
	 
	public UserLogoutController() 
	{
		super();
	}

	@SuppressWarnings("rawtypes")
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception 
	{
		
		// 清除Session对象
		removeSession(request);
	}
	/**
	 * 清除Session对象
	 */
	protected void removeSession(HttpServletRequest request) {
		try {
			//session整体失效
			HttpSession session = request.getSession();
			session.removeAttribute(SysSessionUser.sessionAdminName);
			session.invalidate();
		} catch (Exception e){}
	}

	 
}