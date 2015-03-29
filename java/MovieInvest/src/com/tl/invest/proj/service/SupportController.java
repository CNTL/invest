package com.tl.invest.proj.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.proj.ProjSupportExt;

public class SupportController extends ProjectController {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		super.setOtherData(request, response, model);
		int islist = getInt(request, "list",0);
		int payType = proj == null ? 0 : proj.getPayType();
		int page = getInt(request, "page", 1);
		if(proj!=null){
			ProjSupportExt[] supports = service.getProjectSupports(proj.getId(),20,page, null);
			int supportCount = service.getProjectSupportCount(proj.getId(), null);
			int pageCount = supportCount/20;
			if(supportCount % 20 >0) pageCount = pageCount + 1;
			if(pageCount<=0) pageCount = 1;
			model.put("supports", supports);
			model.put("supportCount", supportCount);
			model.put("pageCount", pageCount);
			model.put("page", page);
		}
		if(islist==0){
			if(payType == 1){
				model.put("@VIEWNAME@", "proj/SupportJP");
			}else {
				model.put("@VIEWNAME@", "proj/Support");
			}
		}
		else{
			//增加一个项目管理中的支持者视图
			model.put("@VIEWNAME@", "proj/SupportList");
		}
	}
}
