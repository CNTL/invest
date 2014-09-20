package com.tl.invest.sys.user;

import org.hibernate.Session;

public interface SysUserManager extends SysUserReader {
	public void saveUser(SysUser user);
	public void saveUser(SysUser user,Session s);
	public int deleteUser(int userID);
	public int deleteUser(int userID,Session s);	
	public SysUser getUser(int userID,Session s);
	public SysUser getUser(String userCode,Session s);
	public SysUser[] getUsers(Session s);
	public SysUser[] getUsers(int roleID,Session s);
	
	
	public void saveRole(Role role);
	public void saveRole(Role role,Session s);
	public void deleteRole(int roleID);
	public void deleteRole(int roleID,Session s);	
	public Role getRole(int roleID,Session s);
	public Role getRole(String roleName,Session s);
	public Role[] getRoles(Session s);
	public Role[] getRoles(int userID,Session s);
	public Role[] getValidRoles(int userID,Session s);
	
	public void saveRoleUser(RoleUser roleUser);
	public void saveRoleUser(RoleUser roleUser,Session s);
	public void deleteRoleUserByRole(int roleID) throws Exception;
	public void deleteRoleUserByRole(int roleID,Session s) throws Exception;
	public void deleteRoleUserByUser(int userID) throws Exception;
	public void deleteRoleUserByUser(int userID,Session s) throws Exception;
	
	public RoleUser[] getRoleUsers(Session s);
	public RoleUser getRoleUser(int userID,int roleID,Session s);
	
	public void savePermission(Permission permission);
	public void savePermission(Permission permission,Session s);
	public void deletePermission(int roleID) throws Exception;
	public void deletePermission(int roleID,Session s) throws Exception;
	public void deletePermission(int roleID,String sourceType) throws Exception;
	public void deletePermission(int roleID,String sourceType,Session s) throws Exception;
	public Permission[] getPermissions(Session s);
	public Permission getPermission(int roleID,String sourceType,Session s);
	public String getPermissionByUser(int userID,String sourceType,Session s);
}
