package com.tl.invest.user.user;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tl.common.WebUtil;
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
		
		// ���Session����
		removeSession(request);
		response.sendRedirect(WebUtil.getRoot(request) + "userout/userLogin.jsp");
	}
	/**
	 * ���Session����
	 */
	protected void removeSession(HttpServletRequest request) {
		try {
			//session����ʧЧ
			HttpSession session = request.getSession();
			session.removeAttribute(SysSessionUser.sessionName);
			session.invalidate();
		} catch (Exception e){}
	}

	 
}