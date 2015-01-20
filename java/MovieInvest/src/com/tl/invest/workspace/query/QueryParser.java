package com.tl.invest.workspace.query;

import java.util.List;

import com.tl.common.StringUtils;

public class QueryParser {
	/**
	 * 按照查询条件生成WHERE语句片段
	 * @param fields
	 * @return
	 */
	public String getSQL(List<QueryField> fields){
		if(fields == null || fields.size()<=0) return "";
		
		StringBuilder result = new StringBuilder();
		
		for (QueryField field : fields) {
			String query = getSQLByField(field);
			if(StringUtils.isEmpty(query)) continue;
			
			if (result.length() > 0){
				result.append(" and ");
			}
        	result.append(query);
		}
		
		return result.toString();
	}

	private String getSQLByField(QueryField field) {
		StringBuilder result = new StringBuilder();
		String value = "";
		switch (field.getOperator()) {
			case IN:
			case NIN:
				result.append(field.getCode());
				value = oneValue(field);
				if(value.indexOf(",")==-1){
					if(field.getOperator().operatorName().equalsIgnoreCase(QueryOperator.IN.operatorName())){
						result.append(QueryOperator.EQ.operator());
					}else {
						result.append(QueryOperator.NEQ.operator());
					}
					result.append(value);
				}else {
					result.append(field.getOperator().operator());
					result.append("(");
					result.append(value);
					result.append(")");
				}
				break;	
			default:
				result.append(field.getCode());				
				result.append(field.getOperator().operator());
				result.append(oneValue(field));
				break;
		}
		
		return result.toString();
	}
	
	private String oneValue(QueryField field){
		StringBuffer sb = new StringBuffer();
		for (QueryFieldValue v : field.getValues()) {
			if(sb.length()>0 && field.isNeedQuote()) sb.append(",");
			if(field.isNeedQuote()) sb.append("'");
			sb.append(v.getValue());
			if(field.isNeedQuote()) sb.append("'");
		}
		return sb.toString();
	}
}
