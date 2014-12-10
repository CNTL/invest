package com.tl.invest.user;

import java.util.Date;

public class Address implements java.io.Serializable{

	private static final long serialVersionUID = 3580692833885663007L;
	
	
	private long id;
	private int userId;
	private String recipients;
	private String mphoneNo;
	private String telphoneNo;
	private int provinceId;
	private String province;
	private int cityId;
	private String city;
	private int countyId;
	private String county;
	private String detail;
	private String zipcode;
	private int type;
	private Date created;
	private int order;
	private int deleted;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getMphoneNo() {
		return mphoneNo;
	}
	public void setMphoneNo(String mphoneNo) {
		this.mphoneNo = mphoneNo;
	}
	public String getTelphoneNo() {
		return telphoneNo;
	}
	public void setTelphoneNo(String telphoneNo) {
		this.telphoneNo = telphoneNo;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getCountyId() {
		return countyId;
	}
	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
