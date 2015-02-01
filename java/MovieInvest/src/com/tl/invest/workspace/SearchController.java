package com.tl.invest.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.recruit.RecruitManager;
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
		int t = getInt(request, "t", 1);
		String key = get(request, "k", "");
		
		int page = getInt(request, "page", 1);
		int pageSize = getInt(request, "pagesize", 20);
		
		if(t == 1){
			searchProject(key,page,pageSize,model);
		}
		
		model.put("page", page);
		model.put("pagesize", pageSize);
		model.put("searchType", t);
		model.put("searchKeyWord", key);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void searchProject(String key,int page,int pageSize,Map model) throws TLException {
		String where = "proj_approveStatus in(-1,2) and proj_name like '%"+StringUtils.safeTrimString(key)+"%'";
		
		ProjectExt[] projs = projectService.getProjectExts(0,pageSize, page,where, null);
		int projCount = projectService.getProjectExtsCount(0,where, null);
		int pageCount = projCount/pageSize;
		if(projCount % pageSize >0) pageCount = pageCount + 1;
		if(pageCount<=0) pageCount = 1;
		
		model.put("projs", projs);
		model.put("projCount", projCount);
		model.put("pageCount", pageCount);
		model.put("pageBegin", getPageBegin(page));
		model.put("pageEnd", getPageEnd(page, pageCount));
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
