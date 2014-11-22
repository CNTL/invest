package com.tl.invest.user.video;

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
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation"})
public class UserVideoManager {
	/**
	 * 根据菜单id获取已发布的消息列表
	 * @param curPage
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public Message getMessageList(int curPage, int length) throws Exception{
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
			List<UserVideo> msgList = queryRecruits(start, length);
			message.setMessages(msgList);
		}
		return message;
	}
	/** 
	* @author  leijj 
	* 功能： 获取条数
	* @return 
	*/ 
	public int getCount(){
		int count = 0;
		String tablename = SysTableLibs.TB_USERVIDEO.getTableCode();
		StringBuilder querySql = new StringBuilder("select count(1) as length from " + tablename);
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
	public List<UserVideo> queryRecruits(int start, int length) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.video.UserVideo as a");
		querySql.append(" order by createTime desc");
		List list = DAOHelper.find(querySql.toString() , start, length);
        if(list.size() > 0)
            return list;
        else
            return null;
        
	}
	
	public void save(UserVideo userVideo) throws Exception{
	     
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(userVideo.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_USERVIDEO.getTableCode());
	    		userVideo.setId(userID);
		        dao.save(userVideo,s);
	    	} else {
	    		dao.update(userVideo,s);
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
	public UserVideo getUserVideoByID(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.video.UserVideo as a where a.id = :id", 
        		new Integer(id), Hibernate.INTEGER);
        if(list.size() > 0)
            return (UserVideo) list.get(0);
        else
            return null;
	}
}