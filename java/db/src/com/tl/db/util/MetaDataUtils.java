package com.tl.db.util;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.tl.db.DBSession;
import com.tl.db.cfg.ColumnMetaData;
import com.tl.db.cfg.DBType;
import com.tl.db.cfg.TableMetaData;

/**
 * �������ڸ���JDBC�ṩ��ResultSetMetaData̽���Ԫ������Ϣ
 * 
 * @author rengy
 */
public class MetaDataUtils {

	/**
	 * ͨ��"select * from $TABLE"��ѯ���ͽ����Ԫ���ݻ�ñ�Ԫ����
	 * 
	 * @param TableMetaData
	 * @param sess
	 * @param tablename
	 * @return TableMetaData
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public static TableMetaData fillMetaData( TableMetaData tmd,
			DBSession sess) throws SQLException {
		String tablename = tmd.getName();
		List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();
		String sql = "select * from " + tablename;

		ResultSet rs = sess.executeQuery( sql );

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();

			for ( int i = 1; i < cnt + 1; i++ ) {
				String name = rsmd.getColumnName( i );

				ColumnMetaData cmd = new ColumnMetaData( name );

				cmd.setType( rsmd.getColumnType( i ) );
				cmd.setTypeName( rsmd.getColumnTypeName( i ) );
				cmd.setLength( rsmd.getColumnDisplaySize( i ) );
				cmd.setScale( rsmd.getScale( i ) );
				cmd.setPrecision( rsmd.getPrecision( i ) );
				if ( rsmd.isNullable( i ) == ResultSetMetaData.columnNoNulls )
					cmd.setNullable( false );

				columns.add( cmd );
			}

		} finally {
			CloseHelper.closeQuietly( rs );
		}

		if ( tmd == null )
			tmd = new TableMetaData( tablename );

		tmd.setColumns( columns.toArray(new ColumnMetaData[0]) );

		// ����̽��������Ϣ

		// JDBC meta data��ʽ
		tmd.setIdColumns( getPKColumns( sess, tablename ) );

		// ����ķ�ʽ���еĻ�
		if ( tmd.getIdColumns().length == 0 ) {

			if ( DBType.ORACLE.equals( sess.getDialect().getDBType() ) ) {
				try {
					String[] ids = getOraclePKColumn( sess, tablename );
					tmd.setIdColumns( ids );
				} catch ( SQLException e ) {
					// ignore this
				}
			}

			else if ( DBType.SQLSERVER.equals( sess.getDialect().getDBType() ) ) {
				try {
					String[] ids = getSqlServerPKColumn( sess, tablename );
					tmd.setIdColumns( ids );
				} catch ( SQLException e ) {
					// ignore this
				}
			}

		}

		return tmd;
	}

	/**
	 * ͨ��JDBC meta data��ȡ������Ϣ
	 * 
	 * @param sess
	 * @param tablename
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static String[] getPKColumns( DBSession sess, String tablename )
			throws SQLException {
		ArrayList list = new ArrayList();
		DatabaseMetaData dbmd = sess.getConnection().getMetaData();
		ResultSet rs = dbmd.getPrimaryKeys( null, null, tablename );
		while ( rs.next() ) {
			list.add( rs.getString( "COLUMN_NAME" ).toUpperCase() );
		}
		rs.close();

		return ( String[] ) list.toArray( new String[ list.size() ] );
	}

	/**
	 * ȡ�ø�����������ֶ�
	 * 
	 * @param sess ���ݿ����� ע�⣺�����ӵ��û������в�ѯsys.dba_cons_columns��Ȩ�ޣ����򽫱�������ͼ�����ڴ���
	 * @param tablename ����
	 * @return String[]
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static String[] getOraclePKColumn( DBSession sess, String tablename )
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct t1.column_name from sys.dba_cons_columns t1, dba_constraints t2 " ).append(
				"where t1.constraint_name=t2.constraint_name and " ).append(
				"t1.table_name='" ).append( tablename.toUpperCase() ).append(
				"'" );
		sql.append( " and t2.constraint_type='P'" );

		ArrayList list = new ArrayList();
		ResultSet rs = sess.executeQuery( sql.toString() );
		while ( rs.next() ) {
			list.add( rs.getString( 1 ) );
		}
		rs.close();

		return ( String[] ) list.toArray( new String[ list.size() ] );
	}

	/**
	 * ȡ�ø�����������ֶ�
	 * 
	 * @param sess ���ݿ�����
	 *            ע�⣺�����ӵ��û������в�ѯINFORMATION_SCHEMA.KEY_COLUMN_USAGE��Ȩ�ޣ����򽫱�������ͼ�����ڴ���
	 * @param tablename ����
	 * @return String[]
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static String[] getSqlServerPKColumn( DBSession sess, String tablename )
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct column_name from INFORMATION_SCHEMA.KEY_COLUMN_USAGE " ).append(
				"where table_name = '" ).append( tablename ).append(
				"' and CONSTRAINT_NAME like 'PK_%'" );

		ArrayList list = new ArrayList();
		ResultSet rs = sess.executeQuery( sql.toString() );
		while ( rs.next() ) {
			list.add( rs.getString( 1 ) );
		}
		rs.close();

		return ( String[] ) list.toArray( new String[ list.size() ] );
	}

}
