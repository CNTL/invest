package com.tl.common;

import java.util.List;


/**
 * @author wang.yq
 * @create 2014-9-7
 * ��ѯ����������
 * ���б�Ĳ�ѯ�л��в��������̨���÷���Ϊ��̨������
 * �ýṹΪ��������������ѯֵ�����ʽ(���ڡ�like��>=��<=��>��<��!=)�ȱ��ʽ
 */
public class QueryParam {
	  
	/**
	 * ����
	 */
	private String key ;
	
	/**
	 * ��ѯ��ֵ
	 */
	private String value;
	
	/**
	 * ���ʽ
	 */
	private SqlExpression exp;
	
	/**
	 * �������
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
	
	/** ��������
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
		//д1=1Ϊ�˺���Ķ����Լ���and
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
	
	/** �������ʽ
	 * @param list(Ϊ�˲��ҿ�ʼʱ��ͽ���ʱ�䣬Ŀǰ�ݲ�ʵ��)
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
