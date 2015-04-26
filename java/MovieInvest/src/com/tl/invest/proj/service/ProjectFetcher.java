package com.tl.invest.proj.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.tl.common.DateJsonValueProcessor;
import com.tl.common.DateUtils;
import com.tl.common.JsonDateValueProcessor;
import com.tl.common.StringUtils;
import com.tl.common.WebUtil;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.favorite.Favorite;
import com.tl.invest.favorite.FavoriteManager;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.ProjSchedule;
import com.tl.invest.proj.ProjSupport;
import com.tl.invest.proj.ProjSupportExt;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectModes;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
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
	protected UserManager userMgr = new UserManager();
	

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
			if(project.getPayType() == 0){
				project.setBeginDate(DateUtils.getDate());
				project.setEndDate(DateUtils.addDate(DateUtils.getDate(), project.getCountDay()));
			}
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
		}else if("delProject".equals(action)){
			delProject(request,response);
		} else if("sortProject".equals(action)){
			sortProject(request,response);
		} else if("getProject".equals(action)){
			getProject(request,response);
		} else if("auction".equals(action)){
			auction(request,response);
		} else if("getjprecord".equals(action)){
			getJPRecord(request,response);
		}
	}
	
	private void getJPRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long proj_id = getLong(request, "id", 0);
		ProjSupportExt[] supports = service.getProjectSupports(proj_id,500,1,"sp_amount desc", null);
		StringBuffer sb = new StringBuffer();
		if(supports!=null && supports.length>0){
			for (ProjSupportExt support : supports) {
				String userName = "";
				if(support.getIsAnonymous() == 1){
					support.setUserHead("");
					if(StringUtils.isEmpty(support.getUserName())) continue;
					String[] userNames = support.getUserName().split("");				
					boolean has = false;
					for (int i=0;i<userNames.length;i++) {
						if(!has && StringUtils.isEmpty(userName)){
							userName += userNames[i];
							if(StringUtils.isNotEmpty(userName))has = true;
						}
						else userName += "*";
					}
				}else {
					userName = support.getUserName();
				}
				if(sb.length()>0) sb.append(",");
				sb.append("{");
				sb.append("\"userName\":\""+userName+"\"");
				sb.append(",\"userHead\":\""+support.getUserHead().replaceAll("\\\\", "/")+"\"");
				sb.append(",\"amount\":\""+support.getAmount()+"\"");
				sb.append(",\"userId\":\""+support.getUserId()+"\"");
				sb.append("}");
			}
		}
		sb.insert(0, "[");
		sb.append("]");
		output(sb.toString(), response);
	}

	private void auction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int userId = SessionHelper.getUserID(request);
		
		if(userId <= 0){
			output("{\"success\":false,\"msg\":\"您还没有登陆...<br /><br /><a href='"+WebUtil.getRoot(request)+"login.jsp' style='color:blue;'>点击登陆</a><br /><br />\"}", response);
			return;
		}
		//判断是否实名认证
		User user =  userMgr.getUserByID(userId);
		if(user.getIsRealNameIdent()!=1){
			output("{\"success\":false,\"msg\":\"您还没有实名认证，请先进行实名认证。\"}", response);
			return;
		}
		if(user.getPoint()<=0){
			output("{\"success\":false,\"msg\":\"抱歉，您的信用积分不够，不能进行竞拍。\"}", response);
			return;
		}
		long proj_id = getLong(request, "id", 0);
		BigDecimal amount = MoneyHelper.toMoney(get(request, "amount", "0"));
		int isAnonymous = getInt(request, "anonymous", 0);
		
		Project proj = service.get(proj_id);
		if(proj == null){
			output("{\"success\":false,\"msg\":\"竞拍项目不存在\"}", response);
			return;
		}
		
		if(amount.compareTo(proj.getAmountGoal())==-1 || amount.compareTo(proj.getAmountRaised())==-1){
			output("{\"success\":false,\"msg\":\"竞拍金额不能低于￥"+(proj.getAmountGoal().compareTo(proj.getAmountRaised())==-1 ? proj.getAmountRaised() : proj.getAmountGoal())+"\"}", response);
			return;
		}
		
		ProjSupport support = new ProjSupport();
		service.initProjSupport(support, request);
		support.setProjId(proj_id);
		support.setAmount(amount);
		support.setModeId(0);
		support.setOrderId(0L);
		support.setIsAnonymous(isAnonymous);
		
		service.save(support);
		if(support.getId()>0){
			proj.setAmountRaised(amount);
			proj.setCountSupport(proj.getCountSupport() + 1);
			service.save(proj);
			service.updateJP(proj_id, support.getId());
			
			output("{\"success\":true,\"msg\":\"恭喜您，竞价成功。\"}", response);
		}else {
			output("{\"success\":false,\"msg\":\"抱歉,竞拍失败\"}", response);
		}
	}

	/**删除项目
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void delProject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try {
			int id = getInt(request, "id",0);
			service.delete(id);
			output("ok", response);
		} catch (Exception e) {
			 output("error", response);
		}
	}
	
	/**删除项目
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getProject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try {
			int id = getInt(request, "id",0);
			Project project = service.get(id);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			String outpuString = JSONObject.fromObject(project, jsonConfig).toString();
			output(outpuString, response);
		} catch (Exception e) {
			 output("error", response);
		}
	}
	
	
	/**设置首页排序
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void sortProject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try {
			int id = getInt(request, "id",0);
			int order = getInt(request, "order",0);
			Project project = service.get(id);
			project.setOrder(order);
			service.save(project);
			output("ok", response);
		} catch (Exception e) {
			 output("error", response);
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
			pm.setModes(modes == null ? new ProjMode[0] : modes);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		
		output(JSONObject.fromObject(pm,jsonConfig).toString(), response);
	}

	private void initDatas(HttpServletResponse response) throws Exception {
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] projTypes = dicReader.getDics(DicTypes.DIC_INVEST_TYPE.typeID());
		Dictionary[] areas = dicReader.getDics(DicTypes.DIC_AREA.typeID());
		Dictionary[] bigmvSection = dicReader.getDics(DicTypes.DIC_BIGMV_TYPE.typeID());
		
		
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
		
		StringBuffer sb3 = new StringBuffer();
		for (Dictionary sec : bigmvSection) {
			if(sb3.length()>0) sb3.append(",");
			sb3.append("{");
			sb3.append("\"id\":"+sec.getId());
			sb3.append(",\"pid\":"+sec.getPid());
			sb3.append(",\"name\":\""+sec.getName()+"\"");
			sb3.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"ProjTypes\":["+sb2.toString()+"]");
		sb.append(",\"areas\":["+sb1.toString()+"]");
		sb.append(",\"bigmvSections\":["+sb3.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
}
