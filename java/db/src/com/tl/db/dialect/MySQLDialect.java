package com.tl.db.dialect;

import java.sql.Types;

import com.tl.db.cfg.ColumnMetaData;
import com.tl.db.cfg.DBType;

/**
 * An SQL dialect for MySQL.
 * 
 */
public class MySQLDialect extends BaseDialect {

	public MySQLDialect() {
		super();
		registerColumnType( Types.BIT, "bit" );
		registerColumnType( Types.BIGINT, "bigint" );
		registerColumnType( Types.SMALLINT, "smallint" );
		registerColumnType( Types.TINYINT, "tinyint" );
		registerColumnType( Types.INTEGER, "numeric($l,0)" );
		registerColumnType( Types.CHAR, "char($l)" );
		registerColumnType( Types.VARCHAR, "longtext" );
		registerColumnType( Types.VARCHAR, 16777215, "mediumtext" );
		registerColumnType( Types.VARCHAR, 65535, "text" );
		registerColumnType( Types.VARCHAR, 255, "varchar($l)" );
		registerColumnType( Types.FLOAT, "numeric($l,$p)" );
		registerColumnType( Types.DOUBLE, "double precision" );
		registerColumnType( Types.DATE, "date" );
		registerColumnType( Types.TIME, "time" );
		registerColumnType( Types.TIMESTAMP, "datetime" );
		registerColumnType( Types.VARBINARY, "longblob" );
		registerColumnType( Types.VARBINARY, 16777215, "mediumblob" );
		registerColumnType( Types.VARBINARY, 65535, "blob" );
		registerColumnType( Types.VARBINARY, 255, "tinyblob" );
		registerColumnType( Types.NUMERIC, "numeric($l,$p)" );
		registerColumnType( Types.BLOB, "longblob" );
		registerColumnType( Types.BLOB, 16777215, "mediumblob" );
		registerColumnType( Types.BLOB, 65535, "blob" );
		registerColumnType( Types.CLOB, "longtext" );
		registerColumnType( Types.CLOB, 16777215, "mediumtext" );
		registerColumnType( Types.CLOB, 65535, "text" );
	}

	/**
	 * @see com.tl.kernel.db.dialect.Dialect#getDBType()
	 */
	public DBType getDBType() {
		return DBType.MYSQL;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuffer( sql.length()+20 )
			.append(sql)
			.append( hasOffset ? " limit ?, ?" : " limit ?")
			.toString();
	}
	public String getLimitString( String sql, int offset, int limit ) {
		StringBuffer result = new StringBuffer( sql.length() + 20 );
		
		//若=0，则起始位置其实没限制
		if (offset > 0)	
			result.append(sql).append(" limit ").append(offset).append(",").append(limit);
		else
			result.append(sql).append(" limit ").append(limit);
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.tl.db.dialect.Dialect#getAddColumnDDL(com.tl.kernel.db.ColumnInfo[], java.lang.String)
	 */
	public String getDDL4AddColumn(ColumnMetaData[] columnArray, String tableName)
	{
		return getDDL4AddColumn2(columnArray, tableName);
	}
	
}
