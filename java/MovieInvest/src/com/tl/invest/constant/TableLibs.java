package com.tl.invest.constant;

public enum TableLibs  {
	TB_USER("user"),
	TB_PHOTOGROUP("photogroup"),
	TB_USERPHOTO("userphoto"),
	TB_WORKS("works"),
	TB_BANKCARD("bankcard"),
	TB_PROJECT("invest_project"),
	TB_PROJMODE("invest_projmode"),
	TB_PROJSUBJECT("invest_projsubject"),
	TB_PROJSUPPORT("invest_projsupport"),
	TB_USERORDER("user_order")
	;
	
	private String tcode;
	private TableLibs(String tcode){
		this.tcode = tcode;
	}
	
	public String getTableCode(){
		return this.tcode;
	}	
}
