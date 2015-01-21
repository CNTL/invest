package com.tl.invest.workspace.query;

import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.kernel.context.Context;

public class QueryParser {
	
	Log log = Context.getLog("invest");
	/**
	 * 按照查询条件生成WHERE语句片段
	 * @param fields
	 * @return
	 */
	public String getSQL(QueryField[] fields){
		if(fields == null || fields.length<=0) return "";
		
		StringBuilder result = new StringBuilder();
		
		for (QueryField field : fields) {
			String query = getSQLByField(field);
			if(StringUtils.isEmpty(query)) continue;
			
			if (result.length() > 0){
				result.append(" and ");
			}
        	result.append(query);
		}
		
		if(log.isDebugEnabled())
			log.debug(result.toString());
		
		return " "+result.toString()+" ";
	}

	private String getSQLByField(QueryField field) {
		String result = "";
		
		switch (field.getEditType()) {
			case INPUT_AUTOCOMPLETE_KV:
			case INPUT_DLG_KV:
				break;
			case INPUT_DATE_RANGE:
				result = parseRangeDate(field);
				break;
			case INPUT_RANGE:
				result = parseRangeNumber(field);
				break;
			default:
				result = parse(field);
				break;
		}
		
		return result;
	}
	
	/**
	 * 单值的解析
	 * @param field
	 * @return
	 */
	private String parse(QueryField field){
		StringBuilder result = new StringBuilder();
		String value = "";
		switch (field.getOperator()) {
			case IN:
			case NIN:
				result.append(field.isExt()? field.getExtCode() : field.getCode());
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
			case LIKE:
			case LLIKE:
			case RLIKE:
				result.append(field.isExt()? field.getExtCode() : field.getCode());				
				result.append(field.getOperator().operator());
				result.append(likeValue(field));
				break;
			default:
				result.append(field.isExt()? field.getExtCode() : field.getCode());				
				result.append(field.getOperator().operator());
				result.append(oneValue(field));
				break;
		}
	
		return result.toString();
	}
	
	/**
	 * 日期范围的解析
	 * @param field
	 * @return
	 */
	private String parseRangeDate(QueryField field){
		StringBuilder result = new StringBuilder();
		
		QueryFieldValue[] values = field.getValues();
		if(values.length == 2){
			for (int i=0;i<values.length;i++) {
				String value = values[i].getValue();
				if(StringUtils.isEmpty(value)) continue;
				
				if(result.length()>0) result.append(" and ");
				
				result.append(i==0 ? field.getCode() : field.getExtCode());
				result.append(i==0 ? ">=" : "<=");
				result.append("'");
				result.append(value);
				result.append(i==0 ? " 0:00:00" : " 23:59:59");
				result.append("'");
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 日期范围的解析
	 * @param field
	 * @return
	 */
	private String parseRangeNumber(QueryField field){
		StringBuilder result = new StringBuilder();
		
		QueryFieldValue[] values = field.getValues();
		if(values.length == 2){
			for (int i=0;i<values.length;i++) {
				String value = values[i].getValue();
				if(StringUtils.isEmpty(value)) continue;
				
				if(result.length()>0) result.append(" and ");
				
				result.append(i==0 ? field.getCode() : field.getExtCode());
				result.append(i==0 ? ">=" : "<=");
				result.append(value);
			}
		}
		
		return result.toString();
	}
	
	private String likeValue(QueryField field){
		StringBuffer sb = new StringBuffer();
		for (QueryFieldValue v : field.getValues()) {
			if(sb.length()>0 && field.isNeedQuote()) sb.append(",");
			if(field.isNeedQuote()) sb.append("'");
			if(field.getOperator().operatorName().equalsIgnoreCase(QueryOperator.LIKE.operatorName())
					|| field.getOperator().operatorName().equalsIgnoreCase(QueryOperator.RLIKE.operatorName())){
				sb.append("%");
			}
			sb.append(v.getValue());
			if(field.getOperator().operatorName().equalsIgnoreCase(QueryOperator.LIKE.operatorName())
					|| field.getOperator().operatorName().equalsIgnoreCase(QueryOperator.LLIKE.operatorName())){
				sb.append("%");
			}
			if(field.isNeedQuote()) sb.append("'");
		}
		return sb.toString();
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
