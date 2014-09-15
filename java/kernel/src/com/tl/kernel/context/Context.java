package com.tl.kernel.context;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javax.naming.NamingException;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;

import com.tl.common.Utils;
import com.tl.common.log.Log;
import com.tl.common.log.Logger;
import com.tl.kernel.config.CentralDB;
import com.tl.kernel.config.ConfigReader;
import com.tl.db.DBSession;
import com.tl.db.DBSessionFactory;
import com.tl.db.DataSource;
import com.tl.db.cfg.DBType;

public class Context {
	private static ConfigReader configReader = ConfigReader.getInstance();
	private static ApplicationContext appContext;
	private static Locale locale;
	
	static{
		//log4j ��ʼ������
		PropertyConfigurator.configure(Utils.getPath("conf/log4j.properties"));
	}
	
	private Context(){
	}
	
	/**
	 * ��Context�����Spring��ApplicationContext
	 * <BR>������com.tl.kernel.load.ContextAware�౻Spring����ʱ��
	 * �Զ���Ϊinit-method�����ã��Ӷ�ʹ��Context���Է���Spring��Bean Factory
	 * <BR>���������ϵͳ�Զ����ã������ֹ�����.
	 * 
	 * <BR>setApplicationContext,getBean(Class),getBean(String),getBeanByID(String)
	 * �ĸ�������ʹ��ʱ����Ҫ����Spring��
	 * @param context
	 */
	public static void setApplicationContext(ApplicationContext context)
	{
		appContext = context;
	}
	/**
	 * ��һ��������������ȡ��ȥ����ǰ׺�Ĵ�����
	 * �磺
	 * ���������com.tl.kernel.cat.CatManager
	 * �򷵻�CatManager
	 * @param className
	 * @return
	 */
	private static String getBareClassName(String className)
	{
		int pos = className.lastIndexOf('.');
		if (pos < 0)
			return className;
		return className.substring(pos + 1);
	}
	/**
	 * ������ȡSpring�����õ�Bean
	 * <BR>�Դ��������������������ȡ����ʵ��������������������
	 * <BR>Ȼ������������ΪSpring�е�bean id�����ж������
	 * <BR>����ע�⣺�����ñ��������õ�Bean��Spring����ʱ��id������������ȫ��ͬ
	 * <BR>�磺
	 * <BR><bean id="CatManager" 
	 * <BR>		class="com.tl.kernel.cat.Factory" 
	 * <BR>		factory-method="buildCatManager"/>
	 * <BR>���÷�ʽΪ��
	 * <BR>Context.getBean("CatManager");
	 * <BR>����
	 * <BR>Context.getBean("com.tl.kernel.cat.CatManager");
	 * @param className ����
	 * @return
	 */
	public static Object getBean(String className)
	{
		Object obj = appContext.getBean(getBareClassName(className));
		return obj;
		//return FactoryManager.find(className);
	}
	/**
	 * ����ȡSpring�����õ�Bean
	 * <BR>�Դ����Class������������ȡ����ʵ������������������
	 * <BR>Ȼ������������ΪSpring�е�bean id�����ж������
	 * <BR>����ע�⣺�����ñ��������õ�Bean��Spring����ʱ��id������������ȫ��ͬ
	 * <BR>�磺
	 * <BR><bean id="CatManager" 
	 * <BR>		class="com.tl.kernel.cat.Factory" 
	 * <BR>		factory-method="buildCatManager"/>
	 * <BR>���÷�ʽΪ��
	 * <BR>Context.getBean(CatManager.class);
	 * @param clazz ��
	 * @return
	 */
	public static Object getBean(Class<?> clazz)
	{
		return getBean(clazz.getName());
	}
	
	/**
	 * ��IDȡSpring�����õ�Bean
	 * <BR>�磺
	 * <BR><bean id="abcd" 
	 * <BR>		class="com.tl.kernel.cat.Factory" 
	 * <BR>		factory-method="buildCatManager"/>
	 * <BR>���÷�ʽΪ��
	 * <BR>Context.getBeanByID("abcd");
	 * @param id
	 * @return
	 */
	public static Object getBeanByID(String id)
	{
		return appContext.getBean(id);
		//return FactoryManager.findByID(id);
	}	
	
	/**
	 * ���������ȡ��Logʵ��
	 * 
	 * @param category ��־�����
	 * @return Logʵ��
	 */
	public static Log getLog( String category ) {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( category );
		return new Logger( category, logger );
	}
	
	/**
	 * ��ȡ���õ�ϵͳ�������˵�Locale��Ϣ
	 * ����Ϣ��sys-config.xml�н�������
	 * @return
	 */
	public static Locale getLocale()
	{
		return locale;
	}
	/**
	 * ����ϵͳ�������˵�Locale��Ϣ
	 * <BR>���������Load�౻���غ��Զ����ã������ֹ�����
	 * @param context
	 */
	public static void setLocale(Locale _locale)
	{
		locale = _locale;
	}
	
	
	
	
	
	/**
	 * ��������Դ���õ����ӣ����Ҳ���������Դ�򷵻ؿ�
	 * @param strDataSource
	 * @return Connection ���Ҳ���������Դ�򷵻ؿ�
	 * @throws Exception
	 */
	private static Connection getConnection(String strDataSource) 
	throws Exception 
	{
		try
		{
			javax.sql.DataSource ds = getDataSource(strDataSource);
			if (ds != null)
				return ds.getConnection();
			else 
				return null;
		}
		catch (SQLException e)
		{
			throw new Exception("[Context.getConnection]", e);
		}
	}
	
	/**
	 * ����JDBC���Ӵ��õ�����
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	private static Connection getConnection(String url, String user, String password) 
	throws Exception 
	{
		try
		{
			return java.sql.DriverManager.getConnection(url, user, password);
		}
		catch (SQLException e)
		{
			throw new Exception("[Context.getConnection]", e);
		}
	}
	
	/**
	 * �ڲ����� ������Դ��ȡ����Դ
	 * @param strDataSource ����Դ��
	 * @return
	 * @throws Exception
	 */
	private static javax.sql.DataSource getDataSource(String strDataSource) 
	throws Exception 
	{
		try
		{
			javax.naming.Context ctx = new  javax.naming.InitialContext();
			return (javax.sql.DataSource)ctx.lookup(strDataSource);
		}
		catch (NamingException e)
		{
			throw new Exception("[Context.getConnection]", e);
		}
	}
	
	/**
	 * ȡ���������ݿ��DBSession
	 */
	public static DBSession getDBSession() 
	throws TLException 
	{
		CentralDB centralDB = configReader.getCentralDB();
		if (centralDB.useJDBC())
			return getDBSession(centralDB.getType(), centralDB.getUrl(), centralDB.getUser(), centralDB.getPassword());
		else
			return getDBSession(centralDB.getType(), centralDB.getUrl());
	}

	/**
	 * ȡĳ����Դ��Ӧ��DBSession
	 * <br>�ӻ����л�ȡ����Դ����Ϣ.
	 * �μ�<code>getDBSession(int, boolean)</code>
	 * @param dsID ����ԴID
	 */
	public static DBSession getDBSession(int dsID) 
	throws Exception
	{
		return getDBSession(dsID, false);
	}
	/**
	 * ȡĳ����Դ��Ӧ��DBSession
	 * <br>
	 * �÷����ȸ���dsIDȡ�õǼǵ�����Դ��Ϣ,
	 * Ȼ����������ļ����Ƿ�JDBC�����ã�
	 * ����ͬ��ʽ����DBSession.
	 * <br>
	 * ������Ϊʹ��jdbcʱ,
	 * ����Դ�Ǽ��еġ�����Դ������Ϊjdbc url,
	 * Ȼ������û����Ϳ���,��DriverManager��ȡ������
	 * <br>
	 * ������Ϊʹ������Դʱ,
	 * ������Դ�Ǽ��еġ�����Դ��������lookup,
	 * ȡ������
	 * @param dsID
	 * @param needManager ��falseʱ��������Դ������ȡ����Դ��Ϣ
	 * trueʱ�������ݿ��ѯ��ȡ������Դ��Ϣ
	 * @return
	 * @throws Exception
	 */
	public static DBSession getDBSession(int dsID, boolean needManager) 
	throws Exception
	{
		DataSource ds = getDataSource(dsID, needManager);
		return getDBSession(ds);
	}
	
	/**
	 * ��IDȡ����Դ
	 * @param dsID
	 * @return
	 * @throws Exception
	 */
	private static DataSource getDataSource(int dsID, boolean needManager) 
	throws Exception 
	{
		DSReader dsReader = null;
		if (needManager) dsReader = getDSManager();
		else dsReader = getDSReader();
		
		return dsReader.get(dsID);
	}
	
	/**
	 * ������Դ��ȡ������Ӧ���ݿ����͵�DBSession
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public static DBSession getDBSession(DataSource ds) 
	throws Exception
	{
		CentralDB centralDB = configReader.getCentralDB();
		if (centralDB.useJDBC())
			return getDBSession(ds.getDbType(), ds.getDataSource(), ds.getUser(), ds.getPassword());
		else
			return getDBSession(ds.getDbType(), ds.getDataSource());
	}
	

	private static DBSession getDBSession(DBType dbType, String strDataSource) 
	throws TLException 
	{
		try
		{
			DBSession dbSession = DBSessionFactory.getDBSession(dbType);
			dbSession.setConnection(getConnection(strDataSource));
			return dbSession;
		}
		catch (Exception e)
		{
			throw new TLException(e);
		}
	}
	
	private static DBSession getDBSession(DBType dbType, String url, String user, String password) 
	throws TLException 
	{
		try
		{
			DBSession dbSession = DBSessionFactory.getDBSession(dbType);
			dbSession.setConnection(getConnection(url, user, password));
			return dbSession;
		}
		catch (Exception e)
		{
			throw new TLException(e);
		}
	}
	/**
	 * �����������ݿ�����ݿ�����
	 * ����Ϣ�������ļ��ж���
	 */
	public static DBType getDBType(){
		return configReader.getCentralDB().getType();
	}
	
	/**
	 * ȡ����Դ������
	 * ��ͬ��ֱ����Context.getBean(DSManager.class);
	 */
	public static DSManager getDSManager(){
		return (DSManager)getBean(DSManager.class);
	}

	/**
	 * ȡ����Դ��ȡ��
	 * ��ͬ��ֱ����Context.getBean(DSReader.class);
	 */
	public static DSReader getDSReader(){
		return (DSReader)getBean(DSReader.class);
	}
	
	/**
	 * ��ȡϵͳ�汾��
	 * @return
	 */
	public static String getVersion() {
		String version = Context.class.getPackage().getImplementationVersion();
		if (version == null) version = "1.0.0";
		return version;
	}
}
