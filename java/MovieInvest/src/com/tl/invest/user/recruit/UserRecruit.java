package com.tl.invest.user.recruit;

// Generated Nov 17, 2014 3:11:25 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * UserRecruit generated by hbm2java
 */
public class UserRecruit {

	private int id;
	private Integer userId;
	private String userName;
	private String jobName;
	private String jobPictrue;
	private Integer jobCateId;
	private String jobCate;
	private String company;
	private String linkman;
	private String linkPhone;
	private String linkEmail;
	private String province;
	private String city;
	private String area;
	private String salary;
	private String content;
	private String address;
	private String working;
	private String eduReq;
	private Integer isFulltime;
	private String jobAttract;
	private String jobIntro;
	private Date createtime;
	private String time;
	//private Date pubTime;
	private Integer isPub;
	private Integer resumeNum;
	private String days;
	private Integer firstType;
	private Integer secondType;
	private String typeName;
	public UserRecruit() {
	}

	public UserRecruit(int id) {
		this.id = id;
	}

	public UserRecruit(int id, Integer userId, String userName, String jobName,
			String jobPictrue, Integer jobCateId, String jobCate,
			String company, String linkman, String linkPhone, String linkEmail,
			String province, String city, String area, String salary,
			String content, String address, String working, String eduReq,
			Integer isFulltime, String jobAttract, String jobIntro,
			Date createtime, Date pubTime, Integer isPub, Integer resumeNum) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.jobName = jobName;
		this.jobPictrue = jobPictrue;
		this.jobCateId = jobCateId;
		this.jobCate = jobCate;
		this.company = company;
		this.linkman = linkman;
		this.linkPhone = linkPhone;
		this.linkEmail = linkEmail;
		this.province = province;
		this.city = city;
		this.area = area;
		this.salary = salary;
		this.content = content;
		this.address = address;
		this.working = working;
		this.eduReq = eduReq;
		this.isFulltime = isFulltime;
		this.jobAttract = jobAttract;
		this.jobIntro = jobIntro;
		this.createtime = createtime;
		//this.pubTime = pubTime;
		this.isPub = isPub;
		this.resumeNum = resumeNum;
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

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobPictrue() {
		return this.jobPictrue;
	}

	public void setJobPictrue(String jobPictrue) {
		this.jobPictrue = jobPictrue;
	}

	public Integer getJobCateId() {
		return this.jobCateId;
	}

	public void setJobCateId(Integer jobCateId) {
		this.jobCateId = jobCateId;
	}

	public String getJobCate() {
		return this.jobCate;
	}

	public void setJobCate(String jobCate) {
		this.jobCate = jobCate;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkEmail() {
		return this.linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSalary() {
		return this.salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorking() {
		return this.working;
	}

	public void setWorking(String working) {
		this.working = working;
	}

	public String getEduReq() {
		return this.eduReq;
	}

	public void setEduReq(String eduReq) {
		this.eduReq = eduReq;
	}

	public Integer getIsFulltime() {
		return this.isFulltime;
	}

	public void setIsFulltime(Integer isFulltime) {
		this.isFulltime = isFulltime;
	}

	public String getJobAttract() {
		return this.jobAttract;
	}

	public void setJobAttract(String jobAttract) {
		this.jobAttract = jobAttract;
	}

	public String getJobIntro() {
		return this.jobIntro;
	}

	public void setJobIntro(String jobIntro) {
		this.jobIntro = jobIntro;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
/*
	public Date getPubTime() {
		return this.pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
*/
	
	public Integer getIsPub() {
		return this.isPub;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setIsPub(Integer isPub) {
		this.isPub = isPub;
	}

	public Integer getResumeNum() {
		return this.resumeNum;
	}

	public void setResumeNum(Integer resumeNum) {
		this.resumeNum = resumeNum;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Integer getFirstType() {
		return firstType;
	}

	public void setFirstType(Integer firstType) {
		this.firstType = firstType;
	}

	public Integer getSecondType() {
		return secondType;
	}

	public void setSecondType(Integer secondType) {
		this.secondType = secondType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}