package com.tl.invest.notice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.workspace.Entry;

public class NoticeDetailController extends Entry {
	private NoticeManager mgr = null;
	
	public void setMgr(NoticeManager mgr) {
		this.mgr = mgr;
	}
	
	protected Notice notice = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response, Map model) throws Exception {
		long id = getLong(request, "id", 0L);
		notice = mgr.get(id);
		model.put("notice", notice);
	}
	
	@Override
	protected String getCurrentMenu() {
		return "��ҳ";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", (notice == null ? "" : notice.getTitle()+"--")+"֪ͨ--����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}
