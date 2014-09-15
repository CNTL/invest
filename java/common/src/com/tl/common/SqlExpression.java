package com.tl.common;
 

/**
 * @author wang.yq	
 * @create 2014-9-7 16:24:25
 * 
 * sql比较表达式
 * 等于、like、>=、<=、>、<、!=
 */
public enum SqlExpression {
	 
	 // 为每个enum实例添加不同的实现方法  
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
