package com.tl.invest.user.resume;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;

/** 
 * @created 2014年11月2日 下午10:41:26 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "deprecation", "rawtypes"})
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
	    	//按数据库类型获得不同的查询个数限制语句
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
	 * 根据ID获取建立信息
	 * 
	 * @param userID    用户ID
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
	* 功能： 我收藏的职位
	* @param curPage
	* @param length
	* @param userId
	* @return
	* @throws Exception 
	*/ 
	public Message getMyRecruits(int curPage, int length, Integer userId) throws Exception{
		String sql = "SELECT rt.* FROM user_recruit rt,user_recruitresume rr WHERE rt.id=rr.recruitID and rr.userId=?";
		Object[] params = new Object[]{userId};
		List<UserRecruit> myRecruits =  getMyRecruits(sql, params, length, curPage, null);
		int total = getMyRecruitsCount(userId, null);
		return setMessage(myRecruits, curPage, length, total);
	} 
	public int getMyRecruitsCount(int userID,DBSession db) throws TLException{
		String sql = "select count(rt.id) FROM user_recruit rt,user_recruitresume rr WHERE rt.id=rr.recruitID and rr.userId=?";
		Object[] params = new Object[]{userID};
		return getSqlCount(sql,params,db);
	}
	
	/** 
	* @author  leijj 
	* 功能： 我已投递简历的职位
	* @param curPage
	* @param length
	* @param userId
	* @return
	* @throws Exception 
	*/ 
	public Message getMyResumeRecruits(int curPage, int length, Integer userId) throws Exception{
		String sql = "SELECT rt.* FROM user_recruit rt,user_recruitresume rr, user_resume rs WHERE rt.id=rr.recruitID AND rs.id=rr.resumeID and rr.userId=?";
		Object[] params = new Object[]{userId};
		List<UserRecruit> myRecruits =  getMyRecruits(sql, params, length, curPage, null);
		int total = getMyResumeRecruitCount(userId, null);
		return setMessage(myRecruits, curPage, length, total);
	}
	public int getMyResumeRecruitCount(int userID,DBSession db) throws Exception{
		String sql = "SELECT COUNT(rt.id) FROM user_recruit rt,user_recruitresume rr, user_resume rs WHERE rt.id=rr.recruitID AND rs.id=rr.resumeID and rr.userId=?";
		Object[] params = new Object[]{userID};
		return getSqlCount(sql,params,db);
	}
	/** 
	* @author  leijj 
	* 功能： 组装翻页信息
	* @param list
	* @param curPage
	* @param length
	* @param total
	* @return 
	*/ 
	public Message setMessage(List list, int curPage, int length, int total){
		if(list != null && list.size() > 0){
        	Message message = new Message();
			int pageCount = total/length;
			if(total % length >0) pageCount = pageCount + 1;
			if(pageCount<=0) pageCount = 1;
			
			message.setCurPage(curPage);
			message.setLength(length);
			message.setMessages(list);
			message.setPageCount(pageCount);
			message.setTotal(total);
			message.setPageBegin(message.getPageBegin(curPage));
			message.setPageEnd(message.getPageEnd(curPage, pageCount));
            return message;
        }
        else
           return null;
	}
	
	private List<UserRecruit> getMyRecruits(String sql, Object[] params, int pageSize, int page, DBSession db) throws TLException{
		List<UserRecruit> list = new ArrayList<UserRecruit>();
		IResultSet rs = null;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		try {
			sql = db.getDialect().getLimitString(sql, pageSize*(page-1), pageSize);
			rs = db.executeQuery(sql, params);
			while (rs.next()) {
				list.add(readRecruitRS(rs));
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}
		if (list.size() == 0) return null;
		
		return list;
	}
	private int getSqlCount(String sql,Object[] params,DBSession db) throws TLException{
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
	private UserRecruit readRecruitRS(IResultSet rs) throws TLException{
		try {
			UserRecruit recruit = new UserRecruit();
			recruit.setId(rs.getInt("id"));
			recruit.setUserId(rs.getInt("userId"));
			recruit.setJobName(rs.getString("jobName"));
			recruit.setJobPictrue(rs.getString("jobPictrue"));
			recruit.setJobCateId(rs.getInt("jobCateId"));
			recruit.setJobCate(rs.getString("jobCate"));
			recruit.setLinkman(rs.getString("linkman"));
			recruit.setLinkPhone(rs.getString("linkPhone"));
			recruit.setLinkEmail(rs.getString("linkEmail"));
			recruit.setSalary(rs.getString("salary"));
			recruit.setContent(rs.getString("content"));
			recruit.setAddress(rs.getString("address"));
			recruit.setWorking(rs.getString("working"));
			recruit.setEduReq(rs.getString("eduReq"));
			recruit.setIsFulltime(rs.getInt("isFulltime"));
			recruit.setJobAttract(rs.getString("jobAttract"));
			recruit.setJobIntro(rs.getString("jobIntro"));
			recruit.setCreatetime(rs.getTimestamp("createtime"));
			User user = userManager.getUserByID(recruit.getUserId());
			recruit.setCompany(user.getOrgFullname());
			recruit.setCity(user.getCity());
			recruit.setTime(DateUtils.format(recruit.getCreatetime(), "yyyy-MM-dd hh:mm:ss"));
			return recruit;
		} catch (Exception e) {
			throw new TLException(e);
		}
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}