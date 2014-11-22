package com.tl.common;

import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web相关的一些工具方法
 * 
 * @created 2014-8-31 
 * @author wang.yq
 * @version 1.0
 */
public class WebUtil {

	/**
	 * 从请求对象中取整型属性（取不到则返回0）
	 * 
	 * @param request 请求对象
	 * @param property 属性名
	 * @return
	 */
	public static int getIntAttr( HttpServletRequest request, String property ) {
		return getIntAttr( request, property, 0 );
	}

	/**
	 * 从请求对象中取整型属性
	 * 
	 * @param request 请求对象
	 * @param property 属性名
	 * @param def 默认值
	 * @return
	 */
	public static int getIntAttr( HttpServletRequest request, String property,
			int def ) {
		String value = ( String ) request.getAttribute( property );
		if ( value != null && !"".equals( value.trim() ) ) {
			try {
				return Integer.parseInt( value );
			} catch ( NumberFormatException e ) {
				return def;
			}
		}

		return def;
	}

	/**
	 * 从请求对象中取整型参数（取不到则返回0）
	 * 
	 * @param request 请求对象
	 * @param property 属性名
	 * @return
	 */
	public static int getIntParam( HttpServletRequest request, String property ) {
		return getIntParam( request, property, 0 );
	}

	/**
	 * 从请求对象中取整型参数
	 * 
	 * @param request 请求对象
	 * @param property 属性名
	 * @param def 默认值
	 * @return
	 */
	public static int getIntParam( HttpServletRequest request, String property,
			int def ) {
		String value = ( String ) request.getParameter( property );
		if ( value != null && !"".equals( value.trim() ) ) {
			try {
				return Integer.parseInt( value );
			} catch ( NumberFormatException e ) {
				return def;
			}
		}

		return def;
	}

	/**
	 * 获取字符串型请求参数，若参数值为空字符串，则返回null
	 * 
	 * @param request
	 * @param property
	 * @return
	 */
	public static String getStringParam( HttpServletRequest request,
			String property ) {
		String str = request.getParameter( property );
		if ( "".equals( str ) )
			return null;
		return str;
	}

	/**
	 * 取web应用的绝对路径，带端口和根目录 格式为：http://10.97.69.205:8080/e5app/
	 * 
	 * @param request
	 * @return
	 */
	public static String getRoot( HttpServletRequest request ) {
		StringBuffer sbRoot = new StringBuffer( 50 );
		sbRoot.append( "http://" ).append( request.getServerName() ).append(
				":" ).append( request.getServerPort() );

		String strContextPath = request.getContextPath();
		if ( strContextPath != null && !strContextPath.equals( "/" ) )
			sbRoot.append( strContextPath );

		sbRoot.append( "/" );
		return sbRoot.toString();
	}
	/** 
	* @author  leijj 
	* 功能： 
	* @param request
	* @return
	* @throws Exception 
	*/ 
	public static String getPath(HttpServletRequest request) throws Exception {
		return request.getSession().getServletContext().getRealPath("/");
	}
	/**
	 * 根据指定资源名获取本地化字符串
	 * 
	 * @param request Http请求对象
	 * @param baseName 资源名基值
	 * @param key 键值
	 * @return
	 */
	public static String getLocalString( HttpServletRequest request,
			String baseName, String key ) {
		ResourceBundle rb = ResourceBundle.getBundle(
				baseName,
				request.getLocale() );
		if ( rb == null )
			return null;
		return rb.getString( key );
	}

	/**
	 * 取一个Cookie值
	 * 
	 * @param request
	 * @param name cookie名
	 * @return
	 */
	public static String getCookie( HttpServletRequest request, String name ) {
		Cookie[] cookieArr = request.getCookies();
		if ( cookieArr == null )
			return null;

		for ( int i = 0; i < cookieArr.length; i++ )
			if ( cookieArr[i].getName().equals( name ) )
				return cookieArr[i].getValue();
		return null;
	}

	/**
	 * 设置一个Cookie 时间为一个月
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie( HttpServletResponse response, String name,
			String value ) {
		Cookie cookie = new Cookie( name, value );
		cookie.setPath( "/" );
		cookie.setMaxAge( 2600000 ); // 差不多一个月

		response.addCookie( cookie );
	}

	/**
	 * 获取bool型参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static boolean getBoolParam( HttpServletRequest request, String name ) {
		return getBoolParam(request,name,false);
	}
	
	/**
	 * 获取bool型参数
	 *  
	 * @param request
	 * @param name
	 * @return
	 */
	public static boolean getBoolParam( HttpServletRequest request, String name ,boolean defaultValue) {
		String value = request.getParameter( name );	
		
		if ( "true".equals( value ) || "yes".equals( value ) || "on".equals( value ) || "1".equals( value ))
			return true;
		
		if("false".equals(value) || "no".equals(value) || "off".equals(value) || "0".equals(value))
			return false;
		
		return defaultValue;
	}
}