package com.tl.invest.common;

import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.invest.constant.ParamProject;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.param.Parameter;
import com.tl.kernel.sys.param.ParameterReader;

public class ParamReader {
	private static Log log = Context.getLog("invest");
	
	/**
	 * 获取SEO的相关配置参数
	 * @param item 在ParamItem中定义的常量
	 * @return
	 */
	public static String getSEOConfig(String item){
		return getConfig(ParamProject.SEO, item);
	}
	
	/**
	 * 获取第三方登陆的相关配置参数
	 * @param item 在ParamItem中定义的常量 如：ParamItem.LOGIN_QQ_URL
	 * @return
	 */
	public static String getLoginConfig(String item){
		return getConfig(ParamProject.LOGIN_THIRD, item);
	}
	
	/**
	 * 获取第三方支付的相关配置参数
	 * @param item item 在ParamItem中定义的常量 如：ParamItem.PAY_ALI_URL
	 * @return
	 */
	public static String getPayConfig(String item){
		return getConfig(ParamProject.PAY_THIRD, item);
	}
	
	/**
	 * 读系统参数
	 * @param project
	 * @param item
	 * @return
	 */
	public static String getConfig(String project, String item) {
		try {
			ParameterReader reader = (ParameterReader) Context.getBean(ParameterReader.class);
			Parameter param = reader.getParameter(project, item);
			if(param==null) return "";
			return StringUtils.isEmpty(param.getValue()) ? "" : param.getValue();
		} catch (Exception e) {
			log.error("读取系统参数失败:" + project + "," + item + ". " + e.getLocalizedMessage());
			return "";
		}
	}
	
}
