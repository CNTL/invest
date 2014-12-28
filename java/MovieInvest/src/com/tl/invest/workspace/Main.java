package com.tl.invest.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.notice.Notice;
import com.tl.invest.notice.NoticeManager;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;

public class Main extends Entry {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//继承实现		
		//获取1条通知
		Notice[] notices =null;
		try {
			notices = noticeManager.getNotices(1, 1);
			model.put("notices", notices);
		} catch (Exception e) {
			 
		}
		
		//获取4条项目
		ProjectExt[] projects =null;
		try {
			 projects = projectService.getProjectExts(4, 1,"proj_approveStatus in(-1,2)", null);
			 model.put("projects", projects);
		}
		catch(Exception e){
			
		}

		//获取4条影聘
		UserRecruit[] userRecruit=null;
		try {
			userRecruit = recruitManager.queryRecruits(0, 4);
			model.put("userRecruit", userRecruit);
		} catch (Exception e) {
			 
		}

		//获取4条影人
		User[] users =null ;
		try {
			users = userManager.getPersons(0, 4);
			model.put("users", users);
		} catch (Exception e) {
			 
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	
	protected String getCurrentMenu() {
		return "首页";
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
	 * 通知公告
	 */
	private NoticeManager noticeManager = null;
	public NoticeManager getNoticeManager() {
		return noticeManager;
	}
	public void setNoticeManager(NoticeManager noticeManager) {
		this.noticeManager = noticeManager;
	}
}
