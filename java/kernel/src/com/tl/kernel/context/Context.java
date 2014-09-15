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
		//log4j 初始化配置
		PropertyConfigurator.configure(Utils.getPath("conf/log4j.properties"));
	}
	
	private Context(){
	}
	
	/**
	 * 向Context中添加Spring的ApplicationContext
	 * <BR>方法在com.tl.kernel.load.ContextAware类被Spring加载时，
	 * 自动作为init-method被调用，从而使得Context可以访问Spring的Bean Factory
	 * <BR>这个方法由系统自动调用，请勿手工调用.
	 * 
	 * <BR>setApplicationContext,getBean(Class),getBean(String),getBeanByID(String)
	 * 四个方法在使用时，需要依赖Spring包
	 * @param context
	 */
	public static void setApplicationContext(ApplicationContext context)
	{
		appContext = context;
	}
	/**
	 * 从一个完整的类名中取出去掉包前缀的纯类名
	 * 如：
	 * 若传入的是com.tl.kernel.cat.CatManager
	 * 则返回CatManager
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
	 * 按类名取Spring中配置的Bean
	 * <BR>对传入的类名参数，本方法取出其实际类名（不含包名），
	 * <BR>然后把这个类名作为Spring中的bean id，进行对象查找
	 * <BR>所以注意：可以用本方法调用的Bean在Spring配置时，id必须与类名完全相同
	 * <BR>如：
	 * <BR><bean id="CatManager" 
	 * <BR>		class="com.tl.kernel.cat.Factory" 
	 * <BR>		factory-method="buildCatManager"/>
	 * <BR>调用方式为：
	 * <BR>Context.getBean("CatManager");
	 * <BR>或者
	 * <BR>Context.getBean("com.tl.kernel.cat.CatManager");
	 * @param className 类名
	 * @return
	 */
	public static Object getBean(String className)
	{
		Object obj = appContext.getBean(getBareClassName(className));
		return obj;
		//return FactoryManager.find(className);
	}
	/**
	 * 按类取Spring中配置的Bean
	 * <BR>对传入的Class参数，本方法取出其实际类名（不含包名）
	 * <BR>然后把这个类名作为Spring中的bean id，进行对象查找
	 * <BR>所以注意：可以用本方法调用的Bean在Spring配置时，id必须与类名完全相同
	 * <BR>如：
	 * <BR><bean id="CatManager" 
	 * <BR>		class="com.tl.kernel.cat.Factory" 
	 * <BR>		factory-method="buildCatManager"/>
	 * <BR>调用方式为：
	 * <BR>Context.getBean(CatManager.class);
	 * @param clazz 类
	 * @return
	 */
	public static Object getBean(Class<?> clazz)
	{
		return getBean(clazz.getName());
	}
	
	/**
	 * 按ID取Spring中配置的Bean
	 * <BR>如：
	 * <BR><bean id="abcd" 
	 * <BR>		class="com.tl.kernel.cat.Factory" 
	 * <BR>		factory-method="buildCatManager"/>
	 * <BR>调用方式为：
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
	 * 根据类别名取得Log实例
	 * 
	 * @param category 日志类别名
	 * @return Log实例
	 */
	public static Log getLog( String category ) {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( category );
		return new Logger( category, logger );
	}
	
	/**
	 * 获取设置的系统服务器端的Locale信息
	 * 该信息在sys-config.xml中进行配置
	 * @return
	 */
	public static Locale getLocale()
	{
		return locale;
	}
	/**
	 * 设置系统服务器端的Locale信息
	 * <BR>这个方法在Load类被加载后自动调用，请勿手工调用
	 * @param context
	 */
	public static void setLocale(Locale _locale)
	{
		locale = _locale;
	}
	
	
	
	
	
	/**
	 * 根据数据源名得到连接，若找不到该数据源则返回空
	 * @param strDataSource
	 * @return Connection 若找不到该数据源则返回空
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
	 * 根据JDBC连接串得到连接
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
	 * 内部方法 按数据源名取数据源
	 * @param strDataSource 数据源名
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
	 * 取对中心数据库的DBSession
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
	 * 取某数据源对应的DBSession
	 * <br>从缓存中获取数据源的信息.
	 * 参见<code>getDBSession(int, boolean)</code>
	 * @param dsID 数据源ID
	 */
	public static DBSession getDBSession(int dsID) 
	throws Exception
	{
		return getDBSession(dsID, false);
	}
	/**
	 * 取某数据源对应的DBSession
	 * <br>
	 * 该方法先根据dsID取得登记的数据源信息,
	 * 然后根据配置文件中是否JDBC的设置，
	 * 按不同方式返回DBSession.
	 * <br>
	 * 当配置为使用jdbc时,
	 * 数据源登记中的“数据源”被作为jdbc url,
	 * 然后根据用户名和口令,从DriverManager中取得连接
	 * <br>
	 * 当配置为使用数据源时,
	 * 按数据源登记中的“数据源”来进行lookup,
	 * 取得连接
	 * @param dsID
	 * @param needManager 当false时，从数据源缓存中取数据源信息
	 * true时，做数据库查询，取得数据源信息
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
	 * 按ID取数据源
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
	 * 按数据源获取符合相应数据库类型的DBSession
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
	 * 返回中心数据库的数据库类型
	 * 该信息在配置文件中定义
	 */
	public static DBType getDBType(){
		return configReader.getCentralDB().getType();
	}
	
	/**
	 * 取数据源管理器
	 * 等同于直接用Context.getBean(DSManager.class);
	 */
	public static DSManager getDSManager(){
		return (DSManager)getBean(DSManager.class);
	}

	/**
	 * 取数据源读取器
	 * 等同于直接用Context.getBean(DSReader.class);
	 */
	public static DSReader getDSReader(){
		return (DSReader)getBean(DSReader.class);
	}
	
	/**
	 * 获取系统版本号
	 * @return
	 */
	public static String getVersion() {
		String version = Context.class.getPackage().getImplementationVersion();
		if (version == null) version = "1.0.0";
		return version;
	}
}
