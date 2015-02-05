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
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

public class Main extends Entry {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//�̳�ʵ��		
		//��ȡ1��֪ͨ
		Notice[] notices =null;
		try {
			notices = noticeManager.getNotices(1, 1);
			model.put("notices", notices);
		} catch (Exception e) {
			 
		}
		
		//��ȡ4����Ŀ
		ProjectExt[] projects =null;
		try {
			 projects = projectService.getProjectExts(4, 1,"proj_approveStatus in(-1,2)", null);
			 model.put("projects", projects);
		}
		catch(Exception e){
			
		}

		//��ȡ4��ӰƸ
		UserRecruit[] userRecruit=null;
		try {
			userRecruit = recruitManager.queryRecruits(0, 4);
			for (int i = 0; i < userRecruit.length; i++) {
				User user = userManager.getUserByID(userRecruit[i].getUserId());
				userRecruit[i].setCompany(user.getOrgFullname());
				userRecruit[i].setResumeNum(recruitManager.getRecruitResumeNum(userRecruit[i].getId()));
			}
			
			model.put("userRecruit", userRecruit);
		} catch (Exception e) {
			 
		}

		//��ȡ4��Ӱ��
		User[] users =null ;
		try {
			users = userManager.getPersons(0, 4);
			model.put("users", users);
		} catch (Exception e) {
			 
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	
	protected String getCurrentMenu() {
		return "��ҳ";
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
	
	/**
	 * ֪ͨ����
	 */
	private NoticeManager noticeManager = null;
	public NoticeManager getNoticeManager() {
		return noticeManager;
	}
	public void setNoticeManager(NoticeManager noticeManager) {
		this.noticeManager = noticeManager;
	}
}
