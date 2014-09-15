package com.tl.kernel.context;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tl.common.Utils;
import com.tl.db.cfg.DBType;
import com.tl.db.hbm.BaseDAO;
import com.tl.kernel.config.CentralDB;
import com.tl.kernel.config.ConfigReader;
/**
 * 对Hibernate操作的管理类
 * 继承自Hibernate Sychronizer实现的抽象类
 * @author rengy
 *
 */
public class DAO extends BaseDAO {
	private static String configFile = null;
	
	/**
	 * 用配置文件进行Hibernate的SessionFactory初始化
	 * 该方法只在初始化时做一次
	 * 系统加载时自动调用
	 */
	public static synchronized Configuration init()
	throws HibernateException 
	{
		//从e5-config配置文件中读取的中心数据库信息：Hiberante配置文件名
        CentralDB centralDB = ConfigReader.getInstance().getCentralDB();
		if (centralDB == null) throw new RuntimeException("No CentralDB info!");
        configFile = centralDB.getConfigFile();
        
		//若用String方式加载Hibernate有问题
		//则假设不是WEB方式，或配置的是绝对路径，改用File类型加载一次
        Configuration cfg = null;
		try {
			cfg = init(configFile);
		} catch (HibernateException e) {
			String cfgPath = Utils.getPath(configFile);
			System.out.println("Cannot get hibernate config by string:" + configFile);
			System.out.println("Try to use file:"+cfgPath);
			File fileConfig = new File(cfgPath);
			cfg = init(fileConfig);
		}
        
        fillCentralDB(cfg, centralDB);
        return cfg;
	}
	
	/**
	 * 清理Hibernate的SessionFactory，释放掉资源。
	 * @throws HibernateException
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized void reset()
	throws HibernateException 
	{
		if (sessionFactoryMap == null) return;
		
		Collection sfList = sessionFactoryMap.values();
		if (sfList == null || (sfList.size() == 0)) return;
		
		Iterator it = sfList.iterator();
		while (it.hasNext())
		{
			SessionFactory sf = (SessionFactory) it.next();
			sf.close();
		}
		sessionFactoryMap.clear();
	}
    //用返回的Hiberante配置对象，再把中心数据库的url/user/pwd等信息补足
	protected static void fillCentralDB(Configuration cfg, CentralDB centralDB)
	{
        if (cfg == null) return;
        
        String url = readCfgItem(cfg, "connection.datasource");
        String isJDBC = "false";
        if (url == null){//不是数据源方式
        	url = readCfgItem(cfg, "connection.url");
        	isJDBC = "true";
        }
        String user = readCfgItem(cfg, "connection.username");
        String password = readCfgItem(cfg, "connection.password");

        centralDB.setIsJDBC(isJDBC);
        centralDB.setUrl(url);
        centralDB.setUser(user);
        centralDB.setPassword(password);
        
        /**
         * hibernate配置文件中dialect的格式：
         * org.hibernate.dialect.Oracle9Dialect
         * 需要先取出最后一段，然后用我们的类型比较，起始部分相符即可
         * 如oracle/sybase/sqlserver/mysql
         * 
         */
        String dialect = readCfgItem(cfg, "dialect");
        if (dialect == null) return;

        dialect = dialect.substring(dialect.lastIndexOf('.') + 1).toLowerCase();
        
        for (DBType dbType : DBType.values()) {
			if(dialect.startsWith(dbType.typeName())){
				centralDB.setType(dbType);
				break;
			}
		}
	}
	/**
	 * 读hibernate配置文件的一项。若按name找不到，则加前缀“hibernate.”再读一次
	 * @param cfg
	 * @param name
	 * @return
	 */
	protected static String readCfgItem(Configuration cfg, String name) {
        String value = cfg.getProperty(name);
        if (value == null)//可能是property名称改变
        	value = cfg.getProperty("hibernate." + name);
        return value;
	}
	/* (non-Javadoc)
	 * @see com.tl.db.hbm.BaseDAO#getConfigurationFileName()
	 */
	protected String getConfigureFile()
	{
		return configFile;
	}
}
