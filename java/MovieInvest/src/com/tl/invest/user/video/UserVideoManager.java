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
	public List<UserVideogroup> getVideoGroups(int userID) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.video.UserVideogroup as a");
		querySql.append(" where a.userId=" + userID);
		querySql.append(" order by a.createTime desc");
		//List list = DAOHelper.find(querySql.toString() , start, length);
		List<UserVideogroup> list = DAOHelper.find(querySql.toString());
        return list;
	}
	
	public UserVideogroup getGroupInfo(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.video.UserVideogroup as a where a.id = :id", 
        		new Integer(id), Hibernate.INTEGER);
        return (UserVideogroup) list.get(0);
	}
	
	/** 
	* @author  leijj 
	* 功能： 创建视频图册
	* @param photogroup
	* @throws Exception 
	*/ 
	public int saveVideoGroup(UserVideogroup videogroup) throws Exception{
		int id = 0;
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(videogroup.getId() <= 0){
	    		id = (int)TBID.getID(SysTableLibs.TB_USERVIDEOGROUP.getTableCode());
	    		videogroup.setId(id);
		        dao.save(videogroup,s);
	    	} else {
	    		dao.update(videogroup,s);
	    		id = videogroup.getId();
	    	}
	    	t.commit();
        }
        catch (Exception e)
        {
        	if(t != null ) t.rollback();
        	
        	if(e instanceof Exception ) throw (Exception)e;
        	
            throw new Exception("update UserPhotogroup error.", e);
        }
        finally
        {
        	dao.closeSession(s);
        }
	    return id;
	}
	/** 
	* @author  leijj 
	* 功能： 删除图册
	* @param id
	* @throws Exception 
	*/ 
	public void delVideoGroup(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.video.UserVideogroup as a where a.id = :id", 
        		   new Integer(id), Hibernate.INTEGER);
	}
	

	public List<UserVideo> getVideos(int groupID) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.video.UserVideo as a");
		querySql.append(" where a.groupId=" + groupID);
		querySql.append(" order by a.createTime desc");
		//List list = DAOHelper.find(querySql.toString() , start, length);
		List<UserVideo> list = DAOHelper.find(querySql.toString());
        return list;
	}
	public UserVideogroup getVideoInfo(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.video.UserVideo as a where a.id = :id", 
        		new Integer(id), Hibernate.INTEGER);
        return (UserVideogroup) list.get(0);
	}
	public int saveVideo(UserVideo userVideo) throws Exception{
		int id = 0;
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(userVideo.getId() <= 0){
	    		id = (int)TBID.getID(SysTableLibs.TB_USERVIDEO.getTableCode());
	    		userVideo.setId(id);
		        dao.save(userVideo,s);
	    	} else {
	    		dao.update(userVideo,s);
	    		id = userVideo.getId();
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
	    return id;
	}
	
	/** 
	* @author  leijj 
	* 功能： 删除图册
	* @param id
	* @throws Exception 
	*/ 
	public void delVideo(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.video.UserVideo as a where a.id = :id", 
        		   new Integer(id), Hibernate.INTEGER);
	}
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
}