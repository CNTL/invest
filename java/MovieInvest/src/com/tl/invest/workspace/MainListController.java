package com.tl.invest.workspace;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.tl.common.DateJsonValueProcessor;
import com.tl.common.JsonDateValueProcessor;
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
	
	/**��ȡ��ҳ��Ŀ
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
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
        
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
		String ttString = "";
		try{
			ttString = JSONObject.fromObject(mainList, jsonConfig).toString();
		}catch(Exception e){
			
		}
		
		output(ttString, response);
	}
	
	/**
	 * ӰƸ
	 */
	private RecruitManager recruitManager =null;
	public RecruitManager getRecruitManager() {
		return recruitManager;
	}
	public void setRecruitManager(RecruitManager recruitManager) {
		this.recruitManager = recruitManager;
	}
	/**
	 * Ӱ��
	 */
	private UserManager userManager = null;
	
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	/**
	 * ��Ŀ
	 */
	private ProjectService projectService = null;
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	

	


	
}
