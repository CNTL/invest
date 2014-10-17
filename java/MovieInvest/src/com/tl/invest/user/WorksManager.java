package com.tl.invest.user;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年9月6日 下午4:55:33 
 * @author  leijj
 * 类说明 ： 
 */
public class WorksManager {
	/**
	 * 添加用户
	 * 
	 * @param user  要创建的用户对象
	 */
	public void save(Works works) throws Exception{
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(works.getId() <= 0){
	    		int id = (int)TBID.getID(SysTableLibs.TB_WORKS.getTableCode());
	    		works.setId(id);
		        dao.save(works,s);
	    	} else {
	    		dao.update(works,s);
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
