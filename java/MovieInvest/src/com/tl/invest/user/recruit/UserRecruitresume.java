package com.tl.invest.user.recruit;

// Generated Nov 17, 2014 3:11:25 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * UserRecruitresume generated by hbm2java
 */
public class UserRecruitresume {

	private int id;
	private Integer userId;
	private String userName;
	private Integer recruitId;
	private Integer resumeId;
	private Integer isPostResume;
	private Date createtime;

	public UserRecruitresume() {
	}

	public UserRecruitresume(int id) {
		this.id = id;
	}

	public UserRecruitresume(int id, Integer userId, String userName,
			Integer recruitId, Integer resumeId, Date createtime) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.recruitId = recruitId;
		this.resumeId = resumeId;
		this.createtime = createtime;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRecruitId() {
		return this.recruitId;
	}

	public void setRecruitId(Integer recruitId) {
		this.recruitId = recruitId;
	}

	public Integer getResumeId() {
		return this.resumeId;
	}

	public void setResumeId(Integer resumeId) {
		this.resumeId = resumeId;
	}

	public Integer getIsPostResume() {
		return isPostResume;
	}

	public void setIsPostResume(Integer isPostResume) {
		this.isPostResume = isPostResume;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
