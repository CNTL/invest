package com.tl.common;
 

/**
 * @author wang.yq	
 * @create 2014-9-7 16:24:25
 * 
 * sql�Ƚϱ��ʽ
 * ���ڡ�like��>=��<=��>��<��!=
 */
public enum SqlExpression {
	 
	 // Ϊÿ��enumʵ����Ӳ�ͬ��ʵ�ַ���  
	Equal{  
	       String getInfo() {  
	           return "=";  
	       }  
	   },  
	Like {  
	       String getInfo() {  
	           return "%";  
	       }  
	   },
	   
	Gt {  
	       String getInfo() {  
	           return ">";  
	       }  
	   }
	   ,
	Lt {  
		       String getInfo() {  
		           return "<";  
		       }  
		   };  
	 abstract String getInfo();  

}
