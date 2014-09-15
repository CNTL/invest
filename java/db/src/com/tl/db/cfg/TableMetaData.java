package com.tl.db.cfg;

import java.util.Arrays;

import com.tl.common.StringUtils;

public class TableMetaData {
	/**����*/
	private String name;
	/**
	 * ��ʶ�ֶΣ������ж����
	 */
	private String[] idColumns = new String[ 0 ];
	
	/**
	 * ��Ԫ���ݻ��棺����(String) - ����Ϣ����(ColumnMetaData) ����Map��Ϊ�˸�������ȡ����ʱ���㣩
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
	 * ����������
	 * 
	 * @return Map�� ����(String) - ����Ϣ����(ColumnMetaData) ��
	 */
	public ColumnMetaData[] getColumns() {
		return columns;
	}

	/**
	 * @param columns ������
	 */
	public void setColumns( ColumnMetaData[] columns ) {
		if ( columns != null )
			this.columns = columns;
	}

	/**
	 * �������б�ʶ��
	 * 
	 * @return
	 */
	public String[] getIdColumns() {
		return idColumns;
	}

	/**
	 * @param idColumns ��ʶ��
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
	 * ��������ȡ������Ϣ����
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
	 * ��������������û�еĻ����ؿ����飩
	 * 
	 * @return ��������
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
	 * �����ź�����������飨û�еĻ����ؿ����飩
	 * 
	 * @return ��������
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
