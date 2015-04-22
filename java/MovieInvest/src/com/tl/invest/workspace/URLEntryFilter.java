package com.tl.invest.workspace;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qq.connect.utils.URLEncodeUtils;
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.common.SessionHelper;

public class URLEntryFilter implements Filter {
	private String encoding = null;

	private String[] excludeSessions = null;
	private String[] excludeCaches = null;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException
	{
		HttpServletRequest servletRequst = (HttpServletRequest)request;
		HttpServletResponse servletResponse = ((HttpServletResponse) response);

		String path = servletRequst.getServletPath();
		//�ӻ��洦��
		if (isCacheExclude(path)) {
			dealCache(servletRequst, servletResponse);
		}
		//��һ�ص��½
		try {
			checkLogin(servletRequst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//���session�Ƿ����
		if (!isSessionExclude(path)) {
			if (!validSession(path, servletRequst, servletResponse)) 
				return;
		}
		chain.doFilter(request,response);
	}
	
	private void checkLogin(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute(SysSessionUser.sessionName) != null) {
			SysSessionUser sysUser = SessionHelper.getUser(request);
			if(sysUser == null) return;
			UserManager manager = (UserManager)Context.getBean(UserManager.class);
			User user = manager.getUserByID(sysUser.getUserID());
			
			if(!user.getLastSessionID().equals(sysUser.getSessionID())){
				session.removeAttribute(SysSessionUser.sessionName);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException{
		this.encoding = filterConfig.getInitParameter("encoding");
		
		//��ʼ������Ҫsession�жϵ�jsp��do·����.do��view��.jsp�����ᾭ��filter
		String exclude = filterConfig.getInitParameter("session-not-checked");  
		if (exclude != null) {  
			excludeSessions = exclude.split(",");
		}
		exclude = filterConfig.getInitParameter("cache-not-allowed");  
		if (exclude != null) {  
			excludeCaches = exclude.split(",");
		}
	}
	
	private void dealCache(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		//����
		if (request.getCharacterEncoding() == null)
			request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		//response.setContentType("text/html;charset=UTF-8");
		
		//�����������Ч
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
	}
	
	private boolean validSession(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String queryString = request.getQueryString();
		String url = request.getRequestURL().toString();
		url = URLEncodeUtils.encodeURL(url + "?" +queryString);
		/*
		 * ��ʱֻҪ������һ����session������ͨ����
		 */
		HttpSession session = request.getSession();
		
		if(session.getAttribute(SysSessionUser.sessionAdminName) != null
				|| session.getAttribute(SysSessionUser.sessionName) != null)
			return true;
		
		if (isAdminConsole(path)) {
			if (session.getAttribute(SysSessionUser.sessionAdminName) == null) {
				System.out.println("[-----�Ƿ�����·��, ���¶�λ����¼����-----]" + path);
				response.sendRedirect(WebUtil.getRoot(request) + "admin/login.jsp?url=" + url);
				return false;
			}
		} else {
			if (session.getAttribute(SysSessionUser.sessionName) == null) {
				System.out.println("[-----Illegal path, redirect to login-----]" + path);
				response.sendRedirect(WebUtil.getRoot(request) + "login.jsp?url=" + url);
				return false;
			}
		}
		
		return true;
	}

	//
	private boolean isCacheExclude(String path) {
		if (excludeCaches == null) return false;
		
		for (int i = 0; i < excludeCaches.length; i++) {
			if (path.endsWith(excludeCaches[i])) return true;
		}
		return false;
	}
	//�Ƿ񲻱ؼ��session
	private boolean isSessionExclude(String path) {
		String clearPath = path;
		if(path.startsWith("/")){
			clearPath = path.substring(1, path.length());
		}
		//ֻ��jsp��do����session���
		if (!clearPath.endsWith(".jsp") && !clearPath.endsWith(".do")) return true;
		if (excludeSessions == null) return false;
		
		for (int i = 0; i < excludeSessions.length; i++) {
			if (clearPath.equals(excludeSessions[i])){
				return true;
			}
		}
		return false;
	}
	//�ж��Ƿ�ϵͳ����˵�¼
	private boolean isAdminConsole(String path) {
		return (path.startsWith("/admin/"));
	}
	
	public void destroy(){
	}
}
