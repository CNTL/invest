package com.tl.kernel;

public enum EditType {
	INPUT							( 0, "任意填写（单行）"),
	INPUT_MULTI					( 3, "任意填写（多行）"),
	INPUT_AUTOCOMPLETE			( 1, "任意填写（单行，带填写提示）"),
	INPUT_AUTOCOMPLETE_KV			( 2, "任意填写（单行，带填写提示，键值对）") ,
	
	SELECT							( 4, "单选（下拉框select）"),
	SELECT_DYNAMIC					( 5, "单选（下拉框select，动态取值）"), 
	RADIO							( 6, "单选（单选框radio）"),
	RADIO_DYNMIC					( 7, "单选（单选框radio，动态取值）"),
	SELECT_MULTI					( 8, "多选（下拉框select）"),
	SELECT_MULTI_DYNAMIC			( 9, "多选（下拉框select，动态取值）"),
	CHECKBOX						(10, "多选（复选框checkbox）"),
	CHECKBOX_DYNAMIC				(11, "多选（复选框checkbox，动态取值）"),
	CATEGORY						(12, "分类（分类树）"),
	CATEGORY_MULTI					(13, "分类（分类树，可多选）"),
	CATEGORY_SELECT				(14, "分类（下拉框select，只可用于单层分类）"),
	TREE_ORG						(16, "部门（部门树）"),
	TREE_ORG_MULTI					(17, "部门（部门树，可多选）"),
	TREE_ORG_PERMISSION			(18, "部门（部门树带权限）"),
	TREE_ORG_MULTI_PERMISSION		(19, "部门（部门树带权限，可多选）"),
	TREE_USER						(20, "用户（用户树）"),
	TREE_USER_MULTI				(21, "用户（用户树，可多选）"),
	TREE_USER_PERMISSION			(22, "用户（用户树带权限）"),
	TREE_USER_MULTI_PERMISSION	(23, "用户（用户树带权限，可多选）"),
	TREE_ROLE						(20, "角色（角色树）"),
	TREE_ROLE_MULTI				(21, "角色（角色树，可多选）"),
	TREE_ROLE_PERMISSION			(22, "角色（角色树带权限）"),
	TREE_ROLE_MULTI_PERMISSION	(23, "角色（角色树带权限，可多选）"),
	INPUT_EMAIL					(24, "电子邮件（专用文本，自动进行格式验证）"),
	INPUT_PHONE					(25, "固定电话（专用文本，自动进行格式验证）"),
	INPUT_MOBILE					(26, "手机（专用文本，自动进行格式验证）"),
	INPUT_ADDRESS					(27, "地址（分开填写方式，分为：省,市,区/县,街道,楼号）"),
	INPUT_DATE_SPLIT				(28, "日期（分开填写方式，分为：年,月,日）"),
	CHECKBOX_YESNO					(29, "是/否，勾选方式"),
	;
	
	private int id;
	private String typeName;
	private EditType(int id, String typeName){
		this.id = id;
		this.typeName = typeName;
	}
	
	public String typeName(){
		return this.typeName;
	}
	
	public int id(){
		return this.id;
	}
}
