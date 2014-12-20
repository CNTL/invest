package com.tl.invest.proj;

import java.util.Date;

public class ProjSchedule implements java.io.Serializable {
	private static final long serialVersionUID = -6672017679693181225L;
	
	private long id;
	private long projId;
	private int stage;
	private int deleted;
	private Date created;
	private Date lastModified;
	private int userId;
	private String content;
	private int approveStatus;
	private int approveUser;
	private Date approveTime;
	private String approveMemo;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProjId() {
		return projId;
	}
	public void setProjId(long projId) {
		this.projId = projId;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(int approveStatus) {
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
	public String getApproveMemo() {
		return approveMemo;
	}
	public void setApproveMemo(String approveMemo) {
		this.approveMemo = approveMemo;
	}
	
	
}
