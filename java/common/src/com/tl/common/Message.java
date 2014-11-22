package com.tl.common;
/** 
 * @created 2014年11月14日 下午10:46:59 
 * @author  leijj
 * 类说明 ： 
 */
import java.util.List;
@SuppressWarnings("rawtypes")
public class Message {
	private int total;
	private int curPage;
	private int pageCount;
	private int length;
	private String userName;
	private List messages;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public List getMessages() {
		return messages;
	}
	public void setMessages(List messages) {
		this.messages = messages;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}