package com.tl.test;


import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.UserEncrypt;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.sys.org.EUID;
import com.tl.sys.org.User;
import com.tl.sys.org.UserManager;

public class UserTest extends TestCase{
	//private Log log = Context.getLog("movieinvest");
	public void testAdd() throws Exception {
		UserManager userManager = (UserManager)Context.getBean(UserManager.class);
		System.out.println(userManager);
		/*
		UserManager userManager = (UserManager)Context.getBean(UserManager.class);
		User user = new User();
		user.setCode("leijj");
		user.setPassword("111111");
		user.setName("张三");
		user.setType(0);
		userManager.create(user);
		*/
		/*DBSession dbSession = null;
		try {
			dbSession = Context.getDBSession();
			dbSession.executeUpdate("insert into user (id, , code, password, name, type) values(?, ?, ?, ?, ?)", 
					new Object[]{1, "leijj","leijj","leijj",0});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbSession!=null)
				try {
					dbSession.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}*/
    }
	private void testCreate() throws Exception{
		User user = new User();
		user.setCode("leijj");
		user.setPassword("111111");
		user.setName("张三");
		user.setType(0);
		
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
   	    String plainText = user.getPassword();
	    try
        {
	        int userID = (int)EUID.getID("UserID");
		    
            user.setId(userID);
            
            s = dao.getSession();
            t = dao.beginTransaction(s);
            
            //数据库中存储的是密文
            dao.save(user,s);
                        
            //加密password
            user.setPassword(UserEncrypt.getInstance().encrypt(plainText));
            
            //commit 
            t.commit();
        }
        catch (Exception e)
        {
        	if(t != null) t.rollback();
        	
        	if(e instanceof Exception) throw (Exception)e;
        	
            throw new Exception("Create User Error.", e);
        }
        finally{
            //user对应继续存储明文
            user.setPassword(plainText);
            
        	dao.closeSession(s);
        }
	}
}
