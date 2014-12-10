package com.tl.invest.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends Entry {
	@SuppressWarnings("rawtypes")
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//继承实现		
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
}
