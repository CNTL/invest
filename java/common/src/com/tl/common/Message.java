package com.tl.common;
/** 
 * @author  leijj 
 * @version 创建时间：2014年8月22日 下午8:50:07 
 * 类说明 ： 翻页实体类
 */
import java.util.List;
@SuppressWarnings("rawtypes")
public class Message {
	private int total;
	private int curPage;
	private int pageCount;
	private int length;
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
}