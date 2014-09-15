package com.tl.kernel.context;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.tl.common.ResourceMgr;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.db.util.CloseHelper;

/**
 * 对DAO的进一步封装，静态方法，方便调用
 * @created on 2005-7-21
 * @author Gong Lijie
 */
@SuppressWarnings("rawtypes")
public class DAOHelper
{
	/** 
	* @author  leijj 
	* 功能： 根据表名获取记录条数
	* @return
	* @throws Exception 
	*/ 
	public static int getCount(String tabaleName) throws Exception{
		int count = 0;
		DBSession dbsession = null;
		IResultSet rs = null;
		try {
			dbsession = Context.getDBSession();
			rs = dbsession.executeQuery( "select count(id) as c from " + tabaleName);
			while(rs.next()){
				count = rs.getInt("c");
			}
		} finally {
			ResourceMgr.closeQuietly( dbsession );
		}
		return count;
	}
	/** 
	* @author wang.yq
	* 功能： 根据表名获取记录条数
	* @tableName 表名
	* @filterSql 查询条件
	* @return
	* @throws Exception 
	*/ 
	public static int getCount(String tabaleName,String filterSql) throws Exception{
		int count = 0;
		DBSession dbsession = null;
		IResultSet rs = null;
		try {
			dbsession = Context.getDBSession();
			rs = dbsession.executeQuery( "select count(id) as c from " + tabaleName +" "+filterSql);
			while(rs.next()){
				count = rs.getInt("c");
			}
		} finally {
			ResourceMgr.closeQuietly( dbsession );
		}
		return count;
	}
	/**
	 * 不带参数的删除动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @throws Exception
	 */
	public static void delete(String sql) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}

	/**
	 * 不带参数的删除动作，外传入Session
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @throws Exception
	 */
	public static void delete(String sql, Session s) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, s);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	/**
	 * 带一个参数的删除动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @throws Exception
	 */
	public static void delete(String sql, Object paramValue, Type paramType) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, paramValue, paramType);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}

	/**
	 * 带一个参数的删除动作，外传入Session
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @param s
	 * @throws Exception
	 */
	public static void delete(String sql, Object paramValue, Type paramType, Session s) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, paramValue, paramType, s);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	/**
	 * 带多个参数的删除动作
	 * <p>
	 * 从Hibernate2.0升级到Hibernate3.0时本方法被迫改动
	 * <br/>因为3.0后去掉了带参数(Object[], Type[])的方法，无法直接调用 
	 * <br/>不能使用Query.getNamedParameters来获取参数名称， 
	 * 因为该方法返回的数组是从Map中得到的，顺序不固定
	 * </p>
	 * @param query HQL删除语句
	 * @param paramName HQL语句中的参数名数组
	 * @param paramValue 参数的值数组
	 * @param paramType 参数的类型数组
	 * @throws Exception
	 */
	public static void delete(String sql, String[] paramName, Object[] paramValue, Type[] paramType) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, paramName, paramValue, paramType);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	
	/**
	 * 带多个参数的删除动作，传入Session
	 * <p>
	 * 从Hibernate2.0升级到Hibernate3.0时本方法被迫改动 
	 * <br/>因为3.0后去掉了带参数(Object[], Type[])的方法，无法直接调用
	 * <br/>不能使用Query.getNamedParameters来获取参数名称，
	 * 因为该方法返回的数组是从Map中得到的，顺序不固定
	 * </p>
	 * @param query HQL删除语句
	 * @param paramName HQL语句中的参数名数组
	 * @param paramValue 参数的值数组
	 * @param paramType 参数的类型数组
	 * @param s
	 * @throws Exception
	 */
	public static void delete(String sql, String[] paramName, Object[] paramValue, Type[] paramType, Session s) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, paramName, paramValue, paramType, s);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	/**
	 * 带多个参数的删除动作
	 * @param query HQL删除语句
	 * @param paramValue 参数的值数组
	 * @throws Exception
	 */
	public static void delete(String sql, Object[] paramValue) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, paramValue);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	
	/**
	 * 带多个参数的删除动作，传入Session
	 * @param query HQL删除语句
	 * @param paramValue 参数的值数组
	 * @param s
	 * @throws Exception
	 */
	public static void delete(String sql, Object[] paramValue, Session s) 
	throws Exception 
	{
		try
		{
			DAO dao = new DAO();
			dao.delete(sql, paramValue, s);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	/**
	 * 不带参数的查找动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	/**
	 * 不带参数的查找动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, int start, int length)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, start, length);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	/**
	 * 不带参数的查找动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, Session s)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, s);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}

	/**
	 * 带一个参数的查找动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, Object paramValue, Type paramType)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, paramValue, paramType);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}

	/**
	 * 带一个参数的查找动作
	 * @param sql
	 * @param paramValue
	 * @param paramType
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, Object paramValue, Type paramType, Session s)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, paramValue, paramType, s);
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}

	/**
	 * 带多个参数的查找动作
	 * <p>从Hibernate2.0升级到Hibernate3.0时本方法被迫改动</p> 
	 * @param query HQL查找语句
	 * @param paramName HQL语句中的参数名数组
	 * @param paramValue 参数的值数组
	 * @param paramType 参数的类型数组
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, String[] paramName, Object[] paramValue, Type[] paramType)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, paramName, paramValue, paramType);			
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	
	/**
	 * 带多个参数的查找动作
	 * <p>
	 * 从Hibernate2.0升级到Hibernate3.0时本方法被迫改动 
	 * <br/>因为3.0后去掉了带参数(Object[], Type[])的方法，无法直接调用
	 * <br/>不能使用Query.getNamedParameters来获取参数名称，
	 * 因为该方法返回的数组是从Map中得到的，顺序不固定
	 * </p>
	 * @param query HQL查找语句
	 * @param paramName HQL语句中的参数名数组
	 * @param paramValue 参数的值数组
	 * @param paramType 参数的类型数组
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, String[] paramName, Object[] paramValue, Type[] paramType, Session s)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, paramName, paramValue, paramType, s);			
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	
	/**
	 * 带多个参数的查找动作
	 * @param query HQL查找语句
	 * @param paramValue 参数的值数组
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, Object[] paramValue)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, paramValue);			
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	
	/**
	 * 带多个参数的查找动作
	 * @param query HQL查找语句
	 * @param paramValue 参数的值数组
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static List find(String sql, Object[] paramValue, Session s)
	throws Exception
	{
		try
		{
			DAO dao = new DAO();
			return dao.find(sql, paramValue, s);			
		}
		catch (HibernateException e)
		{
			throw new Exception(e);
		}
	}
	
	/**
	 * 撤销事务
	 * @param t
	 */
	public static void rollback(Transaction t)
	{
		try{
			t.rollback();
		}catch (HibernateException e1){e1.printStackTrace();}
	}

	/**
	 * 关闭一个会话
	 * @param s
	 */
	public static void close(Session s)
	{
		try{
			s.close();
		}catch (HibernateException e1){e1.printStackTrace();}
	}
	
	//----------------------------------------------------------
	//---对DBSession的MyPersister功能的封装---------
	/**
	 * 使用DBSession而不是DAO对一个数据库对象进行创建。
	 * 要求提前已经用DBContext进行了属性和数据库表字段的映射。
	 * @param obj 实体bean对象
	 * @param tableName 表名
	 * @return 成功则返回1
	 * @throws Exception
	 */
	public static int create(Object obj, String tableName)
	throws Exception
	{
		DBSession ss = null;
		try {
			ss = Context.getDBSession();
			ss.store(tableName, obj );
			return 1;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			CloseHelper.closeQuietly( ss );
		}
	}
	/**
	 * 使用DBSession而不是DAO对一个数据库对象进行删除。
	 * 要求提前已经用DBContext进行了属性和数据库表字段的映射。
	 * @param queryParams 数据库表字段的名值对
	 * @param tableName 表名
	 * @return 删除成功的个数
	 * @throws Exception
	 */
	public static int delete(Map queryParams, String tableName)
	throws Exception
	{
		StringBuffer sb = new StringBuffer();
		sb.append( "delete from " ).append(tableName).append( " where " );

		for ( Iterator i = queryParams.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry me = ( Map.Entry ) i.next();
			sb.append( me.getKey() ).append( "=?" );

			if ( i.hasNext() )
				sb.append( " and " );
		}

		DBSession ss = null;
		try
		{
			ss = Context.getDBSession();
			return ss.executeUpdate( sb.toString(), queryParams.values().toArray() );
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			CloseHelper.closeQuietly( ss );
		}
	}
	/**
	 * 更新数据库中给定的那些bean属性。
	 * 使用DBSession而不是DAO对一个数据库对象进行修改。
	 * 要求提前已经用DBContext进行了属性和数据库表字段的映射。
	 * @param bean
	 * @param tableName 表名
	 * @param dirtyFields 以逗号分隔的bean属性列表
	 * @throws Exception
	 */
	public static int update(Object bean, String tableName, String dirtyFields)
	throws Exception
	{
		String[] dirty = dirtyFields.split( "," );
		DBSession ss = null;
		try {
			ss = Context.getDBSession();
			ss.update(tableName, bean, dirty );
			return 1;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			CloseHelper.closeQuietly( ss );
		}
	}
	/**
	 * 根据给定查询条件查询指定表，返回实体对象列表。<br>
	 * 如果该表未配置映射类，则默认映射到java.util.Map；<br>
	 * 如果未配置列名与属性名的映射规则，则默认采用同名映射
	 * 
	 * @param queryParams 查询参数 {列名 - 列值} 注意：不能含null值
	 * @param tablename 表名
	 * @return bean的集合（若此表存在mappingClass，则为该类实例；否则为Map实例）
	 * @throws Exception
	 */
	public static List query( Map queryParams, String tableName)
	throws Exception
	{
		DBSession ss = null;
		try {
			ss = Context.getDBSession();
			return ss.query(tableName, queryParams );
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			CloseHelper.closeQuietly( ss );
		}
	}
	/**
	 * 根据表名和id从数据库中载入一条记录，装配为实体对象返回。<br>
	 * 如果该表未配置映射类，则默认映射到java.util.Map；<br>
	 * 如果未配置列名与属性名的映射规则，则默认采用同名映射；<br>
	 * 注意：表的标识字段由用户预先配置，也可动态修改。
	 * 
	 * @param tablename 表名
	 * @param id 标识字段的值
	 * @return 实体对象（POJO或Map）
	 * @throws Exception
	 */
	public static Object get(String tableName, Object id)
	throws Exception
	{
		DBSession ss = null;
		try {
			ss = Context.getDBSession();
			return ss.load(tableName, id);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			CloseHelper.closeQuietly( ss );
		}
	}
}