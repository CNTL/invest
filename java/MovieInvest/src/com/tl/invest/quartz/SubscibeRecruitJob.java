package com.tl.invest.quartz;

 
import java.util.HashMap;
import java.util.Map;

import com.tl.common.DateUtils;
 
import com.tl.common.log.Log;
 
import com.tl.invest.common.ParamReader;
import com.tl.invest.user.email.EMailSenderHelper;
import com.tl.invest.user.email.EmailSender;
import com.tl.invest.user.email.SimpleMailSender;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.RecruitSubscibe;
import com.tl.invest.user.recruit.RecruitSubscibeManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.kernel.context.Context;

/** 职位订阅
 * @author wang.yq
 *
 */
public class SubscibeRecruitJob{
	Log log = Context.getLog("invest");
	
	protected void execute() throws Exception {
		log.info("开始执行定时任务：SubscibeRecruitJob");
		try {
			StringBuilder sb = new StringBuilder();
			Map<String, String> map = new HashMap<String, String>();
			//1.获取订阅列表
			RecruitSubscibe[] subs = null;
			subs = recruitSubscibeManager.queryRecruitSubscibe();
			//2.找到要发送的职位信息
			if(subs!=null&&subs.length>0){
				for (int i = 0; i < subs.length; i++) {
					RecruitSubscibe rs = subs[i];
					int cityid = rs.getCityId();
					int recid = rs.getRecId();
					//获得职位
					UserRecruit[] recs = recruitManager.queryRecruitsSubscibe(cityid, recid);
					map.put(rs.getMail(), getRecs(rs.getId(),recs));
					//4.更新职位订阅发送时间
					recruitSubscibeManager.updatePostTime(String.valueOf(rs.getId()));
				}
			}
			
			//3.发送职位信息
			for (String key : map.keySet()) {
				String mail = key;
			    String content = map.get(key);
			    
			    
				 try {
					    EmailSender emailSender = EMailSenderHelper.getEmailSender();//邮件发送服务器信息先组装好
						emailSender.setSubject("合众映画——职位订阅");//邮件标题   
						emailSender.setContent(content); //邮件内容
						emailSender.setTime(DateUtils.getTimestamp());//邮件发送时间
						emailSender.setToAddress(mail);
						SimpleMailSender mailSender = new SimpleMailSender();
						mailSender.setMailInfo(emailSender);
						mailSender.sendHtmlMail();
				} catch (Exception e) {
					 log.debug("职位订阅发送邮件失败。"+e.getMessage());
				}
				
			}

			
		} catch (Exception e) {
			log.info("执行定时任务错误：SubscibeRecruitJob");
			log.info("errorinfo："+e.getMessage());
		}
		
		
		log.info("结束执行定时任务：SubscibeRecruitJob");
	}
	public SubscibeRecruitJob() {
		// TODO Auto-generated constructor stub
	}
	
	/** 组织职位样式
	 * @param recs
	 * @return
	 * @throws Exception
	 */
	private String getRecs(Integer id,UserRecruit[] recs) throws Exception{
		StringBuilder sb = new StringBuilder();
		String urlString = "http://home.crowdcine.cn";
		try {
			urlString = ParamReader.getSEOConfig("当前网址");
		} catch (Exception e) {
			 
		}
		
	 
		if(recs!=null && recs.length>0){
			for (int i = 0; i < recs.length; i++) {
			    sb.append("<p><a href=\""+urlString+"/recruit/DetailMain.do?a=detail&id"+recs[i].getId()+"\">");
			    sb.append(recs[i].getTypeName() +"&nbsp;&nbsp;&nbsp;&nbsp;"+recs[i].getCityName());
			    sb.append("</a></p>");
			}
		}
		sb.append("<a href=\""+urlString+"/recruit/uesrRecruitSubscibe.do?a=cancelRecruitSubscibe&id="+String.valueOf(id)+"\">取消职位订阅</a>");
		return sb.toString();
	}
	
	private RecruitSubscibeManager recruitSubscibeManager = (RecruitSubscibeManager)Context.getBean(RecruitSubscibeManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);

}
