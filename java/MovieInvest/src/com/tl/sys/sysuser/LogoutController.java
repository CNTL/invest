package com.tl.sys.sysuser;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;

 

public class LogoutController extends BaseController 
{
	 
	public LogoutController() 
	{
		super();
	}

	@SuppressWarnings("rawtypes")
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception 
	{
		
		// ���Session����
		removeSession(request);
	}
	/**
	 * ���Session����
	 */
	protected void removeSession(HttpServletRequest request) {
		try {
			//session����ʧЧ
			HttpSession session = request.getSession();
			session.removeAttribute(SysSessionUser.sessionAdminName);
			session.invalidate();
		} catch (Exception e){}
	}

	 
}