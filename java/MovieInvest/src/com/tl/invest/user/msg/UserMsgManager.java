package com.tl.invest.user.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.tl.common.DateUtils;
import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;

/** 
 * @created 2014年10月16日 上午11:06:22 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings("deprecation")
public class UserMsgManager {
	protected Log log = Context.getLog("invest");	
	UserManager userManager = (UserManager) Context.getBean(UserManager.class);
	public void save(UserMsg userMsg) throws Exception{
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(userMsg.getId() <= 0){
	    		int id = (int)TBID.getID(SysTableLibs.TB_USERMSG.getTableCode());;
	    		userMsg.setId(id);
		        dao.save(userMsg,s);
	    	} else {
	    		dao.update(userMsg,s);
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
	public List<Map<String, String>> getMyMsgs(User user){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		//StringBuilder result = new StringBuilder();
		int maxCount = 10;
	
		String tablename = SysTableLibs.TB_USERMSG.getTableCode();
		String sql = "SELECT *  FROM "
				+ tablename + " WHERE (msg_toID=? or msg_fromID=?)  GROUP BY msg_fromID ORDER BY createTime DESC";
		DBSession conn = null;
		IResultSet rs = null;
		IResultSet numRs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId(),user.getId()});
			while (rs.next()) {
				User fromUser = userManager.getUserByID( rs.getInt("msg_fromID")) ;
				User toUser = userManager.getUserByID( rs.getInt("msg_toID")) ;
				
				Map<String, String> oneResult = new HashMap<String, String>();
				oneResult.put("msg_from", rs.getString("msg_from"));
				oneResult.put("msg_fromID", rs.getString("msg_fromID"));
				oneResult.put("msg_fromHead", fromUser.getHead());
				oneResult.put("msg_to", rs.getString("msg_to"));
				oneResult.put("msg_toID", rs.getString("msg_toID"));
				oneResult.put("msg_toHead", toUser.getHead());
				oneResult.put("msg_createTime", DateUtils.format(rs.getTimestamp("createTime"), "yyyy-MM-dd HH:mm:ss"));
				oneResult.put("msg_content", rs.getString("msg_content"));
				oneResult.put("msg_isread", rs.getString("msg_isRead"));
				
				oneResult.put("id", rs.getString("id"));
				String countSql = "SELECT COUNT(1) AS msgNum FROM " + tablename
						+ " WHERE (msg_fromID = ? and msg_toID = ?) or (msg_toID = ? and msg_fromID = ?)";
				numRs = conn.executeQuery(countSql, new Object[]{user.getId(), rs.getString("msg_toID"), user.getId(), rs.getString("msg_toID")});
				while (numRs.next()) {
					oneResult.put("msgNum", numRs.getString("msgNum"));
				}
				result.add(oneResult);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(numRs);
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		return result;
	}
	
	/**得到当前用户的消息会话总数
	 * @return
	 * @throws TLException 
	 */
	public Integer getMyMsgsCount(int UserID) throws TLException{
		 
		int count = 0;
	
		String tablename = SysTableLibs.TB_USERMSG.getTableCode();
		
		String sql = " SELECT max(`id`) as id,maxId,minId,msg_fromID,msg_toID FROM (SELECT `id`,IF(msg_fromID>msg_toID,msg_fromID,msg_toID) AS maxId,IF(msg_fromID>msg_toID,msg_toID,msg_fromID) AS minId,msg_fromID,msg_toID FROM "+tablename+" where (msg_toID=? or msg_fromID=?)) AS `tmp` GROUP BY maxId,minId";
			 
		count = getMaxSqlCount(sql, new Object[]{UserID,UserID},null);
		return count;
	}
	public int getMaxSqlCount(String sql,Object[] params,DBSession db) throws TLException{
		int count =0;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		IResultSet rs = null;
		try{
			//dbSession = Context.getDBSession();
			rs = db.executeQuery(sql,params);
			 
			if(rs.next()){
				count++;
			}
				
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		return count;
	}
	public int getSqlCount(String sql,Object[] params,DBSession db) throws TLException{
		int count =0;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		IResultSet rs = null;
		try{
			//dbSession = Context.getDBSession();
			rs = db.executeQuery(sql,params);
			if(rs.next())
				count = rs.getInt(1);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		return count;
	}
	public List<Map<String, String>> getMyMsgs(User user,Integer pageIndex){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		//StringBuilder result = new StringBuilder();
		int maxCount = 10;
	    int start = (pageIndex -1)*10;
		String tablename = SysTableLibs.TB_USERMSG.getTableCode();
		//先排序后分组
		//select * from (select * from user_msg WHERE msg_toID=41 or msg_fromID=41 ORDER BY createTime DESC) as t GROUP BY t.msg_fromID ORDER BY t.createTime DESC
	
		String sql = "SELECT max(`id`) as id FROM (SELECT id,IF(msg_fromID>msg_toID,msg_fromID,msg_toID) AS maxId,IF(msg_fromID>msg_toID,msg_toID,msg_fromID) AS minId FROM "+tablename+" where (msg_toID=? or msg_fromID=?)) AS `tmp` GROUP BY maxId,minId";
		DBSession conn = null;
		IResultSet rs = null;
		IResultSet numRs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, start, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId(),user.getId()});
			while (rs.next()) {
				UserMsg msg = getMsgByID(rs.getInt("id"));
				 
				User fromUser = userManager.getUserByID(msg.getMsgFromId()) ;
				User toUser = userManager.getUserByID( msg.getMsgToId()) ;
				
				Map<String, String> oneResult = new HashMap<String, String>();
				oneResult.put("msg_from",msg.getMsgFrom());
				oneResult.put("msg_fromID", String.valueOf(msg.getMsgFromId()));
				oneResult.put("msg_fromHead", fromUser.getHead());
				oneResult.put("msg_to",msg.getMsgTo());
				oneResult.put("msg_toID", String.valueOf(msg.getMsgToId()));
				oneResult.put("msg_toHead", toUser.getHead());
				oneResult.put("msg_createTime", DateUtils.format(msg.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				oneResult.put("msg_content", msg.getMsgContent());
				oneResult.put("msg_isread", String.valueOf(msg.getMsgIsRead()));
				
				oneResult.put("id", String.valueOf(msg.getId()));
//				String countSql = "SELECT COUNT(1) AS msgNum FROM " + tablename
//						+ " WHERE (msg_fromID = ? and msg_toID = ?) or (msg_toID = ? and msg_fromID = ?)";
//				numRs = conn.executeQuery(countSql, new Object[]{user.getId(), rs.getString("msg_toID"), user.getId(), rs.getString("msg_toID")});
//				while (numRs.next()) {
//					oneResult.put("msgNum", numRs.getString("msgNum"));
//				}
				result.add(oneResult);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(numRs);
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		return result;
	}
	
	public UserMsg getMsgByID(Integer msgid) throws Exception{
		
		List list = DAOHelper.find("select a from com.tl.invest.user.msg.UserMsg as a where a.id = :id", 
        		new Integer(msgid), Hibernate.INTEGER);
        if(list.size() > 0)
            return (UserMsg) list.get(0);
        else
            return null;
	}
 
	public List<Map<String, String>> getTalkList(User user, int msg_toID){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		String tablename = SysTableLibs.TB_USERMSG.getTableCode();
		String sql = "SELECT * FROM " + tablename + " WHERE (msg_fromID = ? and msg_toID=?) or ( msg_toID=? and msg_fromID = ?) ORDER BY createTime ASC";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId(), msg_toID, user.getId(), msg_toID});
			while (rs.next()) {
				Map<String, String> oneResult = new HashMap<String, String>();
				User fromUser = userManager.getUserByID( rs.getInt("msg_fromID")) ;
				User toUser = userManager.getUserByID( rs.getInt("msg_toID")) ;
	
				oneResult.put("msg_from", rs.getString("msg_from"));
				oneResult.put("msg_fromID", rs.getString("msg_fromID"));
				oneResult.put("msg_fromHead", fromUser.getHead());
				oneResult.put("msg_fromIntro", fromUser.getIntro());
				oneResult.put("msg_to", rs.getString("msg_to"));
				oneResult.put("msg_toID", rs.getString("msg_toID"));
				oneResult.put("msg_toHead", toUser.getHead());
				oneResult.put("msg_toIntro", toUser.getIntro());
				oneResult.put("msg_createTime", DateUtils.format(rs.getTimestamp("createTime"), "yyyy-MM-dd HH:mm:ss"));
				oneResult.put("msg_content", rs.getString("msg_content"));
				oneResult.put("msg_isread", rs.getString("msg_isRead"));
				oneResult.put("id", rs.getString("id"));
				result.add(oneResult);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		return result;
	}
	
	 
	
    /**阅读消息
     * @param fromID
     * @param toID
     */
    public void readMsg(Integer fromID,Integer toID)throws Exception{
    	String tablename = SysTableLibs.TB_USERMSG.getTableCode();
    	String sqlString = "update "+tablename+" set msg_isRead=1 where (msg_fromID =? and msg_toID=? ) or ( msg_toID=? and msg_fromID = ?)";
    	DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	conn.executeUpdate(sqlString, new Object[]{fromID, toID, fromID, toID});
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
    }
    
    /**阅读消息
     * @param fromID
     * @param toID
     */
    public void readMsgToMe(Integer fromID,Integer toID)throws Exception{
    	String tablename = SysTableLibs.TB_USERMSG.getTableCode();
    	String sqlString = "update "+tablename+" set msg_isRead=1 where msg_fromID =? and msg_toID=?  ";
    	DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	conn.executeUpdate(sqlString, new Object[]{fromID, toID});
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
    }
    
    /**设置所有消息为已读
     * @param userID
     * @throws Exception
     */
    public void readMsgAll(Integer userID)throws Exception{
    	String tablename = SysTableLibs.TB_USERMSG.getTableCode();
    	String sqlString = "update "+tablename+" set msg_isRead=1 where  msg_toID=? ";
    	DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	conn.executeUpdate(sqlString, new Object[]{userID});
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
    }
	/** 
	* @author  leijj 
	* 功能： 根据id删除站内消息
	* @param id
	* @throws Exception 
	*/ 
	public void delete(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.msg.UserMsg as userMsg where userMsg.id = :id", 
				id, Hibernate.INTEGER);
	}
	
	/** 
	* @author  leijj 
	* 功能： 根据消息接收者删除站内消息
	* @param msg_toID
	* @throws Exception 
	*/ 
	public void deleteByTo(int userID, int msg_toID) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.msg.UserMsg as userMsg where userMsg.msgFromId = :msgFromId and userMsg.msgFromId = :msgFromId", 
				 new String[]{"msgFromId", "msgFromId"}, new Object[]{userID, msg_toID}, 
				 new Type[]{Hibernate.INTEGER, Hibernate.INTEGER});
	}
}
