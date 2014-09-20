package com.tl.sys.sysuser;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.tl.common.StringUtils;
import com.tl.invest.sys.user.SysUser;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.sysuser.SysuserManager;

/**创建登录session
 * @created 2014-8-31
 * @author wang.yq
 * @version 1.0
 */
public class LoginController extends BaseController 
{
	private SysuserManager sysuserManager = null;
	
	public SysuserManager getSysuserManager() {
		return sysuserManager;
	}

	public void setSysuserManager(SysuserManager sysuserManager) {
		this.sysuserManager = sysuserManager;
	}
	@SuppressWarnings("rawtypes")
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) 
	throws Exception 
	{
		
		String action = request.getParameter("a");
		if(!StringUtils.isEmpty(action)&&action.equals("login")){
	
			String usercode = get(request, "usercode");
			String password = get(request,"password");
			SysUser sysuser = sysuserManager.login(usercode,password);
			if(sysuser!=null){
				JSONObject jsonArray = JSONObject.fromObject(sysuser);
				output(jsonArray.toString(), response);
			}
			else {
				output("", response);
			}
		}else{
			 
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			try {
				
				int nID = getInt(request, "UserID");
				if (nID > 0) { 
					putSession(request, nID, false);
				} 
				out.flush();
				out.close();
			} catch (Exception e) {
				log.error("login has error!",e);
			}
		}
		
	}
	

	/** 将登录信息保存到session中
	 * 
	 * @param request
	 * @param loginID
	 * @param isAdmin
	 * @return
	 */
	protected SysSessionUser putSession(HttpServletRequest request, int loginID, boolean isAdmin) {
		SysSessionUser user = new SysSessionUser();
		user.setAdmin(isAdmin);
		user.setUserName(get(request,"UserName"));
		user.setUserCode(get(request,"UserCode"));
		user.setUserID(getInt(request, "UserID"));
		user.setUserPassword(get(request,"UserPassword"));
		
		user.setIp(request.getRemoteAddr());
		user.setHostName(request.getRemoteHost()); 
		request.getSession().setAttribute(SysSessionUser.sessionAdminName, user);
		return user;
	}
}
