package com.tl.common;
/** 
 * @created 2014��9��6�� ����10:41:31 
 * @author  leijj
 * ��˵�� �� 
 */
public class HeadImage {
	//===ԴͼƬ·��������:c:\1.jpg 
    private String srcPath ;
         
    //===����ͼƬ���·������.��:c:\2.jpg
    private String subPath ;
    
    //===���е�x����
    private int x ;
    
    private int y ;    
      
    //===���е���
    private int width ;
     
    private int height ;
    
    public HeadImage(){
            
    }  
    public HeadImage(int x,int y,int width,int height){
         this.x = x ;
         this.y = y ;
         this.width = width ;   
         this.height = height ;
    }
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcPath(){
        return srcPath;
    }

    public void setSrcPath(String srcPath){
        this.srcPath = srcPath;
    }

    public String getSubPath(){
        return subPath;
    }

    public void setSubPath(String subPath){
        this.subPath = subPath;
    }

    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    } 
}