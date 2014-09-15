package com.tl.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		int width=60, height=20;

		// 在内存中创建图象
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics gg = bimage.getGraphics();

		// 设定背景色
		gg.setColor(getRandColor(200,250));
		gg.fillRect(0, 0, width, height);

		// 设定字体
		gg.setFont(new Font("Times New Roman",Font.PLAIN,18));

		//画边框
		//g.setColor(new Color());
		//g.drawRect(0,0,width-1,height-1);

		// 生成随机类
		Random random = new Random();

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		gg.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gg.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			int r = 20 + random.nextInt(110);
			int g = 20 + random.nextInt(110);
			int b = 20 + random.nextInt(110);
			gg.setColor(new Color(r, g, b));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			gg.drawString(rand, 13 * i + 6, 16);
		}
		// 图象生效
		gg.dispose();

		// 将认证码存入SESSION
		HttpSession session = request.getSession();
		session.setAttribute("rand", sRand);

		// 输出图象到页面
		response.setContentType("image/jpeg");		
		response.setHeader("Cache-Control","no-store");
    	response.setHeader("Pragma","no-cache");
    	
		ServletOutputStream out = response.getOutputStream();		
		ImageIO.write(bimage, "JPEG", out);
	}

	Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		
		Random random = new Random();
		
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}	
}
