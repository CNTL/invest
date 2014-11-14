package com.tl.invest.user.web;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.qq.connect.oauth.Oauth;
import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.common.StringUtils;
import com.tl.invest.user.User;
import com.tl.invest.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;

/**创建登录session
 * @created 2014-8-31
 * @author wang.yq
 * @version 1.0
 */
@SuppressWarnings({"rawtypes"})
public class UserLoginController extends BaseController 
{
	private UserManager manager = null;
	public UserManager getManager() {
		return manager;
	}

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) 
	throws Exception 
	{
		
		String action = request.getParameter("a");
		if(!StringUtils.isEmpty(action) && action.equals("create")){
			
			String result = create(request, response);
			output(result, response);
		} else if(!StringUtils.isEmpty(action) && action.equals("login")){
	
			String usercode = get(request, "usercode");
			String password = get(request,"password");
			User user = manager.login(usercode,password);
			if(user!=null){
				JSONObject jsonArray = JSONObject.fromObject(user);
				output(jsonArray.toString(), response);
			}
			else {
				output("", response);
			}
		} else if(!StringUtils.isEmpty(action) && action.equals("qqlogin")){
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} else {
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
	/** 
	* @author  leijj 
	* 功能： 用户注册
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		user.setCode(ParamInitUtils.getString(request.getParameter("code")));
		user.setEmail(ParamInitUtils.getString(request.getParameter("email")));
		user.setType(ParamInitUtils.getString(request.getParameter("type")));
		user.setTypeId(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		UserManager userManager = (UserManager) Context.getBean(UserManager.class);
		userManager.create(user);
		return "ok";
	}

	/** 将登录信息保存到session中
	 * 
	 * @param request
	 * @param loginID
	 * @param isAdmin
	 * @return
	 * @throws TLException
	 */
	protected SysSessionUser putSession(HttpServletRequest request, int loginID, boolean isAdmin) throws TLException {
		SysSessionUser user = new SysSessionUser();
		user.setAdmin(isAdmin);
		user.setUserName(get(request,"UserName"));
		user.setUserCode(get(request,"UserCode"));
		user.setUserID(getInt(request, "UserID"));
		user.setUserPassword(get(request,"UserPassword"));
		
		user.setIp(request.getRemoteAddr());
		user.setHostName(request.getRemoteHost()); 
		request.getSession().setAttribute(SysSessionUser.sessionName, user);
		return user;
	}
}
