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
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.email.EMailSenderHelper;
import com.tl.invest.user.email.EmailSender;
import com.tl.invest.user.email.SimpleMailSender;
import com.tl.invest.user.photo.PhotoManager;
import com.tl.invest.user.photo.UserPhoto;
import com.tl.invest.user.photo.UserPhotogroup;
import com.tl.invest.user.video.UserVideoManager;
import com.tl.invest.user.video.UserVideogroup;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
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

	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String action = request.getParameter("a");
		if("create".equals(action)){//�û�ע��
			String result = create(request, response);
			output(result, response);
		} if("checkuser".equals(action)){//�û�ע��
			String result = checkUser(request, response);
			output(result, response);
		} else if("loginRel".equals(action)){//�����˺Ź���
			String result = loginRel(request, response);
			output(result, response);
		} else if("registerRel".equals(action)){//���˺Ź���
			String result = registerRel(request, response);
			output(result, response);
		} else if("login".equals(action)){//��ͨ�û���¼
			login(request, response);
		} else if("authCode".equals(action)){//������֤��
			authCode(request, response);
		} else if("qqLogin".equals(action)){//�û�ͨ��qq��¼
			putRedirectURL(request);
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} else if("sinaWeibo".equals(action)){//�û�ͨ������΢����¼
			sinaWeibo(request, response);
		} else if("findPwd".equals(action)){//�һ����루��һ������������ʱ����ע��������ת����
			findPwd(request, response, model);
		} else if("resetPwd".equals(action)){//�һ����루����������������ҳ�棩
			resetPwd(request, response, model);
		} else if("updatePwd".equals(action)){//�һ����루�������뱣�棩
			updatePwd(request, response, model);
		}else if("orgTypeDatas".equals(action)){
			orgTypeDatas(request,response);
		}
		
		else {
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
	private void orgTypeDatas(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_ORG_TYPE.typeID());
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary job : jobTypes) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+job.getId());
			sb1.append(",\"pid\":"+job.getPid());
			sb1.append(",\"name\":\""+job.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"orgTypes\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� �û�ע��
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
				retString1 = "���˺��ѽ����ڡ�";
			}
		} catch (Exception e) {
			
		}
		try {
			user = userManager.getUserByEmail(userMail);
			if(user!=null&&user.getId()>0){
				retString2 = "���ʼ��ѱ�ʹ�á�";
			}
		} catch (Exception e) {
			
		}
		
		
		return "{\"code\":\""+retString1+"\",\"mail\":\""+retString2+"\"}";
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��֤�û������ʼ��Ƿ����ظ�
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
		user.setPoint(10); //Ĭ��Ϊ10��
		user.setPassword(get(request, "password",""));
		user.setCreateTime(DateUtils.getTimestamp());
		user.setPerJob(get(request, "recIDs",""));
		user.setIsRealNameIdent(0);//δ��֤
		user.setName_show(1);
		user.setIntro_show(1);
		user.setHeight_show(1);
		user.setWeight_show(1);
		user.setSchool_show(1);
		user.setProfessional_show(1);
		user.setDegree_show(1);
		UserManager userManager = (UserManager) Context.getBean(UserManager.class);
		String result = userManager.create(user);
		savePhotoGroup(user, type, request, response);
		saveVideoGroup(user, type, request, response);
		return result;
	}
	/** 
	* @author  leijj 
	* ���ܣ� ����ͼ����Ϣ
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
		if(type == 0){//����
			photogroup.setGroupName("������");
			photoManager.savePhotoGroup(photogroup);
			//savePhoto(user, photogroup, request, response);
			
			photogroup.setId(0);
			photogroup.setGroupName("����");
			photoManager.savePhotoGroup(photogroup);
			//savePhoto(user, photogroup, request, response);
		} else if(type == 1){//����
			photogroup.setGroupName("Ĭ��");
			photoManager.savePhotoGroup(photogroup);
			//savePhoto(user, photogroup, request, response);
		}
	}
	private void savePhoto(User user, UserPhotogroup photogroup, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserPhoto photo = new UserPhoto();
		
		photo.setUserId(user.getId());
		photo.setUserName(user.getName());
		photo.setGroupId(photogroup.getId());
		photo.setGroupName(photogroup.getGroupName());
		photo.setPhotoName("Ĭ��");
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
		videogroup.setGroupName("Ĭ��");
		userVideoManager.saveVideoGroup(videogroup);
	}
	/** 
	* @author  leijj 
	* ���ܣ� �����˺Ź���
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
	* ���ܣ� �������˺Ź���
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String loginRel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code = ParamInitUtils.getString(request.getParameter("code"));
		if(code == null || "".equals(code)) return "�˺�Ϊ�գ�";
		
		User user = manager.getUserByCode(code);
		if(user == null) return "���˺Ų����ڣ�";
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
	* ���ܣ� ����ע���û���¼
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
	* ���ܣ� �û�ͨ������΢����¼
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
			System.out.println("(sinawb)requestTokenΪ��,����!");
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
		sysUser.setSessionID(request.getSession().getId());
		sysUser.setAdmin(isAdmin);
		sysUser.setUserName(user.getName());
		sysUser.setUserCode(user.getCode());
		sysUser.setUserID(user.getId());
		//sysUser.setUserPassword(user.getPassword());
		sysUser.setIp(request.getRemoteAddr());
		sysUser.setHostName(request.getRemoteHost());
		request.getSession().setAttribute(SysSessionUser.sessionName, sysUser);
		
		manager.updateLoginInfo(user.getId(), sysUser.getSessionID());
		
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
			sendEmail(request, user);
			//2.��ת���һ����벽��2
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
	* ���ܣ� �����һ������ʼ�
	* @param email 
	*/ 
	private void sendEmail(HttpServletRequest request, User user){
		StringBuilder emailHtml =  new StringBuilder("");
		emailHtml.append("<a href=\"").append(WebUtil.getRoot(request))
			.append("user/userlogin.do?a=resetPwd&id=")
			.append(user.getId()).append("\">").append("�������������ú���ӳ����½����").append("</a>");
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
		//����session�У��Ա��¼�ɹ��󣬿�����ת����ҳ����������ҳ��
		request.getSession(true).setAttribute("redirectURL", redirectURL);
		String state = request.getParameter("state");
		request.getSession().setAttribute("state", state);
	}
	private PhotoManager photoManager = (PhotoManager)Context.getBean(PhotoManager.class);
	private UserVideoManager userVideoManager = (UserVideoManager)Context.getBean(UserVideoManager.class);
}