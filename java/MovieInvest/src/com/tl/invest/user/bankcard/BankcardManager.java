package com.tl.invest.user.bankcard;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年9月6日 下午4:55:33 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class BankcardManager {
	/**
	 * 添加用户
	 * 
	 * @param user  要创建的用户对象
	 */
	public void save(UserBankcard bankcard) throws Exception{
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
	 * 根据用户id获取认证银行卡
	 * 
	 * @param userId    用户id
	 */
	public List<UserBankcard> getByUserId(int userId) throws Exception{
        List list = DAOHelper.find("select a from com.tl.invest.user.bankcard.UserBankcard as a where a.userId = :userId", 
        		userId, Hibernate.INTEGER);
        if(list.size() > 0)
            return list;
        else
            return null;
	}
}
