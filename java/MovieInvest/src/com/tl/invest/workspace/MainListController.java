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
import com.tl.invest.constant.DicTypes;
import com.tl.invest.notice.Notice;
import com.tl.invest.notice.NoticeManager;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
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
		Integer pageSize = 8;
	
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
					 User user = userManager.getUserByID(e.getUserId());
					 e.setCompany(user.getOrgFullname());
		             e.setResumeNum( recruitManager.getRecruitResumeNum(e.getId()));
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
			
			Notice[] notices = noticeManager.getNotices(1, 1);
			if(notices!=null && notices.length>0){
				ArrayList<Notice> noticeItem =new ArrayList<Notice>();
				for (Notice e : notices) {
					noticeItem.add(e);
				}
				mainList.setNotices(noticeItem);
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
	
	/**
	 * 通知
	 */
	private NoticeManager noticeManager = null;
	public NoticeManager getNoticeManager() {
		return noticeManager;
	}
	public void setNoticeManager(NoticeManager noticeManager) {
		this.noticeManager = noticeManager;
	}
}
