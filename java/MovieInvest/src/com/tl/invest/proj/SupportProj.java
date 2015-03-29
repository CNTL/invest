package com.tl.invest.proj;

import java.math.BigDecimal;
import java.util.Date;

public class SupportProj extends ProjectExt {
	private static final long serialVersionUID = 4724970229351721835L;
	private long supportId;
	private long projId;
	private long modeId;
	private int supportUserId;
	private int canpay;
	private BigDecimal amountSupport;
	private Integer addressId;
	private String recipients;
	private String telphone;
	private String address;
	private String zipcode;
	private String message;
	private Date supportCreated;
	private int supportDeleted;
	/**
	 * 0=未付款;1=已付款;2=已发货;3=已确认收货;4=交易关闭
	 */
	private int supportStatus;
	private int isPaid;
	private long orderId;	
	private String paySN;
	private Date payTime;
	
	private String returnContent;
	private String returnTime;
	private BigDecimal freight;
	private BigDecimal price;
	
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

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	
	public int getCanpay() {
		return canpay;
	}

	public void setCanpay(int canpay) {
		this.canpay = canpay;
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

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getReturnContent() {
		return returnContent;
	}

	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
