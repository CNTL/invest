package com.tl.invest.notice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.workspace.Entry;

public class NoticeMainController extends Entry {
	private NoticeManager mgr = null;
	
	public void setMgr(NoticeManager mgr) {
		this.mgr = mgr;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response, Map model) throws Exception {
		int page = getInt(request, "page", 1);
		Notice[] notices = mgr.getNotices(page, 30);
		int noticeCount = mgr.getNoticeCount();
		int pageCount = noticeCount/30;
		if(noticeCount % 20 >0) pageCount = pageCount + 1;
		if(pageCount<=0) pageCount = 1;
		
		model.put("notices", notices);
		model.put("noticeCount", noticeCount);
		model.put("pageCount", pageCount);
		model.put("pageBegin", getPageBegin(page));
		model.put("pageEnd", getPageEnd(page, pageCount));
		model.put("page", page);
	}
	
	protected int getPageBegin(int page) {
		int begin = page;
		
		for (int i=1;i<=4;i++) {
			if(begin == 1){
				break;
			}
			begin = begin - i;
		}
		return begin;
	}
	
	protected int getPageEnd(int page,int pageCount) {
		int end = page;
		for (int i=1;i<=4;i++) {
			if(end==pageCount){
				break;
			}
			end = end + 1;
		}
		return end;
	}
	
	@Override
	protected String getCurrentMenu() {
		return "��ҳ";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--֪ͨ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}
