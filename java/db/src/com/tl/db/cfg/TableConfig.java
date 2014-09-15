package com.tl.db.cfg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ����һ�����������Ϣ�����ݽṹ
 * 
 */
public class TableConfig {

	/**
	 * ��Ԫ���ݶ��󣬱����˱����������С���ʶ�е���Ϣ
	 */
	private TableMetaData metaData;

	/**
	 * �ñ���ӳ�䵽���ࣻ��Ϊnull����ӳ�䵽Map
	 */
	@SuppressWarnings("rawtypes")
	private Class mappingClass;

	/**
	 * �����������Ե�ӳ���ϵ��������String�� - ��������String��
	 */
	@SuppressWarnings("rawtypes")
	private Map propMapping = new HashMap();

	/**
	 * �ñ�������������Ϣ�ļ��ϣ�������String�� - ��������Ϣ����ColumnConfig��
	 */
	@SuppressWarnings("rawtypes")
	private Map columns = new HashMap();

	/**
	 */
	public TableConfig() {
	}

	/**
	 * @param metaData
	 */
	public TableConfig( TableMetaData metaData ) {
		this.metaData = metaData;
	}

	/**
	 * �����������������Զ�תΪ��д���Ƿ����
	 * 
	 * @param columnaName
	 * @return
	 */
	public boolean isColumnExist( String columnaName ) {
		return columns.keySet().contains( columnaName.toUpperCase() );
	}

	/**
	 * ��ȡָ���е�������Ϣ
	 * 
	 * @param columnName
	 * @return
	 */
	public ColumnConfig getColumnConfig( String columnName ) {
		return ( ColumnConfig ) columns.get( columnName.toUpperCase() );
	}

	/**
	 * ��ӱ�������bean��������ӳ����Ϣ
	 * 
	 * @param columnName ������
	 * @param fieldName bean������
	 */
	@SuppressWarnings("unchecked")
	public void addPropMapping( String columnName, String fieldName ) {
		propMapping.put( columnName.toUpperCase(), fieldName );
	}

	/**
	 * ����bean��������ȡ��ӳ��ı�������<br>
	 * <br>
	 * ���Ȳ���ӳ����Ϣ�����ҵ��򷵻أ��������Ƿ������������ͬ�������������ҵ��򷵻أ���������Ҳ������򷵻�null
	 * 
	 * @param fieldName bean������
	 * @return ������
	 */
	@SuppressWarnings("rawtypes")
	public String getMappingColumn( String fieldName ) {
		for ( Iterator i = propMapping.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry me = ( Map.Entry ) i.next();
			if ( fieldName.equals( me.getValue() ) )
				return ( String ) me.getKey();
		}

		String columnName = fieldName.toUpperCase();
		if ( columns.keySet().contains( columnName ) )
			return columnName;

		return null;
	}

	/**
	 * ���ݱ�������ȡ��ӳ���bean������
	 * 
	 * @param columnName
	 * @return
	 */
	public String getMappingField( String columnName ) {
		return ( String ) propMapping.get( columnName.toUpperCase() );
	}

	public TableMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData( TableMetaData metaData ) {
		this.metaData = metaData;
	}

	/**
	 * @return �ñ�������������Ϣ�ļ��ϣ�������String�� - ��������Ϣ����ColumnConfig��
	 */
	@SuppressWarnings("rawtypes")
	public Map getColumns() {
		return columns;
	}

	@SuppressWarnings("rawtypes")
	public void setColumns( Map columns ) {
		if ( columns != null )
			this.columns = columns;
	}

	@SuppressWarnings("rawtypes")
	public Class getMappingClass() {
		return mappingClass;
	}

	@SuppressWarnings("rawtypes")
	public void setMappingClass( Class mappingClass ) {
		this.mappingClass = mappingClass;
	}

	public boolean equals( Object obj ) {
		if ( obj instanceof TableConfig ) {
			return ( ( TableConfig ) obj ).metaData.equals( metaData );
		}
		return false;
	}

	public int hashCode() {
		return metaData.hashCode();
	}

	public String toString() {
		return metaData.toString();
	}
}
