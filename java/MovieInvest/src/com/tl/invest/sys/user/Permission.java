package com.tl.invest.sys.user;

public class Permission implements java.io.Serializable {
	private static final long serialVersionUID = 4897390209187243165L;

	private int roleID;
	private String sourceType;
	private String sourceValues;
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceValues() {
		return sourceValues;
	}
	public void setSourceValues(String sourceValues) {
		this.sourceValues = sourceValues;
	}
}
