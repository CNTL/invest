package com.tl.db.cfg;


/**
 * ����һ���е�������Ϣ�����ݽṹ
 * 
 * @author rengy
 */
public class ColumnConfig {

	private ColumnMetaData metaData;

	/**
	 * �Ƿ����� ��������Ӱ���ѯʱ���ɵ�sql��䣩
	 */
	private boolean load = true;

	/**
	 * �Ƿ���� ��������Ӱ�����ʱ���ɵ�sql��䣩
	 */
	private boolean insert = true;

	/**
	 * @param metaData ��Ԫ����
	 */
	public ColumnConfig( ColumnMetaData metaData ) {
		if ( metaData == null )
			throw new NullPointerException();
		this.metaData = metaData;
	}

	public boolean isInsert() {
		return insert;
	}

	public void setInsert( boolean insert ) {
		this.insert = insert;
	}

	public boolean isLoad() {
		return load;
	}

	public void setLoad( boolean load ) {
		this.load = load;
	}

	public ColumnMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData( ColumnMetaData metaData ) {
		this.metaData = metaData;
	}

	public boolean equals( Object obj ) {
		if ( obj instanceof ColumnConfig ) {
			return ( ( ColumnConfig ) obj ).metaData.equals( metaData );
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
