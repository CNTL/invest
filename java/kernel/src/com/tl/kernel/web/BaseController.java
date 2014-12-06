package com.tl.kernel.web;
/** 
 * @created 2014��8��24�� ����8:43:57 
 * @author  leijj
 * ��˵�� �� 
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
 * ��װ�˻�����Controller����
 * <p>
 * handler������
 * �����з�װ�˶�Controller��handleRequest�Ĵ����Զ���׽�쳣���ռ�����
 * һ��̳���ֻҪʵ�����е�handler��������ɾ���ҵ���߼����ɡ�
 * </p>
 * <p>
 * viewName���ԣ������ж���viewName���ԡ�
 * ��ϵͳ��Ҫһ���ͻ��˵�jspҳ������ʾʱ����viewName���Ը�ֵ��������Բ��ظ�ֵ��
 * һ����Spring mvc�����ļ������á�
 * </p>
 * <p>
 * log���ԣ������ж���log���ԡ�
 * ���̳����Լ������ʺϵ�log����һ��Ҳ�������ļ������á�
 * </p>
 * <p>
 * sessionAuth���ԣ������ж��塣
 * ��������ȡsession�е��û����ṩ���û�Ȩ����صĸ��ַ�����
 * һ���ڴ����Ź���ԱȨ����ص���Դʱʹ�á� 
 * </p>
 * <p>
 * �ṩ������Ϣ�ռ��ͷ��صĹ��ܡ�
 * ���ظ�View�������У���һ�����������Ϣ�����ԣ���������ErrorMessage.errors��ʾ��
 * <BR>�����ʾ������Ϣ��������һ��List����<code>ErrorMessage</code>
 * ���б�
 * <BR>ÿһ��<code>ErrorMessage</code>��һ��(key, message)ֵ�ԣ�
 * ����key��ʾ���������ǩ����Ϊ���ʻ���ǩ������������ͣ�
 * message����Ծ������Ϣ�������ֵ�ȡ�
 * <BR>����ʱ�÷��ǣ�
 * <BR> setError(model, errorKey, errorMessage);
 * 
 * <BR>һ��Controller����һ���Լ������ִ�����
 * flow.error.typeMismatch, flow.error.idError�ȡ�
 * <BR>��Щ����д��List�з��ظ��ͻ��ˣ�������ʾ����
 * 
 * <BR>����������û�п��Ƶ��쳣ʱ��<code>BaseController</code>��
 * handleRequest���Զ����쳣��Ϣд��List��
 * </p>
 * @created 2006-2-14
 * @author Gong Lijie
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BaseController implements Controller 
{
	protected Log log = Context.getLog("invest");	
	protected String viewName; 		//Controller�ɹ������ʾview����
	protected String resourceFile;	//��Դ�ļ���������ϵͳ��¼ʱ���ܻ���
	
	public void setResourceFile(String resourceFile) {
		this.resourceFile = resourceFile;
	}
	/**
	/**
	 * ���ò���viewName
	 * @param viewName
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * ����ʹ�õ�Log����
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
		//��Ϊ�ֲ�����	��Ҫ Controller�ǵ����������ͻ
		Map model = new HashMap();
		try
		{
			handle(request, response, model);
	    }catch (Exception e){
	    	if (log != null) log.error(this.getClass().getName(), e);
	    	
	    	//ȱʡ�Ĵ���ı�ǩ�������ڱ�ʾhandle������û�в�׽���쳣�������ݿ���ʱ�쳣��IO�쳣��	    	 
	    	setError(model, ErrorMessage.defaultErrorKey, e.getMessage());
	    }
		/*
		 * 2012-5-8 Gong Lijie
		 * �����߼���
		 * 1������ʱ��ϣ��Controller��view������model��ʹ��@NOVIEW@��ʾһ�¼��ɡ�
		 * 2������ʱϣ�����ò�ͬ��view������model������@VIEWNAME@����
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
	 * ������£�����ֻҪʵ��<code>handle</code>��������
	 * ���������ñ�Ҫ��model���ݹ����ص�ҳ��ʹ��
	 * @param request
	 * @param response
	 * @param model 
	 * @return String View�����ƣ�handleRequest�������Զ������������ֵ��֯ModelAndView
	 * @throws Exception
	 */
	protected abstract void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception;

	/**
	 * ��¼�����б�
	 * @param model
	 * @param errorList
	 */
	protected final void setError(Map model, List errorList)
	{
		model.put(ErrorMessage.errors, errorList);
	}
	/**
	 * ����һ��������Ϣ�ļ�¼
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
	 * ���߷�������request��ȡһ������ֵ
	 * @param request
	 * @param key request�еĲ�������
	 * @return
	 */
	protected final int getInt(HttpServletRequest request, String key)
	{
		String value = get(request, key);
		if(value == null || value.length() == 0) value = "0";
		return Integer.parseInt(value);
	}
	/**
	 * ���߷�������request��ȡһ������ֵ
	 * @param request
	 * @param key
	 * @return
	 */
	protected final String get(HttpServletRequest request, String key)
	{
		return request.getParameter(key);
	}
	
	/**
	 * ���߷�������request��ȡһ������ֵ�����ޣ�����ȱʡֵ����
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
	 * ���߷�������request��ȡһ������ֵ�����޷�ȡ��������ȱʡֵ����
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
	 * ���߷�������request��ȡһ��longֵ�����޷�ȡ��������ȱʡֵ����
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected long getLong(HttpServletRequest request, String key, long defaultValue) {
		try {
			return Long.parseLong((String)request.getParameter(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * ���߷�������session��ȡһ������ֵ
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
	
	//����output���ܣ��Ƚϳ��á��������������Ѿ��кܶ�output(response, value)��private�������������ߵ�����˳������Ӱ�졣
	/**
	 * �����ַ�����UTF-8����
	 * @param response
	 * @param result �ַ������ݡ�����xml��ʽ��������xmlͷ���������Զ����
	 * @param xml �Ƿ���xml��ʽ����
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
	 * �����ַ�����UTF-8����
	 * @param response
	 * @param result �ַ������ݡ�text/plain
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