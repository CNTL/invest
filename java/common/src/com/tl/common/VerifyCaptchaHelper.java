package com.tl.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


/**创建验证码辅助类
 * @author wang.yq
 *
 */
public class VerifyCaptchaHelper {
	
	/** 获得4位随机字符，数字+字母
	 * @return
	 * @throws Exception
	 */
	public static String getRandomChar () throws Exception{
		char[] codeSequence = { 'A', 'B','C', 'D','E', 'F', 'G','H','I','J', 'K','L', 'M','N','O', 'P', 'Q','R','S','T','U','V','W', 'X','Y', 'Z', '0','1','2','3','4','5','6','7',  '8', '9' }; 

		Random random = new Random();
		// 取随机产生的认证码(4位数字)
		StringBuilder randomCode = new StringBuilder(); 

		 for (int i = 0; i < 4; i++) {    
	            // 得到随机产生的验证码数字。    
	            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);                
	            // 将产生的四个随机数组合在一起。                
	            randomCode.append(strRand);   
		 }
		return randomCode.toString();
	}
	
	/** 获得4位随机字符，数字
	 * @return
	 * @throws Exception
	 */
	public static String getRandomNum () throws Exception{
		char[] codeSequence = { '0','1','2','3','4','5','6','7',  '8', '9' }; 

		Random random = new Random();
		// 取随机产生的认证码(4位数字)
		 
		StringBuilder randomCode = new StringBuilder(); 

		 for (int i = 0; i < 4; i++) {    
	            // 得到随机产生的验证码数字。    
	            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);                
	            // 将产生的四个随机数组合在一起。                
	            randomCode.append(strRand);   
		 }
		return randomCode.toString();
	}
	
	/** 生成验证码图片
	 * @param randomString
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage getVerifyImage(String randomString) throws Exception{
		
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
		char[] randoms = randomString.toCharArray();
		for (int i = 0; i < randoms.length; i++) {
			String rand = String.valueOf(randoms[i]);
			
			// 将认证码显示到图象中
			int r = 20 + random.nextInt(110);
			int g = 20 + random.nextInt(110);
			int b = 20 + random.nextInt(110);
			gg.setColor(new Color(r, g, b));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			gg.drawString(rand, 13 * i + 6, 16);
		}
		// 图象生效
		gg.dispose();
    	
		return bimage;
	}
	
	/** 获得随机颜色值
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		
		Random random = new Random();
		
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}	
}