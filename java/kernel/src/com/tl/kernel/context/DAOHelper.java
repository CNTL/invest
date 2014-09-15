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
 * ��DAO�Ľ�һ����װ����̬�������������
 * @created on 2005-7-21
 * @author Gong Lijie
 */
@SuppressWarnings("rawtypes")
public class DAOHelper
{
	/** 
	* @author  leijj 
	* ���ܣ� ���ݱ�����ȡ��¼����
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
	* ���ܣ� ���ݱ�����ȡ��¼����
	* @tableName ����
	* @filterSql ��ѯ����
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
	 * ����������ɾ������
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
	 * ����������ɾ���������⴫��Session
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
	 * ��һ��������ɾ������
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
	 * ��һ��������ɾ���������⴫��Session
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
	 * �����������ɾ������
	 * <p>
	 * ��Hibernate2.0������Hibernate3.0ʱ���������ȸĶ�
	 * <br/>��Ϊ3.0��ȥ���˴�����(Object[], Type[])�ķ������޷�ֱ�ӵ��� 
	 * <br/>����ʹ��Query.getNamedParameters����ȡ�������ƣ� 
	 * ��Ϊ�÷������ص������Ǵ�Map�еõ��ģ�˳�򲻹̶�
	 * </p>
	 * @param query HQLɾ�����
	 * @param paramName HQL����еĲ���������
	 * @param paramValue ������ֵ����
	 * @param paramType ��������������
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
	 * �����������ɾ������������Session
	 * <p>
	 * ��Hibernate2.0������Hibernate3.0ʱ���������ȸĶ� 
	 * <br/>��Ϊ3.0��ȥ���˴�����(Object[], Type[])�ķ������޷�ֱ�ӵ���
	 * <br/>����ʹ��Query.getNamedParameters����ȡ�������ƣ�
	 * ��Ϊ�÷������ص������Ǵ�Map�еõ��ģ�˳�򲻹̶�
	 * </p>
	 * @param query HQLɾ�����
	 * @param paramName HQL����еĲ���������
	 * @param paramValue ������ֵ����
	 * @param paramType ��������������
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
	 * �����������ɾ������
	 * @param query HQLɾ�����
	 * @param paramValue ������ֵ����
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
	 * �����������ɾ������������Session
	 * @param query HQLɾ�����
	 * @param paramValue ������ֵ����
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
	 * ���������Ĳ��Ҷ���
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
	 * ���������Ĳ��Ҷ���
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
	 * ���������Ĳ��Ҷ���
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
	 * ��һ�������Ĳ��Ҷ���
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
	 * ��һ�������Ĳ��Ҷ���
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
	 * ����������Ĳ��Ҷ���
	 * <p>��Hibernate2.0������Hibernate3.0ʱ���������ȸĶ�</p> 
	 * @param query HQL�������
	 * @param paramName HQL����еĲ���������
	 * @param paramValue ������ֵ����
	 * @param paramType ��������������
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
	 * ����������Ĳ��Ҷ���
	 * <p>
	 * ��Hibernate2.0������Hibernate3.0ʱ���������ȸĶ� 
	 * <br/>��Ϊ3.0��ȥ���˴�����(Object[], Type[])�ķ������޷�ֱ�ӵ���
	 * <br/>����ʹ��Query.getNamedParameters����ȡ�������ƣ�
	 * ��Ϊ�÷������ص������Ǵ�Map�еõ��ģ�˳�򲻹̶�
	 * </p>
	 * @param query HQL�������
	 * @param paramName HQL����еĲ���������
	 * @param paramValue ������ֵ����
	 * @param paramType ��������������
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
	 * ����������Ĳ��Ҷ���
	 * @param query HQL�������
	 * @param paramValue ������ֵ����
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
	 * ����������Ĳ��Ҷ���
	 * @param query HQL�������
	 * @param paramValue ������ֵ����
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
	 * ��������
	 * @param t
	 */
	public static void rollback(Transaction t)
	{
		try{
			t.rollback();
		}catch (HibernateException e1){e1.printStackTrace();}
	}

	/**
	 * �ر�һ���Ự
	 * @param s
	 */
	public static void close(Session s)
	{
		try{
			s.close();
		}catch (HibernateException e1){e1.printStackTrace();}
	}
	
	//----------------------------------------------------------
	//---��DBSession��MyPersister���ܵķ�װ---------
	/**
	 * ʹ��DBSession������DAO��һ�����ݿ������д�����
	 * Ҫ����ǰ�Ѿ���DBContext���������Ժ����ݿ���ֶε�ӳ�䡣
	 * @param obj ʵ��bean����
	 * @param tableName ����
	 * @return �ɹ��򷵻�1
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
	 * ʹ��DBSession������DAO��һ�����ݿ�������ɾ����
	 * Ҫ����ǰ�Ѿ���DBContext���������Ժ����ݿ���ֶε�ӳ�䡣
	 * @param queryParams ���ݿ���ֶε���ֵ��
	 * @param tableName ����
	 * @return ɾ���ɹ��ĸ���
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
	 * �������ݿ��и�������Щbean���ԡ�
	 * ʹ��DBSession������DAO��һ�����ݿ��������޸ġ�
	 * Ҫ����ǰ�Ѿ���DBContext���������Ժ����ݿ���ֶε�ӳ�䡣
	 * @param bean
	 * @param tableName ����
	 * @param dirtyFields �Զ��ŷָ���bean�����б�
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
	 * ���ݸ�����ѯ������ѯָ��������ʵ������б�<br>
	 * ����ñ�δ����ӳ���࣬��Ĭ��ӳ�䵽java.util.Map��<br>
	 * ���δ������������������ӳ�������Ĭ�ϲ���ͬ��ӳ��
	 * 
	 * @param queryParams ��ѯ���� {���� - ��ֵ} ע�⣺���ܺ�nullֵ
	 * @param tablename ����
	 * @return bean�ļ��ϣ����˱����mappingClass����Ϊ����ʵ��������ΪMapʵ����
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
	 * ���ݱ�����id�����ݿ�������һ����¼��װ��Ϊʵ����󷵻ء�<br>
	 * ����ñ�δ����ӳ���࣬��Ĭ��ӳ�䵽java.util.Map��<br>
	 * ���δ������������������ӳ�������Ĭ�ϲ���ͬ��ӳ�䣻<br>
	 * ע�⣺��ı�ʶ�ֶ����û�Ԥ�����ã�Ҳ�ɶ�̬�޸ġ�
	 * 
	 * @param tablename ����
	 * @param id ��ʶ�ֶε�ֵ
	 * @return ʵ�����POJO��Map��
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