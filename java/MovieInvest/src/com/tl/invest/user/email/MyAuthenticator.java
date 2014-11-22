package com.tl.invest.user.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/** 
 * @created 2014��9��18�� ����3:42:25 
 * @author  leijj
 * ��˵�� �� �ʼ���������֤
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
  