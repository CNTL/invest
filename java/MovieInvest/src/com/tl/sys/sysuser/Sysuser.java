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
    
	/** 名称*/
	private String username;
    
	/** 登录账号*/
	private String code;
    
	/** 密码*/
	private String pwd;
    
	/** 手机*/
	private String mobile;
    
	/** 电子邮件*/
	private String email;
    
	/** 是否有效*/
	private Integer deleted;
    
	/** 组别id*/
	private Integer groupid;
    
	/** 创建时间*/
	private Date createtime;
    
	/** 创建时间*/
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
	 * @return 获得 id。
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置 id。
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return 获得 名称。
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置 名称。
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return 获得 登录账号。
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 登录账号。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return 获得 密码。
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * 设置 密码。
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return 获得 手机。
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置 手机。
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return 获得 电子邮件。
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置 电子邮件。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return 获得 是否有效。
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * 设置 是否有效。
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return 获得 组别id。
	 */
	public Integer getGroupid() {
		return groupid;
	}

	/**
	 * 设置 组别id。
	 */
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	/**
	 * @return 获得 创建时间。
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * 设置 创建时间。
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 设置 创建时间。
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	/**
	 * 获取 创建时间。
	 */
	public String getCreateTimeStr() {
		return DateUtils.format(this.createtime, "yyyy-MM-dd HH:mm:ss");
	}


}
