package com.tl.invest.user.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author leijj
 * �ʼ����Ͱ�����
 */
public class EMailSenderHelper {
	public static EmailSender getEmailSender(){
		try {
			// �����ʼ�
			System.setProperty("mail.mime.charset","UTF-8");//��ֹ��linux�±����������
			EmailSender mailInfo = new EmailSender();
			mailInfo.setMailServerHost("smtp.yeah.net");
			mailInfo.setMailServerPort("25");
			mailInfo.setUserName("leijuan1014");
			mailInfo.setPassword("ljj10282925");// ��������
			mailInfo.setFromAddress("leijuan1014@yeah.net");
			mailInfo.setProxy(false);
			mailInfo.setProxyName("");
			mailInfo.setProxyPort("");
			mailInfo.setValidate(true);
			return mailInfo;
		} catch (Exception e) {
			e.printStackTrace();
			//throw new Exception("��������-���÷����ʼ�ʧ��");
		}
		return null;
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� �ʼ���ʽ��֤
	* @param email
	* @return 
	*/ 
	public static boolean regular(String email){
		if(email == null || email == "") return false;
		String regular = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		//"/^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$/i";
		Pattern pattern = Pattern.compile(regular);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}