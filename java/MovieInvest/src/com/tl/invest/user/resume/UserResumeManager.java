package com.tl.invest.user.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;

/** 
 * @created 2014��11��2�� ����10:41:26 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "deprecation", "rawtypes"})
public class UserResumeManager {
	protected Log log = Context.getLog("invest");
	/**
	 * �����û�����
	 * 
	 * @param resume  Ҫ�������û���������
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
	public Message getMyResumes(int start, int length, String typeFlag, User user){
		List<Map<String, String>> list = getMyResumes(user);
		Message message = new Message();
    	//int pageCount = (total/length == 0 ? total/length : (total/length + 1));
		message.setCurPage(start + 1);
		message.setLength(length);
		message.setMessages(list);
		//message.setPageCount(pageCount);
		//message.setTotal(total);
        return message;
	}
	public List<Map<String, String>> getMyResumes(User user){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int maxCount = 10;
		String tablename = SysTableLibs.TB_USERRESUME.getTableCode();
		String sql = "SELECT id, userId, userName, name, content, affix, createTime FROM " + tablename 
				+ " where userId=? ORDER BY createTime DESC";
		DBSession conn = null;
		IResultSet rs = null;
		IResultSet numRs = null;
		try {
	    	conn = Context.getDBSession();
	    	//�����ݿ����ͻ�ò�ͬ�Ĳ�ѯ�����������
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{user.getId()});
			while (rs.next()) {
				Map<String, String> oneResult = new HashMap<String, String>();
				oneResult.put("id", ParamInitUtils.getString(rs.getString("id")));
				oneResult.put("userId", ParamInitUtils.getString(rs.getString("userId")));
				oneResult.put("userName", ParamInitUtils.getString(rs.getString("userName")));
				oneResult.put("name", ParamInitUtils.getString(rs.getString("name")));
				oneResult.put("content", ParamInitUtils.getString(rs.getString("content")));
				oneResult.put("affix", ParamInitUtils.getString(rs.getString("affix")));
				oneResult.put("createTime", ParamInitUtils.getString(rs.getString("createTime")));
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
	/**
	 * ����ID��ȡ������Ϣ
	 * 
	 * @param userID    �û�ID
	 */
	public UserResume curResume(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.resume.UserResume as a where a.id = :id", 
        		new Integer(id), Hibernate.INTEGER);
        if(list.size() > 0)
            return (UserResume) list.get(0);
        else
            return null;
	}
	public void delete(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.resume.UserResume as resume where resume.id = :id", 
        		   new Integer(id), Hibernate.INTEGER);
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ���ղص�ְλ
	* @param curPage
	* @param length
	* @param userId
	* @return
	* @throws Exception 
	*/ 
	public Message getMyRecruits(int curPage, int length, Integer userId) throws Exception{
		String sql = "SELECT rt.* FROM user_recruit rt,user_recruitresume rr WHERE rt.id=rr.recruitID and rr.userId=?";
		Object[] params = new Object[]{userId};
		List<UserRecruit> myRecruits =  recruitManager.getRecruits(sql, params, length, curPage, null);
		int total = getMyRecruitsCount(userId, null);
		return recruitManager.setMessage(myRecruits, curPage, length, total);
	} 
	public int getMyRecruitsCount(int userID,DBSession db) throws TLException{
		String sql = "select count(rt.id) FROM user_recruit rt,user_recruitresume rr WHERE rt.id=rr.recruitID and rr.userId=?";
		Object[] params = new Object[]{userID};
		return recruitManager.getSqlCount(sql,params,db);
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ����Ͷ�ݼ�����ְλ
	* @param curPage
	* @param length
	* @param userId
	* @return
	* @throws Exception 
	*/ 
	public Message getMyResumeRecruits(int curPage, int length, Integer userId) throws Exception{
		String sql = "SELECT rt.* FROM user_recruit rt,user_recruitresume rr, user_resume rs WHERE rt.id=rr.recruitID AND rs.id=rr.resumeID and rr.userId=?";
		Object[] params = new Object[]{userId};
		List<UserRecruit> myRecruits =  recruitManager.getRecruits(sql, params, length, curPage, null);
		int total = getMyResumeRecruitCount(userId, null);
		return recruitManager.setMessage(myRecruits, curPage, length, total);
	}
	public int getMyResumeRecruitCount(int userID,DBSession db) throws Exception{
		String sql = "SELECT COUNT(rt.id) FROM user_recruit rt,user_recruitresume rr, user_resume rs WHERE rt.id=rr.recruitID AND rs.id=rr.resumeID and rr.userId=?";
		Object[] params = new Object[]{userID};
		return recruitManager.getSqlCount(sql,params,db);
	}
	
	RecruitManager recruitManager = (RecruitManager) Context.getBean(RecruitManager.class);
}