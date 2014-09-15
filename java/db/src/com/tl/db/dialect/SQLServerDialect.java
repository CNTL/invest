package com.tl.db.dialect;

import java.sql.Types;

import com.tl.db.cfg.DBType;

/**
 * A dialect for Microsoft SQL Server
 */
public class SQLServerDialect extends SybaseDialect {

	public SQLServerDialect() {
		registerColumnType( Types.VARBINARY, "image" );
		registerColumnType( Types.VARBINARY, 8000, "varbinary($l)" );

		//for UNICODE
		registerColumnType( Types.CHAR, "nchar($l)" );
		registerColumnType( Types.VARCHAR, "nvarchar($l)" );
		registerColumnType( Types.CLOB, "ntext" );
		
		//对integer和float改用numeric，以精确控制长度和精度，主要是可以控制小的长度和精度
		registerColumnType( Types.INTEGER, "numeric($l,0)" );
		registerColumnType( Types.FLOAT, "numeric($l,$p)" );
		registerColumnType( Types.NUMERIC, "numeric($l,$p)" );
	}

	/**
	 * @see com.tl.kernel.db.dialect.Dialect#getDBType()
	 */
	public DBType getDBType() {
		return DBType.SQLSERVER;
	}

	public boolean supportsLimit() {
		return true;
	}

	static int getAfterSelectInsertPoint( String sql ) {
		sql = sql.toLowerCase();
		int selectIndex = sql.indexOf( "select" );
		final int selectDistinctIndex = sql.indexOf( "select distinct" );
		return selectIndex + ( selectDistinctIndex == selectIndex ? 15 : 6 );
	}

	public String getLimitString( String querySelect, int offset, int limit ) {
		if ( offset > 0 )
			throw new UnsupportedOperationException( "sql server has no offset" );
		
		return new StringBuffer( querySelect.length() + 8 ).append( querySelect ).insert(
				getAfterSelectInsertPoint( querySelect ),
				" top " + limit ).toString();
	}

}
