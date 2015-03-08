package com.tl.kernel.web;

import java.io.Serializable;

import com.tl.common.StringUtils;



/**
 * 该类主要是为了设置session登录的参数
 * 
 * @author wang.yq
 * 2014-8-31
 */
public class SysSessionUser implements Serializable
{
	private static final long serialVersionUID = 6881131479183182845L;
	
	/**系统管理登录用户在session中保存用户信息时使用的名称*/
	public final static String sessionAdminName = "adminUser";
	
	/** 网页前端登录用户在session中保存用户信息时使用的名称 */
	public final static String sessionName = "sysUser";
	
	//超级管理员的UserID约定
	public final static String superAdminID = "-9999";
 
	private String sessionID;
	private int userID;
	private String userName;
	private String userCode;
	private String ip;
	private String hostName;//增加客户端机器名的信息
	private String userPassword;
	private boolean admin = true;
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	 
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	/**
	 * 把SysUser按字符串形式表现出来
	 * 注意每个属性的前后顺序是固定的，不能颠倒
	 * 这样可以调用restore方法进行恢复
	 * 
	 * 注意userPassword不显示
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("admin=").append(admin);
		sb.append(",ip=").append(ip);
		sb.append(",userID=").append(userID);
		sb.append(",userName=").append(userName);
		sb.append(",userCode=").append(userCode);
		sb.append(",hostName=").append(hostName);
	
		return sb.toString();
	}
	/**
	 * 把字符串形式表现的SysUser恢复为对象形式
	 * 依赖于SysUser的toString方法
	 * 要求顺序固定
	 * @param strValue
	 */
	public void restore(String strValue)
	{
		String[] parts = StringUtils.split(strValue, ",");
		setAdmin(getIntValue(parts[0])== 1);
		setIp(getValue(parts[1]));
		setUserID(getIntValue(parts[4]));
		setUserName(getValue(parts[5]));
		setUserCode(getValue(parts[6]));
		
		setHostName(getValue(parts[7]));
	}
	private String getValue(String param)
	{
		String[] parts = StringUtils.split(param, "=");
		if (parts == null || parts.length < 2) return null;
		return parts[1];
	}
	private int getIntValue(String param)
	{
		String part = getValue(param);
		if (StringUtils.isEmpty(part)) return 0;
		
		try {
			return Integer.parseInt(part);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
