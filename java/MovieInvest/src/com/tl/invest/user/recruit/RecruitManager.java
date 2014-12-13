package com.tl.invest.user.recruit;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.Message;
import com.tl.common.ResourceMgr;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年11月14日 下午2:07:27 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "rawtypes", "deprecation"})
public class RecruitManager {
	/**
	 * 根据菜单id获取已发布的消息列表
	 * @param openID
	 * @param menuID
	 * @param curPage
	 * @param length
	 * @return
	 * @throws Exception
	 */
	/*public Message getMessageList(int curPage, int length) throws Exception{
		if(curPage == 0) curPage = 1;//下标是0时是第一页
		
		Message message = new Message();
		message.setCurPage(curPage);
		message.setLength(length);
		
		int count = getCount();
		if (count == 0){
			message.setTotal(0);
			message.setPageCount(1);
		} else{
			message.setTotal(count);
			int pageCount = 0;
			if((count%length) == 0)
				pageCount = (count/length);
			else
				pageCount = (count/length) + 1;
			
			message.setPageCount(pageCount);
			int start = (curPage - 1) * length;
			List<UserRecruit> msgList = queryRecruits(start, length, null, null);
			message.setMessages(msgList);
		}
		return message;
	}*/
	/** 
	* @author  leijj 
	* 功能： 获取招聘信息条数
	* @return 
	*/ 
	public int getCount(){
		String tablename = SysTableLibs.TB_USERRECRUIT.getTableCode();
		int count = 0;
		StringBuilder querySql = new StringBuilder("select count(1) as length from ").append(tablename);
		DBSession dbsession = null;
		IResultSet rs = null;
		try {
			dbsession = Context.getDBSession();
			rs = dbsession.executeQuery(querySql.toString(), null );
			while(rs.next()){
				count = rs.getInt("length");
				break;
			}
		} catch(Exception e){
		} finally {
			ResourceMgr.closeQuietly( dbsession );
		}
		return count;
	}
	public Message queryRecruits(int start, int length, String typeFlag, Integer userId) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a");
		if("edit".equals(typeFlag)){//管理我的职位，则增加当前用户查询条件
			querySql.append(" where a.userId=").append(userId);
		}
		querySql.append(" order by createTime desc");
		List list = DAOHelper.find(querySql.toString() , start, length);
        if(list.size() > 0){
        	Message message = new Message();
        	//int pageCount = (total/length == 0 ? total/length : (total/length + 1));
			message.setCurPage(start + 1);
			message.setLength(length);
			message.setMessages(list);
			//message.setPageCount(pageCount);
			//message.setTotal(total);
            return message;
        }
        else
            return null;
        
	}
	public Message queryHot(int start, int length, String typeFlag, Integer userId) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a");
		if("edit".equals(typeFlag)){//管理我的简历，则增加当前用户查询条件
			querySql.append(" where a.userId=").append(userId);
		}
		querySql.append(" order by resumeNum desc");
		List list = DAOHelper.find(querySql.toString() , start, length);
		if(list.size() > 0){
        	Message message = new Message();
        	//int pageCount = (total/length == 0 ? total/length : (total/length + 1));
			message.setCurPage(start + 1);
			message.setLength(length);
			message.setMessages(list);
			//message.setPageCount(pageCount);
			//message.setTotal(total);
            return message;
        }
        else
            return null;
        
	}
	public void save(UserRecruit recruit) throws Exception{
	     
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(recruit.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_USERRECRUIT.getTableCode());
	    		recruit.setId(userID);
		        dao.save(recruit,s);
	    	} else {
	    		dao.update(recruit,s);
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
	 * 根据ID获取影聘信息
	 * 
	 * @param ID
	 */
	public UserRecruit getRecruitByID(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.recruit.UserRecruit as a where a.id = :id", 
        		new Integer(id), Hibernate.INTEGER);
        if(list.size() > 0)
            return (UserRecruit) list.get(0);
        else
            return null;
	}
}