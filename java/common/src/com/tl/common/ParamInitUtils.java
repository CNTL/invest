package com.tl.common;
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
