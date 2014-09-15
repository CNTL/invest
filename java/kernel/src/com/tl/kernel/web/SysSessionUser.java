package com.tl.kernel.web;

import java.io.Serializable;

import com.tl.common.StringUtils;



/**
 * ������Ҫ��Ϊ������session��¼�Ĳ���
 * 
 * @author wang.yq
 * 2014-8-31
 */
public class SysSessionUser implements Serializable
{
	private static final long serialVersionUID = 6881131479183182845L;
	
	/**ϵͳ�����¼�û���session�б����û���Ϣʱʹ�õ�����*/
	public final static String sessionAdminName = "adminUser";
	
	/** ��ҳǰ�˵�¼�û���session�б����û���Ϣʱʹ�õ����� */
	public final static String sessionName = "sysUser";
	
	//��������Ա��UserIDԼ��
	public final static String superAdminID = "-9999";
 
	private int userID;
	private String userName;
	private String userCode;
	private String ip;
	private String hostName;//���ӿͻ��˻���������Ϣ
	private String userPassword;
	private boolean admin = true;
	
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
	 * ��SysUser���ַ�����ʽ���ֳ���
	 * ע��ÿ�����Ե�ǰ��˳���ǹ̶��ģ����ܵߵ�
	 * �������Ե���restore�������лָ�
	 * 
	 * ע��userPassword����ʾ
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
	 * ���ַ�����ʽ���ֵ�SysUser�ָ�Ϊ������ʽ
	 * ������SysUser��toString����
	 * Ҫ��˳��̶�
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
