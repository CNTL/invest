package com.tl.common;
/** 
 * @created 2014��11��18�� ����6:24:15 
 * @author  leijj
 * ��˵�� �� 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class RandomValidateCode {
 
    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//�ŵ�session�е�key
    private Random random = new Random();
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//����������ַ���
     
    private int width = 80;//ͼƬ��
    private int height = 26;//ͼƬ��
    private int lineSize = 25;//����������
    private int stringNum = 4;//��������ַ�����
     
    /**
     * �������ͼƬ
     */
    public String getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(180, 233));
     
    
        //���Ƹ�����
        for(int i=0;i<=lineSize;i++){
            drowLine(g);
        }
        
        //��������ַ�
        String randomString = "";
        for(int i=1;i<=stringNum;i++){
            randomString=drowString(g,randomString,i);
        }
 
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        g.dispose();
        try {
        	String rootPath = WebUtil.getPath(request);
        	ImageIO.write(image, "JPEG", new File(rootPath.concat("userout/img/validate.png")));  
            //ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return randomString;
    }
     
    // ------------- private methods -------------
     
    /*
     * �������
     */
    private Font getFont(){
        return new Font("Fixedsys",Font.CENTER_BASELINE,18);
    }
     
    /*
     * �����ɫ
     */
    private Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
 
    /*
     * �����ַ���
     */
    private String drowString(Graphics g,String randomString,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13*i, 16);
        return randomString;
    }
     
    /*
     * ���Ƹ�����
     */
    private void drowLine(Graphics g){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);
    }
     
    /*
     * ��ȡ������ַ�
     */
    private String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }
}