package com.tl.invest.user.resume;

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
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年11月2日 下午10:41:26 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "deprecation", "resource", "rawtypes"})
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
	* @author  leijj 
	* 功能： 查询我收藏的职位或已投过简历的职位
	* @param start 开始页码
	* @param length 一页长度
	* @param user 用户
	* @param isPost 是否已投简历
	* @return 
	*/ 
	public Message myRecruit(int start, int length, User user, boolean isPost){
		int total = 0;
		Message message = new Message();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		String rTablename = SysTableLibs.TB_USERRECRUIT.getTableCode();
		String rrTablename = SysTableLibs.TB_USERRECRUITRESUME.getTableCode();
		StringBuilder countSql = new StringBuilder("select count(r.id) as total from ").append(rTablename).append("  as r, ")
				.append(rrTablename).append(" as rr where r.id=rr.recruitID and rr.userId=?");
		
		StringBuilder querySql = new StringBuilder("select r.* from ").append(rTablename).append("  as r, ")
				.append(rrTablename).append(" as rr where r.id=rr.recruitID and rr.userId=?");
		
		if(isPost){
			countSql.append(" and rr.isPostResume=1");
			querySql.append(" and rr.isPostResume=1");
		}
		querySql.append( " ORDER BY rr.createtime DESC ");
		DBSession conn = null;
		IResultSet rs = null;
		IResultSet numRs = null;
		try {
	    	conn = Context.getDBSession();
	    	rs = conn.executeQuery(countSql.toString(), new Object[]{user.getId()});
			while (rs.next()) {
				total = rs.getInt("total");
			}
			//按数据库类型获得不同的查询个数限制语句
	    	String sql = conn.getDialect().getLimitString(querySql.toString(), start, length);
	    	rs = conn.executeQuery(sql, new Object[]{user.getId()});
			while (rs.next()) {
				Map<String, String> oneResult = assemble(user.getId(), rs);
				result.add(oneResult);
			}
			int pageCount = (total/length == 0 ? total/length : (total/length + 1));
			message.setCurPage(start + 1);
			message.setLength(length);
			message.setMessages(result);
			message.setPageCount(pageCount);
			message.setTotal(total);
			message.setUserName(user.getName());
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(numRs);
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		return message;
	}
	private Map<String, String> assemble(int userID, IResultSet rs) throws Exception{
		User user = userManager.getUserByID(userID);
		Map<String, String> oneResult = new HashMap<String, String>();
		
		oneResult.put("id", ParamInitUtils.getString(rs.getString("id")));
		oneResult.put("userId", ParamInitUtils.getString(rs.getString("userId")));
		oneResult.put("userName", ParamInitUtils.getString(rs.getString("userName")));
		oneResult.put("createTime", ParamInitUtils.getString(rs.getString("createTime")));
		oneResult.put("company", user.getOrgFullname());
		oneResult.put("city", user.getCity());
		oneResult.put("jobName", ParamInitUtils.getString(rs.getString("jobName")));
		oneResult.put("jobPictrue", ParamInitUtils.getString(rs.getString("jobPictrue")));
		oneResult.put("jobCateId", ParamInitUtils.getString(rs.getString("jobCateId")));
		oneResult.put("jobCate", ParamInitUtils.getString(rs.getString("jobCate")));
		oneResult.put("company", ParamInitUtils.getString(rs.getString("")));
		oneResult.put("linkman", ParamInitUtils.getString(rs.getString("linkman")));
		oneResult.put("linkPhone", ParamInitUtils.getString(rs.getString("linkPhone")));
		oneResult.put("linkEmail", ParamInitUtils.getString(rs.getString("linkEmail")));
		oneResult.put("province", ParamInitUtils.getString(rs.getString("province")));
		oneResult.put("city", ParamInitUtils.getString(rs.getString("city")));
		oneResult.put("area", ParamInitUtils.getString(rs.getString("area")));
		oneResult.put("salary", ParamInitUtils.getString(rs.getString("salary")));
		oneResult.put("content", ParamInitUtils.getString(rs.getString("content")));
		oneResult.put("address", ParamInitUtils.getString(rs.getString("address")));
		oneResult.put("working", ParamInitUtils.getString(rs.getString("working")));
		oneResult.put("eduReq", ParamInitUtils.getString(rs.getString("eduReq")));
		oneResult.put("isFulltime", ParamInitUtils.getString(rs.getString("isFulltime")));
		oneResult.put("jobAttract", ParamInitUtils.getString(rs.getString("jobAttract")));
		oneResult.put("jobIntro", ParamInitUtils.getString(rs.getString("jobIntro")));
		oneResult.put("createtime", ParamInitUtils.getString(rs.getString("createtime")));
		oneResult.put("time", DateUtils.format(rs.getDate("createtime"), "yyyy-MM-dd hh:mm:ss"));
		oneResult.put("isPub", ParamInitUtils.getString(rs.getString("isPub")));
		oneResult.put("resumeNum", ParamInitUtils.getString(rs.getString("resumeNum")));
		oneResult.put("days", ParamInitUtils.getString(rs.getString("days")));
		return oneResult;
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
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	/*public List<UserRecruit> myRecruit1(int start, int length, Integer userId, boolean isPost) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a")
			.append(",com.tl.invest.user.recruit.UserRecruitresume as b where a.id=b.recruitID")
			.append(" and a.userId=").append(userId);
		if(isPost){
			querySql.append(" and b.isPostResume=1");
		}
		querySql.append(" ORDER BY b.createtime DESC");
		List list = DAOHelper.find(querySql.toString() , start, length);
        if(list.size() > 0)
            return list;
        else
            return null;
        
	}*/
}