package com.tl.invest.workspace.query;

/**
 * �������壺�ĵ��������ơ�<br/>
 * ÿ������������������<br/>
 * QueryEditType.XXX.typeCode();<br/>
 * QueryEditType.XXX.typeName();<br/>
 * 
 * ���������·�ʽ�õ�������<br/>
 * QueryEditType type = Enum.valueOf(QueryEditType.class, "XXX");<br/><br/>
 * 
 * @author rengy
 */

public enum QueryEditType {
	INPUT("INPUT","��ͨ�����"),
	INPUT_RANGE("INPUT_RANGE","��ͨ��Χ�����"),
	INPUT_DATE("INPUT_DATE","����ѡ��"),
	INPUT_DATE_RANGE("INPUT_DATE_RANGE","���ڷ�Χѡ��"),
	INPUT_AUTOCOMPLETE("INPUT_AUTOCOMPLETE","�Զ����"),
	INPUT_AUTOCOMPLETE_KV("INPUT_AUTOCOMPLETE_KV","�Զ���ɣ�KV��"),
	INPUT_DLG_KV("INPUT_DLG_KV","�Ի���"),
	SELECT("SELECT","������");
	
	private String typeCode;
	private String typeName;
	private QueryEditType(String code,String name) {
		this.typeCode = code;
		this.typeName = name;
	}
	public String typeCode(){
		return this.typeCode;
	}
	public String typeName() {
		return this.typeName;
	}
}
