package com.tl.invest.notice;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.tl.common.log.Log;
import com.tl.invest.constant.TableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;

public class NoticeManager {
	Log log = Context.getLog("invest");
	
	public void save(Notice notice){
		DAO d = new DAO();
	    try {
			if(notice.getId()>0){
				d.update(notice);
			}else {
				notice.setId(TBID.getID(TableLibs.TB_NOTICE.getTableCode()));
				d.save(notice);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void save(Notice notice,Session session){
		DAO d = new DAO();		
	    try {
			if(notice.getId()>0){
				d.update(notice,session);
			}else {
				notice.setId(TBID.getID(TableLibs.TB_NOTICE.getTableCode()));
				d.save(notice,session);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void delete(long id){
		delete(id,null);
	}
	
	public void delete(long id,Session s){
		Notice notice = s == null ? get(id) : get(id, s);
		if(notice == null){
			log.error("id="+id+"的公告不存在");
			return;
		}
		notice.setDeleted(1);
		if(s == null){
			save(notice);
		}else {
			save(notice, s);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Notice get(long id){
		Notice notice = null;
		DAO d = new DAO();
		List notices = d.find("select a from com.tl.invest.notice.Notice as a where a.id = ?", new Object[]{id});
		if(!notices.isEmpty()){
			notice = (Notice)notices.get(0);
		}		
		return notice;
	}
	
	@SuppressWarnings("rawtypes")
	public Notice get(long id,Session s){
		Notice notice = null;
		DAO d = new DAO();
		List notices = d.find("select a from com.tl.invest.notice.Notice as a where a.id = ?", new Object[]{id}, s);
		if(!notices.isEmpty()){
			notice = (Notice)notices.get(0);
		}
		return notice;
	}
	
	public Notice[] getNotices(int page,int pageSize) throws TLException{
		return getNotices(page,pageSize,null);
	}
	@SuppressWarnings("rawtypes")
	public Notice[] getNotices(int page,int pageSize,Session s) throws TLException{
		List<Notice> list = new ArrayList<Notice>();
		String hql = "select a from com.tl.invest.notice.Notice as a where a.deleted=0 order by a.order,a.id desc";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List notices = d.find(hql, new Object[]{},(page-1)*pageSize,pageSize ,s);
		if(!notices.isEmpty()){
			for(int i=0;i<notices.size();i++){
				list.add((Notice)notices.get(i));
			}
		}
		if (list.size() == 0) return null;
		
		return (Notice[]) list.toArray(new Notice[0]);
	}
}
