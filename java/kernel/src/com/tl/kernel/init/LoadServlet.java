package com.tl.kernel.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class LoadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
		
		Load load = new Load();
		load.setIsWebProject(true);
		load.init(servletConfig.getInitParameter("config"));
	}
	
	 public void destroy(){
		 
	 }
}
