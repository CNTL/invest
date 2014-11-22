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
 * @created 2014��9��18�� ����3:41:52 
 * @author  leijj
 * ��˵�� �� ���ʼ��������������ʼ���������  
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
      * ��HTML��ʽ�����ʼ�  
      * @param mailInfo �����͵��ʼ���Ϣ  
      */   
    public boolean sendHtmlMail(){
    	// �ж��Ƿ���Ҫ�����֤
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// �����Ҫ�����֤���򴴽�һ��������֤��
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			
			// �����ʼ���Ϣ�ķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��   
		    Address to = new InternetAddress(mailInfo.getToAddress());   
		    // Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO   
		    mailMessage.setRecipient(Message.RecipientType.TO,to);   
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵�ʱ��
			mailMessage.setSentDate(mailInfo.getTime());

			// 1.��������  MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
		    MimeMultipart mp = new MimeMultipart();    
		    MimeBodyPart mailContentPart = new MimeBodyPart();
		    mailContentPart.setContent(mailInfo.getContent(),"text/html; charset=utf-8"); 
		    mailMessage.setContent(mailInfo.getContent(),"text/html; charset=utf-8");
		    // ������Ҫ��ǧ��Ҫ����
		    mp.setSubType("related");
		    mp.addBodyPart(mailContentPart);
		    
			//���ø���  
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
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			return false;
		}
	}  
    
    /**  
     * ��HTML��ʽ�����ʼ�  
     * @param mailInfo �����͵��ʼ���Ϣ  
     */   
   public boolean isConnect(){
   	// �ж��Ƿ���Ҫ�����֤
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// �����Ҫ�����֤���򴴽�һ��������֤��
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣ�ķ�����
			mailMessage.setFrom(from);
			//�����ʼ�smtp�������������ֱ�Ϊ��������ַ���û���������
			Transport transport = sendMailSession.getTransport();
			transport.connect(mailInfo.getMailServerHost(), mailInfo.getUserName(), mailInfo.getPassword());
			//�ر�����
			transport.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}