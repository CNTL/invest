package com.tl.db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.db.dialect.type.IBfile;
import com.tl.db.dialect.type.IBlob;
import com.tl.db.dialect.type.IClob;

/**
 * ��java.sql.ResultSet�İ�װ���ṩ��JDBC�������ֱ�ӻ�ȡIClob��IBlob��IBfile����Ľӿڡ�<br>
 * <br>
 * Ŀǰ��ʵ�ַ��صĶ���ռ�����ݿ�������Դ�������ѻ�ʹ��
 */
public interface IResultSet extends ResultSet {

	/**
	 * ���IClob��������<br>
	 * <br>
	 * �÷�����java.sql.Clob�����װΪIClob
	 * 
	 * @param i
	 * @throws SQLException
	 * @throws IOException
	 */
	public IClob getClob2( int i ) throws SQLException, IOException;

	/**
	 * ���IClob��������<br>
	 * <br>
	 * �÷�����java.sql.Clob�����װΪIClob
	 * 
	 * @param columnName
	 * @throws SQLException
	 * @throws IOException
	 */
	public IClob getClob2( String columnName ) throws SQLException, IOException;

	/**
	 * ���IBlob��������<br>
	 * <br>
	 * �÷�����java.sql.Blob�����װΪIBlob
	 * 
	 * @param i
	 * @throws SQLException
	 * @throws IOException
	 */
	public IBlob getBlob2( int i ) throws SQLException, IOException;

	/**
	 * ���IBlob��������<br>
	 * <br>
	 * �÷�����java.sql.Blob�����װΪIBlob
	 * 
	 * @param columnName
	 * @throws SQLException
	 * @throws IOException
	 */
	public IBlob getBlob2( String columnName ) throws SQLException, IOException;

	/**
	 * ���IBfile��������<br>
	 * <br>
	 * �÷�����ResultSet.getObject()�õ�ԭʼ���ݶ��󣬽���BfileFactoryManagerת��
	 * 
	 * @param i
	 * @throws SQLException
	 */
	public IBfile getBfile( int i ) throws SQLException;

	/**
	 * ���IBfile��������<br>
	 * <br>
	 * �÷�����ResultSet.getObject()�õ�ԭʼ���ݶ��󣬽���BfileFactoryManagerת��
	 * 
	 * @param columnName
	 * @throws SQLException
	 */
	public IBfile getBfile( String columnName ) throws SQLException;

}
