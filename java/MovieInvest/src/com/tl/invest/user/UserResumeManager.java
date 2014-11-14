package com.tl.invest.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年11月2日 下午10:41:26 
 * @author  leijj
 * 类说明 ： 
 */
public class UserResumeManager {
	protected Log log = Context.getLog("invest");
	/**
	 * 保存用户简历
	 * 
	 * @param resume  要创建的用户简历对象
	 */
	public void saveResume(UserResume resume) throws Exception{
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(resume.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_USERRESUME.getTableCode());
	    		resume.setId(userID);
		        dao.save(resume,s);
	    	} else {
	    		dao.update(resume,s);
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
	public List<Map<String, String>> getMyResumes(User user){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int maxCount = 10;
		String sql = "SELECT id, userId, userName, name, content, affix, modifyTime FROM user_resume ORDER BY modifyTime DESC";
		DBSession conn = null;
		IResultSet rs = null;
		IResultSet numRs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId(), user.getId()});
			while (rs.next()) {
				Map<String, String> oneResult = new HashMap<String, String>();
				oneResult.put("id", rs.getString("id"));
				oneResult.put("userId", rs.getString("userId"));
				oneResult.put("userName", rs.getString("userName"));
				oneResult.put("name", rs.getString("name"));
				oneResult.put("content", rs.getString("content"));
				oneResult.put("affix", rs.getString("affix"));
				oneResult.put("modifyTime", rs.getString("modifyTime"));
				oneResult.put("userHead", user.getHead());
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
}