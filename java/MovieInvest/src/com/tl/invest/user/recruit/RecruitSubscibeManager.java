package com.tl.invest.user.recruit;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.DateUtils;
import com.tl.common.ResourceMgr;
import com.tl.db.DBSession;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;


/** 职位订阅
 * @created 2015年2月6日
 * @author  wang.yq
 * 类说明 ： 
 */
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked"})
public class RecruitSubscibeManager {
	
	/** 保存或更新职位订阅
	 * @param subscibe
	 * @throws Exception
	 */
	public void save(RecruitSubscibe subscibe) throws Exception{
	     
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(subscibe.getId() <= 0){
	    		int subid = (int)TBID.getID(SysTableLibs.TB_USERRECRUITSUBSCIBE.getTableCode());
	    		subscibe.setId(subid);
		        dao.save(subscibe,s);
	    	} else {
	    		dao.update(subscibe,s);
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
	
	/** 取消职位订阅
	 * @param id
	 * @throws Exception
	 */
	public void cancle(Integer id) throws Exception{
		if(id>0){
			DAOHelper.delete("delete from com.tl.invest.user.recruit.RecruitSubscibe as r where r.id="+String.valueOf(id));
		}
		
	}
	
	/** 更新职位更新发布时间
	 * @param id
	 * @param postDate
	 */
	public void updatePostTime(String idlist) throws Exception{
		 
		DBSession dbsession = null;
		
		try {
			String tableName = SysTableLibs.TB_USERRECRUITSUBSCIBE.getTableCode() ;
			dbsession = Context.getDBSession();
			String sql = "update "+tableName+" set posttime=? where id in(?)";
			Object[] params = new Object[]{DateUtils.getTimestamp(),idlist};
			dbsession.executeQuery(sql,params);
			 
		} finally {
			ResourceMgr.closeQuietly( dbsession );
		}
		 
	}
}