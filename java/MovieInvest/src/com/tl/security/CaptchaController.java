package com.tl.security;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tl.common.StringUtils;
import com.tl.common.VerifyCaptchaHelper;
import com.tl.kernel.web.BaseController;


/**
 * 登录验证码生成器
 * @author wang.yq
 * 2014-8-29
 */
public class CaptchaController extends BaseController
{
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response,
			Map model) throws Exception
	{
		String actionString = get(request, "action","");
		if(actionString.equals("randomcode")){
			String randomString = VerifyCaptchaHelper.getRandomChar();
			output(randomString, response);
		}
		if(actionString.equals("getimage")){
			String randomString = get(request, "randomcode","");
			if(!StringUtils.isEmpty(randomString)){
				// 在内存中创建图象
				BufferedImage bimage = VerifyCaptchaHelper.getVerifyImage(randomString);
				// 输出图象到页面
				response.setContentType("image/jpeg");		
				response.setHeader("Cache-Control","no-store");
		    	response.setHeader("Pragma","no-cache");           
		    	response.setDateHeader("Expires", 0);
		    	
				ServletOutputStream out = response.getOutputStream();		
				ImageIO.write(bimage, "JPEG", out);
			}
			
		}
	}
}
