package com.tl.invest.proj.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.tl.common.DateUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectModes;
import com.tl.invest.user.user.User;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;
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
			project.setApproveStatus((short)1);
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
			project.setApproveStatus((short)2);
			project.setApproveTime(DateUtils.getTimestamp());
			project.setApproveUser(SessionHelper.getSysUserID(request));
			project.setStatus(0);
			service.save(project);
			output("{\"success\":true}", response);
		}
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
