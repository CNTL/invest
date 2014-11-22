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
import com.tl.kernel.context.TLException;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;

/**������¼session
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

	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) 
	throws Exception 
	{
		
		String action = request.getParameter("a");
		if("create".equals(action)){//�û�ע��
			String result = create(request, response);
			output(result, response);
		} else if("login".equals(action)){//��ͨ�û���¼
			login(request, response);
		} else if("authCode".equals(action)){//������֤��
			authCode(request, response);
		} else if("qqLogin".equals(action)){//�û�ͨ��qq��¼
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} else if("xlWeiboLogin".equals(action)){//�û�ͨ������΢����¼
			xlWeiboLogin(request, response);
		} else if("findPwd".equals(action)){//�һ����루��һ������������ʱ����ע��������ת����
			findPwd(request, response, model);
		} else if("resetPwd".equals(action)){//�һ����루��һ������������ʱ����ע��������ת����
			resetPwd(request, response, model);
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
	* ���ܣ� �û�ע��
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		user.setCode(ParamInitUtils.getString(request.getParameter("code")));
		user.setEmail(ParamInitUtils.getString(request.getParameter("email")));
		//user.setType(ParamInitUtils.getString(request.getParameter("type")));
		user.setType(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		UserManager userManager = (UserManager) Context.getBean(UserManager.class);
		String result = userManager.create(user);
		return result;
	}

	/** 
	* @author  leijj 
	* ���ܣ� ����ע���û���¼
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
	* ���ܣ� �û�ͨ������΢����¼
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
		response.setContentType("image/gif");//������Ӧ����,������������������ΪͼƬ
	    response.setHeader("Pragma", "No-cache");//������Ӧͷ��Ϣ�������������Ҫ���������
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expire", 0);
	    RandomValidateCode randomValidateCode = new RandomValidateCode();
	    try {
	    	String validate = randomValidateCode.getRandcode(request, response);//���ͼƬ����
	    	output(validate, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	/** ����¼��Ϣ���浽session��
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
	
	/** 
	* @author  leijj 
	* ���ܣ� �һ������һ��
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void findPwd(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		String email = ParamInitUtils.getString(request.getParameter("email"));
		if(email == null || email.length() == 0){
			output("{\"error\":\"�����������ַ��\"}", response);
		}
		User user = manager.getUserByEmail(email);
		if(user == null){
			output("{\"error\":\"������������ַδע�ᣡ\"}", response);
		} else {//��ע��
			//1.��ע�����䷢���ʼ�
			sendEmail(user);
			//2.��ת���һ����벽��2
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
	* ���ܣ� �����һ������ʼ�
	* @param email 
	*/ 
	private void sendEmail(User user){
		StringBuilder emailHtml =  new StringBuilder("");
		emailHtml.append("<a href=\"../../user/userlogin.do?a=resetPwd&id=")
			.append(user.getId()).append("\">").append("��������������").append("</a>");
		EmailSender emailSender = EMailSenderHelper.getEmailSender();//�ʼ����ͷ�������Ϣ����װ��
		emailSender.setSubject("����ӳ�������һ�����");//�ʼ�����   
		emailSender.setContent(emailHtml.toString()); //�ʼ�����
		emailSender.setTime(DateUtils.getTimestamp());//�ʼ�����ʱ��
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