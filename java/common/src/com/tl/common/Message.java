package com.tl.common;
/** 
 * @author  leijj 
 * @version ����ʱ�䣺2014��8��22�� ����8:50:07 
 * ��˵�� �� ��ҳʵ����
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