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
 * ��¼��֤��������
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

		// ���ڴ��д���ͼ��
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// ��ȡͼ��������
		Graphics gg = bimage.getGraphics();

		// �趨����ɫ
		gg.setColor(getRandColor(200,250));
		gg.fillRect(0, 0, width, height);

		// �趨����
		gg.setFont(new Font("Times New Roman",Font.PLAIN,18));

		//���߿�
		//g.setColor(new Color());
		//g.drawRect(0,0,width-1,height-1);

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
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// ����֤����ʾ��ͼ����
			int r = 20 + random.nextInt(110);
			int g = 20 + random.nextInt(110);
			int b = 20 + random.nextInt(110);
			gg.setColor(new Color(r, g, b));// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
			gg.drawString(rand, 13 * i + 6, 16);
		}
		// ͼ����Ч
		gg.dispose();

		// ����֤�����SESSION
		HttpSession session = request.getSession();
		session.setAttribute("rand", sRand);

		// ���ͼ��ҳ��
		response.setContentType("image/jpeg");		
		response.setHeader("Cache-Control","no-store");
    	response.setHeader("Pragma","no-cache");
    	
		ServletOutputStream out = response.getOutputStream();		
		ImageIO.write(bimage, "JPEG", out);
	}

	Color getRandColor(int fc, int bc) {// ������Χ��������ɫ
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		
		Random random = new Random();
		
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}	
}
