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
	 * ��ȡSEO��������ò���
	 * @param item ��ParamItem�ж���ĳ���
	 * @return
	 */
	public static String getSEOConfig(String item){
		return getConfig(ParamProject.SEO, item);
	}
	
	/**
	 * ��ȡ��������½��������ò���
	 * @param item ��ParamItem�ж���ĳ��� �磺ParamItem.LOGIN_QQ_URL
	 * @return
	 */
	public static String getLoginConfig(String item){
		return getConfig(ParamProject.LOGIN_THIRD, item);
	}
	
	/**
	 * ��ȡ������֧����������ò���
	 * @param item item ��ParamItem�ж���ĳ��� �磺ParamItem.PAY_ALI_URL
	 * @return
	 */
	public static String getPayConfig(String item){
		return getConfig(ParamProject.PAY_THIRD, item);
	}
	
	/**
	 * ��ϵͳ����
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
			log.error("��ȡϵͳ����ʧ��:" + project + "," + item + ". " + e.getLocalizedMessage());
			return "";
		}
	}
	
}
