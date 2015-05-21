package com.tl.invest.workspace;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.invest.constant.TableLibs;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.TLException;

public class SearchController extends Entry {
	private ProjectService projectService = null;
	private RecruitManager recruitManager =null;
	private UserManager userManager = null;
	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public void setRecruitManager(RecruitManager recruitManager) {
		this.recruitManager = recruitManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		int t = getInt(request, "t", 0);
		String key = get(request, "k", "");
		
		int page = getInt(request, "page", 1);
		int pageSize = getInt(request, "pagesize", 8);
		
		int projCount = 0,recruitCount = 0,userCount = 0,companyCount=0;
		
		if(t == 1 || t == 0){
			projCount = searchProject(key,page,pageSize,model);
		}
		if (t == 2 || t == 0) {
			recruitCount = searchRecruit(key,page,pageSize,model);
		}
		if (t == 3 || t == 0) {
			userCount = searchUser(key,page,pageSize,model);
		}
		if(t==4 || t==0){
			companyCount = searchCompany(key,page,pageSize,model);
		}
		
		int pageCount = 1;
		int allCount = projCount + recruitCount + userCount+companyCount;
		
		model.put("page", page);
		model.put("pagesize", pageSize);
		model.put("searchType", t);
		model.put("searchKeyWord", key);
		
		int maxCount = projCount;
		if(recruitCount>maxCount) maxCount = recruitCount;
		if(userCount>maxCount) maxCount = userCount;
		
		pageCount = maxCount/pageSize;
		if(maxCount % pageSize >0) pageCount = pageCount + 1;
		if(pageCount<=0) pageCount = 1;

		model.put("projCount", allCount);
		model.put("pageCount", pageCount);
		model.put("pageBegin", getPageBegin(page));
		model.put("pageEnd", getPageEnd(page, pageCount));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int searchProject(String key,int page,int pageSize,Map model) throws TLException {
		String where = "proj_approveStatus in(-1,2) and proj_name like '%"+StringUtils.safeTrimString(key)+"%'";
		
		ProjectExt[] projs = projectService.getProjectExts(0,pageSize, page,where, null);
		int projCount = projectService.getProjectExtsCount(0,where, null);
		
		model.put("projs", projs);		
		return projCount;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int searchRecruit(String key,int page,int pageSize,Map model) throws TLException {
		String sql = "select * from "+TableLibs.TB_USERRECRUIT.getTableCode();
		sql += " where jobName like '%"+key+"%'";
		List<UserRecruit> recruits = recruitManager.getRecruits(sql, new Object[]{}, pageSize, page, null);
		String countSQL = "select count(0) from "+TableLibs.TB_USERRECRUIT.getTableCode() + " where jobName like '%"+key+"%'";
		int count = recruitManager.getSqlCount(countSQL, new Object[]{}, null);
		
		model.put("recruits", recruits);
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int searchUser(String key,int page,int pageSize,Map model) throws TLException {
		String sql = "select * from "+TableLibs.TB_USER.getTableCode() + " where type = 0 and isRealNameIdent=1 and perNickName like '%"+key+"%'";
		List<User> users = userManager.getUsers(sql, new Object[]{}, pageSize, page, null);
		String countSQL = "select count(0) from "+TableLibs.TB_USER.getTableCode() + " where type = 0 and isRealNameIdent=1 and perNickName like '%"+key+"%'";
		int count = userManager.getSqlCount(countSQL, new Object[]{}, null);
		
		model.put("users", users);
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int searchCompany(String key,int page,int pageSize,Map model) throws TLException {
		String sql = "select * from "+TableLibs.TB_USER.getTableCode() + " where type = 1 and isRealNameIdent=1 and orgFullname like '%"+key+"%'";
		List<User> users = userManager.getUsers(sql, new Object[]{}, pageSize, page, null);
		String countSQL = "select count(0) from "+TableLibs.TB_USER.getTableCode() + " where type = 1 and isRealNameIdent=1 and orgFullname like '%"+key+"%'";
		int count = userManager.getSqlCount(countSQL, new Object[]{}, null);
		
		model.put("companys", users);
		return count;
	}
	
	protected int getPageBegin(int page) {
		int begin = page;
		
		for (int i=1;i<=4;i++) {
			if(begin == 1){
				break;
			}
			begin = begin - i;
		}
		return begin;
	}
	
	protected int getPageEnd(int page,int pageCount) {
		int end = page;
		for (int i=1;i<=4;i++) {
			if(end==pageCount){
				break;
			}
			end = end + 1;
		}
		return end;
	}

}
