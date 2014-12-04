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

import com.tl.invest.common.BackResult;
import com.tl.invest.proj.DataMode;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.Project;
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
		
		Session s = null;
		try {
			DAO d = new DAO();
			s = d.getSession();
			Transaction t = s.beginTransaction();
			
			JSONArray modesObj = _jsonArray(params, "modes");
			modes = assembleProjModes(project,modesObj,request,s);			
			
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
				mode.setProjId(project.getId());
				service.save(mode, s);
			}
		}
		
		list = modes.getEditList();
		if(list!=null && list.size()>0){
			for (ProjMode mode : list) {
				mode.setProjId(project.getId());
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
	
	private DataMode assembleProjModes(Project proj,JSONArray modeObjs,
			HttpServletRequest request,Session s) throws TLException {
		DataMode modes = new DataMode();
		ProjMode[] projModes = null;
		if(proj.getId()>0){
			projModes = service.getProjectModes(proj.getId(), s);
		}
		
        if (modeObjs != null && modeObjs.size() > 0) {
            List<ProjMode> formList = new ArrayList<ProjMode>();
        	for (int i = 0; i < modeObjs.size(); i++) {
        		JSONObject formObj = modeObjs.getJSONObject(i);
        		ProjMode mode = assembleProjMode(formObj, request, s);
        		mode.setProjId(proj.getId());
        		mode.setOrder(i);
        		formList.add(mode);
			}
        	
        	if(projModes != null){
        		List<ProjMode> addList = new ArrayList<ProjMode>();
            	List<ProjMode> editList = new ArrayList<ProjMode>();
            	List<ProjMode> delList = new ArrayList<ProjMode>();
        		for (ProjMode projMode : formList) {
					boolean exist = false;
					for (ProjMode pm : projModes) {
						if(pm.getId() == projMode.getId()){
							exist = true;
							editList.add(projMode);
							break;
						}
					}
					if(!exist){
						addList.add(projMode);
					}
				}
        		for (ProjMode pm : projModes) {
        			boolean exist = false;
        			for (ProjMode projMode : formList) {
        				if(pm.getId() == projMode.getId()){
        					exist = true;
        					break;
        				}
        			}
        			if(!exist){
        				delList.add(pm);
        			}
        		}
        		long[] delIDs = new long[delList.size()];
        		for (int i=0;i<delIDs.length;i++) {
					delIDs[i] = delList.get(i).getId();
				}
        		modes.setDeleteList(delIDs);
        		modes.setNewList(addList);
        		modes.setEditList(editList);
        	}else {
        		modes.setNewList(formList);
			}
        	
        }

		return modes;
	}

	private ProjMode assembleProjMode(JSONObject formObj,
			HttpServletRequest request,Session s) throws TLException {
		ProjMode mode = null;
		String docID = formObj.getString("mode_id");
		 if (docID.startsWith("TEMP_")) {
			 mode = new ProjMode();
			 service.initProjMode(mode, request);
		 }else {
			mode = service.getProjectMode(Long.parseLong(docID),s);
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
