package com.tl.db.dialect;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.tl.common.StringUtils;
import com.tl.db.cfg.ColumnMetaData;
import com.tl.db.cfg.DBType;
import com.tl.db.cfg.TableMetaData;
import com.tl.db.dialect.type.IBfile;
import com.tl.db.dialect.type.IBlob;
import com.tl.db.dialect.type.IClob;
import com.tl.db.exception.MappingException;

public abstract class Dialect {	
	/**
	 * �������ݿ����ͣ���com.tl.db.DBTypes���壩
	 */
	public abstract DBType getDBType();
	
	// �÷��Ե���������ӳ����Ϣ
	private final TypeNames typeNames = new TypeNames();
	
	/**
	 * Get the name of the database type associated with the given
	 * <tt>java.sql.Types</tt> typecode.
	 * 
	 * @param code <tt>java.sql.Types</tt> typecode
	 * @return the database type name
	 * @throws MappingException
	 */
	public String getTypeName( int code ) throws MappingException {
		String result = typeNames.get( code );
		if ( result == null ) {
			throw new MappingException(
					"No default type mapping for (java.sql.Types) " + code );
		}
		return result;
	}

	/**
	 * Get the name of the database type associated with the given
	 * <tt>java.sql.Types</tt> typecode.
	 * 
	 * @param code <tt>java.sql.Types</tt> typecode
	 * @param length the length or precision of the column
	 * @param precision the scale of the column
	 * @param scale
	 * 
	 * @return the database type name
	 * @throws MappingException
	 */
	public String getTypeName( int code, int length, int precision, int scale )
			throws MappingException {
		String result = typeNames.get( code, length, precision, scale );
		if ( result == null ) {
			throw new MappingException(
					"No type mapping for java.sql.Types code: " + code
							+ ", length: " + length );
		}
		return result;
	}

	/**
	 * Subclasses register a typename for the given type code and maximum column
	 * length. <tt>$l</tt> in the type name with be replaced by the column
	 * length (if appropriate).
	 * 
	 * @param code <tt>java.sql.Types</tt> typecode
	 * @param capacity maximum length of database type
	 * @param name the database type name
	 */
	protected void registerColumnType( int code, int capacity, String name ) {
		typeNames.put( code, capacity, name );
	}

	/**
	 * Subclasses register a typename for the given type code. <tt>$l</tt> in
	 * the type name with be replaced by the column length (if appropriate).
	 * 
	 * @param code <tt>java.sql.Types</tt> typecode
	 * @param name the database type name
	 */
	protected void registerColumnType( int code, String name ) {
		typeNames.put( code, name );
	}

	/**
	 * Does this dialect support the <tt>UNIQUE</tt> column syntax?
	 * 
	 * @return boolean
	 */
	public boolean supportsUnique() {
		return true;
	}
	
	/**
	 * Does this dialect support sequences?
	 * 
	 * @return boolean
	 */
	public boolean supportsSequences() {
		return false;
	}
	
	/**
	 * �Ƿ�֧��bfile����
	 * 
	 * @return boolean
	 */
	public boolean supportBfile() {
		return false;
	}
	
	/**
	 * Does this <tt>Dialect</tt> have some kind of <tt>LIMIT</tt> syntax?
	 */
	public boolean supportsLimit() {
		return false;
	}

	/**
	 * Does this dialect support an offset?
	 */
	public boolean supportsLimitOffset() {
		return supportsLimit();
	}

	/**
	 * Add a <tt>LIMIT</tt> clause to the given SQL <tt>SELECT</tt>
	 * 
	 * @return the modified SQL
	 */
	public String getLimitString( String querySelect, boolean hasOffset ) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

	/**
	 * ��÷�Χ��ѯ���
	 * 
	 * @param querySelect ������ѡȡ��Χ��ԭʼsql���
	 * @param offset ��ʼλ�ã���1��ʼ
	 * @param limit ��¼����
	 * @return
	 */
	public String getLimitString( String querySelect, int offset, int limit ) {
		return getLimitString( querySelect, offset > 0 );
	}

	/**
	 * ��÷�Χ��ѯ���
	 * 
	 * @param querySelect ������ѡȡ��Χ��ԭʼsql���
	 * @param offset ��ʼλ�ã���1��ʼ
	 * @param limit ��¼����
	 * @return
	 */
	public String getLimitString( String querySelect, String offset, String limit ) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}
	
	/**
	 * ��װ�������ֶ�ֵ���Ա�������sql�����
	 * 
	 * @param type �ֶ�����
	 * @param value �ֶ�ֵ
	 * @return sqlֱ����
	 */
	public String getWrappedValue( int type, String value ) {
		if ( type == Types.VARCHAR )
			return '\'' + value + '\'';

		switch ( type ) {		
			case Types.VARCHAR :
			case Types.CHAR :
				return '\'' + value + '\'';
			case Types.INTEGER :
			case Types.BIGINT :
			case Types.FLOAT :
			case Types.DOUBLE :
				return value;
			default :
				return value;
		}
	}
	
	/**
	 * �����ֶ���Ϣ�������ڶ����ֶε�DDL���Ƭ��
	 * 
	 * @param columnInfo �ֶ���Ϣ
	 * @return DDL���Ƭ�ϣ��磺columnCode number not null default 0��
	 */
	public String getDDL4Column( ColumnMetaData columnInfo ) {
		StringBuffer sb = new StringBuffer();
		sb.append( columnInfo.getCode() ).append( " " );

		int type = columnInfo.getType();
		int length = columnInfo.getLength();
		//����������ʵ��������δ���ó��ȣ����Ϊ10
		if (length == 0 && (type == Types.INTEGER || type == Types.BIGINT || type == Types.FLOAT || type == Types.NUMERIC))
			length = 10;
		int precision = columnInfo.getPrecision();
		String typeName = getTypeName( type, length, precision, 0 );

		sb.append( typeName );

		String defaultValue = columnInfo.getDefaultValue();
		if ( defaultValue != null && !"".equals( defaultValue.trim() ) ) {
			sb.append( " default " ).append(
					getWrappedValue( type, defaultValue ) );
		}

		if ( columnInfo.isNullable() ) {
			if (!ignoreDDLNull())
				sb.append( " null" );
		}
		else
			sb.append( " not null" );

		if ( supportsUnique() && columnInfo.isUnique() )
			sb.append( " unique" );

		return sb.toString();
	}
	
	/**
	 * DDL���ɵ�ʱ���Ƿ���Ժ���Field�����е�null
	 * @return
	 */
	public boolean ignoreDDLNull() {
		return false;
	}
	
	/**
	 * �����ֶ���Ϣ�ͱ������ɴ������DLL���
	 * @param table
	 * @return ������DDL���,��create table TableName ()
	 */
	public String getDDL4CreateTable(TableMetaData table){
		StringBuffer createSQLBuffer = new StringBuffer();
		ColumnMetaData[] columns = table.getColumns();
		
		if(columns!=null && columns.length>0){
			StringBuffer columnSQLBuffer = new StringBuffer();
			String pk = "";
			for (ColumnMetaData column : columns) {
				String subDDL = getDDL4Column(column);
				if(columnSQLBuffer.length()>0)
					columnSQLBuffer.append(",");
				columnSQLBuffer.append(subDDL);
				if(column.isPrimarykey()){
					pk = column.getCode();
				}
			}
			
			//����Ϊ����
			if(StringUtils.isNotEmpty(pk)){
				columnSQLBuffer.append(",constraint PK_");
				columnSQLBuffer.append(table.getName());
				columnSQLBuffer.append (" primary key ("+pk+")");
			}
			
			createSQLBuffer.append("create table ");
	        createSQLBuffer.append(table.getName());
	        createSQLBuffer.append("(").append(columnSQLBuffer).append(")");
		}
		
		return createSQLBuffer.toString();
	}
	
	/**
	 * �����ֶ���Ϣ�ͱ�����������ֶε�DLL���
	 * 
	 * @param columnArray - �ֶ���Ϣ����
	 * @param tableName - ����
	 * @return ������DDL���,��alter table TableName add ColumnName varchar(200)
	 */
	public String getDDL4AddColumn(ColumnMetaData[] columnArray,String tableName)
	{
		StringBuffer alterSQLBuf = new StringBuffer();
        alterSQLBuf.append("alter table "); //alter table ATEST add VVVVV number;
        alterSQLBuf.append(tableName).append(" add ");

        for (int i = 0; i < columnArray.length; i++) {
            String ddl = this.getDDL4Column(columnArray[i]);
            alterSQLBuf.append(ddl).append(",");
            
        }
        alterSQLBuf.deleteCharAt(alterSQLBuf.length() - 1);
        return alterSQLBuf.toString();
	}
	
	/**
	 * �����ֶ���Ϣ�ͱ�����������ֶε�DDL��䣬add������������Ű�Χ��
	 * ����ר��Ϊ����׼����getAddColumnDDL�ڶ�����ʽ�������š�
	 * �������ǱȽϳ�������ʽ��������ڸ���������ֱ�ӵ��ã�
	 * @param columnoArray - �ֶ���Ϣ����
	 * @param tableName - ����
	 * @return ������DDL���,��alter table TableName add (ColumnName varchar(200))
	 */
	protected String getDDL4AddColumn2(ColumnMetaData[] columnArray, String tableName)
	{
		StringBuffer alterSQLBuf = new StringBuffer();
        alterSQLBuf.append("alter table "); //alter table ATEST add VVVVV number;
        alterSQLBuf.append(tableName).append(" add (");

        for (int i = 0; i < columnArray.length; i++) {
            String ddl = this.getDDL4Column(columnArray[i]);
            alterSQLBuf.append(ddl).append(",");
            
        }
        alterSQLBuf.deleteCharAt(alterSQLBuf.length() - 1);
        alterSQLBuf.append(")");
        return alterSQLBuf.toString();
	}
	
	/**
	 * �õ��������ɿ�clobֵ��sqlƬ��
	 * 
	 */
	public String getEmptyClobSql() {
		return "''";
	}
	
	/**
	 * �õ��������ɿ�blobֵ��sqlƬ��
	 * 
	 */
	public String getEmptyBlobSql() {
		return "null";
	}
	
	/**
	 * ���ɲ�ѯ�������е���һ��ֵ��sql���
	 * 
	 * @param sequenceName ������
	 * @return sql���
	 * @throws MappingException
	 */
	public String getSelectSequenceNextValString( String sequenceName )
			throws MappingException {
		throw new MappingException( "Dialect " + this
				+ " doesn't support sequence" );
	}
	
	/**
	 * д�����ݵ����ݿ��Clob�����ֶΡ� <br>
	 * <br>
	 * ע�⣺�÷����������beginTransaction��commitTransactionʹ��
	 * 
	 * @param id ��¼��ʶֵ
	 * @param tablename ���ݱ���
	 * @param idColumnName ��ʶ�ֶ���
	 * @param lobColumnName Clob�ֶ���
	 * @param clob ��д������
	 * @exception IOException
	 * @exception SQLException
	 */
	public abstract void writeClob( long id, String tablename,
			String idColumnName, String lobColumnName, IClob clob )
			throws SQLException, IOException;

	/**
	 * д�����ݵ����ݿ��Blob�����ֶ�<br>
	 * ע�⣺�÷����������beginTransaction��commitTransactionʹ��
	 * 
	 * @param id ��¼��ʶֵ
	 * @param tablename ���ݱ���
	 * @param idColumnName ��ʶ�ֶ���
	 * @param lobColumnName Blob�ֶ���
	 * @param blob ��д������
	 * @exception IOException
	 * @exception SQLException
	 */
	public abstract void writeBlob( long id, String tablename,
			String idColumnName, String lobColumnName, IBlob blob )
			throws SQLException, IOException;

	// -------------------------------------------------------------------------

	/**
	 * �ӽ������ȡ��Clob�����ֶ�ֵ
	 * 
	 * @param rs
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract IClob getClob( ResultSet rs, String name )
			throws SQLException, IOException;

	/**
	 * �ӽ������ȡ��Blob�����ֶ�ֵ
	 * 
	 * @param rs
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract IBlob getBlob( ResultSet rs, String name )
			throws SQLException, IOException;

	/**
	 * �ӽ������ȡ��Bfile�����ֶ�ֵ
	 * 
	 * @param rs
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract IBfile getBfile( ResultSet rs, String name )
			throws SQLException, IOException;
}
