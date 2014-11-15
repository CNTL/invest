package com.tl.invest.proj.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.web.BaseController;

public class ProjectController extends BaseController{
	private ProjectService service = null;
	
	public void setService(ProjectService service) {
		this.service = service;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		
	}
}
