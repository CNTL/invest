package com.tl.invest.doc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
 


import javax.servlet.http.HttpServletResponse;

import com.tl.invest.workspace.Entry;
import com.tl.kernel.web.BaseController;

public class DocMainController extends Entry {

	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//继承实现
		String loginCurrentUrl = request.getParameter("url");
 
		model.put("loginCurrentUrl", loginCurrentUrl);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画--登录");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	 
	 
}
