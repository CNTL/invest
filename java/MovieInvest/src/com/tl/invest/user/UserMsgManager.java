package com.tl.invest.user;

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
	public String getMyMsgs(User user){
		StringBuilder result = new StringBuilder();
		result.append("[");
		int i = 0;
		int maxCount = 10;
		
		String sql = "SELECT msg_toID,msg_to,MAX(createTime) as createTime, MAX(id) as id, "
				+ "MAX(msg_content) as msg_content,COUNT(1) as msgNum "
				+ "FROM user_msg WHERE msg_fromID = ? GROUP BY msg_toID,msg_to ";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId()});
			while (rs.next()) {
				if (i++ > 0) result.append(",");
				result.append("{msg_toID:\"").append(rs.getInt("msg_toID"))
					.append("\",msg_to:\"").append(rs.getString("msg_to"))
					.append("\",createTime:\"").append(rs.getInt("createTime"))
					.append("\",msg_content:\"").append(rs.getString("msg_content"))
					.append("\",userHead:\"").append(user.getHead())
					.append("\",id:\"").append(rs.getInt("id"))
					.append("\",msgNum:\"").append(rs.getInt("msgNum"))
					.append("\"}");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		result.append("]");
		return result.toString();
	}
	public String getTalkList(User user, int msg_toID){
		StringBuilder result = new StringBuilder();
		result.append("[");
		int i = 0;
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
				if (i++ > 0) result.append(",");
				int isRead = rs.getInt("msg_isRead");
				String isReadStr = "否";
				if(isRead == 1) isReadStr = "是";
				result.append("{id:\"").append(rs.getInt("id"))
					.append("\",msg_fromID:\"").append(rs.getInt("msg_fromID"))
					.append("\",msg_from:\"").append(rs.getString("msg_from"))
					.append("\",msg_toID:\"").append(rs.getInt("msg_toID"))
					.append("\",msg_to:\"").append(rs.getString("msg_to"))
					.append("\",msg_content:\"").append(rs.getString("msg_content"))
					.append("\",msg_isRead:\"").append(isReadStr)
					.append("\",createTime:\"").append(rs.getString("createTime"))
					.append("\",userHead:\"").append(user.getHead())
					.append("\"}");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		result.append("]");
		return result.toString();
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
