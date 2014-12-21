package com.tl.invest.notice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

public class NoticeController extends BaseController {
	private NoticeManager mgr = null;
	
	public void setMgr(NoticeManager mgr) {
		this.mgr = mgr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		if("update".equals(action)){
			Notice notice = assembleNotice(request);
			mgr.save(notice);
			String viewName = "redirect:/workspace/After.do?IDs="+notice.getId();
			model.put("@VIEWNAME@", viewName);
		}else if ("del".equals(action)) {
			model.put("ItemID", get(request, "id", "0"));
			model.put("Item", get(request, "item", ""));
			model.put("@VIEWNAME@", "admin/org/delnotice");
		}else if ("delete".equals(action)) {
			
			String idStr = get(request, "ItemID", "");
			String[] ids = idStr.split(",");
			
			for (String id : ids) {
				try {
					int itemID = Integer.parseInt(id);
					mgr.delete(itemID);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
			String viewName = "redirect:/workspace/After.do?IDs="+idStr;
			model.put("@VIEWNAME@", viewName);
		}else if("".equals(action)) {
			long id = getLong(request, "id", 0);
			if(id>0){
				Notice notice = mgr.get(id);
				if(notice!=null){
					model.put("notice", notice);
				}
			}			
			model.put("@VIEWNAME@", "admin/org/notice");
		}
		
	}
	
	
	private Notice assembleNotice(HttpServletRequest request){
		Notice notice = null;
		long id = getLong(request, "notice_id", 0);
		int order = getInt(request, "notice_order", 0);
		if(id>0){
			notice = mgr.get(id);
		}else {
			notice = new Notice();
			notice.setCreated(DateUtils.getTimestamp());
			notice.setDeleted(0);
		}
		notice.setId(id);
		notice.setUserId(SessionHelper.getSysUserID(request));
		notice.setOrder(order);
		notice.setTitle(get(request, "notice_title", ""));
		notice.setContent(get(request, "notice_content", ""));
		return notice;
	}
}
