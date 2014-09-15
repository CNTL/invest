package com.tl.invest.proj.service;

import java.util.List;

import org.hibernate.Session;

import com.tl.common.DateUtils;
import com.tl.common.log.Log;
import com.tl.invest.constant.TableLibs;
import com.tl.invest.proj.Project;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;

public class ProjectService {
	Log log = Context.getLog("invest");
	
	public void save(Project proj){
		DAO d = new DAO();
	    try {
			proj.setLastModified(DateUtils.getTimestamp());
			if(proj.getId()>0){
				d.update(proj);
			}else {
				proj.setId(TBID.getID(TableLibs.TB_PROJECT.getTableCode()));
				d.save(proj);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void save(Project proj,Session session){
		DAO d = new DAO();		
	    try {
			proj.setLastModified(DateUtils.getTimestamp());
			if(proj.getId()>0){
				d.update(proj,session);
			}else {
				proj.setId(TBID.getID(TableLibs.TB_PROJECT.getTableCode()));
				d.save(proj,session);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
		
	public void delete(long id){
		delete(id,null);
	}
	
	public void delete(long id,Session s){
		Project proj = s == null ? get(id) : get(id, s);
		if(proj == null){
			log.error("id="+id+"的项目不存在");
			return;
		}
		proj.setLastModified(DateUtils.getTimestamp());
		proj.setDeleted((short) 1);
		if(s == null){
			save(proj);
		}else {
			save(proj, s);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Project get(long id){
		Project proj = null;
		DAO d = new DAO();
		List projs = d.find("select a from com.tl.invest.proj.Project as a where a.id = :id", new Object[]{id});
		if(!projs.isEmpty()){
			proj = (Project)projs.get(0);
		}		
		return proj;
	}
	
	@SuppressWarnings("rawtypes")
	public Project get(long id,Session s){
		Project proj = null;
		DAO d = new DAO();
		List projs = d.find("select a from com.tl.invest.proj.Project as a where a.id = :id", new Object[]{id}, s);
		if(!projs.isEmpty()){
			proj = (Project)projs.get(0);
		}
		return proj;
	}
}
