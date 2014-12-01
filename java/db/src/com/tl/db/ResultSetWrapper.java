package com.tl.db;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * 对java.sql.ResultSet的简单包装，所有方法调用转发给底层的java.sql.ResultSet对象
 * 
 */
public class ResultSetWrapper implements ResultSet {

	protected ResultSet underlying;

	/**
	 * @param underlying
	 */
	public ResultSetWrapper( ResultSet underlying ) {
		this.underlying = underlying;
	}

	public boolean absolute( int row ) throws SQLException {
		return underlying.absolute( row );
	}

	public void afterLast() throws SQLException {
		underlying.afterLast();
	}

	public void beforeFirst() throws SQLException {
		underlying.beforeFirst();
	}

	public void cancelRowUpdates() throws SQLException {
		underlying.cancelRowUpdates();
	}

	public void clearWarnings() throws SQLException {
		underlying.clearWarnings();
	}

	public void close() throws SQLException {
		underlying.close();
	}

	public void deleteRow() throws SQLException {
		underlying.deleteRow();
	}

	public int findColumn( String columnName ) throws SQLException {
		return underlying.findColumn( columnName );
	}

	public boolean first() throws SQLException {
		return underlying.first();
	}

	public Array getArray( int i ) throws SQLException {
		return underlying.getArray( i );
	}

	public Array getArray( String colName ) throws SQLException {
		return underlying.getArray( colName );
	}

	public InputStream getAsciiStream( int columnIndex ) throws SQLException {
		return underlying.getAsciiStream( columnIndex );
	}

	public InputStream getAsciiStream( String columnName ) throws SQLException {
		return underlying.getAsciiStream( columnName );
	}

	/**
	 * @see java.sql.ResultSet#getBigDecimal(int, int)
	 * @deprecated
	 */
	public BigDecimal getBigDecimal( int columnIndex, int scale )
			throws SQLException {
		return underlying.getBigDecimal( columnIndex, scale );
	}

	public BigDecimal getBigDecimal( int columnIndex ) throws SQLException {
		return underlying.getBigDecimal( columnIndex );
	}

	/**
	 * @see java.sql.ResultSet#getBigDecimal(java.lang.String, int)
	 * @deprecated
	 */
	public BigDecimal getBigDecimal( String columnName, int scale )
			throws SQLException {
		return underlying.getBigDecimal( columnName, scale );
	}

	public BigDecimal getBigDecimal( String columnName ) throws SQLException {
		return underlying.getBigDecimal( columnName );
	}

	public InputStream getBinaryStream( int columnIndex ) throws SQLException {
		return underlying.getBinaryStream( columnIndex );
	}

	public InputStream getBinaryStream( String columnName ) throws SQLException {
		return underlying.getBinaryStream( columnName );
	}

	public Blob getBlob( int i ) throws SQLException {
		return underlying.getBlob( i );
	}

	public Blob getBlob( String colName ) throws SQLException {
		return underlying.getBlob( colName );
	}

	public boolean getBoolean( int columnIndex ) throws SQLException {
		return underlying.getBoolean( columnIndex );
	}

	public boolean getBoolean( String columnName ) throws SQLException {
		return underlying.getBoolean( columnName );
	}

	public byte getByte( int columnIndex ) throws SQLException {
		return underlying.getByte( columnIndex );
	}

	public byte getByte( String columnName ) throws SQLException {
		return underlying.getByte( columnName );
	}

	public byte[] getBytes( int columnIndex ) throws SQLException {
		return underlying.getBytes( columnIndex );
	}

	public byte[] getBytes( String columnName ) throws SQLException {
		return underlying.getBytes( columnName );
	}

	public Reader getCharacterStream( int columnIndex ) throws SQLException {
		return underlying.getCharacterStream( columnIndex );
	}

	public Reader getCharacterStream( String columnName ) throws SQLException {
		return underlying.getCharacterStream( columnName );
	}

	public Clob getClob( int i ) throws SQLException {
		return underlying.getClob( i );
	}

	public Clob getClob( String colName ) throws SQLException {
		return underlying.getClob( colName );
	}

	public int getConcurrency() throws SQLException {
		return underlying.getConcurrency();
	}

	public String getCursorName() throws SQLException {
		return underlying.getCursorName();
	}

	public Date getDate( int columnIndex, Calendar cal ) throws SQLException {
		return underlying.getDate( columnIndex, cal );
	}

	public Date getDate( int columnIndex ) throws SQLException {
		return underlying.getDate( columnIndex );
	}

	public Date getDate( String columnName, Calendar cal ) throws SQLException {
		return underlying.getDate( columnName, cal );
	}

	public Date getDate( String columnName ) throws SQLException {
		return underlying.getDate( columnName );
	}

	public double getDouble( int columnIndex ) throws SQLException {
		return underlying.getDouble( columnIndex );
	}

	public double getDouble( String columnName ) throws SQLException {
		return underlying.getDouble( columnName );
	}

	public int getFetchDirection() throws SQLException {
		return underlying.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return underlying.getFetchSize();
	}

	public float getFloat( int columnIndex ) throws SQLException {
		return underlying.getFloat( columnIndex );
	}

	public float getFloat( String columnName ) throws SQLException {
		return underlying.getFloat( columnName );
	}

	public int getInt( int columnIndex ) throws SQLException {
		return underlying.getInt( columnIndex );
	}

	public int getInt( String columnName ) throws SQLException {
		return underlying.getInt( columnName );
	}

	public long getLong( int columnIndex ) throws SQLException {
		return underlying.getLong( columnIndex );
	}

	public long getLong( String columnName ) throws SQLException {
		return underlying.getLong( columnName );
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return underlying.getMetaData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObject( int i, Map map ) throws SQLException {
		return underlying.getObject( i, map );
	}

	public Object getObject( int columnIndex ) throws SQLException {
		return underlying.getObject( columnIndex );
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObject( String colName, Map map ) throws SQLException {
		return underlying.getObject( colName, map );
	}

	public Object getObject( String columnName ) throws SQLException {
		return underlying.getObject( columnName );
	}

	public Ref getRef( int i ) throws SQLException {
		return underlying.getRef( i );
	}

	public Ref getRef( String colName ) throws SQLException {
		return underlying.getRef( colName );
	}

	public int getRow() throws SQLException {
		return underlying.getRow();
	}

	public short getShort( int columnIndex ) throws SQLException {
		return underlying.getShort( columnIndex );
	}

	public short getShort( String columnName ) throws SQLException {
		return underlying.getShort( columnName );
	}

	public Statement getStatement() throws SQLException {
		return underlying.getStatement();
	}

	public String getString( int columnIndex ) throws SQLException {
		return underlying.getString( columnIndex );
	}

	public String getString( String columnName ) throws SQLException {
		return underlying.getString( columnName );
	}

	public Time getTime( int columnIndex, Calendar cal ) throws SQLException {
		return underlying.getTime( columnIndex, cal );
	}

	public Time getTime( int columnIndex ) throws SQLException {
		return underlying.getTime( columnIndex );
	}

	public Time getTime( String columnName, Calendar cal ) throws SQLException {
		return underlying.getTime( columnName, cal );
	}

	public Time getTime( String columnName ) throws SQLException {
		return underlying.getTime( columnName );
	}

	public Timestamp getTimestamp( int columnIndex, Calendar cal )
			throws SQLException {
		return underlying.getTimestamp( columnIndex, cal );
	}

	public Timestamp getTimestamp( int columnIndex ) throws SQLException {
		return underlying.getTimestamp( columnIndex );
	}

	public Timestamp getTimestamp( String columnName, Calendar cal )
			throws SQLException {
		return underlying.getTimestamp( columnName, cal );
	}

	public Timestamp getTimestamp( String columnName ) throws SQLException {
		return underlying.getTimestamp( columnName );
	}

	public int getType() throws SQLException {
		return underlying.getType();
	}

	/**
	 * @see java.sql.ResultSet#getUnicodeStream(int)
	 * @deprecated
	 */
	public InputStream getUnicodeStream( int columnIndex ) throws SQLException {
		return underlying.getUnicodeStream( columnIndex );
	}

	/**
	 * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
	 * @deprecated
	 */
	public InputStream getUnicodeStream( String columnName )
			throws SQLException {
		return underlying.getUnicodeStream( columnName );
	}

	public URL getURL( int columnIndex ) throws SQLException {
		return underlying.getURL( columnIndex );
	}

	public URL getURL( String columnName ) throws SQLException {
		return underlying.getURL( columnName );
	}

	public SQLWarning getWarnings() throws SQLException {
		return underlying.getWarnings();
	}

	public void insertRow() throws SQLException {
		underlying.insertRow();
	}

	public boolean isAfterLast() throws SQLException {
		return underlying.isAfterLast();
	}

	public boolean isBeforeFirst() throws SQLException {
		return underlying.isBeforeFirst();
	}

	public boolean isFirst() throws SQLException {
		return underlying.isFirst();
	}

	public boolean isLast() throws SQLException {
		return underlying.isLast();
	}

	public boolean last() throws SQLException {
		return underlying.last();
	}

	public void moveToCurrentRow() throws SQLException {
		underlying.moveToCurrentRow();
	}

	public void moveToInsertRow() throws SQLException {
		underlying.moveToInsertRow();
	}

	public boolean next() throws SQLException {
		return underlying.next();
	}

	public boolean previous() throws SQLException {
		return underlying.previous();
	}

	public void refreshRow() throws SQLException {
		underlying.refreshRow();
	}

	public boolean relative( int rows ) throws SQLException {
		return underlying.relative( rows );
	}

	public boolean rowDeleted() throws SQLException {
		return underlying.rowDeleted();
	}

	public boolean rowInserted() throws SQLException {
		return underlying.rowInserted();
	}

	public boolean rowUpdated() throws SQLException {
		return underlying.rowUpdated();
	}

	public void setFetchDirection( int direction ) throws SQLException {
		underlying.setFetchDirection( direction );
	}

	public void setFetchSize( int rows ) throws SQLException {
		underlying.setFetchSize( rows );
	}

	public void updateArray( int columnIndex, Array x ) throws SQLException {
		underlying.updateArray( columnIndex, x );
	}

	public void updateArray( String columnName, Array x ) throws SQLException {
		underlying.updateArray( columnName, x );
	}

	public void updateAsciiStream( int columnIndex, InputStream x, int length )
			throws SQLException {
		underlying.updateAsciiStream( columnIndex, x, length );
	}

	public void updateAsciiStream( String columnName, InputStream x, int length )
			throws SQLException {
		underlying.updateAsciiStream( columnName, x, length );
	}

	public void updateBigDecimal( int columnIndex, BigDecimal x )
			throws SQLException {
		underlying.updateBigDecimal( columnIndex, x );
	}

	public void updateBigDecimal( String columnName, BigDecimal x )
			throws SQLException {
		underlying.updateBigDecimal( columnName, x );
	}

	public void updateBinaryStream( int columnIndex, InputStream x, int length )
			throws SQLException {
		underlying.updateBinaryStream( columnIndex, x, length );
	}

	public void updateBinaryStream( String columnName, InputStream x, int length )
			throws SQLException {
		underlying.updateBinaryStream( columnName, x, length );
	}

	public void updateBlob( int columnIndex, Blob x ) throws SQLException {
		underlying.updateBlob( columnIndex, x );
	}

	public void updateBlob( String columnName, Blob x ) throws SQLException {
		underlying.updateBlob( columnName, x );
	}

	public void updateBoolean( int columnIndex, boolean x ) throws SQLException {
		underlying.updateBoolean( columnIndex, x );
	}

	public void updateBoolean( String columnName, boolean x )
			throws SQLException {
		underlying.updateBoolean( columnName, x );
	}

	public void updateByte( int columnIndex, byte x ) throws SQLException {
		underlying.updateByte( columnIndex, x );
	}

	public void updateByte( String columnName, byte x ) throws SQLException {
		underlying.updateByte( columnName, x );
	}

	public void updateBytes( int columnIndex, byte[] x ) throws SQLException {
		underlying.updateBytes( columnIndex, x );
	}

	public void updateBytes( String columnName, byte[] x ) throws SQLException {
		underlying.updateBytes( columnName, x );
	}

	public void updateCharacterStream( int columnIndex, Reader x, int length )
			throws SQLException {
		underlying.updateCharacterStream( columnIndex, x, length );
	}

	public void updateCharacterStream( String columnName, Reader reader,
			int length ) throws SQLException {
		underlying.updateCharacterStream( columnName, reader, length );
	}

	public void updateClob( int columnIndex, Clob x ) throws SQLException {
		underlying.updateClob( columnIndex, x );
	}

	public void updateClob( String columnName, Clob x ) throws SQLException {
		underlying.updateClob( columnName, x );
	}

	public void updateDate( int columnIndex, Date x ) throws SQLException {
		underlying.updateDate( columnIndex, x );
	}

	public void updateDate( String columnName, Date x ) throws SQLException {
		underlying.updateDate( columnName, x );
	}

	public void updateDouble( int columnIndex, double x ) throws SQLException {
		underlying.updateDouble( columnIndex, x );
	}

	public void updateDouble( String columnName, double x ) throws SQLException {
		underlying.updateDouble( columnName, x );
	}

	public void updateFloat( int columnIndex, float x ) throws SQLException {
		underlying.updateFloat( columnIndex, x );
	}

	public void updateFloat( String columnName, float x ) throws SQLException {
		underlying.updateFloat( columnName, x );
	}

	public void updateInt( int columnIndex, int x ) throws SQLException {
		underlying.updateInt( columnIndex, x );
	}

	public void updateInt( String columnName, int x ) throws SQLException {
		underlying.updateInt( columnName, x );
	}

	public void updateLong( int columnIndex, long x ) throws SQLException {
		underlying.updateLong( columnIndex, x );
	}

	public void updateLong( String columnName, long x ) throws SQLException {
		underlying.updateLong( columnName, x );
	}

	public void updateNull( int columnIndex ) throws SQLException {
		underlying.updateNull( columnIndex );
	}

	public void updateNull( String columnName ) throws SQLException {
		underlying.updateNull( columnName );
	}

	public void updateObject( int columnIndex, Object x, int scale )
			throws SQLException {
		underlying.updateObject( columnIndex, x, scale );
	}

	public void updateObject( int columnIndex, Object x ) throws SQLException {
		underlying.updateObject( columnIndex, x );
	}

	public void updateObject( String columnName, Object x, int scale )
			throws SQLException {
		underlying.updateObject( columnName, x, scale );
	}

	public void updateObject( String columnName, Object x ) throws SQLException {
		underlying.updateObject( columnName, x );
	}

	public void updateRef( int columnIndex, Ref x ) throws SQLException {
		underlying.updateRef( columnIndex, x );
	}

	public void updateRef( String columnName, Ref x ) throws SQLException {
		underlying.updateRef( columnName, x );
	}

	public void updateRow() throws SQLException {
		underlying.updateRow();
	}

	public void updateShort( int columnIndex, short x ) throws SQLException {
		underlying.updateShort( columnIndex, x );
	}

	public void updateShort( String columnName, short x ) throws SQLException {
		underlying.updateShort( columnName, x );
	}

	public void updateString( int columnIndex, String x ) throws SQLException {
		underlying.updateString( columnIndex, x );
	}

	public void updateString( String columnName, String x ) throws SQLException {
		underlying.updateString( columnName, x );
	}

	public void updateTime( int columnIndex, Time x ) throws SQLException {
		underlying.updateTime( columnIndex, x );
	}

	public void updateTime( String columnName, Time x ) throws SQLException {
		underlying.updateTime( columnName, x );
	}

	public void updateTimestamp( int columnIndex, Timestamp x )
			throws SQLException {
		underlying.updateTimestamp( columnIndex, x );
	}

	public void updateTimestamp( String columnName, Timestamp x )
			throws SQLException {
		underlying.updateTimestamp( columnName, x );
	}

	//-------------------------------------------
	//---- Add for jdk1.6
	//-------------------------------------------
	
	public boolean wasNull() throws SQLException {
		return underlying.wasNull();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return underlying.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return underlying.isWrapperFor(iface);
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		return underlying.getRowId(columnIndex);
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		return underlying.getRowId(columnLabel);
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		underlying.updateRowId(columnIndex, x);
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		underlying.updateRowId(columnLabel, x);
	}

	@Override
	public int getHoldability() throws SQLException {
		return underlying.getHoldability();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return underlying.isClosed();
	}

	@Override
	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		underlying.updateNString(columnIndex, nString);
	}

	@Override
	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		underlying.updateNString(columnLabel, nString);
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		underlying.updateNClob(columnIndex, nClob);
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		underlying.updateNClob(columnLabel, nClob);
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		return underlying.getNClob(columnIndex);
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return underlying.getNClob(columnLabel);
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		
		return underlying.getSQLXML(columnIndex);
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		
		return underlying.getSQLXML(columnLabel);
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		underlying.updateSQLXML(columnIndex, xmlObject);
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		underlying.updateSQLXML(columnLabel, xmlObject);
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		return underlying.getNString(columnIndex);
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		return underlying.getNString(columnLabel);
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return underlying.getNCharacterStream(columnIndex);
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return underlying.getNCharacterStream(columnLabel);
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		underlying.updateNCharacterStream(columnIndex, x, length);
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		underlying.updateNCharacterStream(columnLabel, reader, length);
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		underlying.updateAsciiStream(columnIndex, x, length);
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		underlying.updateBinaryStream(columnIndex, x, length);
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		underlying.updateCharacterStream(columnIndex, x, length);
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		underlying.updateAsciiStream(columnLabel, x, length);
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		underlying.updateBinaryStream(columnLabel, x, length);
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		underlying.updateCharacterStream(columnLabel, reader, length);
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		underlying.updateBlob(columnIndex, inputStream, length);
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		underlying.updateBlob(columnLabel, inputStream, length);
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		underlying.updateClob(columnIndex, reader, length);
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		underlying.updateClob(columnLabel, reader, length);
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		underlying.updateNClob(columnIndex, reader, length);
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		underlying.updateNClob(columnLabel, reader, length);
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		underlying.updateNCharacterStream(columnIndex, x);
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		underlying.updateNCharacterStream(columnLabel, reader);
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		underlying.updateAsciiStream(columnIndex, x);
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		underlying.updateBinaryStream(columnIndex, x);
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		underlying.updateCharacterStream(columnIndex, x);
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		underlying.updateAsciiStream(columnLabel, x);
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		underlying.updateBinaryStream(columnLabel, x);
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		underlying.updateCharacterStream(columnLabel, reader);
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		underlying.updateBlob(columnIndex, inputStream);
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		underlying.updateBlob(columnLabel, inputStream);
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		underlying.updateClob(columnIndex, reader);
	}

	@Override
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		underlying.updateClob(columnLabel, reader);
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		underlying.updateNClob(columnIndex, reader);
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		underlying.updateNClob(columnLabel, reader);
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
