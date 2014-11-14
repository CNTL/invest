package com.tl.kernel.constant;

public enum SysTableLibs {
	TB_SYS_USER("SYS_User"),
	TB_SYS_DICTIONARY("SYS_Dictionary"),
	TB_SYS_DICTIONARYTYPE("SYS_DictionaryType"),
	TB_USER("user"),
	TB_USERMSG("user_msg"),
	TB_USERRESUME("user_resume"),
	TB_BANKCARD("bankcard"),
	TB_PHOTOGROUP("photogroup"),
	TB_USERPHOTO("userphoto"),
	TB_WORKS("works")
	;
	
	private String tcode;
	private SysTableLibs(String tcode){
		this.tcode = tcode;
	}
	
	public String getTableCode(){
		return this.tcode;
	}
}
