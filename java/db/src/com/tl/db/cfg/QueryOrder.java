package com.tl.db.cfg;

public class QueryOrder {
	private String orderField;
	private OrderRule orderRule = OrderRule.ASC;
	
	
	
	public String getOrderField() {
		return orderField;
	}



	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}



	public OrderRule getOrderRule() {
		return orderRule;
	}



	public void setOrderRule(OrderRule orderRule) {
		this.orderRule = orderRule;
	}



	public enum OrderRule{
		ASC,
		DESC
	}
}
