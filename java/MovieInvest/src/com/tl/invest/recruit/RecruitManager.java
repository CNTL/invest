package com.tl.invest.recruit;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.UserEncrypt;
import com.tl.invest.user.User;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014��11��14�� ����2:07:27 
 * @author  leijj
 * ��˵�� �� 
 */
public class RecruitManager {
	public void save(User user) throws Exception{
	     
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	//����password,���ݿ��д洢��������
            user.setPassword(UserEncrypt.getInstance().encrypt(user.getPassword()));
	    	if(user.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_USER.getTableCode());
		        user.setId(userID);
		        dao.save(user,s);
	    	} else {
	    		dao.update(user,s);
	    	}
	    	t.commit();
        }
        catch (Exception e)
        {
        	if(t != null ) t.rollback();
        	
        	if(e instanceof Exception ) throw (Exception)e;
        	
            throw new Exception("update user error.", e);
        }
        finally
        {
        	dao.closeSession(s);
        }
	}
}