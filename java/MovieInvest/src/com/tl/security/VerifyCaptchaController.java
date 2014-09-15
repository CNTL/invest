package com.tl.security;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.kernel.web.BaseController;
 

/**
 * 登录验证码校验器
 * @author wang.yq	
 * 2014-8-29
 */
public class VerifyCaptchaController extends BaseController
{
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response,
			Map model) throws Exception
	{
		String result = "";
	 
		String checkCode = request.getParameter("checkCode");
		String rand = (String)request.getSession().getAttribute("rand");
		
		if (StringUtils.isEmpty(rand)) {
			result = "-1";
		}
		else if (checkCode.equals(rand)) {
			result = "1";
		}
		else {
			result = "0";
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
}
