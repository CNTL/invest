package com.tl.kernel.web;
/** 
 * @created 2014年8月24日 下午8:43:57 
 * @author  leijj
 * 类说明 ： 
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tl.common.ErrorMessage;
import com.tl.common.log.Log;
import com.tl.kernel.context.Context;

/**
 * 封装了基本的Controller功能
 * <p>
 * handler方法：
 * 基类中封装了对Controller的handleRequest的处理，自动捕捉异常并收集错误。
 * 一般继承类只要实现其中的handler方法，完成具体业务逻辑即可。
 * </p>
 * <p>
 * viewName属性：基类中定义viewName属性。
 * 当系统需要一个客户端的jsp页面做显示时，给viewName属性赋值。否则可以不必赋值。
 * 一般在Spring mvc配置文件中配置。
 * </p>
 * <p>
 * log属性：基类中定义log属性。
 * 各继承类自己赋予适合的log对象。一般也在配置文件中配置。
 * </p>
 * <p>
 * sessionAuth属性：基类中定义。
 * 可用来获取session中的用户，提供与用户权限相关的各种方法。
 * 一般在处理部门管理员权限相关的资源时使用。 
 * </p>
 * <p>
 * 提供错误信息收集和返回的功能。
 * 返回给View的属性中，有一个代表错误信息的属性，属性名由ErrorMessage.errors表示。
 * <BR>这个表示错误信息的属性是一个List，是<code>ErrorMessage</code>
 * 的列表。
 * <BR>每一个<code>ErrorMessage</code>是一个(key, message)值对，
 * 其中key表示错误的类别标签，作为国际化标签，代表错误类型；
 * message是相对具体的信息，如参数值等。
 * <BR>设置时用法是：
 * <BR> setError(model, errorKey, errorMessage);
 * 
 * <BR>一个Controller可能一次性检测出多种错误，如
 * flow.error.typeMismatch, flow.error.idError等。
 * <BR>这些错误写在List中返回给客户端，进行显示处理。
 * 
 * <BR>当发生程序没有控制的异常时，<code>BaseController</code>的
 * handleRequest会自动把异常信息写进List。
 * </p>
 * @created 2006-2-14
 * @author Gong Lijie
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BaseController implements Controller 
{
	protected Log log = Context.getLog("invest");	
	protected String viewName; 		//Controller成功后的显示view名称
	protected String resourceFile;	//资源文件名，在做系统记录时可能会用
	
	public void setResourceFile(String resourceFile) {
		this.resourceFile = resourceFile;
	}
	/**
	/**
	 * 设置参数viewName
	 * @param viewName
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * 设置使用的Log对象
	 * @param log
	 */
	public void setLog(Log log) {
		this.log = log;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) 
	throws Exception 
	{
		if (log != null && log.isDebugEnabled())
		{
			log.debug(getClass().getName() + ".viewName:" + viewName);
		}
		//作为局部变量	必要 Controller是单例，避免冲突
		Map model = new HashMap();
		try
		{
			handle(request, response, model);
	    }catch (Exception e){
	    	if (log != null) log.error(this.getClass().getName(), e);
	    	
	    	//缺省的错误的标签名，用于表示handle方法中没有捕捉的异常，如数据库临时异常、IO异常等	    	 
	    	setError(model, ErrorMessage.defaultErrorKey, e.getMessage());
	    }
		/*
		 * 2012-5-8 Gong Lijie
		 * 增加逻辑：
		 * 1、若临时不希望Controller走view，则在model里使用@NOVIEW@标示一下即可。
		 * 2、若临时希望换用不同的view，则在model里配置@VIEWNAME@即可
		 */
		if (model.containsKey("@NOVIEW@")) {
			if (log != null && log.isDebugEnabled()) 
				log.debug("View JSP Unused :" + this.getClass().getName());
			return null;
		}
		if (model.containsKey("@VIEWNAME@")) {
			if (log != null && log.isDebugEnabled()) 
				log.debug("Change View to:" + (String)(model.get("@VIEWNAME@")) + "," + this.getClass().getName());
			return new ModelAndView((String)(model.get("@VIEWNAME@")), model);
		}
		
		if (viewName != null)
			return new ModelAndView(viewName, model);
		else
			return null;
	}
	
	/**
	 * 简单情况下，子类只要实现<code>handle</code>方法即可
	 * 在其中设置必要的model内容供返回的页面使用
	 * @param request
	 * @param response
	 * @param model 
	 * @return String View的名称，handleRequest方法中自动根据这个返回值组织ModelAndView
	 * @throws Exception
	 */
	protected abstract void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception;

	/**
	 * 记录错误列表
	 * @param model
	 * @param errorList
	 */
	protected final void setError(Map model, List errorList)
	{
		model.put(ErrorMessage.errors, errorList);
	}
	/**
	 * 增加一条错误信息的记录
	 * @param model
	 * @param key
	 * @param message
	 */
	protected final void setError(Map model, String key, String message)
	{
		List list = (List)model.get(ErrorMessage.errors);
		if (list == null)
		{
			list = new ArrayList(1);
			model.put(ErrorMessage.errors, list);
		}
		list.add(new ErrorMessage(key, message));
	}
	
	/**
	 * 工具方法：从request中取一个整数值
	 * @param request
	 * @param key request中的参数名称
	 * @return
	 */
	protected final int getInt(HttpServletRequest request, String key)
	{
		String value = get(request, key);
		if(value == null || value.length() == 0) value = "0";
		return Integer.parseInt(value);
	}
	/**
	 * 工具方法：从request中取一个对象值
	 * @param request
	 * @param key
	 * @return
	 */
	protected final String get(HttpServletRequest request, String key)
	{
		return request.getParameter(key);
	}
	
	/**
	 * 工具方法：从request中取一个对象值。若无，则用缺省值返回
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected final String get(HttpServletRequest request, String key, String defaultValue)
	{
		String result =  get(request, key);
		if (result == null) result = defaultValue;
		
		return result;
	}
	/**
	 * 工具方法：从request中取一个整数值。若无法取到，则用缺省值返回
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected final int getInt(HttpServletRequest request, String key, int defaultValue)
	{
		try {
			return Integer.parseInt((String)request.getParameter(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 工具方法：从session中取一个整数值
	 * @param request
	 * @param key
	 * @return
	 */
	protected final int getIntInSession(HttpServletRequest request, String key)
	{
		return Integer.parseInt((String)
				request.getSession().getAttribute(key));
	}
	
	protected String getValue(ResourceBundle bundle, String key)
	{
		try
		{
			return bundle.getString(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	//增加output功能，比较常用。由于其它子类已经有很多output(response, value)的private方法，因此这里颠倒参数顺序，以免影响。
	/**
	 * 返回字符串，UTF-8编码
	 * @param response
	 * @param result 字符串内容。若是xml格式，不包含xml头声明，会自动添加
	 * @param xml 是否以xml格式返回
	 * @throws Exception
	 */
	protected void output(String result, HttpServletResponse response, boolean xml) throws Exception {
		if (xml)
			response.setContentType("text/xml; charset=UTF-8");
		else
			response.setContentType("text/plain; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		if (xml) out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.write(result);
		out.close();
	}
	/**
	 * 返回字符串，UTF-8编码
	 * @param response
	 * @param result 字符串内容。text/plain
	 * @throws Exception
	 */
	protected void output(String result, HttpServletResponse response)
	throws Exception {
		output(result, response, false);
	}
	
	protected void outputJson(String result, HttpServletResponse response)
	throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
}