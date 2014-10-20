package com.tl.common;

import java.io.UnsupportedEncodingException;

/** 
 * @author  leijj 
 * @version ����ʱ�䣺2014��8��22�� ����9:01:43 
 * ��˵�� �� 
 */
public class ParamInitUtils {
	/** 
	* @author leijj 
	* @param param
	* @return ��ʼ������
	*/ 
	public static String getString(String param){
		if(param == null || "null".equals(param))
			return "";
		return param;
	}
	
	public static String decodeStr(String param) throws UnsupportedEncodingException{
		if(param == null || "null".equals(param))
			return "";
		String paramsTrans = new String(param.getBytes("ISO-8859-1"),"UTF-8");
		param = java.net.URLDecoder.decode(paramsTrans , "UTF-8");
		return param;
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ������������
	* @param param
	* @return 
	*/ 
	public static int getInt(String param){
		if(param == null)
			return 0;
		
		return Integer.valueOf(param);
	}
}
