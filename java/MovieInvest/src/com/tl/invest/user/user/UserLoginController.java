package com.tl.invest.user.user;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.qq.connect.oauth.Oauth;
import com.tl.common.DateJsonValueProcessor;
import com.tl.common.DateUtils;
import com.tl.common.JsonDateValueProcessor;
import com.tl.common.ParamInitUtils;
import com.tl.common.RandomValidateCode;
import com.tl.common.WebUtil;
import com.tl.invest.user.email.EMailSenderHelper;
import com.tl.invest.user.email.EmailSender;
import com.tl.invest.user.email.SimpleMailSender;
import com.tl.invest.user.photo.PhotoManager;
import com.tl.invest.user.photo.UserPhoto;
import com.tl.invest.user.photo.UserPhotogroup;
import com.tl.invest.user.video.UserVideoManager;
import com.tl.invest.user.video.UserVideogroup;
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
		} if("checkuser".equals(action)){//用户注册
			String result = checkUser(request, response);
			output(result, response);
		} else if("loginRel".equals(action)){//已有账号关联
			String result = loginRel(request, response);
			output(result, response);
		} else if("registerRel".equals(action)){//新账号管理
			String result = registerRel(request, response);
			output(result, response);
		} else if("login".equals(action)){//普通用户登录
			login(request, response);
		} else if("authCode".equals(action)){//绘制验证码
			authCode(request, response);
		} else if("qqLogin".equals(action)){//用户通过qq登录
			putRedirectURL(request);
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} else if("sinaWeibo".equals(action)){//用户通过新浪微博登录
			sinaWeibo(request, response);
		} else if("findPwd".equals(action)){//找回密码（第一步，输入邮箱时按照注册邮箱跳转到）
			findPwd(request, response, model);
		} else if("resetPwd".equals(action)){//找回密码（第三步，重置密码页面）
			resetPwd(request, response, model);
		} else if("updatePwd".equals(action)){//找回密码（重置密码保存）
			updatePwd(request, response, model);
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
	private String checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		String retString1 ="";
		String retString2 ="";
		String usercode = get(request, "code","");
		String userMail = get(request, "mail","");
		UserManager userManager = (UserManager) Context.getBean(UserManager.class);
		try {
			user = userManager.getUserByCode(usercode);
			if(user!=null&&user.getId()>0){
				retString1 = "该账号已近存在。";
			}
		} catch (Exception e) {
			
		}
		try {
			user = userManager.getUserByEmail(userMail);
			if(user!=null&&user.getId()>0){
				retString2 = "该邮件已被使用。";
			}
		} catch (Exception e) {
			
		}
		
		
		return "{\"code\":\""+retString1+"\",\"mail\":\""+retString2+"\"}";
	}
	/** 
	* @author  leijj 
	* 功能： 验证用户名和邮件是否有重复
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		user.setCode(get(request, "code",""));
		user.setPerNickName(get(request, "code",""));
		user.setEmail(get(request, "email",""));
		Integer type = getInt(request, "type",0);
		user.setType(type);
		user.setPassword(get(request, "password",""));
		user.setCreateTime(DateUtils.getTimestamp());
		user.setIsRealNameIdent(0);//未认证
		UserManager userManager = (UserManager) Context.getBean(UserManager.class);
		String result = userManager.create(user);
		savePhotoGroup(user, type, request, response);
		saveVideoGroup(user, type, request, response);
		return result;
	}
	/** 
	* @author  leijj 
	* 功能： 保存图册信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void savePhotoGroup(User user, int type, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserPhotogroup photogroup = new UserPhotogroup();
		
		photogroup.setUserId(user.getId());
		photogroup.setUserName(user.getName());
		photogroup.setGroupPhoto("user//photo//img//framels_hover.jpg");
		photogroup.setCreateTime(DateUtils.getTimestamp());
		if(type == 0){//个人
			photogroup.setGroupName("生活照");
			photoManager.savePhotoGroup(photogroup);
			savePhoto(user, photogroup, request, response);
			
			photogroup.setId(0);
			photogroup.setGroupName("剧照");
			photoManager.savePhotoGroup(photogroup);
			savePhoto(user, photogroup, request, response);
		} else if(type == 1){//机构
			photogroup.setGroupName("默认");
			photoManager.savePhotoGroup(photogroup);
			savePhoto(user, photogroup, request, response);
		}
	}
	private void savePhoto(User user, UserPhotogroup photogroup, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserPhoto photo = new UserPhoto();
		
		photo.setUserId(user.getId());
		photo.setUserName(user.getName());
		photo.setGroupId(photogroup.getId());
		photo.setGroupName(photogroup.getGroupName());
		photo.setPhotoName("默认");
		photo.setPhoto("user//photo//img//framels_hover.jpg");
		photo.setCreateTime(DateUtils.getTimestamp());
		photoManager.savePhoto(photo);
	}
	private void saveVideoGroup(User user, int type , HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserVideogroup videogroup = new UserVideogroup();
		
		videogroup.setUserId(user.getId());
		videogroup.setUserName(user.getName());
		videogroup.setGroupPhoto("user//photo//img//framels_hover.jpg");
		videogroup.setCreateTime(DateUtils.getTimestamp());
		videogroup.setGroupName("默认");
		userVideoManager.saveVideoGroup(videogroup);
	}
	/** 
	* @author  leijj 
	* 功能： 与新账号关联
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String registerRel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		String qqOpenId = ParamInitUtils.getString(request.getParameter("qqOpenId"));
		String xlWeiboCode = ParamInitUtils.getString(request.getParameter("xlWeiboCode"));
		if(qqOpenId != null && !("".equals(qqOpenId))){
			user.setQqOpenId(qqOpenId);
		}
		if(xlWeiboCode != null && !("".equals(xlWeiboCode))){
			user.setXlWeiboCode(xlWeiboCode);
		}
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
	* 功能： 与已有账号关联
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String loginRel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code = ParamInitUtils.getString(request.getParameter("code"));
		if(code == null || "".equals(code)) return "账号为空！";
		
		User user = manager.getUserByCode(code);
		if(user == null) return "该账号不存在！";
		String qqOpenId = ParamInitUtils.getString(request.getParameter("qqOpenId"));
		String xlWeiboCode = ParamInitUtils.getString(request.getParameter("xlWeiboCode"));
		if(qqOpenId != null && !("".equals(qqOpenId))){
			user.setQqOpenId(qqOpenId);
		}
		if(xlWeiboCode != null && !("".equals(xlWeiboCode))){
			user.setXlWeiboCode(xlWeiboCode);
		}
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
		putRedirectURL(request);
		String usercode = get(request, "usercode");
		String password = get(request,"password");
		User user = manager.login(usercode,password);
		if(user!=null){
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
			JSONObject jsonArray = JSONObject.fromObject(user,jsonConfig);
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
	protected void sinaWeibo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		putRedirectURL(request);
		response.sendRedirect(new Oauth().getAuthorizeURL(request));
		request.getSession().setAttribute("type", "sinaWeibo");
		weibo4j.Oauth oauth = new weibo4j.Oauth();
	    String accessURL = oauth.authorize("code","","");
	
		if(accessURL!=null){
			response.sendRedirect(accessURL);
			return;
		}else{
			System.out.println("(sinawb)requestToken为空,出错!");
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
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		 
		if(user != null){
			JSONObject jsonArray = JSONObject.fromObject(user,jsonConfig);
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
			sendEmail(request, user);
			//2.跳转至找回密码步骤2
			model.put("email", email);
			Map<String, String> result = new HashMap<String, String>();
			result.put("email", email);
			result.put("emailType", "http://mail.".concat(email.substring(email.lastIndexOf("@") + 1)));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
			JSONObject jsonArray = JSONObject.fromObject(result,jsonConfig);
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
	private void sendEmail(HttpServletRequest request, User user){
		StringBuilder emailHtml =  new StringBuilder("");
		emailHtml.append("<a href=\"").append(WebUtil.getRoot(request))
			.append("user/userlogin.do?a=resetPwd&id=")
			.append(user.getId()).append("\">").append("请点击此链接重置合众映画登陆密码").append("</a>");
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
		model.put("@VIEWNAME@", "userout/findPwdStep3");
	}
	private void updatePwd(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		if(id == 0) return;
		User user = manager.getUserByID(id);
		user.setPassword(ParamInitUtils.getString(request.getParameter("newpwd")));
		manager.update(user);
		output(String.valueOf(user.getId()), response);
	}
	private void putRedirectURL(HttpServletRequest request){
		String redirectURL = request.getParameter("redirectURL");
		//放入session中，以便登录成功后，可以跳转到主页或者是其他页面
		request.getSession(true).setAttribute("redirectURL", redirectURL);
		String state = request.getParameter("state");
		request.getSession().setAttribute("state", state);
	}
	private PhotoManager photoManager = (PhotoManager)Context.getBean(PhotoManager.class);
	private UserVideoManager userVideoManager = (UserVideoManager)Context.getBean(UserVideoManager.class);
}