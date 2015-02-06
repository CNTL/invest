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

/** ְλ����
 * @author wang.yq
 *
 */
public class SubscibeRecruitJob{
	Log log = Context.getLog("invest");
	
	protected void execute() throws Exception {
		log.info("��ʼִ�ж�ʱ����SubscibeRecruitJob");
		try {
			StringBuilder sb = new StringBuilder();
			Map<String, String> map = new HashMap<String, String>();
			//1.��ȡ�����б�
			RecruitSubscibe[] subs = null;
			subs = recruitSubscibeManager.queryRecruitSubscibe();
			//2.�ҵ�Ҫ���͵�ְλ��Ϣ
			if(subs!=null&&subs.length>0){
				for (int i = 0; i < subs.length; i++) {
					RecruitSubscibe rs = subs[i];
					int cityid = rs.getCityId();
					int recid = rs.getRecId();
					//���ְλ
					UserRecruit[] recs = recruitManager.queryRecruitsSubscibe(cityid, recid);
					map.put(rs.getMail(), getRecs(rs.getId(),recs));
					//4.����ְλ���ķ���ʱ��
					recruitSubscibeManager.updatePostTime(String.valueOf(rs.getId()));
				}
			}
			
			//3.����ְλ��Ϣ
			for (String key : map.keySet()) {
				String mail = key;
			    String content = map.get(key);
			    
			    
				 try {
					    EmailSender emailSender = EMailSenderHelper.getEmailSender();//�ʼ����ͷ�������Ϣ����װ��
						emailSender.setSubject("����ӳ������ְλ����");//�ʼ�����   
						emailSender.setContent(content); //�ʼ�����
						emailSender.setTime(DateUtils.getTimestamp());//�ʼ�����ʱ��
						emailSender.setToAddress(mail);
						SimpleMailSender mailSender = new SimpleMailSender();
						mailSender.setMailInfo(emailSender);
						mailSender.sendHtmlMail();
				} catch (Exception e) {
					 log.debug("ְλ���ķ����ʼ�ʧ�ܡ�"+e.getMessage());
				}
				
			}

			
		} catch (Exception e) {
			log.info("ִ�ж�ʱ�������SubscibeRecruitJob");
			log.info("errorinfo��"+e.getMessage());
		}
		
		
		log.info("����ִ�ж�ʱ����SubscibeRecruitJob");
	}
	public SubscibeRecruitJob() {
		// TODO Auto-generated constructor stub
	}
	
	/** ��ְ֯λ��ʽ
	 * @param recs
	 * @return
	 * @throws Exception
	 */
	private String getRecs(Integer id,UserRecruit[] recs) throws Exception{
		StringBuilder sb = new StringBuilder();
		String urlString = "http://home.crowdcine.cn";
		try {
			urlString = ParamReader.getSEOConfig("��ǰ��ַ");
		} catch (Exception e) {
			 
		}
		
	 
		if(recs!=null && recs.length>0){
			for (int i = 0; i < recs.length; i++) {
			    sb.append("<p><a href=\""+urlString+"/recruit/DetailMain.do?a=detail&id"+recs[i].getId()+"\">");
			    sb.append(recs[i].getTypeName() +"&nbsp;&nbsp;&nbsp;&nbsp;"+recs[i].getCityName());
			    sb.append("</a></p>");
			}
		}
		sb.append("<a href=\""+urlString+"/recruit/uesrRecruitSubscibe.do?a=cancelRecruitSubscibe&id="+String.valueOf(id)+"\">ȡ��ְλ����</a>");
		return sb.toString();
	}
	
	private RecruitSubscibeManager recruitSubscibeManager = (RecruitSubscibeManager)Context.getBean(RecruitSubscibeManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);

}
