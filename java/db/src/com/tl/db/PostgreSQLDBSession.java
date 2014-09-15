package com.tl.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tl.db.cfg.DBType;
import com.tl.db.dialect.type.IBlob;
import com.tl.db.dialect.type.IClob;
import com.tl.db.dialect.type.LobHelper;
import com.tl.db.util.CloseHelper;

/**
 * PostgreSQL Ãÿ ‚¥¶¿Ì
 * 
 */
public class PostgreSQLDBSession extends BaseDBSession
{
	public PostgreSQLDBSession(){
		this.dialect = DBType.POSTGRESQL.dialcet();
	}
	
	/* (non-Javadoc)
	 * @see com.tl.db.BaseDBSession#getClob(java.lang.String, java.lang.Object[])
	 */
	public IClob getClob(String sql, Object[] params) throws SQLException, IOException
	{
		ResultSet rs = executeQuery0( sql, params, null );
		try {
			if ( rs.next() )
			{
				Reader pgReader = rs.getCharacterStream(1);
				if(pgReader == null) return null;
				
				return LobHelper.createClob(LobHelper.readClob( pgReader ));
			}
		} finally {
			Statement pmt = rs.getStatement();
			CloseHelper.closeQuietly( rs );
			CloseHelper.closeQuietly( pmt );
		}
		return null;
	}
	/**
	 * @see com.tl.db.DBSession#executeQuery(java.lang.String,
	 *      java.lang.Object[], int[])
	 */
	public IResultSet executeQuery( String sql, Object[] params, int[] types )
			throws SQLException {
		ResultSet rs = executeQuery0( sql, params, types );
		return new PostgreSQLResultSet( rs );
	}

	/**
	 * @see com.tl.db.DBSession#executeQuery(java.lang.String,
	 *      java.lang.Object[], int[], int, int)
	 */
	public IResultSet executeQuery( String sql, Object[] params, int[] types,
			int resultSetType, int resultSetConcurrency ) throws SQLException {
		ResultSet rs = executeQuery0(
				sql,
				params,
				types,
				resultSetType,
				resultSetConcurrency );
		return new PostgreSQLResultSet( rs );
	}
	
	/* (non-Javadoc)
	 * @see com.tl.db.BaseDBSession#getBlob(java.lang.String, java.lang.Object[])
	 */
	public IBlob getBlob(String sql, Object[] params) throws SQLException, IOException
	{
		ResultSet rs = executeQuery0( sql, params, null );
		try {
			if ( rs.next() )
			{
				InputStream pgIn = rs.getBinaryStream(1);
				if(pgIn == null) return null; 
					
				return LobHelper.createBlob(LobHelper.readBlob(pgIn));
			}
		} finally {
			Statement pmt = rs.getStatement();
			CloseHelper.closeQuietly( rs );
			CloseHelper.closeQuietly( pmt );
		}
		return null;
	}
}
