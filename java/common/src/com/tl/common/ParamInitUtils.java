package com.tl.common;
/** 
 * @author  leijj 
 * @version 创建时间：2014年8月22日 下午9:01:43 
 * 类说明 ： 
 */
public class ParamInitUtils {
	/** 
	* @author leijj 
	* @param param
	* @return 初始化参数
	*/ 
	public static String getString(String param){
		if(param == null || "null".equals(param))
			return "";
		return param;
	}
	
	/** 
	* @author  leijj 
	* 功能： 返回整数类型
	* @param param
	* @return 
	*/ 
	public static int getInt(String param){
		if(param == null)
			return 0;
		
		return Integer.valueOf(param);
	}
}
