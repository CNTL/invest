package com.tl.invest.user.recruit;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.common.ParamReader;
import com.tl.invest.user.email.EMailSenderHelper;
import com.tl.invest.user.email.EmailSender;
import com.tl.invest.user.email.SimpleMailSender;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年12月24日 下午8:46:18 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings("rawtypes")
public class RecruitResumeController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("collect".equals(action)){//收藏职位
			collect(request, response, model);
		} else if("post".equals(action)){//投递简历
			post(request, response, model);
		}
		else if("report".equals(action)){//举报职位
			report(request, response);
		}
	}
	
	 
	
	/** 
	* @author  leijj 
	* 功能： 收藏职位保存
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private void collect(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int recruitID = ParamInitUtils.getInt(request.getParameter("recruitID"));
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = 0;
		if(user != null) userId = user.getId();
		UserRecruitresume recruitresume = recruitManager.recruitresume(userId, recruitID);
		if(recruitresume != null) {
			output("您已收藏该职位！", response);
			return;
		}
		
		//未收藏职位时保存收藏职位信息
		UserRecruitresume recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setCollected(1);//收藏为1
  
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		
		output("ok", response);
	}
	
	private void report(HttpServletRequest request, HttpServletResponse response) throws Exception{
		StringBuilder emailHtml =  new StringBuilder("");
		String reportItem  = get(request, "reportitem","其他");
		String reportConetent = get(request, "reportcontent","");
		int recIDString = getInt(request, "recid",0);
		
		String homeurlString = ParamReader.getSEOConfig("当前网址");
		String webrootString = WebUtil.getRoot(request);
		if(homeurlString!=null){
			webrootString = homeurlString;
		}
		emailHtml.append("<a href=\"").append(webrootString)
			.append("recruit/DetailMain.do?a=detail&id=")
			.append(recIDString).append("\">").append("举报职位链接,点击查看").append("</a>");
		emailHtml.append("<p>"+reportConetent+"</p>");
		EmailSender emailSender = EMailSenderHelper.getEmailSender();//邮件发送服务器信息先组装好
		emailSender.setSubject("职位举报——"+reportItem);//邮件标题   
		emailSender.setContent(emailHtml.toString()); //邮件内容
		emailSender.setTime(DateUtils.getTimestamp());//邮件发送时间
		emailSender.setToAddress("24295781@qq.com");
		SimpleMailSender mailSender = new SimpleMailSender();
		mailSender.setMailInfo(emailSender);
		mailSender.sendHtmlMail();
		output("ok", response);
	}
	
	private void post(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = 0;
		if(user != null) userId = user.getId();
		int recruitID = ParamInitUtils.getInt(request.getParameter("recruitID"));
		 
		UserRecruitresume recruitresume = recruitManager.recruitresumePost(userId, recruitID);
		if(recruitresume != null) {
			output("该职位您已投递简历！", response);
			return;
		}
		
		UserRecruitresume recruitResume = recruitManager.recruitresume(user.getId(), recruitID);
		if(recruitResume == null)
			recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setCollected(0);//投递为0
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		output("ok", response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}
