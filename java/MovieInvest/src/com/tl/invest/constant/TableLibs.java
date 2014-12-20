package com.tl.invest.constant;

public enum TableLibs  {
	TB_PROJECT("invest_project"),
	TB_PROJMODE("invest_projmode"),
	TB_PROJSUBJECT("invest_projsubject"),
	TB_PROJSUPPORT("invest_projsupport"),
	TB_PROJSCHEDULE("invest_projschedule"),
	TB_SYS_MENUES("sys_menues"),
	TB_SYS_ROLE("sys_role"),
	TB_SYS_ROLEUSER("sys_roleuser"),
	TB_SYS_PERMISSION("sys_permission"),
	TB_SYS_USER("SYS_User"),
	TB_SYS_DICTIONARY("SYS_Dictionary"),
	TB_SYS_DICTIONARYTYPE("SYS_DictionaryType"),
	
	TB_USER("user"),
	TB_USERADDRESS("user_address"),
	TB_BANKCARD("user_bankcard"),
	TB_USERMSG("user_msg"),
	TB_USERORDER("user_order"),
	TB_USERPHOTO("user_photo"),
	TB_PHOTOGROUP("user_photogroup"),
	TB_USERRECRUIT("user_recruit"),
	TB_USERRECRUITRESUME("user_recruitresume"),
	TB_USERRECRUITSTORE("user_recruitstore"),
	TB_USERRESUME("user_resume"),
	TB_USERVIDEO("user_video"),
	TB_USERVIDEOGROUP("user_videogroup"),
	TB_USERWORKS("user_works")
	;
	
	private String tcode;
	private TableLibs(String tcode){
		this.tcode = tcode;
	}
	
	public String getTableCode(){
		return this.tcode;
	}	
}
