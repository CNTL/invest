package com.tl.sys.sysuser;

import java.util.Date;
import com.tl.common.DateUtils;

/**
 * SysUser
 *
 * @author wang.yq
 * @create 2014-9-18 19:13:09
 */
@SuppressWarnings({"serial", "unused"})
public class Sysuser implements java.io.Serializable {

	/** id*/
	private int id;
    
	/** ����*/
	private String username;
    
	/** ��¼�˺�*/
	private String code;
    
	/** ����*/
	private String pwd;
    
	/** �ֻ�*/
	private String mobile;
    
	/** �����ʼ�*/
	private String email;
    
	/** �Ƿ���Ч*/
	private Integer deleted;
    
	/** ���id*/
	private Integer groupid;
    
	/** ����ʱ��*/
	private Date createtime;
    
	/** ����ʱ��*/
	private String createTimeStr;
	
	public Sysuser() {
	}

	public Sysuser(int id, String username, String code, String pwd,String mobile,String email,Integer deleted 
			,Integer groupid,Date createTime) {
		this.id = id;
		this.username = username;
		this.code = code;
		this.pwd = pwd;
		this.mobile = mobile;
		this.email = email;
		this.deleted = deleted;
		this.groupid = groupid;
		this.createtime = createTime;
		
	}
	

	/**
	 * @return ��� id��
	 */
	public int getId() {
		return id;
	}

	/**
	 * ���� id��
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return ��� ���ơ�
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * ���� ���ơ�
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return ��� ��¼�˺š�
	 */
	public String getCode() {
		return code;
	}

	/**
	 * ���� ��¼�˺š�
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return ��� ���롣
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * ���� ���롣
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return ��� �ֻ���
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * ���� �ֻ���
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return ��� �����ʼ���
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ���� �����ʼ���
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return ��� �Ƿ���Ч��
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * ���� �Ƿ���Ч��
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return ��� ���id��
	 */
	public Integer getGroupid() {
		return groupid;
	}

	/**
	 * ���� ���id��
	 */
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	/**
	 * @return ��� ����ʱ�䡣
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * ���� ����ʱ�䡣
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * ���� ����ʱ�䡣
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	/**
	 * ��ȡ ����ʱ�䡣
	 */
	public String getCreateTimeStr() {
		return DateUtils.format(this.createtime, "yyyy-MM-dd HH:mm:ss");
	}


}
