package com.tl.invest.proj.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.constant.DicTypes;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.user.user.UserManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

public class ProjectMainController extends Entry {
	protected ProjectService service = null;
	protected UserManager userMgr = null;
	protected DictionaryReader dicReader = null;
	
	public void setService(ProjectService service) {
		this.service = service;
	}
	public void setUserMgr(UserManager userMgr) {
		this.userMgr = userMgr;
	}
	public void setDicReader(DictionaryReader dicReader) {
		this.dicReader = dicReader;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response, Map model) throws Exception {
		int type = getInt(request, "type", 0);
		if(type<=0){
			int pageSize = getInt(request, "pagesize", 4);
			Dictionary[] types = dicReader.getSubDics(DicTypes.DIC_INVEST_TYPE.typeID(), 0);
			for (Dictionary dic : types) {
				int typeID = dic.getId();
				ProjectExt[] projs = service.getProjectExts(typeID, pageSize, 1,"proj_approveStatus in(-1,2)", null);
				if(dic.getName().indexOf("微电影")>=0){
					model.put("projs1", projs);
					model.put("projType1", typeID);
				}else if(dic.getName().indexOf("长片")>=0){
					model.put("projs2", projs);
					model.put("projType2", typeID);
				}else if(dic.getName().indexOf("活动")>=0){
					model.put("projs3", projs);
					model.put("projType3", typeID);
				}
			}
			model.put("projCount", 0);
			model.put("pageCount", 1);
			model.put("pageBegin", 1);
			model.put("pageEnd", 1);
			model.put("page", 1);
		}else {
			int page = getInt(request, "page", 1);
			int pageSize = getInt(request, "pagesize", 20);
			ProjectExt[] projs = service.getProjectExts(type, pageSize, page,"proj_approveStatus in(-1,2)", null);
			int projCount = service.getProjectExtsCount(type,"proj_approveStatus in(-1,2)", null);
			int pageCount = projCount/pageSize;
			if(projCount % pageSize >0) pageCount = pageCount + 1;
			if(pageCount<=0) pageCount = 1;
			
			Dictionary dic = dicReader.getDic(DicTypes.DIC_INVEST_TYPE.typeID(), type);
			
			if(dic.getName().indexOf("微电影")>=0){
				model.put("projs1", projs);
				model.put("projType1", type);
			}else if(dic.getName().indexOf("长片")>=0){
				model.put("projs2", projs);
				model.put("projType2", type);
			}else if(dic.getName().indexOf("活动")>=0){
				model.put("projs3", projs);
				model.put("projType3", type);
			}
			model.put("projCount", projCount);
			model.put("pageCount", pageCount);
			model.put("pageBegin", getPageBegin(page));
			model.put("pageEnd", getPageEnd(page, pageCount));
			model.put("page", page);
			model.put("pagesize", pageSize);
		}
		model.put("type", type);
	};
	
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
	
	@Override
	protected String getCurrentMenu() {
		return "项目";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画--项目");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
}
