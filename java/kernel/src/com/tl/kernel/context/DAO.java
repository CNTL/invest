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
 * ��Hibernate�����Ĺ�����
 * �̳���Hibernate Sychronizerʵ�ֵĳ�����
 * @author rengy
 *
 */
public class DAO extends BaseDAO {
	private static String configFile = null;
	
	/**
	 * �������ļ�����Hibernate��SessionFactory��ʼ��
	 * �÷���ֻ�ڳ�ʼ��ʱ��һ��
	 * ϵͳ����ʱ�Զ�����
	 */
	public static synchronized Configuration init()
	throws HibernateException 
	{
		//��e5-config�����ļ��ж�ȡ���������ݿ���Ϣ��Hiberante�����ļ���
        CentralDB centralDB = ConfigReader.getInstance().getCentralDB();
		if (centralDB == null) throw new RuntimeException("No CentralDB info!");
        configFile = centralDB.getConfigFile();
        
		//����String��ʽ����Hibernate������
		//����費��WEB��ʽ�������õ��Ǿ���·��������File���ͼ���һ��
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
	 * ����Hibernate��SessionFactory���ͷŵ���Դ��
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
    //�÷��ص�Hiberante���ö����ٰ��������ݿ��url/user/pwd����Ϣ����
	protected static void fillCentralDB(Configuration cfg, CentralDB centralDB)
	{
        if (cfg == null) return;
        
        String url = readCfgItem(cfg, "connection.datasource");
        String isJDBC = "false";
        if (url == null){//��������Դ��ʽ
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
         * hibernate�����ļ���dialect�ĸ�ʽ��
         * org.hibernate.dialect.Oracle9Dialect
         * ��Ҫ��ȡ�����һ�Σ�Ȼ�������ǵ����ͱȽϣ���ʼ�����������
         * ��oracle/sybase/sqlserver/mysql
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
	 * ��hibernate�����ļ���һ�����name�Ҳ��������ǰ׺��hibernate.���ٶ�һ��
	 * @param cfg
	 * @param name
	 * @return
	 */
	protected static String readCfgItem(Configuration cfg, String name) {
        String value = cfg.getProperty(name);
        if (value == null)//������property���Ƹı�
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
