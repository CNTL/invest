package com.tl.invest.proj;

// Generated 2014-9-9 23:05:48 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import com.tl.common.DateUtils;
import com.tl.invest.constant.DicTypes;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

/**
 * InvestProject generated by hbm2java
 */
public class Project implements java.io.Serializable {
	private static final long serialVersionUID = -3452151172070833009L;
	
	private long id;
	private java.util.Date created;
	private java.util.Date lastModified;
	private short deleted;
	private short approveStatus;
	private int approveUser;
	private java.util.Date approveTime;
	private String approveMemo;
	private int status;
	private java.util.Date locktime;
	private long pid;
	private String name;
	private int userId;
	private int payType;
	private int type;
	private int timeType;
	private int countDay;
	private java.util.Date beginDate;
	private java.util.Date endDate;
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
	
	private int province;
	private int city;
	private int county;
	
	private String provinceName;
	private String cityName;
	private int notified;
	

	private int order;
	
	private String beginDateStr;
	private String endDateStr;

	public Project() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

	public java.util.Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(java.util.Date lastModified) {
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

	public java.util.Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(java.util.Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveMemo() {
		return approveMemo;
	}

	public void setApproveMemo(String approveMemo) {
		this.approveMemo = approveMemo;
	}

	public int getStatus() {
		if(this.payType == 1 && this.endDate.getTime()<=DateUtils.getTimestamp().getTime()){
			return 2;
		}
		return status;
	}

	public void setStatus(int status) {
		if(this.payType == 1 && this.endDate.getTime()<=DateUtils.getTimestamp().getTime()){
			this.status = 2;
		}
		this.status = status;
	}

	public java.util.Date getLocktime() {
		return locktime;
	}

	public void setLocktime(java.util.Date locktime) {
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

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
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

	public java.util.Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(java.util.Date beginDate) {
		this.beginDate = beginDate;
		getBeginDateStr();
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
		getEndDateStr();
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

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getCounty() {
		return county;
	}

	public void setCounty(int county) {
		this.county = county;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
    
	 
	public String getBeginDateStr() {
		if(this.beginDate == null) return "";
		this.beginDateStr = DateUtils.format(this.beginDate, "yyyy-MM-dd");
		if(this.payType == 1)
			this.beginDateStr = DateUtils.format(this.beginDate, "yyyy/MM/dd HH:mm:ss");
	 
		return beginDateStr;
	}

	 
	public String getEndDateStr() {
		if(this.endDate == null) return "";
		this.endDateStr = DateUtils.format(this.endDate, "yyyy-MM-dd");
		if(this.payType == 1)
			this.endDateStr = DateUtils.format(this.endDate, "yyyy/MM/dd HH:mm:ss");
		return endDateStr;
	}
	
	public String getProvinceName(){
		try {
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
			if(this.province>0){
				Dictionary dic = reader.getDic(DicTypes.DIC_AREA.typeID(),this.province);
				return dic.getName();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	public String getCityName(){
		try {
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
			if(this.city>0){
				Dictionary dic = reader.getDic(DicTypes.DIC_AREA.typeID(),this.city);
				return dic.getName();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	public int getNotified() {
		return notified;
	}

	public void setNotified(int notified) {
		this.notified = notified;
	}
	 
}
