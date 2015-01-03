package com.tl.invest.proj.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.SupportProj;
import com.tl.sys.common.SessionHelper;

public class UserProjectController extends ProjectMainController {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response, Map model) throws Exception {
		int m = getInt(request, "m", 1);
		int page = getInt(request, "page", 1);
		int userID = SessionHelper.getUserID(request);
		int projCount = 0;
		if(m == 1){
			ProjectExt[] projs = service.getProjectExtsPublished(userID, 20, page, null);
			projCount = service.getProjectExtsPublishedCount(userID, null);
			model.put("projs", projs);
		}else if(m == 2){
			SupportProj[] projs = service.getProjectExtsSupported(userID, 20, page, null);
			projCount = service.getProjectExtsSupportedCount(userID, null);
			model.put("projs", projs);
		}else if(m == 3){
			ProjectExt[] projs = service.getProjectExtsFavorited(userID, 20, page, null);
			projCount = service.getProjectExtsFavoritedCount(userID, null);
			model.put("projs", projs);
		}
		
		int pageCount = projCount/20;
		if(projCount % 20 >0) pageCount = pageCount + 1;
		if(pageCount<=0) pageCount = 1;
		
		model.put("menu", m);
		model.put("projCount", projCount);
		model.put("pageCount", pageCount);
		model.put("pageBegin", getPageBegin(page));
		model.put("pageEnd", getPageEnd(page, pageCount));
		model.put("page", page);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		int m = getInt(request, "m", 1);
		model.put("title", (m==1?"发起的项目":(m==2?"支持的项目":(m==3?"喜欢的项目":"未知")))+"--合众映画");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
}
