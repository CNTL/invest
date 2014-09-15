
package com.tl.db;

import gnu.trove.TIntArrayList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tl.db.cfg.TypeMapping;
import com.tl.db.util.CloseHelper;

/**
 * DBQuery����ʵ��
 * 
 */
public class BaseDBQuery implements DBQuery {

	/**
	 * ��������
	 * 
	 * @param DBSessionʾ��
	 * @param sql���
	 * @return
	 */
	public static DBQuery getInstance( DBSession dbsess, String sql ) {
		return new BaseDBQuery( dbsess, sql );
	}

	/**
	 * �˱��������˴����Լ���DBSession����
	 */
	protected DBSession owner;

	/**
	 * this�����Ӧ��sql���
	 */
	protected String sql;

	/**
	 * ���󶨲���ֵ
	 */
	@SuppressWarnings("rawtypes")
	protected ArrayList paramValues = new ArrayList();

	/**
	 * �󶨲������ͣ�˳����paramValues��Ӧ
	 */
	protected TIntArrayList paramTypes = new TIntArrayList();

	/**
	 * @param owner
	 */
	public BaseDBQuery( DBSession owner ) {
		this.owner = owner;
	}

	/**
	 * @param owner
	 * @param sql
	 */
	public BaseDBQuery( DBSession owner, String sql ) {
		this.owner = owner;
		this.sql = sql;
	}

	/**
	 * @see com.tl.db.DBQuery#setInt(int, int)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setInt( int position, int value ) {
		paramValues.add( position - 1, new Integer( value ) );
		paramTypes.insert( position - 1, Types.INTEGER );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setLong(int, long)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setLong( int position, long value ) {
		paramValues.add( position - 1, new Long( value ) );
		paramTypes.insert( position - 1, Types.BIGINT );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setFloat(int, float)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setFloat( int position, float value ) {
		paramValues.add( position - 1, new Float( value ) );
		paramTypes.insert( position - 1, Types.FLOAT );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setDouble(int, double)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setDouble( int position, double value ) {
		paramValues.add( position - 1, new Double( value ) );
		paramTypes.insert( position - 1, Types.DOUBLE );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setString(int, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setString( int position, String value ) {
		paramValues.add( position - 1, value );
		paramTypes.insert( position - 1, Types.VARCHAR );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setDate(int, java.sql.Date)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setDate( int position, Date value ) {
		paramValues.add( position - 1, value );
		paramTypes.insert( position - 1, Types.DATE );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setTime(int, java.sql.Time)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setTime( int position, Time value ) {
		paramValues.add( position - 1, value );
		paramTypes.insert( position - 1, Types.TIME );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setTimestamp(int, java.sql.Timestamp)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setTimestamp( int position, Timestamp value ) {
		paramValues.add( position - 1, value );
		paramTypes.insert( position - 1, Types.TIMESTAMP );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setObject(int, Object)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setObject( int position, Object value ) {
		paramValues.add( position - 1, value );

		int type = TypeMapping.map( value );
		paramTypes.insert( position - 1, type );
		return this;
	}

	/**
	 * @see com.tl.db.DBQuery#setNull(int, int)
	 */
	@SuppressWarnings("unchecked")
	public DBQuery setNull( int position, int sqlType ) {
		paramValues.add( position - 1, null );
		paramTypes.insert( position - 1, sqlType );
		return this;
	}

	/**
	 * @throws SQLException
	 * @see com.tl.db.DBQuery#executeQuery()
	 */
	public IResultSet executeQuery() throws SQLException {
		return owner.executeQuery(
				sql,
				paramValues.toArray(),
				paramTypes.toNativeArray() );
	}

	/**
	 * @throws SQLException
	 * @see com.tl.db.DBQuery#executeUpdate()
	 */
	public int executeUpdate() throws SQLException {
		return owner.executeUpdate(
				sql,
				paramValues.toArray(),
				paramTypes.toNativeArray() );
	}

	/**
	 * @throws SQLException
	 * @see com.tl.db.DBQuery#list()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List list() throws SQLException {
		ArrayList result = new ArrayList();

		ResultSet rs = executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cnt = rsmd.getColumnCount();

		try {
			while ( rs.next() ) {
				ArrayList row = new ArrayList( cnt );
				for ( int i = 0; i < cnt; i++ ) {
					Object value = rs.getObject( i + 1 );
					row.add( value );
				}
				result.add(row);
			}
		} finally {
			CloseHelper.closeQuietly( rs );
		}

		return result;
	}

	/**
	 * @throws SQLException
	 * @see com.tl.db.DBQuery#iterator()
	 */
	@SuppressWarnings("rawtypes")
	public Iterator iterator() throws SQLException {
		return list().iterator();
	}

}
