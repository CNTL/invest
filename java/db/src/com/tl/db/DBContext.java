package com.tl.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.tl.db.cfg.ColumnConfig;
import com.tl.db.cfg.DBConfig;
import com.tl.db.cfg.TableConfig;
import com.tl.db.cfg.ColumnMetaData;
import com.tl.db.cfg.TableMetaData;
import com.tl.db.exception.LaterInitException;
import com.tl.db.util.MetaDataUtils;

/**
 * ���ౣ��Ӧ�����漰����ÿ�����ݿ��������Ϣ���������֡����͡�DataSource�����Լ�ÿ�����ݿ�
 * ����Ӧ�ı��Ԫ���ݣ������ֶΣ������ֶε��ֶ��������͡����ȡ��ɿյȣ�<br>
 * <br>
 * Ϊ��ʹ�÷��㣬�������з������վ�̬���÷�ʽ����<br>
 * <br>
 * Ϊ����ֻʹ��һ�����ݿ⣨���Ǵ󲿷������ʱ���ŷ��㣬�����ṩ�ˡ�ȱʡ���ݿ⡱������<br>
 * <br>
 * �����ṩ���ֳ�ʼ��������Ϣ�ķ�ʽ�������ļ� �� ���ʽ��registerXXX����
 * 
 */
public class DBContext {

	private static final String DEFAULT_DB_NAME = "DEFAULT_DB";

	/**
	 * ���ݿ�������Ϣ���棺���ݿ�����String�� - ���ݿ�������Ϣ����DBConfig��
	 */
	@SuppressWarnings("rawtypes")
	private static final Hashtable dbConfigCache = new Hashtable();

	// ����˽�з�����tablenameһ����Ϊ�Ǵ�д
	// ��Щ˽�е�get����������������Ӧ���ö��󲻴������Զ�����

	@SuppressWarnings("unchecked")
	private static DBConfig getDBConfig( String dbname, String tablename ) {
		DBConfig dc = ( DBConfig ) dbConfigCache.get( dbname );
		if ( dc == null ) {
			dc = new DBConfig( dbname );
			dbConfigCache.put( dbname, dc );
		}
		return dc;
	}

	private static TableConfig getTableConfig( String dbname, String tablename ) {
		DBConfig dc = getDBConfig( dbname, tablename );
		TableConfig tc = dc.getTableConfig( tablename );
		if ( tc == null ) {
			tc = new TableConfig();
			dc.addTableConfig( tablename, tc );
		}
		return tc;
	}

	private static TableMetaData getTableMetaData( String dbname,
			String tablename ) {
		TableConfig tc = getTableConfig( dbname, tablename );
		TableMetaData tmd = tc.getMetaData();
		if ( tmd == null ) {
			tmd = new TableMetaData( tablename );
			tc.setMetaData( tmd );
		}
		return tmd;
	}

	// ------------------------------------------------------------------------

	/**
	 * @param dbname
	 * @param tablename
	 * @param idColumn
	 */
	public static void registerIdColumn( String dbname, String tablename,
			String idColumn ) {
		tablename = tablename.toUpperCase();
		getTableMetaData( dbname, tablename ).setIdColumn( idColumn );
	}

	/**
	 * ע���ʶ�ֶ�
	 * 
	 * @param tablename
	 * @param idColumn
	 */
	public static void registerIdColumn( String tablename, String idColumn ) {
		registerIdColumn( DEFAULT_DB_NAME, tablename, idColumn );
	}

	/**
	 * ע��ӳ����
	 * 
	 * @param dbname
	 * @param tablename
	 * @param mappingClass
	 */
	@SuppressWarnings("rawtypes")
	public static void registerMappingClass( String dbname, String tablename,
			Class mappingClass ) {
		tablename = tablename.toUpperCase();
		getTableConfig( dbname, tablename ).setMappingClass( mappingClass );
	}

	/**
	 * ע��ӳ����
	 * 
	 * @param tablename
	 * @param mappingClass
	 */
	@SuppressWarnings("rawtypes")
	public static void registerMappingClass( String tablename,
			Class mappingClass ) {
		registerMappingClass( DEFAULT_DB_NAME, tablename, mappingClass );
	}

	/**
	 * ע��������ӳ���ϵ�����������������ӳ��ͱ�����������������ӳ��
	 * 
	 * @param dbname ���ݿ���
	 * @param tablename ����
	 * @param mappingClass ��
	 * @param propMapping ������������������ӳ�� {key-���� value-������}
	 */
	@SuppressWarnings("rawtypes")
	public static void registerMapping( String dbname, String tablename,
			Class mappingClass, Map propMapping ) {
		tablename = tablename.toUpperCase();
		TableConfig tc = getTableConfig( dbname, tablename );

		tc.setMappingClass( mappingClass );

		for ( Iterator i = propMapping.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry me = ( Map.Entry ) i.next();

			String columnName = ( String ) me.getKey();
			String fieldName = ( String ) me.getValue();

			tc.addPropMapping( columnName, fieldName );
		}
	}

	/**
	 * ע��������ӳ���ϵ�����������������ӳ��ͱ�����������������ӳ��
	 * 
	 * @param tablename ����
	 * @param mappingClass ��
	 * @param propMapping ������������������ӳ�� {key-���� value-������}
	 */
	@SuppressWarnings("rawtypes")
	public static void registerMapping( String tablename, Class mappingClass,
			Map propMapping ) {
		registerMapping( DEFAULT_DB_NAME, tablename, mappingClass, propMapping );
	}

	// ------------------------------------------------------------------------

	/**
	 * ��ȡ���������Ϣ�������������Զ�������
	 * 
	 * @param dbname ���������ݿ�
	 * @param tablename �������Զ���תΪ��д
	 * @param DBSessionʵ��������Ԫ���ݲ��������Զ�������
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TableConfig getTableConfig( String dbname, String tablename,
			DBSession sess ) {
		tablename = tablename.toUpperCase();

		TableConfig tc = getTableConfig( dbname, tablename );
		TableMetaData tmd = tc.getMetaData();

		if ( tmd == null ) {
			tmd = new TableMetaData( tablename );
			tc.setMetaData( tmd );
		}

		try {

			// ��ʱ������Ԫ���ݲ����ڣ���ִ�����ݿ��ѯ���Զ�����Ԫ����

			if ( tmd.getColumns().length == 0 )
				MetaDataUtils.fillMetaData( tmd, sess);

			// ��ColumnMetaData��װΪColumnConfig

			ColumnMetaData[] columns = tmd.getColumns();
			HashMap tmp = new HashMap( columns.length );
			for (int i = 0; i < columns.length; i++) {
				ColumnMetaData cmd = columns[i];
				ColumnConfig cc = new ColumnConfig( cmd );
				tmp.put( cmd.getCode(), cc );
			}

			tc.setColumns( tmp );

		} catch ( SQLException e ) {
			throw new LaterInitException( e );
		}

		return tc;
	}

	/**
	 * ��ȡ���������Ϣ��ʹ��Ĭ�����ݿ⣩
	 * 
	 * @param tablename �������Զ���תΪ��д
	 * @param DBSessionʵ�������ڵ���Ҫ�ı�Ԫ�����в�����ʱ�Զ�����
	 * @return
	 */
	public static TableConfig getTableConfig( String tablename, DBSession sess ) {
		return getTableConfig( DEFAULT_DB_NAME, tablename, sess );
	}

}
