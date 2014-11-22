package com.tl.invest.user.email;

import java.sql.Timestamp;
import java.util.Properties;

public class EmailSender {
	private String mailServerHost;
	private String mailServerPort = "25";
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String toAddress;
	private String[] toAddressList;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = false;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名    
    private String[] attachFileNames;
    //是够启用代理
    private boolean isProxy=false;
    //代理名称
    private String proxyName;
    //代理端口
    private String proxyPort;
    // 邮件发送时间
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