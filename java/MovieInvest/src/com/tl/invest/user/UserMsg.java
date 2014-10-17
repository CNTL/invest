package com.tl.invest.user;

import java.util.Date;

/** 
 * @created 2014年10月16日 上午8:31:17 
 * @author  leijj
 * 类说明 ： 
 */
public class UserMsg {
	private int id;
	private int msgFromID;
	private String msgFrom;
	private int msgToID;
	private String msgTo;
	private String msgContent;
	private int msgIsRead;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMsgFromID() {
		return msgFromID;
	}
	public void setMsgFromID(int msgFromID) {
		this.msgFromID = msgFromID;
	}
	public String getMsgFrom() {
		return msgFrom;
	}
	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}
	public int getMsgToID() {
		return msgToID;
	}
	public void setMsgToID(int msgToID) {
		this.msgToID = msgToID;
	}
	public String getMsgTo() {
		return msgTo;
	}
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public int getMsgIsRead() {
		return msgIsRead;
	}
	public void setMsgIsRead(int msgIsRead) {
		this.msgIsRead = msgIsRead;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}