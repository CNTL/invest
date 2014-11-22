package com.tl.invest.user.email;

import java.sql.Timestamp;
import java.util.Properties;

public class EmailSender {
	private String mailServerHost;
	private String mailServerPort = "25";
	// �ʼ������ߵĵ�ַ
	private String fromAddress;
	// �ʼ������ߵĵ�ַ
	private String toAddress;
	private String[] toAddressList;
	// ��½�ʼ����ͷ��������û���������
	private String userName;
	private String password;
	// �Ƿ���Ҫ�����֤
	private boolean validate = false;
	// �ʼ�����
	private String subject;
	// �ʼ����ı�����
	private String content;
	// �ʼ��������ļ���    
    private String[] attachFileNames;
    //�ǹ����ô���
    private boolean isProxy=false;
    //��������
    private String proxyName;
    //����˿�
    private String proxyPort;
    // �ʼ�����ʱ��
    private Timestamp time;
	public Properties getProperties(){    
	      Properties p = new Properties(); 
	      p.put("mail.transport.protocol", "smtp");  
	      p.put("mail.smtp.host", this.mailServerHost);    
	      p.put("mail.smtp.port", this.mailServerPort);    
	      p.put("mail.smtp.auth", validate ? "true" : "false");   
	      if(this.isProxy){
	    	  p.put("proxySet", isProxy);
	    	  p.put("socksProxyHost",proxyName);
	    	  p.put("socksProxyPort",proxyPort);
	      }
	      return p;    
	}    

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String[] getToAddressList() {
		return toAddressList;
	}

	public void setToAddressList(String[] toAddressList) {
		this.toAddressList = toAddressList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
}