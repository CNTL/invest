package com.tl.common;

import java.util.List;


/**
 * @author wang.yq
 * @create 2014-9-7
 * 查询参数辅助类
 * 在列表的查询中会有参数传入后台，该方法为后台接收类
 * 该结构为：包含列名、查询值、表达式(等于、like、>=、<=、>、<、!=)等表达式
 */
public class QueryParam {
	  
	/**
	 * 列名
	 */
	private String key ;
	
	/**
	 * 查询的值
	 */
	private String value;
	
	/**
	 * 表达式
	 */
	private SqlExpression exp;
	
	/**
	 * 排序规则
	 */
	private String order;
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public QueryParam(){};
	
	public QueryParam(String key,String value,SqlExpression exp){
		
		this.key = key;
		this.value=value;
		this.exp = exp;
	};
	
	/** 解析参数
	 * @param list QueryParam List
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String parseQueryParam( List list){
		StringBuilder querySql = new StringBuilder();
		StringBuilder orderSql = new StringBuilder();
		if(list==null ||list.size()==0){
			
			return querySql.toString();
		}
		//写1=1为了后面的都可以加上and
		querySql.append(" 1=1 ");
		for(int i = 0; i < list.size(); i++){
			QueryParam param = (QueryParam)list.get(i);
			
			querySql.append(getExpression(list,param));
			
			if(!StringUtils.isEmpty(param.getOrder())){
				orderSql.append(" "+param.getKey()+" "+param.getOrder());
			}
    	}
		
		if(!StringUtils.isEmpty(orderSql.toString())){
			querySql.append(" order by "+orderSql.toString());
		}
		
		
		return querySql.toString();
	}
	
	/** 解析表达式
	 * @param list(为了查找开始时间和结束时间，目前暂不实现)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getExpression(List list,QueryParam param){
		
		if(param.getExp().equals(SqlExpression.Like)){
			
			return " and "+param.getKey()+" "+param.getExp().getInfo()+ " %"+param.getValue()+"% ";
			
		}else {
			return " and "+param.getKey()+" "+param.getExp().getInfo()+ " "+param.getValue()+" ";
		}
		 
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SqlExpression getExp() {
		return exp;
	}

	public void setExp(SqlExpression exp) {
		this.exp = exp;
	}

 
}
