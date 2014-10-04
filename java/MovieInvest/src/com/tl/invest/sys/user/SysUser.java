package com.tl.invest.sys.user;

import java.util.Date;
import com.tl.common.DateUtils;

/**
 * SysUser
 *
 * @author wang.yq
 * @create 2014-9-18 19:13:09
 */
public class SysUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946218243428004580L;

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
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * ��ȡ ����ʱ�䡣
	 */
	public String getCreateTimeStr() {
		return DateUtils.format(this.createtime, "yyyy-MM-dd HH:mm:ss");
	}


}
