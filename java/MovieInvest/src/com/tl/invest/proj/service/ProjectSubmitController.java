package com.tl.invest.proj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.common.ResourceMgr;
import com.tl.db.DBSession;
import com.tl.invest.common.BackResult;
import com.tl.invest.proj.DataMode;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.Project;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TLException;
import com.tl.kernel.web.BaseController;

public class ProjectSubmitController extends BaseController {
	private ProjectService service = null;
	
	public void setService(ProjectService service) {
		this.service = service;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		JSONObject params = null;
		Project project = null;
		
		try {
			String param = get(request, "param");
			if (log.isDebugEnabled()) {
				log.debug("项目提交，参数：" + param);
			}
			params = JSONObject.fromObject(param);
			// 用提交参数组装出Order对象
			JSONObject projObj = _jsonObj(params, "project");
			project = assembleProject(projObj, request);
		} catch (Exception e) {
			output(JSONObject.fromObject(new BackResult(false,e.getMessage())).toString(), response);
        	return;
		}
		DataMode modes = null;
		DBSession conn = null;
		try {
			conn = Context.getDBSession();
	    	conn.beginTransaction();
			
			JSONObject modesObj = _jsonObj(params, "modes");
			modes = assembleProjModes(modesObj,request,conn);
			
			conn.commitTransaction();
		} catch (Exception e) {
			ResourceMgr.rollbackQuietly(conn);
			output(JSONObject.fromObject(new BackResult(false,e.getLocalizedMessage())).toString(), response);
			return;
		}
		Session s = null;
		try {
			DAO d = new DAO();
			s = d.getSession();
			Transaction t = s.beginTransaction();
			
			service.save(project, s);
			if(project.getId()>0){
				saveModes(project,modes,s);
			}
			
			t.commit();
			
			BackResult result = new BackResult(project.getId(),true);
	    	output(JSONObject.fromObject(result).toString(), response);
		} catch (Exception e) {
			output(JSONObject.fromObject(new BackResult(false,e.getLocalizedMessage())).toString(), response);
			return;
		} finally{
			if(s!=null) s.close();
		}
	}
	
	private void saveModes(Project project, DataMode modes, Session s) throws TLException {
		if(modes == null) return;
		List<ProjMode> list = modes.getNewList();
		if(list!=null && list.size()>0){
			for (ProjMode mode : list) {
				service.save(mode, s);
			}
		}
		
		list = modes.getEditList();
		if(list!=null && list.size()>0){
			for (ProjMode mode : list) {
				service.save(mode, s);
			}
		}
		
		long[] delIds = modes.getDeleteList();
		if(delIds!=null && delIds.length>0){
			for (long id : delIds) {
				service.deleteMode(id, s);
			}
		}
	}

	private Project assembleProject(JSONObject projObj,
			HttpServletRequest request) throws TLException {
		Project project = null;
		
		String proj_id = projObj.getString("proj_id");
		if ("0".equals(proj_id)) {
			project = new Project();
			service.initProject(project, request);			
		}else {
			project = service.get(Long.parseLong(proj_id));
		}
		//赋新值
		service.fillProjectValues(project, projObj);
		
		return project;
	}
	
	private DataMode assembleProjModes(JSONObject modesObj,
			HttpServletRequest request,DBSession conn) throws TLException {
		DataMode modes = new DataMode();
		
		JSONArray array = _jsonArray(modesObj, "deleteList");
		if (array != null && array.size() > 0) {
			long[] list = new long[array.size()];
			for (int i = 0; i < array.size(); i++) {
				list[i] = array.getLong(i);
			}
			modes.setDeleteList(list);
		}
		
		//新增
        JSONArray formListObj = _jsonArray(modesObj, "formList");
        if (formListObj != null && formListObj.size() > 0) {
            List<ProjMode> formList = new ArrayList<ProjMode>();
        	for (int i = 0; i < formListObj.size(); i++) {
        		JSONObject formObj = formListObj.getJSONObject(i);
        		ProjMode mode = assembleProjMode(formObj, request, conn);
        		formList.add(mode);
			}
        	modes.setNewList(formList);
        }

        //修改
        formListObj = _jsonArray(modesObj, "editList");
        if (formListObj != null && formListObj.size() > 0) {
            List<ProjMode> formList = new ArrayList<ProjMode>();
        	for (int i = 0; i < formListObj.size(); i++) {
        		JSONObject formObj = formListObj.getJSONObject(i);
        		ProjMode mode = assembleProjMode(formObj, request, conn);
        		formList.add(mode);
			}
        	modes.setEditList(formList);
        }
		
		return modes;
	}

	private ProjMode assembleProjMode(JSONObject formObj,
			HttpServletRequest request,DBSession conn) throws TLException {
		ProjMode mode = null;
		String docID = formObj.getString("mode_id");
		 if (docID.startsWith("TEMP_")) {
			 mode = new ProjMode();
			 service.initProjMode(mode, request);
		 }else {
			mode = service.getProjectMode(Long.parseLong(docID),conn);
		}
		service.fillProjModeValues(mode, formObj);
		
		return mode;
	}

	private JSONArray _jsonArray(JSONObject obj, String key) {
		if (obj.containsKey(key))
			return obj.getJSONArray(key);
		else
			return null;
	}
	
	private JSONObject _jsonObj(JSONObject obj, String key) {
		if (obj.containsKey(key))
			return obj.getJSONObject(key);
		else
			return null;
	}

}
