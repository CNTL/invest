package com.tl.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tl.db.cfg.DBType;
import com.tl.db.dialect.Dialect;
import com.tl.db.util.DBUtils;

/**
 * DBSession�����������ض��ھ������ݿ��DBSessionʵ�����ʵ��<br>
 * <br>
 * Ŀǰ�Ĳ����ǣ��Ȳ����û���ע���DBSessionʵ�֣����Ҳ����������Ĭ��ʵ�֣�BaseDBSession��
 * 
 */
public class DBSessionFactory {

	static Log log = LogFactory.getLog( "rengy" );

	/**
	 * ���ݿ����Ͷ��壨���ݿ�������--ʵ���ࣩ����
	 */
	@SuppressWarnings("rawtypes")
	private static HashMap dbdef = new HashMap();
	
	static
	{
		try
		{
			//�Զ�ע�������dbsession�����������ļ����滻
			registerDB(DBType.POSTGRESQL.name(), "com.tl.db.PostgreSQLDBSession");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * ע�����ݿ����ͼ���DBSessionʵ����
	 * 
	 * @param dbname ���ݿ�������
	 * @param implementation ʵ������
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static void registerDB( String dbname, String implementation )
			throws ClassNotFoundException {
		Class clazz = Class.forName( implementation );
		registerDB( dbname, clazz );
	}

	/**
	 * ע�����ݿ����ͼ���DBSessionʵ����
	 * 
	 * @param dbname ���ݿ�������
	 * @param implemention ʵ����
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void registerDB( String dbname, Class implemention ) {
		dbdef.put( dbname, implemention );
	}

	/**
	 * �������ݿ���������ö�Ӧ��ʵ��������Ȼ�󷵻ظ���ʵ��<br>
	 * <br>
	 * ע�⣺�÷������ص�DBSessionʵ����δע��Connection���û�ʹ��ǰ���ֶ�ע��
	 * 
	 * @param dbtype ���ݿ����������������ֲ�ͬ���͵����ݿ⣩
	 */
	@SuppressWarnings("rawtypes")
	public static DBSession getDBSession( DBType dbType ) {

		// �Ȳ����Ƿ������ע��ʵ���࣬���У������
		Class clazz = ( Class ) dbdef.get( dbType.typeName() );
		if ( clazz != null ) {
			try {
				return ( DBSession ) clazz.newInstance();
			} catch ( Exception e ) {
				log.error( "newInstance error! " + clazz, e );
			}
		}

		// �Ҳ�����ʵ����ʧ�ܣ������Ĭ��ʵ��
		Dialect dialect = dbType.dialcet();
		if ( dialect == null )
			throw new IllegalArgumentException( "unsupported dbtype: " + dbType.typeName() );

		BaseDBSession dbsession = new BaseDBSession( dialect );
		return dbsession;
	}

	/**
	 * ����ע���Connection����²����ݿ����ͣ�Ȼ�󴴽���Ӧ��DBSessionʵ��
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException ����ȡ���ݿ�Ԫ���ݳ������׳��쳣
	 */
	@SuppressWarnings("rawtypes")
	public static DBSession getDBSession( Connection conn ) throws SQLException {

		DBType dbtype = DBUtils.getDbType(conn);

		// �Ȳ����Ƿ������ע��ʵ���࣬���У������
		Class clazz = ( Class ) dbdef.get( dbtype.typeName() );
		if ( clazz != null ) {
			try {
				DBSession result = ( DBSession ) clazz.newInstance();
				result.setConnection( conn );
				return result;
			} catch ( Exception e ) {
				log.error( "newInstance error! " + clazz, e );
			}
		}

		// �Ҳ�����ʵ����ʧ�ܣ������Ĭ��ʵ��
		Dialect dialect = dbtype.dialcet();
		if ( dialect == null )
			throw new IllegalArgumentException( "unsupported dbtype: " + dbtype );

		BaseDBSession dbsession = new BaseDBSession( dialect );
		return dbsession;
	}

}
