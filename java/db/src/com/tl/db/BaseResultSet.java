package com.tl.db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tl.db.dialect.type.BfileFactoryManager;
import com.tl.db.dialect.type.IBfile;
import com.tl.db.dialect.type.IBlob;
import com.tl.db.dialect.type.IClob;
import com.tl.db.dialect.type.LobHelper;
import com.tl.db.util.CloseHelper;

/**
 * ��IResultSet�ĳ���ʵ�֣�������������ݿ���صķ�����������ʵ��
 * 
 */
class BaseResultSet extends ResultSetWrapper implements IResultSet {

	/**
	 * ��������
	 * 
	 * @param rs
	 * @return
	 */
	static IResultSet createResultSet( ResultSet rs ) {
		return new BaseResultSet( rs );
	}

	// ------------------------------------------------------------------------

	/**
	 * ���캯��
	 * 
	 * @param ResultSet
	 */
	public BaseResultSet( ResultSet underlying ) {
		super( underlying );
	}

	// ------------------------------------------------------------------------

	/**
	 * ��ȡIClob��������
	 * 
	 * @param i i
	 * @throws IOException
	 */
	public IClob getClob2( int i ) throws SQLException, IOException {
		return LobHelper.getClob(underlying, i);
	}

	/**
	 * ��ȡIClob��������
	 * 
	 * @param columnName columnName
	 * @throws IOException
	 */
	public IClob getClob2( String columnName ) throws SQLException, IOException {
		return LobHelper.getClob(underlying, columnName);
	}

	/**
	 * ��ȡIBlob��������
	 * 
	 * @param i i
	 * @throws IOException
	 */
	public IBlob getBlob2( int i ) throws SQLException, IOException {
		return LobHelper.getBlob(underlying, i);
	}

	/**
	 * ��ȡIBlob��������
	 * 
	 * @param columnName columnName
	 * @throws IOException
	 */
	public IBlob getBlob2( String columnName ) throws SQLException, IOException {
		return LobHelper.getBlob(underlying, columnName);
	}

	/**
	 * ��ȡIBfile��������<br>
	 * ����ֻ�Ǽ򵥵�ȡ�ض�����BfileFactoryManager����ת��ΪIBfile����
	 * 
	 * @see com.tl.db.IResultSet#getBfile(int)
	 */
	public IBfile getBfile( int i ) throws SQLException {
		Object obj = underlying.getObject( i );
		if ( obj == null )
			return null;

		return BfileFactoryManager.convert( obj );
	}

	/**
	 * ��ȡIBfile��������<br>
	 * ����ֻ�Ǽ򵥵�ȡ�ض�����BfileFactoryManager����ת��ΪIBfile����
	 * 
	 * @see com.tl.db.IResultSet#getBfile(java.lang.String)
	 */
	public IBfile getBfile( String columnName ) throws SQLException {
		Object obj = underlying.getObject( columnName );
		if ( obj == null )
			return null;

		return BfileFactoryManager.convert( obj );
	}

	// ------------------------------------------------------------------------

	/**
	 * �رյײ���������֮���������
	 */
	public void close() throws SQLException {
		if ( underlying != null ) {
			Statement stmt = underlying.getStatement();

			try {
				CloseHelper.closeQuietly( underlying );
			} finally {
				CloseHelper.closeQuietly( stmt );
			}
		}
	}

}
