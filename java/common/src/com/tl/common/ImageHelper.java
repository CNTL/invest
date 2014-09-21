package com.tl.common;
/** 
 * @created 2014��9��6�� ����10:41:00 
 * @author  leijj
 * ��˵�� �� 
 */

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @author Ren Yanfang
 * 2014-7-3
 */
public class ImageHelper {
	/** *//** 
     * ��ͼƬ�ü������Ѳü��군��ͼƬ���� ��
     */
    public static void cut(HeadImage headImage) throws IOException{ 
        FileInputStream is = null ;
        ImageInputStream iis =null ;
        String srcPath = headImage.getSrcPath();
        String subPath=headImage.getSubPath();
        int x=headImage.getX();
        int y=headImage.getY();
        int width=headImage.getWidth();
        int height=headImage.getHeight();
        String fileExt="jpg";
        try{   
            //��ȡͼƬ�ļ�
            is = new FileInputStream(srcPath); 
            //�õ��ļ���չ��
            if (srcPath.lastIndexOf(".") >= 0) 
				fileExt = srcPath.substring(srcPath.lastIndexOf(".")+1);
            /**//*
             * ���ذ������е�ǰ��ע�� ImageReader �� Iterator����Щ ImageReader 
             * �����ܹ�����ָ����ʽ�� ������formatName - ��������ʽ��ʽ���� .
             *������ "jpeg" �� "tiff"���� �� 
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileExt);  
            ImageReader reader = it.next(); 
            //��ȡͼƬ�� 
            iis = ImageIO.createImageInputStream(is);
               
            /**//* 
             * <p>iis:��ȡԴ.true:ֻ��ǰ���� </p>.�������Ϊ ��ֻ��ǰ��������
             * ��������ζ�Ű���������Դ�е�ͼ��ֻ��˳���ȡ���������� reader
             *  ���⻺���������ǰ�Ѿ���ȡ��ͼ����������ݵ���Щ���벿�֡�
             */
            reader.setInput(iis,true) ;
            
            /**//* 
             * <p>������ζ������н������<p>.����ָ�����������ʱ�� Java Image I/O 
             * ��ܵ��������е���ת��һ��ͼ���һ��ͼ�������ض�ͼ���ʽ�Ĳ��
             * ������ ImageReader ʵ�ֵ� getDefaultReadParam �����з��� 
             * ImageReadParam ��ʵ����  
             */
            ImageReadParam param = reader.getDefaultReadParam(); 
             
            /**//*
             * ͼƬ�ü�����Rectangle ָ��������ռ��е�һ������ͨ�� Rectangle ����
             * �����϶�������꣨x��y������Ⱥ͸߶ȿ��Զ���������� 
             */ 
            if(x<0)
            	x=0;
            if(y<0)
            	y=0;
            Rectangle rect = new Rectangle(x, y, width, height); 
            
              
            //�ṩһ�� BufferedImage���������������������ݵ�Ŀ�ꡣ 
            param.setSourceRegion(rect); 

            /**//*
             * ʹ�����ṩ�� ImageReadParam ��ȡͨ������ imageIndex ָ���Ķ��󣬲���
             * ����Ϊһ�������� BufferedImage ���ء�
             */
            BufferedImage bi = reader.read(0,param);                
      
            //������ͼƬ 
            ImageIO.write(bi, fileExt, new File(subPath));     
        }
        finally{
            if(is!=null)
               is.close() ;       
            if(iis!=null)
               iis.close();  
        } 
    }
    
    public static void ZoomTheImage(String fileUrl,String newUrl,int width,int height){
		java.io.File file = new java.io.File(fileUrl);         //����ղ��ϴ����ļ�
	    Image src = null;
		try {
			src = javax.imageio.ImageIO.read(file);
			//����Image����
		    BufferedImage tag = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
   
		    tag.getGraphics().drawImage(src,0,0,width,height,null);        //������С���ͼ
			//FileOutputStream newimage=new FileOutputStream(newUrl);           //������ļ���
			//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage); 
			//encoder.encode(tag);                                                //��JPEG����
			
			String formatName = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
			ImageIO.write(tag, /*"GIF"*/ formatName /* format desired */ , new File(newUrl) /* target */ );  
			
			//newimage.close();  
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}