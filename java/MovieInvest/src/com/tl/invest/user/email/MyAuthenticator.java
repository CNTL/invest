package com.tl.invest.user.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/** 
 * @created 2014年9月18日 下午3:42:25 
 * @author  leijj
 * 类说明 ： 邮件服务器认证
 */

public class MyAuthenticator extends Authenticator{  
    String userName=null;  
    String password=null;  
       
    public MyAuthenticator(){  
    }  
    public MyAuthenticator(String username, String password) {   
        this.userName = username;   
        this.password = password;   
    }   
    protected PasswordAuthentication getPasswordAuthentication(){  
        return new PasswordAuthentication(userName, password);  
    }  
}  
  