package com.tl.invest.user;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014��9��6�� ����4:55:33 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class BankcardManager {
	/**
	 * ����û�
	 * 
	 * @param user  Ҫ�������û�����
	 */
	public void save(Bankcard bankcard) throws Exception{
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(bankcard.getId() <= 0){
	    		int id = (int)TBID.getID(SysTableLibs.TB_BANKCARD.getTableCode());;
	    		bankcard.setId(id);
		        dao.save(bankcard,s);
	    	} else {
	    		dao.update(bankcard,s);
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
	
	/**
	 * �����û�id��ȡ��֤���п�
	 * 
	 * @param userId    �û�id
	 */
	public List<Bankcard> getByUserId(int userId) throws Exception{
        List list = DAOHelper.find("select a from com.tl.invest.user.Bankcard as a where a.userId = :userId", 
        		userId, Hibernate.INTEGER);
        if(list.size() > 0)
            return list;
        else
            return null;
	}
}
