package com.tl.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.db.dialect.type.BfileFactoryManager;
import com.tl.db.dialect.type.IBfile;

/**
 * IResultSetµÄOracleÊµÏÖ
 * 
 */
class OracleResultSet extends BaseResultSet {

	/**
	 * @param rs
	 */
	public OracleResultSet( ResultSet rs ) {
		super( rs );
	}

	/**
	 * @see com.tl.db.IResultSet#getBfile(int)
	 */
	public IBfile getBfile( int i ) throws SQLException {
		oracle.sql.BFILE bfile = ( oracle.sql.BFILE ) underlying.getObject( i );
		return BfileFactoryManager.convert( bfile );
	}

	/**
	 * @see com.tl.db.IResultSet#getBfile(java.lang.String)
	 */
	public IBfile getBfile( String columnName ) throws SQLException {
		oracle.sql.BFILE bfile = ( oracle.sql.BFILE ) underlying.getObject( columnName );
		return BfileFactoryManager.convert( bfile );
	}

}
