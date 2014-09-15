package com.tl.invest.proj;

// Generated 2014-9-9 23:05:48 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvestProject generated by hbm2java
 */
public class Project implements java.io.Serializable {
	private static final long serialVersionUID = -3452151172070833009L;
	
	private long id;
	private Date created;
	private Date lastModified;
	private short deleted;
	private short approveStatus;
	private int approveUser;
	private Date approveTime;
	private int status;
	private Date locktime;
	private long pid;
	private String name;
	private int userId;
	private int type;
	private int timeType;
	private int countDay;
	private Date beginDate;
	private Date endDate;
	private String imgUrl;
	private String videoUrl;
	private String summary;
	private String content;
	private BigDecimal amountGoal;
	private BigDecimal amountRaised;
	private BigDecimal amountPaid;
	private int countLove;
	private int countView;
	private int countSubject;
	private int countSupport;

	public Project() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public short getDeleted() {
		return deleted;
	}

	public void setDeleted(short deleted) {
		this.deleted = deleted;
	}

	public short getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(short approveStatus) {
		this.approveStatus = approveStatus;
	}

	public int getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(int approveUser) {
		this.approveUser = approveUser;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLocktime() {
		return locktime;
	}

	public void setLocktime(Date locktime) {
		this.locktime = locktime;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTimeType() {
		return timeType;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public int getCountDay() {
		return countDay;
	}

	public void setCountDay(int countDay) {
		this.countDay = countDay;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getAmountGoal() {
		return amountGoal;
	}

	public void setAmountGoal(BigDecimal amountGoal) {
		this.amountGoal = amountGoal;
	}

	public BigDecimal getAmountRaised() {
		return amountRaised;
	}

	public void setAmountRaised(BigDecimal amountRaised) {
		this.amountRaised = amountRaised;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public int getCountLove() {
		return countLove;
	}

	public void setCountLove(int countLove) {
		this.countLove = countLove;
	}
	
	public int getCountView() {
		return countView;
	}

	public void setCountView(int countView) {
		this.countView = countView;
	}

	public int getCountSubject() {
		return countSubject;
	}

	public void setCountSubject(int countSubject) {
		this.countSubject = countSubject;
	}

	public int getCountSupport() {
		return countSupport;
	}

	public void setCountSupport(int countSupport) {
		this.countSupport = countSupport;
	}
}
