package com.tl.invest.proj;

import java.math.BigDecimal;
import java.util.Date;

public class SupportProj extends ProjectExt {
	private static final long serialVersionUID = 4724970229351721835L;
	private long supportId;
	private long projId;
	private long modeId;
	private int supportUserId;
	private BigDecimal amountSupport;
	private Integer addressId;
	private String message;
	private Date supportCreated;
	private int supportDeleted;
	/**
	 * 0=未付款;1=已付款;2=已发货;3=已确认收货;4=交易关闭
	 */
	private int supportStatus;
	private String paySN;
	private Date payTime;

	public long getProjId() {
		return projId;
	}

	public void setProjId(long projId) {
		this.projId = projId;
	}

	public long getModeId() {
		return modeId;
	}

	public void setModeId(long modeId) {
		this.modeId = modeId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPaySN() {
		return paySN;
	}

	public void setPaySN(String paySN) {
		this.paySN = paySN;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public long getSupportId() {
		return supportId;
	}

	public void setSupportId(long supportId) {
		this.supportId = supportId;
	}

	public int getSupportUserId() {
		return supportUserId;
	}

	public void setSupportUserId(int supportUserId) {
		this.supportUserId = supportUserId;
	}

	public BigDecimal getAmountSupport() {
		return amountSupport;
	}

	public void setAmountSupport(BigDecimal amountSupport) {
		this.amountSupport = amountSupport;
	}

	public Date getSupportCreated() {
		return supportCreated;
	}

	public void setSupportCreated(Date supportCreated) {
		this.supportCreated = supportCreated;
	}

	public int getSupportDeleted() {
		return supportDeleted;
	}

	public void setSupportDeleted(int supportDeleted) {
		this.supportDeleted = supportDeleted;
	}

	public int getSupportStatus() {
		return supportStatus;
	}

	public void setSupportStatus(int supportStatus) {
		this.supportStatus = supportStatus;
	}
}
