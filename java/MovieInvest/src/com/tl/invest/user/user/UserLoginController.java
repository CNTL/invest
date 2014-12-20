package com.tl.invest.user.user;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.qq.connect.oauth.Oauth;
import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.common.RandomValidateCode;
import com.tl.common.WebUtil;
import com.tl.invest.user.email.EMailSenderHelper;
import com.tl.invest.user.email.EmailSender;
import com.tl.invest.user.email.SimpleMailSender;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;

/**创建登录session
 * @created 2014-8-31
 * @author wang.yq
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserLoginController extends BaseController 
{
	private UserManager manager = null;
	public UserManager getManager() {
		return manager;
	}

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String action = request.getParameter("a");
		if("create".equals(action)){//用户注册
			String result = create(request, response);
			output(result, response);
		} else if("login".equals(action)){//普通用户登录
			login(request, response);
		} else if("authCode".equals(action)){//绘制验证码
			authCode(request, response);
		} else if("qqLogin".equals(action)){//用户通过qq登录
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} else if("xlWeiboLogin".equals(action)){//用户通过新浪微博登录
			xlWeiboLogin(request, response);
		} else if("findPwd".equals(action)){//找回密码（第一步，输入邮箱时按照注册邮箱跳转到）
			findPwd(request, response, model);
		} else if("resetPwd".equals(action)){//找回密码（第一步，输入邮箱时按照注册邮箱跳转到）
			resetPwd(request, response, model);
		} else {
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			try {
				int nID = getInt(request, "UserID");
				if (nID > 0) { 
					putSession(request, response, nID, false);
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
		user.setPerNickName(ParamInitUtils.getString(request.getParameter("code")));
		user.setEmail(ParamInitUtils.getString(request.getParameter("email")));
		//user.setType(ParamInitUtils.getString(request.getParameter("type")));
		user.setType(ParamInitUtils.getInt(request.getParameter("type")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		UserManager userManager = (UserManager) Context.getBean(UserManager.class);
		String result = userManager.create(user);
		return result;
	}

	/** 
	* @author  leijj 
	* 功能： 正常注册用户登录
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	protected void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
	}
	/** 
	* @author  leijj 
	* 功能： 用户通过新浪微博登录
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	protected void xlWeiboLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
	}
	protected void authCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("image/gif");//设置相应类型,告诉浏览器输出的内容为图片
	    response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expire", 0);
	    RandomValidateCode randomValidateCode = new RandomValidateCode();
	    try {
	    	String validate = randomValidateCode.getRandcode(request, response);//输出图片方法
	    	output(validate, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	/** 将登录信息保存到session中
	 * 
	 * @param request
	 * @param loginID
	 * @param isAdmin
	 * @return
	 * @throws Exception 
	 */
	protected User putSession(HttpServletRequest request,
			HttpServletResponse response, int loginID, boolean isAdmin) throws Exception {
		User user = manager.getUserByID(loginID);
		if(user != null){
			JSONObject jsonArray = JSONObject.fromObject(user);
			output(jsonArray.toString(), response);
		} else {
			output("", response);
		}
		SysSessionUser sysUser = new SysSessionUser();
		sysUser.setAdmin(isAdmin);
		sysUser.setUserName(user.getName());
		sysUser.setUserCode(user.getCode());
		sysUser.setUserID(user.getId());
		//sysUser.setUserPassword(user.getPassword());
		sysUser.setIp(request.getRemoteAddr());
		sysUser.setHostName(request.getRemoteHost());
		request.getSession().setAttribute(SysSessionUser.sessionName, sysUser);
		return user;
	}
	
	/** 
	* @author  leijj 
	* 功能： 找回密码第一步
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void findPwd(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		String email = ParamInitUtils.getString(request.getParameter("email"));
		if(email == null || email.length() == 0){
			output("{\"error\":\"请输入邮箱地址！\"}", response);
		}
		User user = manager.getUserByEmail(email);
		if(user == null){
			output("{\"error\":\"您输入的邮箱地址未注册！\"}", response);
		} else {//已注册
			//1.忘注册邮箱发送邮件
			sendEmail(user);
			//2.跳转至找回密码步骤2
			model.put("email", email);
			Map<String, String> result = new HashMap<String, String>();
			result.put("email", email);
			result.put("emailType", "http://mail.".concat(email.substring(email.lastIndexOf("@") + 1)));
			JSONObject jsonArray = JSONObject.fromObject(result);
			output(jsonArray.toString(), response);
			//output("{\"email\":\"" + email + "\"}", response);
			//model.put("@VIEWNAME@", WebUtil.getRoot(request) + "user/noSession/findPwdStep2.jsp");
		}
	}
	
	/** 
	* @author  leijj 
	* 功能： 发送找回密码邮件
	* @param email 
	*/ 
	private void sendEmail(User user){
		StringBuilder emailHtml =  new StringBuilder("");
		emailHtml.append("<a href=\"../../user/userlogin.do?a=resetPwd&id=")
			.append(user.getId()).append("\">").append("此链接重置密码").append("</a>");
		EmailSender emailSender = EMailSenderHelper.getEmailSender();//邮件发送服务器信息先组装好
		emailSender.setSubject("合众映画——找回密码");//邮件标题   
		emailSender.setContent(emailHtml.toString()); //邮件内容
		emailSender.setTime(DateUtils.getTimestamp());//邮件发送时间
		emailSender.setToAddress(user.getEmail());
		SimpleMailSender mailSender = new SimpleMailSender();
		mailSender.setMailInfo(emailSender);
		mailSender.sendHtmlMail();
	}
	
	private void resetPwd(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		User user = manager.getUserByID(id);
		//JSONObject jsonArray = JSONObject.fromObject(user);
		//output(jsonArray.toString(), response);
		model.put("user", user);
		model.put("@VIEWNAME@", WebUtil.getRoot(request) + "user/noSession/findPwdStep3.jsp");
	}
}