package com.tl.invest.user.recruit;

import java.util.List;

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


/** ְλ����
 * @created 2015��2��6��
 * @author  wang.yq
 * ��˵�� �� 
 */
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked"})
public class RecruitSubscibeManager {
	
	/** ��������ְλ����
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
	
	/** ȡ��ְλ����
	 * @param id
	 * @throws Exception
	 */
	public void cancle(Integer id) throws Exception{
		if(id>0){
			DAOHelper.delete("delete from com.tl.invest.user.recruit.RecruitSubscibe as r where r.id="+String.valueOf(id));
		}
		
	}
	
	/** ����ְλ���·���ʱ��
	 * @param id
	 * @param postDate
	 */
	public void updatePostTime(String idlist) throws Exception{
		 
		DBSession dbsession = null;
		
		try {
			String tableName = SysTableLibs.TB_USERRECRUITSUBSCIBE.getTableCode() ;
			dbsession = Context.getDBSession();
			String sql = "update user_subscibe set posttime=? where id in(?)";
			Object[] params = new Object[]{DateUtils.getTimestamp(),idlist};
			dbsession.executeUpdate(sql,params);
			 
		} finally {
			ResourceMgr.closeQuietly( dbsession );
		}
		 
	}
	
	/** ���Ƶ�ʷ�Χ��ְλ�����б�
	 * @return
	 * @throws Exception
	 */
	public RecruitSubscibe[] queryRecruitSubscibe() throws Exception{
		try {
			StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.RecruitSubscibe as a where  deleted=0 and (datediff(NOW(),a.posttime)=a.rate) ");
			List<RecruitSubscibe> list = DAOHelper.find(querySql.toString());
			if(list == null || list.size() == 0) return null;
			return list.toArray(new RecruitSubscibe[0]);
		} catch (Exception e) {
			 
			return null;
		}
		
	}
}