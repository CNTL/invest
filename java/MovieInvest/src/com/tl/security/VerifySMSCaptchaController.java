package com.tl.security;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cloopen.rest.sdk.CCPRestSDK;
import com.tl.common.VerifyCaptchaHelper;
import com.tl.kernel.web.BaseController;
 

/**
 * 短信验证码
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
	
	/** 验证短信验证码
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void VerifySMS(HttpServletRequest request, HttpServletResponse response,
			Map model) throws Exception{
		
		//接收手机号
		String mobile = get(request, "mobile");
		//获得4位随机验证码
		String verifyCode = VerifyCaptchaHelper.getRandomNum();
		//短信验证码模板ID
		String smsTemplateID = "1";
		//验证有效时间分钟
		String verifTime = "5";
		
		HashMap<String, Object> result = null;

		//初始化SDK
		CCPRestSDK restAPI = new CCPRestSDK();
		
		//******************************注释*********************************************
		//*初始化服务器地址和端口                                                       *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		restAPI.init("sandboxapp.cloopen.com", "8883");
		
		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
		restAPI.setAccount("8a48b5514ab8cc53014abceb5f6f01ea", "6d8c19cc04fb4d658b80ea32c7fa8679");
		
		
		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		restAPI.setAppId("aaf98f894ab8c7a6014abced441e01eb");
		
		
		//******************************注释****************************************************************
		//*调用发送模板短信的接口发送短信                                                                  *
		//*参数顺序说明：                                                                                  *
		//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
		//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
		//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		//*第三个参数是要替换的内容数组。																														       *
		//**************************************************************************************************
		
		//**************************************举例说明***********************************************************************
		//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
		//*********************************************************************************************************************
		result = restAPI.sendTemplateSMS(mobile,smsTemplateID ,new String[]{verifyCode,verifTime});
		
		if("000000".equals(result.get("statusCode"))){
			output(verifyCode, response);
		}else{
			output("error", response);
		}
	}
}
