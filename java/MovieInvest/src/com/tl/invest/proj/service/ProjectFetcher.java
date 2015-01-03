package com.tl.invest.proj.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.common.DateUtils;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.favorite.Favorite;
import com.tl.invest.favorite.FavoriteManager;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.ProjSchedule;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectModes;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

public class ProjectFetcher extends BaseController{
	private ProjectService service = null;
	
	public void setService(ProjectService service) {
		this.service = service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		
		if("datas".equals(action)){
			initDatas(response);
		} else if("edit".equals(action)){
			initProjectDatas(request,response);
		}else if("del".equals(action)){
			long proj_id = getLong(request, "id", 0);
			service.delete(proj_id);
			output("{\"success\":true}", response);
		}else if("pass".equals(action)){
			long proj_id = getLong(request, "id", 0);
			Project project = service.get(proj_id);
			project.setApproveStatus((short)2);
			project.setApproveTime(DateUtils.getTimestamp());
			project.setApproveUser(SessionHelper.getSysUserID(request));
			project.setBeginDate(DateUtils.getDate());
			project.setEndDate(DateUtils.addDate(DateUtils.getDate(), project.getCountDay()));
			project.setStatus(1);
			service.save(project);
			output("{\"success\":true}", response);
		}else if("reject".equals(action)){
			long proj_id = getLong(request, "id", 0);
			Project project = service.get(proj_id);
			project.setApproveStatus((short)3);
			project.setApproveTime(DateUtils.getTimestamp());
			project.setApproveUser(SessionHelper.getSysUserID(request));
			project.setStatus(0);
			service.save(project);
			output("{\"success\":true}", response);
		}else if("submitstage".equals(action)){
			submitStage(request,response);
		}else if ("addfavorite".equals(action)) {
			int userId = SessionHelper.getUserID(request);
			if(userId <= 0){
				output("{\"success\":false,\"msg\":\"您还没有登陆...<br /><br /><a href='"+WebUtil.getRoot(request)+"login.jsp' style='color:blue;'>点击登陆</a><br /><br />\"}", response);
				return;
			}
			FavoriteManager mgr = (FavoriteManager)Context.getBean(FavoriteManager.class);
			long proj_id = getLong(request, "id", 0);
			Favorite favorite = mgr.get(SessionHelper.getUserID(request), 1, proj_id);
			if(favorite == null){
				Project project = service.get(proj_id);
				if(project != null){
					favorite = new Favorite();
					favorite.setCreated(DateUtils.getTimestamp());
					favorite.setItemId(proj_id);
					favorite.setLibId(1);
					favorite.setUserId(SessionHelper.getUserID(request));
					
					mgr.save(favorite);
					service.updateFavoriteCount(proj_id);
				}
			}
			output("{\"success\":true}", response);
		}else if ("delfavorite".equals(action)) {
			int userId = SessionHelper.getUserID(request);
			if(userId <= 0){
				output("{\"success\":false,\"msg\":\"您还没有登陆...<br /><br /><a href='"+WebUtil.getRoot(request)+"login.jsp' style='color:blue;'>点击登陆</a><br /><br />\"}", response);
				return;
			}
			FavoriteManager mgr = (FavoriteManager)Context.getBean(FavoriteManager.class);
			long proj_id = getLong(request, "id", 0);
			Favorite favorite = mgr.get(SessionHelper.getUserID(request), 1, proj_id);
			if(favorite!=null){
				mgr.delete(favorite);
				service.updateFavoriteCount(proj_id);
			}
			output("{\"success\":true}", response);
		}
	}
	
	private void submitStage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String param = get(request, "param");
		JSONObject params = JSONObject.fromObject(param);
		long projId = params.getLong("projId");
		ProjSchedule[] schedules = service.getProjSchedules(projId);
		JSONArray contents = params.getJSONArray("schedules");
		if (contents != null && contents.size() > 0) {
			for (int i = 0; i < contents.size(); i++) {
				JSONObject formObj = contents.getJSONObject(i);
				int stage = formObj.getInt("stage");
				String c = formObj.getString("content");
				boolean isupdate = false;
				if(schedules!=null && schedules.length>0){
					for (ProjSchedule ps : schedules) {
						if(ps.getStage() == stage){
							isupdate = true;
							if(!ps.getContent().equals(c)){
								ps.setContent(c);
								service.save(ps);
							}
							break;
						}
					}
				}
				if(!isupdate){
					ProjSchedule ps = new ProjSchedule();
					service.initProjSchedule(ps, request);
					ps.setProjId(projId);
					ps.setStage(stage);
					ps.setContent(c);
					service.save(ps);
				}
			}
		}
		output("{\"success\":true}", response);
	}

	private void initProjectDatas(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long proj_id = getLong(request, "id", 0);
		ProjectModes pm = new ProjectModes();
		if(proj_id > 0){
			Project proj = service.get(proj_id);
			ProjMode[] modes = service.getProjectModes(proj_id);
			pm.setProj(proj);
			pm.setModes(modes);
		}
		output(JSONObject.fromObject(pm).toString(), response);
	}

	private void initDatas(HttpServletResponse response) throws Exception {
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] projTypes = dicReader.getDics(DicTypes.DIC_INVEST_TYPE.typeID());
		Dictionary[] areas = dicReader.getDics(DicTypes.DIC_AREA.typeID());
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary area : areas) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+area.getId());
			sb1.append(",\"pid\":"+area.getPid());
			sb1.append(",\"name\":\""+area.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb2 = new StringBuffer();
		for (Dictionary projType : projTypes) {
			if(sb2.length()>0) sb2.append(",");
			sb2.append("{");
			sb2.append("\"id\":"+projType.getId());
			sb2.append(",\"pid\":"+projType.getPid());
			sb2.append(",\"name\":\""+projType.getName()+"\"");
			sb2.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"ProjTypes\":["+sb2.toString()+"]");
		sb.append(",\"areas\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
}
