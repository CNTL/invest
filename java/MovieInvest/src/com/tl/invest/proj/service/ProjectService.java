package com.tl.invest.proj.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.hibernate.Session;

import com.tl.common.DateUtils;
import com.tl.common.ResourceMgr;
import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.constant.TableLibs;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.ProjModeFields;
import com.tl.invest.proj.ProjSchedule;
import com.tl.invest.proj.ProjScheduleExt;
import com.tl.invest.proj.ProjSupport;
import com.tl.invest.proj.ProjSupportExt;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.ProjectFields;
import com.tl.invest.proj.SupportProj;
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
	
	public void save(ProjSchedule schedule){
		DAO d = new DAO();
	    try {
	    	schedule.setLastModified(DateUtils.getTimestamp());
			if(schedule.getId()>0){
				d.update(schedule);
			}else {
				schedule.setId(TBID.getID(TableLibs.TB_PROJSCHEDULE.getTableCode()));
				d.save(schedule);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void save(ProjSchedule schedule,Session session){
		DAO d = new DAO();		
	    try {
	    	schedule.setLastModified(DateUtils.getTimestamp());
			if(schedule.getId()>0){
				d.update(schedule,session);
			}else {
				schedule.setId(TBID.getID(TableLibs.TB_PROJSCHEDULE.getTableCode()));
				d.save(schedule,session);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void deleteSchedule(long id,Session s) throws TLException{
		ProjSchedule schedule = s == null ? getProjSchedule(id) : getProjSchedule(id, s);
		if(schedule == null){
			log.error("id="+id+"的项目不存在");
			return;
		}
		schedule.setDeleted((short) 1);
		if(s == null){
			save(schedule);
		}else {
			save(schedule, s);
		}
	}
	
	public void save(ProjSupport support) {
		DAO d = new DAO();
	    try {
			if(support.getId()>0){
				d.update(support);
			}else {
				support.setId(TBID.getID(TableLibs.TB_PROJSUPPORT.getTableCode()));
				d.save(support);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	public void save(ProjSupport support,Session session) {
		DAO d = new DAO();		
	    try {
			if(support.getId()>0){
				d.update(support,session);
			}else {
				support.setId(TBID.getID(TableLibs.TB_PROJSUPPORT.getTableCode()));
				d.save(support,session);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void updateJP(long projId,long supportId){
		String sql = "update invest_projsupport set sp_status=4 where sp_projID=? and sp_id<>?";
		Object[] params = new Object[]{projId,supportId};
		DBSession db = null;
		try {
			db = Context.getDBSession();
			db.executeUpdate(sql, params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally{
			ResourceMgr.closeQuietly(db);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ProjSchedule getProjSchedule(long id, Session s) {
		ProjSchedule schedule = null;
		String sql = "select a from com.tl.invest.proj.ProjSchedule as a where a.id=?";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List schedules = d.find(sql, new Object[]{id}, s);
		if(!schedules.isEmpty()){
			schedule = (ProjSchedule)schedules.get(0);
		}
		return schedule;
	}

	public ProjSchedule getProjSchedule(long id) {
		return getProjSchedule(id, null);
	}
	
	public ProjSchedule[] getProjSchedules(long projId){
		return getProjSchedules(projId, null);
	}
	
	@SuppressWarnings("rawtypes")
	public ProjSchedule[] getProjSchedules(long projId, Session s) {
		List<ProjSchedule> list = new ArrayList<ProjSchedule>();
		String hql = "select a from com.tl.invest.proj.ProjSchedule as a where a.projId=? and a.deleted=0 order by a.stage";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List schedules = d.find(hql, new Object[]{projId}, s);
		if(!schedules.isEmpty()){
			for(int i=0;i<schedules.size();i++){
				list.add((ProjSchedule)schedules.get(i));
			}
		}
		if (list.size() == 0) return null;
		
		return (ProjSchedule[]) list.toArray(new ProjSchedule[0]);
	}
	

	@SuppressWarnings("rawtypes")
	public Project get(long id){
		Project proj = null;
		DAO d = new DAO();
		List projs = d.find("select a from com.tl.invest.proj.Project as a where a.id = ?", new Object[]{id});
		if(!projs.isEmpty()){
			proj = (Project)projs.get(0);
		}		
		return proj;
	}
	
	@SuppressWarnings("rawtypes")
	public Project get(long id,Session s){
		Project proj = null;
		DAO d = new DAO();
		List projs = d.find("select a from com.tl.invest.proj.Project as a where a.id = ?", new Object[]{id}, s);
		if(!projs.isEmpty()){
			proj = (Project)projs.get(0);
		}
		return proj;
	}
	
	@SuppressWarnings("rawtypes")
	public ProjMode getMode(long modeId,Session s) throws TLException{
		ProjMode mode = null;
		DAO d = new DAO();
		List modes = d.find("select a from com.tl.invest.proj.ProjMode as a where a.id = ?", new Object[]{modeId}, s);
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
		String sql = "select a from com.tl.invest.proj.ProjMode as a where a.id=?";
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
		String hql = "select a from com.tl.invest.proj.ProjMode as a where a.projId=? and a.deleted=0 order by a.order,a.id";
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
	
	private int getSqlCount(String sql,Object[] params,DBSession db) throws TLException{
		int count =0;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		IResultSet rs = null;
		try{
			//dbSession = Context.getDBSession();
			rs = db.executeQuery(sql,params);
			if(rs.next())
				count = rs.getInt(1);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		return count;
	}
	
	private ProjectExt[] getProjectExts(String sql,Object[] params,int pageSize,int page,DBSession db) throws TLException{
		List<ProjectExt> list = new ArrayList<ProjectExt>();
		IResultSet rs = null;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		try {
			sql = db.getDialect().getLimitString(sql, pageSize*(page-1), pageSize);
			rs = db.executeQuery(sql, params);
			while (rs.next()) {
				list.add(readProjectExtRS(rs));
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		if (list.size() == 0) return null;
		
		return (ProjectExt[]) list.toArray(new ProjectExt[0]);
	}
	
	private SupportProj[] getSupportProjs(String sql,Object[] params,int pageSize,int page,DBSession db) throws TLException{
		List<SupportProj> list = new ArrayList<SupportProj>();
		IResultSet rs = null;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		try {
			sql = db.getDialect().getLimitString(sql, pageSize*(page-1), pageSize);
			rs = db.executeQuery(sql, params);
			while (rs.next()) {
				list.add(readSupportProjRS(rs));
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		if (list.size() == 0) return null;
		
		return (SupportProj[]) list.toArray(new SupportProj[0]);
	}
	
	public void updateFavoriteCount(long projId){
		String sql = "update invest_project set proj_countLove=(select count(0) from invest_favorite where fav_libId=1 and fav_itemId=proj_id) where proj_id=?";
		Object[] params = new Object[]{projId};
		DBSession db = null;
		try {
			db = Context.getDBSession();
			db.executeUpdate(sql, params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally{
			ResourceMgr.closeQuietly(db);
		}
	}
	
	public int getProjectExtsFavoritedCount(int userID,DBSession db) throws TLException{
		String sql = "select count(0) from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " left JOIN invest_favorite on fav_itemId=proj_id";
		sql += " where fav_libId=1 and fav_userId=?";
		Object[] params = new Object[]{userID};
		return getSqlCount(sql,params,db);
	}
	
	public ProjectExt[] getProjectExtsFavorited(int userID,int pageSize,int page,DBSession db) throws TLException{
		String sql = "select invest_project.*,sys_dictionary.dic_name typeName,`user`.`perNickName` userName,fav_created from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " left JOIN invest_favorite on fav_itemId=proj_id";
		sql += " where fav_libId=1 and fav_userId=? order by fav_created desc";
		Object[] params = new Object[]{userID};
		return getProjectExts(sql, params, pageSize, page, db);
	}
	
	public int getProjectExtsPublishedCount(int userID,DBSession db) throws TLException{
		String sql = "select count(0) from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " where invest_project.proj_userID=?";
		Object[] params = new Object[]{userID};
		return getSqlCount(sql,params,db);
	}
	
	public ProjectExt[] getProjectExtsPublished(int userID,int pageSize,int page,DBSession db) throws TLException{
		String sql = "select invest_project.*,sys_dictionary.dic_name typeName,`user`.`perNickName` userName from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " where invest_project.proj_userID=? order by proj_id desc";
		Object[] params = new Object[]{userID};
		return getProjectExts(sql, params, pageSize, page, db);
	}
	
	public int getProjectExtsSupportedCount(int userID,DBSession db) throws TLException{
		String sql = "select count(0) from invest_projsupport left join invest_project on invest_projsupport.sp_projID=invest_project.proj_id";
		sql += " left join `user` on `user`.id=invest_project.proj_userID left join sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " where invest_projsupport.sp_userID=?";
		Object[] params = new Object[]{userID};
		return getSqlCount(sql,params,db);
	}
	
	public SupportProj[] getProjectExtsSupported(int userID,int pageSize,int page,DBSession db) throws TLException{
		String sql = "select invest_project.*,invest_projsupport.*,invest_projmode.*,`user`.`perNickName` userName,`user`.`code` userCode,sys_dictionary.dic_name typeName";
			sql +=" from invest_projsupport left join invest_project on invest_projsupport.sp_projID=invest_project.proj_id";
			sql +=" left join invest_projmode on invest_projsupport.sp_modeID=invest_projmode.mode_id";
		sql += " left join `user` on `user`.id=invest_project.proj_userID left join sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " where invest_projsupport.sp_userID=? order by sp_created desc";
		Object[] params = new Object[]{userID};
		return getSupportProjs(sql, params, pageSize, page, db);
	}
	
	public int getProjectExtsCount(int type,String where,DBSession db) throws TLException{
		Object[] params = new Object[]{};
		String sql = "select count(0) from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " where invest_project.proj_deleted=0";
		if(type > 0){
			sql += " and invest_project.proj_type=? ";
			params = new Object[]{type};
		}
		if(!StringUtils.isEmpty(where)){
			sql += " and " + where;
		}

		return getSqlCount(sql,params,db);
	}
	
	public int getProjectExtsCount(String where,DBSession db) throws TLException{
		String sql = "select count(0) from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
		sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " where invest_project.proj_deleted=0";
		if(!StringUtils.isEmpty(where)){
			sql += " and " + where;
		}
		Object[] params = new Object[]{};
		return getSqlCount(sql,params,db);
	}
	
	/**
	 * 
	 * @param pageSize
	 * @param page
	 * @param where 如 type=1 and type=2
	 * @param db
	 * @return
	 * @throws TLException
	 */
	public ProjectExt[] getProjectExts(int pageSize,int page,String where,DBSession db) throws TLException{
		String sql = "select invest_project.*,sys_dictionary.dic_name typeName,`user`.`perNickName` userName from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
			sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
			sql += " where invest_project.proj_deleted=0 and invest_project.proj_order>0";
		if(!StringUtils.isEmpty(where)){
			sql += " and " + where;
		}
		sql += " order by proj_order asc";
		Object[] params = new Object[]{};
		return getProjectExts(sql, params, pageSize, page, db);
	}
	
	public ProjectExt[] getProjectExts(int type,int pageSize,int page,String where,DBSession db) throws TLException{
		Object[] params = new Object[]{};
		String sql = "select invest_project.*,sys_dictionary.dic_name typeName,`user`.`perNickName` userName from invest_project left JOIN sys_dictionary on sys_dictionary.dic_id=invest_project.proj_type";
			sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
			sql += " where invest_project.proj_deleted=0";
			if(type > 0){
				sql += " and invest_project.proj_type=? ";
				params = new Object[]{type};
			}
			if(!StringUtils.isEmpty(where)){
				sql += " and " + where;
			}
			sql += " order by proj_order,proj_id desc";
		
		return getProjectExts(sql, params, pageSize, page, db);
	}
	
	public int getProjectSupportCount(long projectId,DBSession db) throws TLException{
		String sql = "select count(0) from "+TableLibs.TB_PROJSUPPORT.getTableCode()
				+" left JOIN `user` ON `user`.id="+TableLibs.TB_PROJSUPPORT.getTableCode()+".sp_userID where sp_projID=? and sp_deleted=0";
		Object[] params = new Object[]{projectId};
		return getSqlCount(sql,params,db);
	}
	
	public ProjScheduleExt[] getProjScheduleExts(long projId,int pageSize,int page,DBSession db) throws TLException{
		Object[] params = new Object[]{};
		String sql = "select invest_projschedule.*,sys_dictionary.dic_name stageName,`user`.`perNickName` userName from invest_projschedule left JOIN sys_dictionary on sys_dictionary.dic_id=invest_projschedule.sc_stage";
		sql += " left JOIN `user` on `user`.id=invest_projschedule.sc_userid";
		sql += " where sc_deleted=0 ";
		if(projId>0){
			sql += " and sc_projid=?";
			params = new Object[]{projId};
		}
		sql += " order by sc_lastModified desc";
		
		return getProjScheduleExts(sql, params, pageSize, page, db);
	}
	
	private ProjScheduleExt[] getProjScheduleExts(String sql, Object[] params,
			int pageSize, int page, DBSession db) throws TLException {
		List<ProjScheduleExt> list = new ArrayList<ProjScheduleExt>();
		IResultSet rs = null;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		try {
			sql = db.getDialect().getLimitString(sql, pageSize*(page-1), pageSize);
			rs = db.executeQuery(sql, params);
			while (rs.next()) {
				list.add(readProjScheduleExtRS(rs));
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		if (list.size() == 0) return null;
		
		return (ProjScheduleExt[]) list.toArray(new ProjScheduleExt[0]);
	}

	private ProjScheduleExt readProjScheduleExtRS(IResultSet rs) {
		ProjScheduleExt schedule = new ProjScheduleExt();
		try {
			schedule.setId(rs.getLong("sc_id"));
			schedule.setProjId(rs.getLong("sc_projid"));
			schedule.setStage(rs.getInt("sc_stage"));
			schedule.setStageName(rs.getString("stageName"));
			schedule.setCreated(rs.getTimestamp("sc_created"));
			schedule.setDeleted(rs.getInt("sc_deleted"));
			schedule.setUserId(rs.getInt("sc_userid"));
			schedule.setUserName(rs.getString("userName"));
			schedule.setContent(rs.getString("sc_content"));
			schedule.setApproveStatus(rs.getInt("sc_approveStatus"));
			schedule.setApproveUser(rs.getInt("sc_approveUser"));
			schedule.setApproveTime(rs.getDate("sc_approveTime"));
			schedule.setApproveMemo(rs.getString("sc_approveMemo"));		
			schedule.setLastModified(rs.getTimestamp("sc_lastModified"));
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}		
		
		return schedule;
	}
	public ProjSupportExt[] getProjectSupports(long projectId,int pageSize,int page,DBSession db) throws TLException{
		return getProjectSupports(projectId,pageSize,page,"sp_id desc",db);
	}
	public ProjSupportExt[] getProjectSupports(long projectId,int pageSize,int page,String orderBy,DBSession db) throws TLException{
		List<ProjSupportExt> list = new ArrayList<ProjSupportExt>();
		String hql = "select "+TableLibs.TB_PROJSUPPORT.getTableCode()+".*,`user`.`perNickName` userName,`user`.`id` userId,`user`.head userHead,`user`.`code` userCode from "+TableLibs.TB_PROJSUPPORT.getTableCode()
				+" left JOIN `user` ON `user`.id="+TableLibs.TB_PROJSUPPORT.getTableCode()+".sp_userID where sp_projID=? and sp_deleted=0";
		if(StringUtils.isNotEmpty(orderBy)){
			hql += "  order by "+orderBy;
		}
		IResultSet rs = null;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		try {
			hql = db.getDialect().getLimitString(hql, pageSize*(page-1), pageSize);
			rs = db.executeQuery(hql, new Object[]{projectId});
			while (rs.next()) {
				list.add(readProjSupportExtRS(rs));
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		if (list.size() == 0) return null;
		
		return (ProjSupportExt[]) list.toArray(new ProjSupportExt[0]);
	}
	
	private ProjSupportExt readProjSupportExtRS(IResultSet rs) throws TLException {
		try {
			ProjSupportExt support = new ProjSupportExt();
			support.setId(rs.getLong("sp_id"));
			support.setProjId(rs.getLong("sp_projID"));
			support.setModeId(rs.getLong("sp_modeID"));
			support.setUserId(rs.getInt("sp_userID"));
			support.setCanpay(rs.getInt("sp_canpay"));
			support.setAmount(rs.getBigDecimal("sp_amount"));
			support.setAddressId(rs.getInt("sp_addressID"));
			support.setMessage(rs.getString("sp_message"));
			support.setCreated(rs.getDate("sp_created"));
			support.setDeleted(rs.getInt("sp_deleted"));
			support.setStatus(rs.getInt("sp_status"));
			support.setOrderId(rs.getLong("sp_orderid"));
			support.setPaySN(rs.getString("sp_paysn"));
			support.setPayTime(rs.getTimestamp("sp_paytime"));
			support.setUserHead(rs.getString("userHead"));
			support.setUserName(rs.getString("userName"));
			support.setIsAnonymous(rs.getInt("sp_anonymous"));
			support.setUserId(rs.getInt("userId"));
			return support;
		} catch (Exception e) {
			throw new TLException(e);
		}
	}

	@SuppressWarnings("unused")
	private ProjSupport readProjSupportRS(IResultSet rs) throws TLException {
		try {
			ProjSupport support = new ProjSupport();
			support.setId(rs.getLong("sp_id"));
			support.setProjId(rs.getLong("sp_projID"));
			support.setModeId(rs.getLong("sp_modeID"));
			support.setUserId(rs.getInt("sp_userID"));
			support.setCanpay(rs.getInt("sp_canpay"));
			support.setAmount(rs.getBigDecimal("sp_amount"));
			support.setAddressId(rs.getInt("sp_addressID"));
			support.setMessage(rs.getString("sp_message"));
			support.setCreated(rs.getDate("sp_created"));
			support.setDeleted(rs.getInt("sp_deleted"));
			support.setStatus(rs.getInt("sp_status"));
			support.setOrderId(rs.getLong("sp_orderid"));
			support.setPaySN(rs.getString("sp_paysn"));
			support.setPayTime(rs.getTimestamp("sp_paytime"));
			support.setIsAnonymous(rs.getInt("sp_anonymous"));
			return support;
		} catch (Exception e) {
			throw new TLException(e);
		}
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
	
	private SupportProj readSupportProjRS(IResultSet rs) throws TLException{
		try {
			SupportProj proj = new SupportProj();
			proj.setId(rs.getLong("proj_id"));
			proj.setCreated(rs.getTimestamp("proj_created"));
			proj.setLastModified(rs.getTimestamp("proj_lastModified"));
			proj.setDeleted(rs.getShort("proj_deleted"));
			proj.setApproveStatus(rs.getShort("proj_approveStatus"));
			proj.setApproveUser(rs.getInt("proj_approveUser"));
			proj.setApproveTime(rs.getTimestamp("proj_approveTime"));
			proj.setStatus(rs.getInt("proj_status"));
			proj.setLocktime(rs.getTimestamp("proj_locktime"));
			proj.setPid(rs.getLong("proj_pid"));
			proj.setName(rs.getString("proj_name"));
			proj.setUserId(rs.getInt("proj_userID"));
			proj.setPayType(rs.getInt("proj_payType"));
			proj.setType(rs.getInt("proj_type"));
			proj.setTimeType(rs.getInt("proj_timeType"));
			proj.setCountDay(rs.getInt("proj_countDay"));
			proj.setBeginDate(proj.getPayType() == 1 ? rs.getTimestamp("proj_beginDate") : rs.getDate("proj_beginDate"));
			proj.setEndDate(proj.getPayType() == 1 ? rs.getTimestamp("proj_endDate") : rs.getDate("proj_endDate"));
			proj.setImgUrl(rs.getString("proj_imgURL"));
			proj.setVideoUrl(rs.getString("proj_videoURL"));
			proj.setSummary(rs.getString("proj_summary"));
			proj.setContent(rs.getString("proj_content"));
			proj.setAmountGoal(rs.getBigDecimal("proj_amountGoal"));
			proj.setAmountRaised(rs.getBigDecimal("proj_amountRaised"));
			proj.setAmountPaid(rs.getBigDecimal("proj_amountPaid"));
			proj.setCountLove(rs.getInt("proj_countLove"));
			proj.setCountView(rs.getInt("proj_countView"));
			proj.setCountSubject(rs.getInt("proj_countSubject"));
			proj.setCountSupport(rs.getInt("proj_countSupport"));
			proj.setProvince(rs.getInt("proj_province"));
			proj.setCity(rs.getInt("proj_city"));
			proj.setCounty(rs.getInt("proj_county"));
			proj.setOrder(rs.getInt("proj_order"));
			proj.setUserName(rs.getString("userName"));
			proj.setTypeName(rs.getString("typeName"));
			
			if(proj.getTypeName().indexOf("微电影")>=0){
				proj.setTypeNickName("短片");
			}else if(proj.getTypeName().indexOf("长片")>=0){
				proj.setTypeNickName("长片");
			}else if(proj.getTypeName().indexOf("活动")>=0){
				proj.setTypeNickName("活动");
			}
			
			//完成百分比
			BigDecimal per = MoneyHelper.ZERO;
			if(proj.getAmountGoal().compareTo(MoneyHelper.ZERO)<=0){
				per = MoneyHelper.toMoney("100");
			}
			else {
				per = proj.getAmountRaised().divide(proj.getAmountGoal()).multiply(MoneyHelper.toMoney("100"));
			}
			per = MoneyHelper.getBigDecimal(per, 0);
			//剩余时间
			String surplus = "--";
			Date endDate = proj.getEndDate();
			if(endDate != null){
				long l=endDate.getTime() - DateUtils.getDate().getTime();
				if(l<=0){
					surplus = "0天";
				}else {
					surplus = String.valueOf(l/(24*60*60*1000))+"天";
				}
			}
			
			proj.setSurplus(surplus);
			proj.setFinishPer(per);
			proj.setSupportId(rs.getLong("sp_id"));
			proj.setProjId(rs.getLong("sp_projID"));
			proj.setModeId(rs.getLong("sp_modeID"));
			proj.setSupportUserId(rs.getInt("sp_userID"));
			proj.setCanpay(rs.getInt("sp_canpay"));
			proj.setAmountSupport(rs.getBigDecimal("sp_amount"));
			proj.setAddressId(rs.getInt("sp_addressID"));
			proj.setRecipients(rs.getString("sp_recipients"));
			proj.setTelphone(rs.getString("sp_telphone"));
			proj.setAddress(rs.getString("sp_address"));
			proj.setZipcode(rs.getString("sp_zipcode"));
			proj.setMessage(rs.getString("sp_message"));
			proj.setSupportCreated(rs.getTimestamp("sp_created"));
			proj.setSupportDeleted(rs.getInt("sp_deleted"));
			proj.setSupportStatus(rs.getInt("sp_status"));
			proj.setPaySN(rs.getString("sp_paysn"));
			proj.setPayTime(rs.getTimestamp("sp_paytime"));
			proj.setOrderId(rs.getLong("sp_orderid"));
			proj.setIsPaid(rs.getInt("sp_ispaid"));
			proj.setReturnContent(rs.getString("mode_return"));
			proj.setReturnTime(rs.getString("mode_returntime"));
			proj.setFreight(rs.getBigDecimal("mode_freight"));
			proj.setPrice(rs.getBigDecimal("mode_price"));
			return proj;
		} catch (Exception e) {
			throw new TLException(e);
		}
	}
	
	private ProjectExt readProjectExtRS(IResultSet rs) throws TLException{
		try {
			ProjectExt proj = new ProjectExt();
			proj.setId(rs.getLong("proj_id"));
			proj.setCreated(rs.getTimestamp("proj_created"));
			proj.setLastModified(rs.getTimestamp("proj_lastModified"));
			proj.setDeleted(rs.getShort("proj_deleted"));
			proj.setApproveStatus(rs.getShort("proj_approveStatus"));
			proj.setApproveUser(rs.getInt("proj_approveUser"));
			proj.setApproveTime(rs.getTimestamp("proj_approveTime"));
			proj.setStatus(rs.getInt("proj_status"));
			proj.setLocktime(rs.getTimestamp("proj_locktime"));
			proj.setPid(rs.getLong("proj_pid"));
			proj.setName(rs.getString("proj_name"));
			proj.setUserId(rs.getInt("proj_userID"));
			proj.setPayType(rs.getInt("proj_payType"));
			proj.setType(rs.getInt("proj_type"));
			proj.setTimeType(rs.getInt("proj_timeType"));
			proj.setCountDay(rs.getInt("proj_countDay"));
			proj.setBeginDate(proj.getPayType() == 1 ? rs.getTimestamp("proj_beginDate") : rs.getDate("proj_beginDate"));
			proj.setEndDate(proj.getPayType() == 1 ? rs.getTimestamp("proj_endDate") : rs.getDate("proj_endDate"));
			proj.setImgUrl(rs.getString("proj_imgURL"));
			proj.setVideoUrl(rs.getString("proj_videoURL"));
			proj.setSummary(rs.getString("proj_summary"));
			proj.setContent(rs.getString("proj_content"));
			proj.setAmountGoal(rs.getBigDecimal("proj_amountGoal"));
			proj.setAmountRaised(rs.getBigDecimal("proj_amountRaised"));
			proj.setAmountPaid(rs.getBigDecimal("proj_amountPaid"));
			proj.setCountLove(rs.getInt("proj_countLove"));
			proj.setCountView(rs.getInt("proj_countView"));
			proj.setCountSubject(rs.getInt("proj_countSubject"));
			proj.setCountSupport(rs.getInt("proj_countSupport"));
			proj.setProvince(rs.getInt("proj_province"));
			proj.setCity(rs.getInt("proj_city"));
			proj.setCounty(rs.getInt("proj_county"));
			proj.setOrder(rs.getInt("proj_order"));
			proj.setUserName(rs.getString("userName"));
			proj.setTypeName(rs.getString("typeName"));
			
			if(proj.getTypeName().indexOf("微电影")>=0){
				proj.setTypeNickName("短片");
			}else if(proj.getTypeName().indexOf("长片")>=0){
				proj.setTypeNickName("长片");
			}else if(proj.getTypeName().indexOf("电影")>=0){
				proj.setTypeNickName("电影");
			}
			
			//完成百分比
			BigDecimal per = MoneyHelper.ZERO;
			if(proj.getPayType() == 1){
				per = MoneyHelper.toMoney("100");
//				Date beginDate = proj.getBeginDate();
//				if(beginDate!=null){
//					long l= DateUtils.getDate().getTime() - beginDate.getTime();
//					if(l>0){
//						BigDecimal t = MoneyHelper.toMoney(String.valueOf(l/(24*60*60*1000)));
//						per = t.divide(MoneyHelper.getBigDecimal(String.valueOf(proj.getCountDay()), 2), 2,BigDecimal.ROUND_HALF_UP).multiply(MoneyHelper.toMoney("100"));
//					}
//				}
			}else{
				if(proj.getAmountGoal().compareTo(MoneyHelper.ZERO)<=0){
					per = MoneyHelper.toMoney("100");
				}
				else {
					per = proj.getAmountRaised().divide(proj.getAmountGoal(),2,BigDecimal.ROUND_HALF_UP).multiply(MoneyHelper.toMoney("100"));
				}
			}
			per = MoneyHelper.getBigDecimal(per, 0);
			//剩余时间
			String surplus = "--";
			Date endDate = proj.getEndDate();
			if(endDate != null){
				long l=endDate.getTime() - DateUtils.getDate().getTime();
				if(l<=0){
					surplus = "0天";
				}else {
					surplus = String.valueOf(l/(24*60*60*1000))+"天";
				}
			}
			
			proj.setSurplus(surplus);
			proj.setFinishPer(per);
			
			return proj;
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
	
	public void initProjSchedule(ProjSchedule schedule,HttpServletRequest request){
		if (schedule == null) {
			schedule = new ProjSchedule();
		}
		SysSessionUser user = SessionHelper.getUser(request);
		schedule.setId(0);
		schedule.setApproveMemo("");
		schedule.setApproveStatus(0);
		schedule.setApproveUser(0);
		schedule.setCreated(DateUtils.getTimestamp());
		schedule.setDeleted(0);
		schedule.setLastModified(DateUtils.getTimestamp());
		schedule.setProjId(0);
		schedule.setStage(0);
		schedule.setUserId(user.getUserID());
	}
	
	public void initProjSupport(ProjSupport support,HttpServletRequest request){
		if(support == null){
			support = new ProjSupport();
		}
		SysSessionUser user = SessionHelper.getUser(request);
		support.setId(0);
		support.setIsPaid(0);
		support.setCreated(DateUtils.getTimestamp());
		support.setDeleted(0);
		support.setStatus(0);
		support.setUserId(user.getUserID());
	}
}
