package com.tl.invest.user.recruit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sun.util.logging.resources.logging;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ResourceMgr;
import com.tl.common.StringUtils;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

/** 
 * @created 2014年11月14日 下午2:07:27 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked"})
public class RecruitManager {
	
	
 
	public Dictionary[] getHotCitys(){
		List<Dictionary> list = new ArrayList<Dictionary>();
		DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
		try {
			Dictionary[] hotcitys = reader.getChildrenDics(DicTypes.DIC_AREA.typeID(), 0);
			if(hotcitys!=null && hotcitys.length>0){
				for (Dictionary dic : hotcitys) {
					if(dic.getValue()!=null&&dic.getValue().equals("hot")){
						list.add(dic);
					}
				}
			}
		} catch (TLException e) {
			
		}
		
		if (list.size() == 0) return null;
		//排序
		
		//取到值为hot的城市分类
		return (Dictionary[]) list.toArray(new Dictionary[0]);
		 
	}
	
	/**获得所有的城市
	 * @return
	 */
	public Dictionary[] getProvCitys() {
		Dictionary[] provCitys = null;
		DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
		try {
			provCitys = reader.getChildrenDics(DicTypes.DIC_AREA.typeID(), 0);
		} catch (Exception e) {
			 
		}
		return provCitys;
		
	}
	/**根据职位ID获取相应的人的信息
	 * @param recruitid
	 * @return json格式
	 * @throws Exception
	 */
	public String getRecruitUserJson(int recruitid)throws Exception{
		
		StringBuilder querySql = new StringBuilder("select u.id,u.name,u.head,rr.recruitID,rr.createtime from user_recruitresume as rr,user as u,user_resume as r where rr.collected=0 and rr.userID=u.id  and rr.recruitID= "+String.valueOf(recruitid));
		StringBuilder sb = new StringBuilder();
		String contentString = "";
		IResultSet rs = null;
		boolean dbIsCreated = false;
		DBSession db =null;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		try {
			String sql = querySql.toString() ;
			rs = db.executeQuery(sql, null);
			
			String prefix = "{"+" \"users\": [";
			while (rs.next()) {
				int userid = rs.getInt("id");
				String username = rs.getString("name");
				String headurl = rs.getString("head");
				String createtime = rs.getString("createtime");
				headurl = headurl.replace("\\", "/");
				int recid = rs.getInt("recruitID");
				 
				sb.append("{");
				sb.append("\"userid\":\""+String.valueOf(userid)+"\",");
				sb.append("\"username\":\""+username+"\",");
				sb.append("\"headurl\":\""+headurl+"\",");
				sb.append("\"recid\":\""+String.valueOf(recid)+"\",");
				sb.append("\"createtime\":\""+createtime+"\"");
				
				sb.append("}");
				sb.append(",");
				
			}
			String suffix = "]}";
			if(sb.toString().endsWith(",")){
				contentString = prefix + sb.toString().substring(0,sb.toString().length()-1) + suffix;
			}
			else{
				contentString = prefix + suffix;
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			if(dbIsCreated){
				ResourceMgr.closeQuietly(db);
			}
		}	
		
		return contentString;
	}
	/** 
	* @author  leijj 
	* 功能： 获取招聘信息条数
	* @return 
	*/ 
	public int getCount(){
		String tablename = SysTableLibs.TB_USERRECRUIT.getTableCode();
		int count = 0;
		StringBuilder querySql = new StringBuilder("select count(1) as length from ").append(tablename).append(" where "+tablename+".deleted=0 ");
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
	public UserRecruit[] queryRecruits(int start, int length) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a where a.jobOrder>0 and deleted=0 ");
		querySql.append(" order by a.jobOrder asc");
		List<UserRecruit> list = DAOHelper.find(querySql.toString() , start, length);
		if(list == null || list.size() == 0) return null;
		return list.toArray(new UserRecruit[0]);
	}
	
	 
	/** 获取特定城市和职业的招聘信息
	 * @param cityid
	 * @param recid
	 * @return
	 * @throws Exception
	 */
	public UserRecruit[] queryRecruitsSubscibe(int cityid,int recid)throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a where a.city="+String.valueOf(cityid)+" and a.secondType="+String.valueOf(recid)+" and deleted=0 ");
		querySql.append(" order by a.createtime desc LIMIT 4");
		List<UserRecruit> list = DAOHelper.find(querySql.toString());
		
		if(list == null || list.size() == 0) return null;
		return list.toArray(new UserRecruit[0]);
	}
	
	/** 获得用户发布的所有职位
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public UserRecruit[] queryRecruitsByUserID(int userid)throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a where a.userId="+String.valueOf(userid)+" and deleted=0 ");
		querySql.append(" order by a.createtime desc");
		List<UserRecruit> list = DAOHelper.find(querySql.toString());
		
		if(list == null || list.size() == 0) return null;
		return list.toArray(new UserRecruit[0]);
	}
	
	/** 
	* @author  leijj 
	* 功能： 获取最新/最热招聘职位
	* @param curPage
	* @param length
	* @param recruitType
	* @param qeuryType
	* @param userId
	* @return
	* @throws Exception 
	*/ 
	public Message queryRecruits(int curPage, int length, String recruitType, String queryType, Integer userId) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.recruit.UserRecruit as a where deleted=0 ");
		String countSql = "";
		if("edit".equals(recruitType) && userId > 0){//管理我的职位，则增加当前用户查询条件
			querySql.append(" where a.userId=").append(userId);
			countSql = " where userID=" + userId;
		}
		if("queryNew".equals(queryType)){//最新职位
			querySql.append(" order by a.createtime desc");
		} else if("queryHot".equals(queryType)){//最热职位
			querySql.append(" order by a.resumeNum desc");
		}
		
		int total = DAOHelper.getCount("user_recruit", countSql);
		List list = DAOHelper.find(querySql.toString() , length*(curPage-1), length);
        if(list.size() > 0){
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
	 
	public Message queryRecruits(int curPage, int length, Integer userId, 
			String recruitType, String queryType, int type, String key,Integer province, Integer city,Integer Days ,Integer Degree,Integer JobType,Integer PubTime) throws Exception{
		StringBuilder querySql = new StringBuilder("SELECT DISTINCT rt.*,u.city,dic.dic_id,dic.dic_name FROM user_recruit rt,user u LEFT JOIN sys_dictionary AS dic ON u.city=dic.dic_id WHERE u.id=rt.userID ");
		StringBuilder countSql = new StringBuilder("SELECT count(rt.id) FROM user_recruit rt,user u LEFT JOIN sys_dictionary AS dic ON u.city=dic.dic_id WHERE u.id=rt.userID ");
		
		List<Object> paramList = new ArrayList<Object>();
		if("edit".equals(recruitType) && userId > 0){//管理我的职位，则增加当前用户查询条件
			querySql.append(" and u.id=?");
			countSql.append(" and u.id=?");
			paramList.add(userId);
		}
		if(type == 0 && !StringUtils.isEmpty(key)){//职位查询条件
			querySql.append(" AND rt.typeName LIKE '%").append(key).append("%'");
			countSql.append(" AND rt.typeName LIKE '%").append(key).append("%'");
		} else if(type == 1 && !StringUtils.isEmpty(key)){//公司查询条件
			querySql.append(" AND (u.orgShortname LIKE '%").append(key).append("%' OR u.orgFullname LIKE '%").append(key).append("%')");
			countSql.append(" AND (u.orgShortname LIKE '%").append(key).append("%' OR u.orgFullname LIKE '%").append(key).append("%')");
		}
		if(Days>0){
			querySql.append(" and CONVERT(rt.days,SIGNED)>="+String.valueOf(Days) +" ");
			countSql.append(" and CONVERT(rt.days,SIGNED)>="+String.valueOf(Days) +" ");
		}
		
		if(Degree>0){
			querySql.append(" and (CONVERT(rt.eduReq,SIGNED)=1 or CONVERT(rt.eduReq,SIGNED)>="+String.valueOf(Degree) +") ");
			countSql.append(" and (CONVERT(rt.eduReq,SIGNED)=1 or CONVERT(rt.eduReq,SIGNED)>="+String.valueOf(Degree) +") ");
		}
		
		if(JobType>0){
			querySql.append(" and rt.isFulltime="+String.valueOf(JobType) +" ");
			countSql.append(" and rt.isFulltime="+String.valueOf(JobType) +" ");
		}
		
		if(PubTime>0){
			querySql.append(" and DATEDIFF(NOW(),rt.createtime)<="+String.valueOf(PubTime) +" ");
			countSql.append(" and DATEDIFF(NOW(),rt.createtime)<="+String.valueOf(PubTime) +" ");
		}
		
		
		if(city>-1){
			querySql.append(" and rt.city=?");
			countSql.append(" and rt.city=?");
			paramList.add(city);
		}
		if(province>-1){
			querySql.append(" and rt.province=?");
			countSql.append(" and rt.province=?");
			paramList.add(province);
		}
		querySql.append(" order by rt.createtime desc");
		
		Object[] params = null;
		if(paramList != null && paramList.size() > 0){
			params = paramList.toArray(new Object[0]);
		}
		List<UserRecruit> myRecruits =  getRecruits(querySql.toString(), params, length, curPage, null);
		int total = getSqlCount(countSql.toString(), params, null);
		return setMessage(myRecruits, curPage, length, total);
	}
	public Message queryRecruits(int curPage, int length, Integer userId, 
			String recruitType, String queryType, int type, String key,String province, String city) throws Exception{
		StringBuilder querySql = new StringBuilder("SELECT DISTINCT rt.*,u.city,dic.dic_id,dic.dic_name FROM user_recruit rt,user u LEFT JOIN sys_dictionary AS dic ON u.city=dic.dic_id WHERE u.id=rt.userID ");
		StringBuilder countSql = new StringBuilder("SELECT count(rt.id) FROM user_recruit rt,user u LEFT JOIN sys_dictionary AS dic ON u.city=dic.dic_id WHERE u.id=rt.userID ");
		
		List<Object> paramList = new ArrayList<Object>();
		if("edit".equals(recruitType) && userId > 0){//管理我的职位，则增加当前用户查询条件
			querySql.append(" and u.id=?");
			countSql.append(" and u.id=?");
			//params = new Object[]{userId};
			paramList.add(userId);
		}
		if(type == 0 && !StringUtils.isEmpty(key)){//职位查询条件
			querySql.append(" AND rt.typeName LIKE '%").append(key).append("%'");
			countSql.append(" AND rt.typeName LIKE '%").append(key).append("%'");
		} else if(type == 1 && !StringUtils.isEmpty(key)){//公司查询条件
			querySql.append(" AND (u.orgShortname LIKE '%").append(key).append("%' OR u.orgFullname LIKE '%").append(key).append("%')");
			countSql.append(" AND (u.orgShortname LIKE '%").append(key).append("%' OR u.orgFullname LIKE '%").append(key).append("%')");
		}
		if(!StringUtils.isEmpty(city)){
			querySql.append(" and rt.city=?");
			countSql.append(" and rt.city=?");
			paramList.add(city);
		}
		if(!StringUtils.isEmpty(province)){
			querySql.append(" and rt.province=?");
			countSql.append(" and rt.province=?");
			paramList.add(province);
		}
		if("queryNew".equals(queryType)){//最新职位
			querySql.append(" order by rt.createtime desc");
		} else if("queryHot".equals(queryType)){//最热职位
			querySql.append(" order by rt.resumeNum desc");
		}
		Object[] params = null;
		if(paramList != null && paramList.size() > 0){
			params = paramList.toArray(new Object[0]);
		}
		List<UserRecruit> myRecruits =  getRecruits(querySql.toString(), params, length, curPage, null);
		int total = getSqlCount(countSql.toString(), params, null);
		return setMessage(myRecruits, curPage, length, total);
	}
	public List<UserRecruit> querySimiRecruits(String typeName,int recid) throws TLException{
		StringBuilder querySql = new StringBuilder("SELECT rt.* FROM user_recruit rt WHERE rt.typeName LIKE '%")
			.append(typeName).append("%' and rt.id<> "+ String.valueOf(recid));
		List<UserRecruit> myRecruits =  getRecruits(querySql.toString(), null, 4, 1, null);
		return myRecruits;
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
		List list = DAOHelper.find("select a from com.tl.invest.user.recruit.UserRecruit as a where a.id = :id and deleted=0", 
        		new Integer(id), Hibernate.INTEGER);
        if(list.size() > 0)
            return (UserRecruit) list.get(0);
        else
            return null;
	}
	/**
	 * 根据recruitID查询是否已收藏职位
	 * 
	 * @param ID
	 */
	public UserRecruitresume recruitresume(int userId, int recruitId) throws Exception{
		String sql = "select a from com.tl.invest.user.recruit.UserRecruitresume as a where a.userId = "+
				userId + " and a.collected = 1 and  a.recruitId = " + recruitId;
		List<UserRecruitresume> list = DAOHelper.find(sql);
        if(list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
	}
	
	public void delRecCollectandPost(Integer userid,Integer id) throws Exception{
		if(id<=0){
			return;
		}
		String sql = "delete  com.tl.invest.user.recruit.UserRecruitresume as a where a.userId = "+
				userid + "  and  a.recruitId = " + id;
		DAOHelper.delete(sql);
		 
	}
	
	/**
	 * 根据recruitID查询是否已收藏职位
	 * 
	 * @param ID
	 */
	public UserRecruitresume recruitresumePost(int userId, int recruitId) throws Exception{
		String sql = "select a from com.tl.invest.user.recruit.UserRecruitresume as a where a.userId = "+
				userId + " and a.collected = 0 and  a.recruitId = " + recruitId;
		List<UserRecruitresume> list = DAOHelper.find(sql);
        if(list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
	}
	
	 
	/** 
	* @author  leijj 
	* 功能： 收藏职位保存
	* @param recruitResume 
	 * @throws Exception 
	*/ 
	public void collect(UserRecruitresume recruitResume) throws Exception{
		 
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(recruitResume.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_USERRECRUITRESUME.getTableCode());
	    		recruitResume.setId(userID);
		        dao.save(recruitResume,s);
	    	} else {
	    		dao.update(recruitResume,s);
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
	* @author  leijj 
	* 功能： 获取职业分类
	* @param response
	* @throws Exception 
	*/ 
	public Dictionary[] types() throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] types = dicReader.getSubDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), 0);
		if(types == null || types.length == 0) return null;
		
		List<Dictionary> typeList = assembleType(dicReader, types);
		return typeList.toArray(new Dictionary[0]);
	}
	
	/** 
	* @author  leijj 
	* 功能： 
	* @param dicReader
	* @param types
	* @return
	* @throws TLException 
	*/ 
	public List<Dictionary> assembleType(DictionaryReader dicReader, Dictionary[] types) throws TLException{
		if(types == null || types.length == 0) return null;
		List<Dictionary> typeList = new ArrayList<Dictionary>();
		for (Dictionary type : types) {
			int id = type.getId();
			Dictionary[] jobSubTypes = dicReader.getChildrenDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), id);
			/*if(jobSubTypes != null && jobSubTypes.length > 0){
				assembleType(dicReader, types);
			}*/
			type.setSubDics(jobSubTypes);
			typeList.add(type);
		}
		
		return typeList;
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
	
	public List<UserRecruit> getRecruits(String sql, Object[] params, int pageSize, int page, DBSession db) throws TLException{
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
	public int getSqlCount(String sql,Object[] params,DBSession db) throws TLException{
		int count =0;
		boolean dbIsCreated = false;
		if(db==null){
			dbIsCreated = true;
			db= Context.getDBSession();
		}
		IResultSet rs = null;
		try{
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
	
	/** 获得简历数量
	 * @param recid
	 * @return
	 * @throws Exception
	 */
	public int getRecruitResumeNum(int recid) throws Exception{
		int count = 0;
		try {
			count = getSqlCount("select count(*) from user_recruitresume as rr where rr.recruitID="+String.valueOf(recid),null,null);
		} catch (Exception e) {
			 
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
			recruit.setJobOrder(rs.getInt("jobOrder"));
			recruit.setDeleted(rs.getInt("deleted"));
			recruit.setDays(rs.getString("days"));
			recruit.setJobType(rs.getInt("jobType"));
			recruit.setProvince(rs.getString("province"));
			recruit.setCity(rs.getString("city"));
		
			User user = userManager.getUserByID(recruit.getUserId());
			if(user!=null){
				recruit.setCompany(user.getOrgFullname());
			}
			
		    
		    recruit.setResumeNum(getRecruitResumeNum(recruit.getId()));
			
			recruit.setTime(DateUtils.format(recruit.getCreatetime(), "yyyy-MM-dd hh:mm:ss"));
			return recruit;
		} catch (Exception e) {
			throw new TLException(e);
		}
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}