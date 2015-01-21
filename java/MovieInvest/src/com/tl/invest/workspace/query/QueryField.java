package com.tl.invest.workspace.query;

public class QueryField {
	private String name;
	private String code;
	
	/**
	 * 扩展字段，比如：开始日期对应的为结束日期；键值对类型的字段对应的ID字段
	 */
	private String extCode;
	private boolean ext;//通过扩展字段检索
	private QueryOperator operator;
	private QueryEditType editType;
	private boolean needQuote;
	private String sql;
	private QueryFieldValue[] values;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getExtCode() {
		return extCode;
	}
	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}
	public boolean isExt() {
		return ext;
	}
	public void setExt(boolean ext) {
		this.ext = ext;
	}
	public QueryEditType getEditType() {
		return editType;
	}
	public void setEditType(QueryEditType editType) {
		this.editType = editType;
	}
	public QueryOperator getOperator() {
		return operator;
	}
	public void setOperator(QueryOperator operator) {
		this.operator = operator;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public QueryFieldValue[] getValues() {
		return values;
	}
	public void setValues(QueryFieldValue[] values) {
		this.values = values;
	}
	public boolean isNeedQuote() {
		return needQuote;
	}
	public void setNeedQuote(boolean needQuote) {
		this.needQuote = needQuote;
	}
}
