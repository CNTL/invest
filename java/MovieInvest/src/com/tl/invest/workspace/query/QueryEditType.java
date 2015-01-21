package com.tl.invest.workspace.query;

/**
 * 常量定义：文档类型名称。<br/>
 * 每个常量有三个方法：<br/>
 * QueryEditType.XXX.typeCode();<br/>
 * QueryEditType.XXX.typeName();<br/>
 * 
 * 可以用如下方式得到常量：<br/>
 * QueryEditType type = Enum.valueOf(QueryEditType.class, "XXX");<br/><br/>
 * 
 * @author rengy
 */

public enum QueryEditType {
	INPUT("INPUT","普通输入框"),
	INPUT_RANGE("INPUT_RANGE","普通范围输入框"),
	INPUT_DATE("INPUT_DATE","日期选择"),
	INPUT_DATE_RANGE("INPUT_DATE_RANGE","日期范围选择"),
	INPUT_AUTOCOMPLETE("INPUT_AUTOCOMPLETE","自动完成"),
	INPUT_AUTOCOMPLETE_KV("INPUT_AUTOCOMPLETE_KV","自动完成（KV）"),
	INPUT_DLG_KV("INPUT_DLG_KV","对话框"),
	SELECT("SELECT","下拉库");
	
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
