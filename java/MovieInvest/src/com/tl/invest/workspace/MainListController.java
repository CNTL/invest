package com.tl.invest.workspace;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.web.BaseController;

public class MainListController extends BaseController {

	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		
		String actionString =get(request, "action","");
		
		if(actionString.equals("getIndexItems")){
			getIndexItems(request,response,model);
		}
	}
	
	/**获取首页项目
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void getIndexItems(HttpServletRequest request,
			HttpServletResponse response, Map model)throws Exception{
		Integer pageIndex = getInt(request, "pageIndex",0);
		Integer pageSize = 4;
	
		MainList mainList = new MainList();
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
        
		try
		{
			Integer startIndex = (pageIndex-1)*pageSize;
			
			ProjectExt[] projectExts = projectService.getProjectExts(pageSize, pageIndex,"proj_approveStatus in(-1,2)", null);
			if(projectExts!=null&&projectExts.length>0){
				ArrayList<ProjectExt> projectItems =new ArrayList<ProjectExt>();  
				for (ProjectExt e : projectExts) {
					projectItems.add(e);
				}
				mainList.setProjectItems(projectItems);
			}
			
			UserRecruit[] userRecruits = recruitManager.queryRecruits(startIndex, pageSize);
			if(userRecruits!=null && userRecruits.length>0){
				ArrayList<UserRecruit> userRecruitItems =new ArrayList<UserRecruit>(); 
				 for (UserRecruit e : userRecruits) {
					 userRecruitItems.add(e);
				}
				mainList.setUserRecruitItems(userRecruitItems);
			}
			
			User[] users=userManager.getPersons(startIndex, pageSize);
			if(users!=null && users.length>0){
				ArrayList<User> userItem =new ArrayList<User>();
				for (User e : users) {
					userItem.add(e);
				}
				mainList.setUserItem(userItem);
			}
            
        }catch(Exception e){    
           
        }
		
		String ttString = JSONObject.fromObject(mainList, jsonConfig).toString();
		output(ttString, response);
	}
	
	/**
	 * 影聘
	 */
	private RecruitManager recruitManager =null;
	public RecruitManager getRecruitManager() {
		return recruitManager;
	}
	public void setRecruitManager(RecruitManager recruitManager) {
		this.recruitManager = recruitManager;
	}
	/**
	 * 影人
	 */
	private UserManager userManager = null;
	
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	/**
	 * 项目
	 */
	private ProjectService projectService = null;
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
}
