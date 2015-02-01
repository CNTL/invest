package com.tl.invest.user.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ResourceMgr;
import com.tl.common.StringUtils;
import com.tl.common.UserEncrypt;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;




/**
 * @author leijj
 *
 */
@SuppressWarnings({"rawtypes","deprecation","unchecked"})
public class UserManager {

	public UserManager(){

	}

	/**
	 * 添加用户
	 * 
	 * @param user  要创建的用户对象
	 */
	public String create(User user) throws Exception{
		StringBuilder result = new StringBuilder("");
	    if(user.getCode() == null || user.getCode().trim().equals("")){
	    	result.append("账号(").append(user.getCode()).append(")不能为空！");
	    	System.out.println(result);
	    	return result.toString();
	    	//throw new Exception("User Code is Null.");
	    } else if(user.getId() <= 0 && getUserByCode(user.getCode()) != null){
	    	result.append("账号(").append(user.getCode()).append(")已存在！");
	    	System.out.println(result);
	    	return result.toString();
	    	//throw new Exception("The Same User Code exist.");
	    } else if(user.getId() <= 0 && getUserByEmail(user.getEmail()) != null){
	    	result.append("邮箱(").append(user.getEmail()).append(")已注册！");
	    	System.out.println(result);
	    	return result.toString();
	    	//throw new Exception("The Same User Email exist.");
	    } 
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	//加密password,数据库中存储的是密文
            user.setPassword(UserEncrypt.getInstance().encrypt(user.getPassword()));
	    	if(user.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_USER.getTableCode());
		        user.setId(userID);
		        dao.save(user,s);
	    	} else {
	    		dao.update(user,s);
	    	}
	    	result.append("ok");
	    	t.commit();
        }
        catch (Exception e)
        {
        	if(t != null ) t.rollback();
        	if(e instanceof Exception ) throw (Exception)e;
            throw new Exception("update user error.", e);
        } finally{
        	dao.closeSession(s);
        }
	    return result.toString();
	}
	/**
	 * 获得所有的用户
	 * @return User对象的数组
	 */
	public User[] getUsers() throws Exception{
        try
        {
            DAO dao = new DAO();
            List list = dao.find("select user from com.tl.invest.user.user.User as user order by user.id");
    		return getUsers(list);
        }
        catch (Exception e)
        {
            throw new Exception("", e);
        }
	}
	
	/**
	 * 获得用户ID指定的用户
	 * 
	 * @param userID    用户ID
	 */
	public User getUserByID(int userID) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.id = :id", 
        		new Integer(userID), Hibernate.INTEGER);
        if(list.size() > 0)
            return this.decrypt(list.get(0));
        else
            return null;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user    用户对象
	 */
	public void update(User user) throws Exception{
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
        String plainText = user.getPassword();
	    try
        {            
            s = dao.getSession();
            t = dao.beginTransaction(s);
            
            //数据库中存储的是密文
            dao.update(user,s);
                      
            //update event（需要密码明文）
           // this.doEvent(ManagerEventListener.EVENT_UPDATE, user);
            
            //加密password
            user.setPassword(UserEncrypt.getInstance().encrypt(plainText));
            
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
            //user对应继续存储明文
            user.setPassword(plainText);
            
        	dao.closeSession(s);
        }
	}

	/**
	 * 删除用户
	 * 
	 * @param userID    用户ID
	 */
	public void delete(int userID) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.user.User as user where user.id = :id", 
        		   new Integer(userID), Hibernate.INTEGER);
	}
	public List<User> queryUsersStr(int iDisplayStart, int iDisplayLength, User query) throws Exception{
		List<User> users = new ArrayList<User>();
		List list = queryUsers(iDisplayStart, iDisplayLength, query);
        if(list != null && list.size() > 0){
        	for(int i = 0; i < list.size(); i++){
        		User user = (User)list.get(i);
        		user.setPassword(UserEncrypt.getInstance().decrypt(user.getPassword()));
        		//user.setCreateTimeStr(DateUtils.format(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        		users.add(user);
        	}
        	 return users;	
        } else
            return null;
        
	}
	public List<User> queryUsers(int iDisplayStart, int iDisplayLength, User query) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.user.User as a");
		if(query.getName() != null && !"".equals(query.getName())
				&& query.getCode() != null && !"".equals(query.getCode())){
			querySql.append(" where a.name like '%" + query.getName())
					.append("%' and a.code like '%" + query.getCode()).append("%'");
		} else if(query.getName() != null && !"".equals(query.getName())
				&& (query.getCode() == null || "".equals(query.getCode()))){
			querySql.append(" where a.name like '%" + query.getName()).append("%'");
		} else if((query.getName() == null || "".equals(query.getName()))
				&& query.getCode() != null && !"".equals(query.getCode())){
			querySql.append(" where a.code like '%" + query.getCode()).append("%'");
		}
		querySql.append(" order by createTime desc");
		List list = DAOHelper.find(querySql.toString() , iDisplayStart, iDisplayLength);
        if(list.size() > 0)
            return list;
        else
            return null;
        
	}
	/* 获取所有的用户数据
	 * @see com.tl.sys.org.UserManager#getAll()
	 */
	public List<User> getAll() throws Exception{
		List<User> list = new ArrayList<User>();
		DBSession dbsession = null;
		IResultSet rs = null;
		try {
			dbsession = Context.getDBSession();
			String tablename = SysTableLibs.TB_USER.getTableCode();
			rs = dbsession.executeQuery( "select * from " + tablename, null );
			while(rs.next()){
				//User user = setUser(rs);
				//list.add(user);
			}
			//dbsession.commitTransaction();
		} finally {
			ResourceMgr.closeQuietly( dbsession );
		}
		return list;
	}
	
	/**
	 * 获得用户代码指定的用户
	 * 
	 * @param userCode    用户编码
	 */
	public User getUserByCode(String code) throws Exception{
        List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.code = :code", 
        		code, Hibernate.STRING);
        if(list.size() > 0)
            return decrypt(list.get(0));
        else
            return null;
	}

	/** 系统用户登录
	 * @return Boolean
	 */
	public User login(String code,String pwd) {
		User user = null;
		String field = "";
		String mailcheck = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		 
		Pattern mailregex = Pattern.compile(mailcheck);    
		Matcher mailmatcher = mailregex.matcher(code);    
		boolean isMailMatched = mailmatcher.matches(); 
		
		String check = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$";  
		Pattern regex = Pattern.compile(check);  
		Matcher matcher = regex.matcher(code);  
		boolean isMatched = matcher.matches();  
		
		if(isMailMatched){
			field = "email";

		}else if(isMatched){
			field = "perPhone";

		}else{
			field = "code";

		}
		
		Object[] paramObjects = {code,UserEncrypt.getInstance().encrypt(pwd)};
		try {
			List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where  a.deleted=0 and a."+field+"=? and a.password=?",
					paramObjects);
			if(!list.isEmpty()){
				user = (User)list.get(0);
			}
		} catch (Exception e) {
			 
		}
		return user;
	}
	
	/*
	 * 根据用户名获取对应的用户
	 * 
	 * 
	 */
	 public User [] getUsersByName(String userName) throws Exception
	 {
	        List list = DAOHelper.find("from com.tl.invest.user.user.User as user where user.name=:name or user.code=:code", 
		    		new String[]{"userName", "userCode"},
		    		new Object[] {userName, userName}, 
		    		new Type[] {Hibernate.STRING, Hibernate.STRING});

    		return getUsers(list);
	 }

	private User decrypt(Object _user){
		User user = (User)_user;
		if(UserEncrypt.getInstance().isDecryptText(user.getPassword()))
			user.setPassword(UserEncrypt.getInstance().decrypt(user.getPassword()));
		return user;
	}
	private User[] getUsers(List list)
	{
		if (list == null) return null;
		
		User[] users = new User[list.size()];
		list.toArray(users);
		
	    for (int i = 0; i < users.length; i++) {
			decrypt(users[i]);
		}
        
		return users;
	}
	/** 
	* @author  leijj 
	* 功能： 根据qq openID获取用户信息
	* @param openID
	* @return 
	*/ 
	public User getUserByQQ(String openID) {
		User user = null;
		 
		Object[] paramObjects = {openID};
		try {
			List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.qqOpenId=?",
					paramObjects);
			if(!list.isEmpty()){
				user = (User)list.get(0);
			}
		} catch (Exception e) {
			 
		}
		return user;
	}
	/** 
	* @author  leijj 
	* 功能： 根据新浪微博昵称获取用户信息
	* @param weibo
	* @return 
	*/ 
	public User getUserByWeibo(String weibo) {
		User user = null;
		Object[] paramObjects = {weibo};
		try {
			List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.xlWeiboCode=?",
					paramObjects);
			if(!list.isEmpty()){
				user = (User)list.get(0);
			}
		} catch (Exception e) {
			 
		}
		return user;
	}
	/** 
	* @author  leijj 
	* 功能： 
	* @param weibo
	* @return 
	*/ 
	public User getUserByEmail(String email) {
		User user = null;
		Object[] paramObjects = {email};
		try {
			List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.email=?",
					paramObjects);
			if(!list.isEmpty()){
				user = (User)list.get(0);
			}
		} catch (Exception e) {
			 
		}
		return user;
	}
	/** 
	* @author  leijj 
	* 功能： 是否已实名认证
	* @param userID
	* @param db
	* @return
	* @throws TLException 
	*/ 
	public boolean isRelAuth(int userID,DBSession db) throws TLException{
		String sql = "SELECT COUNT(0) FROM user u, user_bankcard ub WHERE u.id = ub.userID AND u.id=?";
		Object[] params = new Object[]{userID};
		int num =  getSqlCount(sql,params,db);
		if(num > 0) return true;
		return false;
	}
	/** 
	* @author  leijj 
	* 功能： 获取个人用户
	* @param start 查询开始数
	* @param length 查询条数
	* @return
	* @throws Exception 
	*/ 
	public User[] getPersons(int start, int length) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.user.User as a where a.type = 0 and a.perOrder>0 and a.deleted=0 order by a.perOrder asc");
		List<User> list = DAOHelper.find(querySql.toString() , start, length);
		if(list == null || list.size() == 0) return null;
		List<User> users = new ArrayList<User>();
		for(User user : list){
			if(user!=null){
				users.add(user);
			}
		}
		return users.toArray(new User[0]);
	}
	/** 
	* @author  leijj 
	* 功能： 根据职业查询相应的个人用户
	* @param perJob
	* @param curPage
	* @param length
	* @return
	* @throws Exception 
	*/ 
	public Message queryPersons(int perJob, int curPage, int length) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.user.User as a where a.type = 0 and a.isRealNameIdent=1 and a.perJob = "+perJob+" order by a.createTime desc");
		List<User> persons = DAOHelper.find(querySql.toString() , length*(curPage-1), length);
		int total = getPersonsCount(perJob, null);
		return setMessage(setPerJob(persons), curPage, length, total);
	}
	
	public List<User> setPerJob(List<User> persons){
		
		for (int i = 0; i < persons.size(); i++) {
			User user = persons.get(i);
			user.setPerJobName(user.getPerJobName());
		}
		return persons;
	}
	
	/** 
	* @author  leijj 
	* 功能： 根据职业查询相应的个人用户数
	* @param perJob
	* @param db
	* @return
	* @throws TLException 
	*/ 
	public int getPersonsCount(int perJob, DBSession db) throws TLException{
		String sql = "select count(0) from user as a where a.type=0 and a.deleted=0  and a.perJob=?";
		Object[] params = new Object[]{perJob};
		return getSqlCount(sql,params,db);
	}
	
	public Message queryMorePersons(int curPage, int length,int perJob,
			int age, int gender, String typeIds) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.user.User as a")
			.append(" where a.type = 0 and a.isRealNameIdent=1 and a.deleted=0 and a.perJob='"+String.valueOf(perJob)+"'");
		querySql.append(morePersonsSql(age, gender, typeIds));
		querySql.append(" order by a.createTime desc");
		List<User> persons = DAOHelper.find(querySql.toString() , length*(curPage-1), length);
		int total = getMorePersonsCount(age, gender, typeIds, null);
		return setMessage(persons, curPage, length, total);
	}
	/** 
	* @author  leijj 
	* 功能： 根据职业查询相应的个人用户数
	* @param perJob
	* @param db
	* @return
	* @throws TLException 
	*/ 
	public int getMorePersonsCount(int age, int gender, String typeIds, DBSession db) throws TLException{
		String sql = "select count(0) from user as a where a.type=0 ";
		sql += morePersonsSql(age, gender, typeIds);
		return getSqlCount(sql,null,db);
	}
	
	private String morePersonsSql(int age, int gender, String typeIds) {
		StringBuilder querySql = new StringBuilder("");
		if(age > 0){
			querySql.append(calBirthday(age));
		}
		if(gender >=0){
			querySql.append(" and a.gender=").append(gender);		
		}
		if(!StringUtils.isEmpty(typeIds)){
			querySql.append("  and a.secondType in(").append(typeIds).append(")");	
		}
		return querySql.toString();
	}
	private String calBirthday(int value) {
		StringBuilder querySql = new StringBuilder("");
		switch (value) {
		case 1://20岁以下
			querySql.append(" and a.birthdate <=").append(DateUtils.getSysDateYears(20));
			break;
		case 2://20-30岁
			querySql.append(" and a.birthdate <=").append(DateUtils.getSysDateYears(20));
			querySql.append(" and a.birthdate >=").append(DateUtils.getSysDateYears(30));
			break;
		case 3://30岁以上
			querySql.append(" and a.birthdate <=").append(DateUtils.getSysDateYears(30));
			break;
		default:
			break;
		}
		return querySql.toString();
	}
	/** 
	* @author  leijj 
	* 功能： 查询公司用户
	* @param curPage
	* @param length
	* @return
	* @throws Exception 
	*/ 
	public Message queryCompanys(int curPage, int length, String city) throws Exception{
		StringBuilder querySql = new StringBuilder("SELECT DISTINCT u.*,dic.dic_id,dic.dic_name FROM user_recruit rt,user u LEFT JOIN sys_dictionary AS dic ON u.city=dic.dic_id WHERE u.id=rt.userID AND u.type=1");
		StringBuilder countSql = new StringBuilder("SELECT count(rtUser.userID) AS count FROM (SELECT DISTINCT u.id as userID FROM user_recruit rt,user u LEFT JOIN sys_dictionary AS dic ON u.city=dic.dic_id WHERE u.id=rt.userID AND u.type=1");
		List<Object> paramList = new ArrayList<Object>();
		if(!StringUtils.isEmpty(city)){
			if(!StringUtils.isEmpty(city)){
				querySql.append(" and rt.cityName=?");
				countSql.append(" and rt.cityName=?");
				paramList.add(city);
			}
			/*if("其他".equals(city)){
				String citys = "'北京','上海','广州','南京','重庆','长春','银川','苏州','横店','涿州','海外'";
				querySql.append(" and dic.dic_name not in (").append(citys).append(")");
				countSql.append(" and dic.dic_name not in (").append(citys).append(")");
			} else {
				querySql.append(" and dic.dic_name=?");
				countSql.append(" and dic.dic_name=?");
				paramList.add(city);
			}*/
		}
		countSql.append(" ) AS rtUser");
	
		Object[] params = null;
		if(paramList != null && paramList.size() > 0){
			params = paramList.toArray(new Object[0]);
		}
		List<User> companys =  getUsers(querySql.toString(), params, length, curPage, null);
		int total = getSqlCount(countSql.toString(), params, null);
		return setMessage(companys, curPage, length, total);
	}
	
	public List<User> getUsers(String sql, Object[] params, int pageSize, int page, DBSession db) throws TLException{
		List<User> list = new ArrayList<User>();
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
				list.add(readUser(rs));
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
	
	private User readUser(IResultSet rs) throws TLException{
		try {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setCode(rs.getString("code"));
			user.setOrganization(rs.getString("organization"));
			user.setOrgBusinessLicense(rs.getString("orgBusinessLicense"));
			user.setOrgFullname(rs.getString("orgFullname"));
			user.setOrgHomePage(rs.getString("orgHomePage"));
			user.setOrgNature(rs.getString("orgNature"));
			user.setOrgScale(rs.getString("orgScale"));
			user.setOrgShortname(rs.getString("orgShortname"));
			user.setOrgTrade(rs.getString("orgTrade"));
			user.setHead(rs.getString("head"));
			user.setIntro(rs.getString("intro"));
			return user;
		} catch (Exception e) {
			throw new TLException(e);
		}
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
}