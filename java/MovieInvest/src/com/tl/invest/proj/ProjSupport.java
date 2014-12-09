package com.tl.invest.proj;

// Generated 2014-9-9 23:05:48 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvestProjsupport generated by hbm2java
 */
public class ProjSupport implements java.io.Serializable {

	private static final long serialVersionUID = 7286196772641479724L;
	
	private long id;
	private long projId;
	private long modeId;
	private int userId;
	private BigDecimal amount;
	private Integer addressId;
	private String message;
	private Date created;
	private int deleted;
	/**
	 * 0=未付款;1=已付款;2=已发货;3=已确认收货;4=交易关闭
	 */
	private int status;
	private int isPaid;
	private Long orderId;
	private String paySN;
	private Date payTime;

	public ProjSupport() {
	}

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

	public long getModeId() {
		return modeId;
	}

	public void setModeId(long modeId) {
		this.modeId = modeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}
}
