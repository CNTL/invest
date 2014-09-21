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
 * DBSession工厂，生成特定于具体数据库的DBSession实现类的实例<br>
 * <br>
 * 目前的策略是：先查找用户已注册的DBSession实现，若找不到，则采用默认实现（BaseDBSession）
 * 
 */
public class DBSessionFactory {

	static Log log = LogFactory.getLog( "tl" );

	/**
	 * 数据库类型定义（数据库类型名--实现类）缓存
	 */
	@SuppressWarnings("rawtypes")
	private static HashMap dbdef = new HashMap();
	
	static
	{
		try
		{
			//自动注册基本的dbsession，可在配置文件中替换
			registerDB(DBType.POSTGRESQL.name(), "com.tl.db.PostgreSQLDBSession");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 注册数据库类型及其DBSession实现类
	 * 
	 * @param dbname 数据库类型名
	 * @param implementation 实现类名
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static void registerDB( String dbname, String implementation )
			throws ClassNotFoundException {
		Class clazz = Class.forName( implementation );
		registerDB( dbname, clazz );
	}

	/**
	 * 注册数据库类型及其DBSession实现类
	 * 
	 * @param dbname 数据库类型名
	 * @param implemention 实现类
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void registerDB( String dbname, Class implemention ) {
		dbdef.put( dbname, implemention );
	}

	/**
	 * 根据数据库类型名获得对应的实现类名，然后返回该类实例<br>
	 * <br>
	 * 注意：该方法返回的DBSession实例尚未注入Connection，用户使用前需手动注入
	 * 
	 * @param dbtype 数据库类型名（用来区分不同类型的数据库）
	 */
	@SuppressWarnings("rawtypes")
	public static DBSession getDBSession( DBType dbType ) {

		// 先查找是否存在已注册实现类，如有，则采用
		Class clazz = ( Class ) dbdef.get( dbType.typeName() );
		if ( clazz != null ) {
			try {
				return ( DBSession ) clazz.newInstance();
			} catch ( Exception e ) {
				log.error( "newInstance error! " + clazz, e );
			}
		}

		// 找不到或实例化失败，则采用默认实现
		Dialect dialect = dbType.dialcet();
		if ( dialect == null )
			throw new IllegalArgumentException( "unsupported dbtype: " + dbType.typeName() );

		BaseDBSession dbsession = new BaseDBSession( dialect );
		return dbsession;
	}

	/**
	 * 根据注入的Connection对象猜测数据库类型，然后创建相应的DBSession实例
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException 若获取数据库元数据出错，则抛出异常
	 */
	@SuppressWarnings("rawtypes")
	public static DBSession getDBSession( Connection conn ) throws SQLException {

		DBType dbtype = DBUtils.getDbType(conn);

		// 先查找是否存在已注册实现类，如有，则采用
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

		// 找不到或实例化失败，则采用默认实现
		Dialect dialect = dbtype.dialcet();
		if ( dialect == null )
			throw new IllegalArgumentException( "unsupported dbtype: " + dbtype );

		BaseDBSession dbsession = new BaseDBSession( dialect );
		return dbsession;
	}

}
