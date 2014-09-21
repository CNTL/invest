package com.tl.kernel.web;
/** 
 * @created 2014��8��26�� ����3:26:48 
 * @author  leijj
 * ��˵�� �� 
 */
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

/**
	<filter>
		<filter-name>URLEntryFilter</filter-name>
		<filter-class>com.tl.kernel.web.URLEntryFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
		<init-param>
			<!-- if file contains part below, it will not do session check.-->
			<param-name>session-not-checked</param-name>
			<param-value>Login.jsp,sys.jsp,login.jsp,AutoRefresh.jsp,sysAuth.do,sysLogin.do,auth.do,keeplive.do,PermissionProxy.do,AppChangeProxy.do</param-value>
		</init-param>
		<init-param>
			<!-- extensions of the files not-allow-browser-cache -->
			<param-name>cache-not-allowed</param-name>  
			<param-value>.do,.jsp,.js,.css</param-value>
		</init-param>
	</filter>
	<filter-mapping>
	    <filter-name>URLEntryFilter</filter-name>
	    <url-pattern>/*</url-pattern>
	</filter-mapping>

 * Filter,���ñ��룻���������ͻ��˻��棬ʹÿ�����·����µĳ���
 * ����Ҫ��������������ˡ�
 * Ҳ���ڼ��jsp��do�Ƿ񾭹��Ϸ���¼��session��Ч�Լ�飩
 */
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
		
		//���session�Ƿ����
		if (!isSessionExclude(path)) {
			if (!validSession(path, servletRequst, servletResponse)) 
				return;
		}
		chain.doFilter(request,response);
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
				response.sendRedirect(WebUtil.getRoot(request) + "admin/login.jsp");
				return false;
			}
		} else {
			if (session.getAttribute(SysSessionUser.sessionName) == null) {
				System.out.println("[-----Illegal path, redirect to login-----]" + path);
				response.sendRedirect(WebUtil.getRoot(request) + "user/userLogin.jsp");
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
		//ֻ��jsp��do����session���
		if (!path.endsWith(".jsp") && !path.endsWith(".do")) return true;
		if (excludeSessions == null) return false;
		
		for (int i = 0; i < excludeSessions.length; i++) {
			if (path.contains(excludeSessions[i])) return true;
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