package com.tl.db.cfg;

import java.util.Arrays;

import com.tl.common.StringUtils;

public class TableMetaData {
	/**表名*/
	private String name;
	/**
	 * 标识字段（可能有多个）
	 */
	private String[] idColumns = new String[ 0 ];
	
	/**
	 * 列元数据缓存：列名(String) - 列信息对象(ColumnMetaData) （用Map是为了根据名字取对象时方便）
	 */
	private ColumnMetaData[] columns = new ColumnMetaData[0];
	
	@SuppressWarnings("unused")
	private TableMetaData(){
		super();
	}
	
	/**
	 * @param tablename
	 */
	public TableMetaData( String name ) {
		this.name = name;
	}

	/**
	 * 返回所有列
	 * 
	 * @return Map（ 列名(String) - 列信息对象(ColumnMetaData) ）
	 */
	public ColumnMetaData[] getColumns() {
		return columns;
	}

	/**
	 * @param columns 所有列
	 */
	public void setColumns( ColumnMetaData[] columns ) {
		if ( columns != null )
			this.columns = columns;
	}

	/**
	 * 返回所有标识列
	 * 
	 * @return
	 */
	public String[] getIdColumns() {
		return idColumns;
	}

	/**
	 * @param idColumns 标识列
	 */
	public void setIdColumns( String[] idColumns ) {
		if ( idColumns != null ) {
			this.idColumns = idColumns;
		}
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public boolean equals( Object obj ) {
		if ( obj instanceof TableMetaData ) {
			return ( ( TableMetaData ) obj ).name.equals( name );
		}
		return false;
	}
	
	public String getIdColumn() {
		if ( idColumns.length > 0 )
			return idColumns[0];
		return null;
	}

	public void setIdColumn( String idColumn ) {
		if ( idColumn != null )
			this.idColumns = new String[] { idColumn };
	}
	
	/**
	 * 根据列名取得列信息对象
	 * 
	 * @param columnName
	 * @return
	 */
	public ColumnMetaData getColumn( String code ) {
		if(StringUtils.isNotEmpty(code) && columns!=null && columns.length>0){
			for (ColumnMetaData column : columns) {
				if(code.equals(column.getCode())){
					return column;
				}
			}
		}
		return null;
	}

	/**
	 * 返回所有列名（没有的话返回空数组）
	 * 
	 * @return 列名数组
	 */
	public String[] getColumnCodes() {
		String[] codes = new String[0];
		if(columns!=null && columns.length>0){
			codes = new String[columns.length];
			for (int i = 0; i < columns.length; i++) {
				ColumnMetaData column = columns[i];
				codes[i] = column.getCode();
			}
		}
		return codes;
	}

	/**
	 * 返回排好序的列名数组（没有的话返回空数组）
	 * 
	 * @return 列名数组
	 */
	public String[] getSortedColumnCodes() {
		String[] codes = new String[0];
		if(columns!=null && columns.length>0){
			codes = new String[columns.length];
			for (int i = 0; i < columns.length; i++) {
				ColumnMetaData column = columns[i];
				codes[i] = column.getCode();
			}
		}
		Arrays.sort( codes );
		return codes;
	}

}
