package com.tl.db.dialect;

import java.sql.Types;

import com.tl.db.cfg.ColumnMetaData;
import com.tl.db.cfg.DBType;
import com.tl.db.exception.MappingException;

/**
 * A SQL dialect for DB2.
 * 
 */
public class DB2Dialect extends BaseDialect {
	public DB2Dialect(){
		super();
		registerColumnType( Types.BIT, "SMALLINT" );
		registerColumnType( Types.SMALLINT, "SMALLINT" );
		registerColumnType( Types.TINYINT, "SMALLINT" );
		registerColumnType( Types.INTEGER, "INTEGER" );
		registerColumnType( Types.BIGINT, "BIGINT" );
		registerColumnType( Types.CHAR, "CHAR($l)" );
		registerColumnType( Types.VARCHAR, "VARCHAR($l)" );
		registerColumnType( Types.FLOAT, "REAL" );
		registerColumnType( Types.DOUBLE, "DOUBLE" );
		registerColumnType( Types.DATE, "DATE" );
		registerColumnType( Types.TIME, "TIME" );
		registerColumnType( Types.TIMESTAMP, "TIMESTAMP" );
		registerColumnType( Types.VARBINARY, "VARGRAPHIC" );
		registerColumnType( Types.CLOB, "CLOB(2G)" );
		registerColumnType( Types.BLOB, "BLOB(2G)" ); 
		registerColumnType( Types.NUMERIC, "DECIMAL($p, $s)" );
	}

	public DBType getDBType() {
		return DBType.DB2;
	}
	
	public String getLimitString( String sql, int offset, int limit ) {
		if ( offset > 0 )
			throw new UnsupportedOperationException( "db2 has no offset" );
		
		if (limit == 1)
			return new StringBuffer( sql.length() + 23 ).append( sql )
					.append(" FETCH FIRST 1 ROW ONLY")
					.toString();
		else
			return new StringBuffer( sql.length() + 30 ).append( sql )
				.append(" FETCH FIRST ").append(limit).append(" ROWS ONLY")
				.toString();
	}
	
	public String getSelectSequenceNextValString( String sequenceName )
			throws MappingException {
		throw new UnsupportedOperationException("DB2-SEQUENCE:not support select, use NEXT VALUE FOR directly.");
	}
	
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsSequences() {
		return true;
	}
	
	public String getAddColumnDDL(ColumnMetaData[] columnArray, String tableName) {
		//格式：alter table MYTEST add AA integer add BB integer add CC varchar(10);
		StringBuffer alterSQLBuf = new StringBuffer();
        alterSQLBuf.append("alter table "); 
        alterSQLBuf.append(tableName);

        for (int i = 0; i < columnArray.length; i++) {
            String ddl = this.getDDL4Column(columnArray[i]);
            alterSQLBuf.append(" add ");
            alterSQLBuf.append(ddl);
            
        }
        return alterSQLBuf.toString();
	}
	//Field片段中不显示null（只显示not null），如此可以避免在defaultValue时null放在default后面，造成语句报错的情况。
	public boolean ignoreDDLNull() {
		return true;
	}
}
