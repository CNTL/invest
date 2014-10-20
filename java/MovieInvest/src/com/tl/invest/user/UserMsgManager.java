package com.tl.invest.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年10月16日 上午11:06:22 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings("deprecation")
public class UserMsgManager {
	protected Log log = Context.getLog("invest");	
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
		
		String sql = "SELECT msg_toID,msg_to,MAX(createTime) as createTime, MAX(id) as id, "
				+ "MAX(msg_content) as msg_content,COUNT(1) as msgNum "
				+ "FROM user_msg WHERE msg_fromID = ? GROUP BY msg_toID,msg_to ORDER BY createTime DESC";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId()});
			while (rs.next()) {
				Map<String, String> oneResult = new HashMap<String, String>();
				oneResult.put("msg_toID", rs.getString("msg_toID"));
				oneResult.put("msg_to", rs.getString("msg_to"));
				oneResult.put("createTime", rs.getString("createTime"));
				oneResult.put("msg_content", rs.getString("msg_content"));
				oneResult.put("userHead", user.getHead());
				oneResult.put("id", rs.getString("id"));
				oneResult.put("msgNum", rs.getString("msgNum"));
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
	public List<Map<String, String>> getTalkList(User user, int msg_toID){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int maxCount = 10;
		
		String sql = "SELECT * FROM user_msg WHERE msg_fromID = ? AND msg_toID = ?";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId(), msg_toID});
			while (rs.next()) {
				Map<String, String> oneResult = new HashMap<String, String>();
				int isRead = rs.getInt("msg_isRead");
				String isReadStr = "否";
				if(isRead == 1) isReadStr = "是";
				oneResult.put("id", rs.getString("id"));
				oneResult.put("msg_fromID", rs.getString("msg_fromID"));
				oneResult.put("msg_from", rs.getString("msg_from"));
				oneResult.put("msg_toID", rs.getString("msg_toID"));
				oneResult.put("msg_to", rs.getString("msg_to"));
				oneResult.put("createTime", rs.getString("createTime"));
				oneResult.put("msg_content", rs.getString("msg_content"));
				oneResult.put("msg_isRead", isReadStr);
				oneResult.put("userHead", user.getHead());
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
	/** 
	* @author  leijj 
	* 功能： 根据id删除站内消息
	* @param id
	* @throws Exception 
	*/ 
	public void delete(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.UserMsg as userMsg where userMsg.id = :id", 
				id, Hibernate.INTEGER);
	}
	
	/** 
	* @author  leijj 
	* 功能： 根据消息接收者删除站内消息
	* @param msg_toID
	* @throws Exception 
	*/ 
	public void deleteByTo(int userID, int msg_toID) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.UserMsg as userMsg where userMsg.msg_fromID = :msg_fromID and userMsg.msg_toID = :msg_toID", 
				 new String[]{"msg_fromID", "msg_toID"}, new Object[]{userID, msg_toID}, 
				 new Type[]{Hibernate.INTEGER, Hibernate.INTEGER});
	}
}
