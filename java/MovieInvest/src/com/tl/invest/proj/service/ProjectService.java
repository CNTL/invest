package com.tl.invest.proj.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.hibernate.Session;

import com.tl.common.DateUtils;
import com.tl.common.log.Log;
import com.tl.db.IResultSet;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.constant.TableLibs;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.ProjModeFields;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectFields;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.common.SessionHelper;

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
	
	public void save(ProjMode mode){
		DAO d = new DAO();
	    try {
			if(mode.getId()>0){
				d.update(mode);
			}else {
				mode.setId(TBID.getID(TableLibs.TB_PROJECT.getTableCode()));
				d.save(mode);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void save(ProjMode mode,Session session){
		DAO d = new DAO();		
	    try {
			if(mode.getId()>0){
				d.update(mode,session);
			}else {
				mode.setId(TBID.getID(TableLibs.TB_PROJMODE.getTableCode()));
				d.save(mode,session);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void deleteMode(long id,Session s) throws TLException{
		ProjMode mode = s == null ? getProjectMode(id) : getMode(id, s);
		if(mode == null){
			log.error("id="+id+"的项目不存在");
			return;
		}
		mode.setDeleted((short) 1);
		if(s == null){
			save(mode);
		}else {
			save(mode, s);
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
	
	@SuppressWarnings("rawtypes")
	public ProjMode getMode(long modeId,Session s) throws TLException{
		ProjMode mode = null;
		DAO d = new DAO();
		List modes = d.find("select a from com.tl.invest.proj.ProjMode as a where a.id = :id", new Object[]{modeId}, s);
		if(!modes.isEmpty()){
			mode = (ProjMode)modes.get(0);
		}
		return mode;
	}
	
	public ProjMode getProjectMode(long modeId) throws TLException{
		return getProjectMode(modeId, null);
	}
	
	@SuppressWarnings("rawtypes")
	public ProjMode getProjectMode(long modeId,Session s) throws TLException{
		ProjMode mode = null;
		String sql = "select * from "+TableLibs.TB_PROJMODE.getTableCode()
				+" where mode_id=?";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List modes = d.find(sql, new Object[]{modeId}, s);
		if(!modes.isEmpty()){
			mode = (ProjMode)modes.get(0);
		}
		return mode;
	}
	
	public ProjMode[] getProjectModes(long projectId) throws TLException{
		return getProjectModes(projectId, null);
	}
	@SuppressWarnings("rawtypes")
	public ProjMode[] getProjectModes(long projectId,Session s) throws TLException{
		List<ProjMode> list = new ArrayList<ProjMode>();
		String hql = "select * from "+TableLibs.TB_PROJMODE.getTableCode()
				+" where mode_projID=? and mode_deleted=0 ";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List modes = d.find(hql, new Object[]{projectId}, s);
		if(!modes.isEmpty()){
			for(int i=0;i<modes.size();i++){
				list.add((ProjMode)modes.get(i));
			}
		}
		if (list.size() == 0) return null;
		
		return (ProjMode[]) list.toArray(new ProjMode[0]);
	}
	
	@SuppressWarnings("unused")
	private ProjMode readProjModeRS(IResultSet rs) throws TLException{
		try {
			ProjMode mode = new ProjMode();
			mode.setProjId(rs.getLong("mode_projID"));
			mode.setId(rs.getLong("mode_id"));
			mode.setName(rs.getString("mode_name"));
			mode.setPrice(rs.getBigDecimal("mode_price"));
			mode.setCountGoal(rs.getInt("mode_countGoal"));
			mode.setImgURL(rs.getString("mode_imgURL"));
			mode.setReturnContent(rs.getString("mode_return"));
			mode.setReturntime(rs.getString("mode_returntime"));
			mode.setFreight(rs.getBigDecimal("mode_freight"));
			mode.setDeleted(rs.getInt("mode_deleted"));
			mode.setStatus(rs.getInt("mode_status"));
			
			return mode;
		} catch (Exception e) {
			throw new TLException(e);
		}
	}
	
	public void initProject(Project proj,HttpServletRequest request){
		SysSessionUser user = SessionHelper.getUser(request);
		proj.setId(0);
		proj.setUserId(user.getUserID());
		proj.setCreated(DateUtils.getTimestamp());
		proj.setLastModified(DateUtils.getTimestamp());
		proj.setDeleted((short)0);
		proj.setApproveStatus((short)0);
		proj.setApproveUser(0);
		proj.setStatus(0);
		proj.setPid(0);
		proj.setCountLove(0);
		proj.setCountSubject(0);
		proj.setCountSupport(0);
		proj.setCountView(0);
		proj.setAmountPaid(MoneyHelper.ZERO);
		proj.setAmountGoal(MoneyHelper.ZERO);
		proj.setAmountRaised(MoneyHelper.ZERO);
		proj.setOrder(0);
	}
	
	public void fillProjectValues(Project project,JSONObject obj) throws TLException{
		if(project==null) return;
		ProjectFields[] fields = ProjectFields.values();
		for (ProjectFields field : fields) {
			String code = field.fieldCode();
			String value = null;
			try {
				if(obj.containsKey(code)){
					value = obj.getString(code);
					field.setValue(project, value);
				}
			} catch (Exception e) {
				throw new TLException(e.getMessage());
			}
		}
	}
	
	public void initProjMode(ProjMode mode,HttpServletRequest request){
		mode.setOrder(0);
		mode.setCountSupport(0);
	}
	
	public void fillProjModeValues(ProjMode mode,JSONObject obj) throws TLException{
		if(mode==null) return;
		ProjModeFields[] fields = ProjModeFields.values();
		for (ProjModeFields field : fields) {
			String code = field.fieldCode();
			String value = null;
			try {
				if(obj.containsKey(code)){
					value = obj.getString(code);
					field.setValue(mode, value);
				}
			} catch (Exception e) {
				throw new TLException(e.getMessage());
			}
		}
	}
}
