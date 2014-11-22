package com.tl.invest.user.email;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.tl.common.log.Log;
import com.tl.kernel.context.Context;

/** 
 * @created 2014年9月18日 下午3:41:52 
 * @author  leijj
 * 类说明 ： 简单邮件（不带附件的邮件）发送器  
 */
  
public class SimpleMailSender  { 
	protected Log log = Context.getLog("invest");	
	private EmailSender mailInfo;
	public SimpleMailSender(){
	}
	
	public EmailSender getMailInfo() {
		return mailInfo;
	}

	public void setMailInfo(EmailSender mailInfo) {
		this.mailInfo = mailInfo;
	}
      
    /**  
      * 以HTML格式发送邮件  
      * @param mailInfo 待发送的邮件信息  
      */   
    public boolean sendHtmlMail(){
    	// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			// 创建邮件的接收者地址，并设置到邮件消息中   
		    Address to = new InternetAddress(mailInfo.getToAddress());   
		    // Message.RecipientType.TO属性表示接收者的类型为TO   
		    mailMessage.setRecipient(Message.RecipientType.TO,to);   
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(mailInfo.getTime());

			// 1.保存内容  MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		    MimeMultipart mp = new MimeMultipart();    
		    MimeBodyPart mailContentPart = new MimeBodyPart();
		    mailContentPart.setContent(mailInfo.getContent(),"text/html; charset=utf-8"); 
		    mailMessage.setContent(mailInfo.getContent(),"text/html; charset=utf-8");
		    // 这句很重要，千万不要忘了
		    mp.setSubType("related");
		    mp.addBodyPart(mailContentPart);
		    
			//设置附件  
			String[] attachFileNames = mailInfo.getAttachFileNames();
            if(attachFileNames != null && attachFileNames.length > 0){  
                for(int i = 0 ; i < attachFileNames.length;i++){  
                    MimeBodyPart attachmentPart = new MimeBodyPart();  
                    File attachFile = new File(attachFileNames[i]); 
                    FileDataSource source = new FileDataSource(attachFile);  
                    attachmentPart.setDataHandler(new DataHandler(source));  
                    String fileName = attachFileNames[i].substring(attachFileNames[i].lastIndexOf("_") + 1);
                    attachmentPart.setFileName(MimeUtility.encodeWord(fileName, "gb2312", null));  
                    mp.addBodyPart(attachmentPart);  
                }   
            } 
            mailMessage.setContent(mp);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			return false;
		}
	}  
    
    /**  
     * 以HTML格式发送邮件  
     * @param mailInfo 待发送的邮件信息  
     */   
   public boolean isConnect(){
   	// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			//连接邮件smtp服务器，参数分别为服务器地址，用户名和密码
			Transport transport = sendMailSession.getTransport();
			transport.connect(mailInfo.getMailServerHost(), mailInfo.getUserName(), mailInfo.getPassword());
			//关闭连接
			transport.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}