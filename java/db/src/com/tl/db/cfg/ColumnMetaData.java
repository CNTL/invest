package com.tl.db.cfg;

public class ColumnMetaData {
	private String code;//�ֶ�����
	private int type;//java.sql.Types
	private String typeName;
	/** ���е��ֽ������� */
	private int length;
	/** NUMBER���е�С����λ�� */
	private int precision;
	private int scale;
	/** ��ֵ�ɷ�Ϊ�� */
	private boolean nullable = true;
	private boolean unique; // �Ƿ�Ψһ
	private boolean primarykey;
	private String defaultValue; // Ĭ��ֵ
	
	public ColumnMetaData (String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	public boolean isPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(boolean primarykey) {
		this.primarykey = primarykey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
