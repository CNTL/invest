package com.tl.common;

import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web��ص�һЩ���߷���
 * 
 * @created 2014-8-31 
 * @author wang.yq
 * @version 1.0
 */
public class WebUtil {

	/**
	 * �����������ȡ�������ԣ�ȡ�����򷵻�0��
	 * 
	 * @param request �������
	 * @param property ������
	 * @return
	 */
	public static int getIntAttr( HttpServletRequest request, String property ) {
		return getIntAttr( request, property, 0 );
	}

	/**
	 * �����������ȡ��������
	 * 
	 * @param request �������
	 * @param property ������
	 * @param def Ĭ��ֵ
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
	 * �����������ȡ���Ͳ�����ȡ�����򷵻�0��
	 * 
	 * @param request �������
	 * @param property ������
	 * @return
	 */
	public static int getIntParam( HttpServletRequest request, String property ) {
		return getIntParam( request, property, 0 );
	}

	/**
	 * �����������ȡ���Ͳ���
	 * 
	 * @param request �������
	 * @param property ������
	 * @param def Ĭ��ֵ
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
	 * ��ȡ�ַ��������������������ֵΪ���ַ������򷵻�null
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
	 * ȡwebӦ�õľ���·�������˿ں͸�Ŀ¼ ��ʽΪ��http://10.97.69.205:8080/e5app/
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
	* ���ܣ� 
	* @param request
	* @return
	* @throws Exception 
	*/ 
	public static String getPath(HttpServletRequest request) throws Exception {
		return request.getSession().getServletContext().getRealPath("/");
	}
	/**
	 * ����ָ����Դ����ȡ���ػ��ַ���
	 * 
	 * @param request Http�������
	 * @param baseName ��Դ����ֵ
	 * @param key ��ֵ
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
	 * ȡһ��Cookieֵ
	 * 
	 * @param request
	 * @param name cookie��
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
	 * ����һ��Cookie ʱ��Ϊһ����
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie( HttpServletResponse response, String name,
			String value ) {
		Cookie cookie = new Cookie( name, value );
		cookie.setPath( "/" );
		cookie.setMaxAge( 2600000 ); // ���һ����

		response.addCookie( cookie );
	}

	/**
	 * ��ȡbool�Ͳ���
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static boolean getBoolParam( HttpServletRequest request, String name ) {
		return getBoolParam(request,name,false);
	}
	
	/**
	 * ��ȡbool�Ͳ���
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