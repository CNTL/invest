package com.tl.invest.workspace.query;

public class QueryField {
	private String name;
	private String code;
	private QueryOperator operator;
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
