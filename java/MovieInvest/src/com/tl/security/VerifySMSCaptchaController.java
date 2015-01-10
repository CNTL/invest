package com.tl.security;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cloopen.rest.sdk.CCPRestSDK;
import com.tl.common.VerifyCaptchaHelper;
import com.tl.kernel.web.BaseController;
 

/**
 * ������֤��
 * @author wang.yq	
 * 2015-1-10
 */
public class VerifySMSCaptchaController extends BaseController
{
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response,
			Map model) throws Exception
	{
		
		String actionString = get(request, "action","");
		if(actionString.equals("VerifySMS")){
			VerifySMS(request,response,model);
		}
	} 
	
	/** ��֤������֤��
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void VerifySMS(HttpServletRequest request, HttpServletResponse response,
			Map model) throws Exception{
		
		//�����ֻ���
		String mobile = get(request, "mobile");
		//���4λ�����֤��
		String verifyCode = VerifyCaptchaHelper.getRandomNum();
		//������֤��ģ��ID
		String smsTemplateID = "1";
		//��֤��Чʱ�����
		String verifTime = "5";
		
		HashMap<String, Object> result = null;

		//��ʼ��SDK
		CCPRestSDK restAPI = new CCPRestSDK();
		
		//******************************ע��*********************************************
		//*��ʼ����������ַ�Ͷ˿�                                                       *
		//*ɳ�л���������Ӧ�ÿ������ԣ���restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*�����������û�Ӧ������ʹ�ã���restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		restAPI.init("sandboxapp.cloopen.com", "8883");
		
		//******************************ע��*********************************************
		//*��ʼ�����ʺź����ʺ�����,��Ӧ�������������˺��µ�ACCOUNT SID��AUTH TOKEN     *
		//*����˳�򣺵�һ��������ACOUNT SID���ڶ���������AUTH TOKEN��                   *
		//*******************************************************************************
		restAPI.setAccount("8a48b5514ab8cc53014abceb5f6f01ea", "6d8c19cc04fb4d658b80ea32c7fa8679");
		
		
		//******************************ע��*********************************************
		//*��ʼ��Ӧ��ID                                                                 *
		//*���Կ�����ʹ�á�����Demo����APP ID����ʽ������Ҫʹ���Լ�������Ӧ�õ�App ID     *
		//*Ӧ��ID�Ļ�ȡ����½�������ڡ�Ӧ��-Ӧ���б������Ӧ�����ƣ���Ӧ�������ȡAPP ID*
		//*******************************************************************************
		restAPI.setAppId("aaf98f894ab8c7a6014abced441e01eb");
		
		
		//******************************ע��****************************************************************
		//*���÷���ģ����ŵĽӿڷ��Ͷ���                                                                  *
		//*����˳��˵����                                                                                  *
		//*��һ������:��Ҫ���͵��ֻ����룬�����ö��ŷָ���һ�����֧��100���ֻ���                          *
		//*�ڶ�������:��ģ��ID����ƽ̨�ϴ����Ķ���ģ���IDֵ�����Ե�ʱ�����ʹ��ϵͳ��Ĭ��ģ�壬idΪ1��    *
		//*ϵͳĬ��ģ�������Ϊ������ͨѶ����ʹ�õ�����ͨѶ����ģ�壬������֤����{1}������{2}��������ȷ���롱*
		//*������������Ҫ�滻���������顣																														       *
		//**************************************************************************************************
		
		//**************************************����˵��***********************************************************************
		//*�������ò���Demo��APP ID������ʹ��Ĭ��ģ��ID 1�������ֻ�����13800000000���������Ϊ6532��5������÷�ʽΪ           *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*��13800000000�ֻ����յ��Ķ��������ǣ�����ͨѶ����ʹ�õ�����ͨѶ����ģ�壬������֤����6532������5��������ȷ����     *
		//*********************************************************************************************************************
		result = restAPI.sendTemplateSMS(mobile,smsTemplateID ,new String[]{verifyCode,verifTime});
		
		if("000000".equals(result.get("statusCode"))){
			output(verifyCode, response);
		}else{
			output("error", response);
		}
	}
}
