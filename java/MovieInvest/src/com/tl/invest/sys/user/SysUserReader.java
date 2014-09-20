package com.tl.invest.sys.user;

public interface SysUserReader {
	public SysUser getUser(int userID);
	public SysUser getUser(String userCode);
	public SysUser[] getUsers();
	public SysUser[] getUsers(int roleID);
	
	
	public Role getRole(int roleID);
	public Role getRole(String roleName);
	public Role[] getRoles();
	public Role[] getRoles(int userID);
	public Role[] getValidRoles(int userID);
	
	public RoleUser[] getRoleUsers();
	
	public Permission[] getPermissions();
	public Permission getPermission(int roleID,String sourceType);
	public String getPermissionByUser(int userID,String sourceType);
}
