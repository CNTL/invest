package com.tl.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


/**������֤�븨����
 * @author wang.yq
 *
 */
public class VerifyCaptchaHelper {
	
	/** ���4λ����ַ�������+��ĸ
	 * @return
	 * @throws Exception
	 */
	public static String getRandomChar () throws Exception{
		char[] codeSequence = { 'A', 'B','C', 'D','E', 'F', 'G','H','I','J', 'K','L', 'M','N','O', 'P', 'Q','R','S','T','U','V','W', 'X','Y', 'Z', '0','1','2','3','4','5','6','7',  '8', '9' }; 

		Random random = new Random();
		// ȡ�����������֤��(4λ����)
		StringBuilder randomCode = new StringBuilder(); 

		 for (int i = 0; i < 4; i++) {    
	            // �õ������������֤�����֡�    
	            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);                
	            // ���������ĸ�����������һ��                
	            randomCode.append(strRand);   
		 }
		return randomCode.toString();
	}
	
	/** ���4λ����ַ�������
	 * @return
	 * @throws Exception
	 */
	public static String getRandomNum () throws Exception{
		char[] codeSequence = { '0','1','2','3','4','5','6','7',  '8', '9' }; 

		Random random = new Random();
		// ȡ�����������֤��(4λ����)
		 
		StringBuilder randomCode = new StringBuilder(); 

		 for (int i = 0; i < 4; i++) {    
	            // �õ������������֤�����֡�    
	            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);                
	            // ���������ĸ�����������һ��                
	            randomCode.append(strRand);   
		 }
		return randomCode.toString();
	}
	
	/** ������֤��ͼƬ
	 * @param randomString
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage getVerifyImage(String randomString) throws Exception{
		
		int width=60, height=20;

		// ���ڴ��д���ͼ��
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// ��ȡͼ��������
		Graphics gg = bimage.getGraphics();

		// �趨����ɫ
		gg.setColor(getRandColor(200,250));
		gg.fillRect(0, 0, width, height);

		// �趨����
		gg.setFont(new Font("Times New Roman",Font.PLAIN,18));

		// ���������
		Random random = new Random();

		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		gg.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gg.drawLine(x, y, x + xl, y + yl);
		}

		// ȡ�����������֤��(4λ����)
		char[] randoms = randomString.toCharArray();
		for (int i = 0; i < randoms.length; i++) {
			String rand = String.valueOf(randoms[i]);
			
			// ����֤����ʾ��ͼ����
			int r = 20 + random.nextInt(110);
			int g = 20 + random.nextInt(110);
			int b = 20 + random.nextInt(110);
			gg.setColor(new Color(r, g, b));// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
			gg.drawString(rand, 13 * i + 6, 16);
		}
		// ͼ����Ч
		gg.dispose();
    	
		return bimage;
	}
	
	/** ��������ɫֵ
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {// ������Χ��������ɫ
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		
		Random random = new Random();
		
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}	
}