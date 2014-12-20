package com.tl.invest.user.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.tl.common.ResourceMgr;
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
	 * 获得所有的用户
	 * @return User对象的数组
	 */
	public User[] getPersons(int perJob){
        try
        {
            List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.type = :type and a.perJob = :perJob order by a.id", 
            		new Object[]{0, perJob});
            return getUsers(list);
        } catch (Exception e) {
        }
        return null;
	}
	public int getPersonsCount(int type,DBSession db) throws TLException{
		String sql = "select count(0) from invest_user left JOIN sys_dictionary on sys_dictionary.dic_id=invest_user.perJob";
		//sql += " left JOIN `user` on `user`.id=invest_project.proj_userID";
		sql += " where invest_user.type=0 invest_user.perJob=?";
		Object[] params = new Object[]{type};
		return getSqlCount(sql,params,db);
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
	    /*
		User user1 = getUserByID(user.getId());
	    if( user1 == null)
	        throw new Exception("the updated user not exist.");
	    if(user1.getId() != user.getId())
	        throw new Exception("cannt update user code.");
	    */
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
		 
		Object[] paramObjects = {code,UserEncrypt.getInstance().encrypt(pwd)};
		try {
			List list = DAOHelper.find("select a from com.tl.invest.user.user.User as a where a.code=? and a.password=?",
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
}