package com.tl.invest.sys.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tl.common.DateUtils;

public class SysUserReaderImpl implements SysUserReader {

	@Override
	public SysUser getUser(int userID) {
		SysUser[] users = getUsers();
		if(users == null || users.length<=0) return null;
		for (SysUser user : users) {
			if(user.getId() == userID){
				return user;
			}
		}
		return null;
	}

	@Override
	public SysUser getUser(String userCode) {
		SysUser[] users = getUsers();
		if(users == null || users.length<=0) return null;
		for (SysUser user : users) {
			if(userCode.equals(user.getCode())){
				return user;
			}
		}
		return null;
	}

	@Override
	public SysUser[] getUsers() {
		return SysUserHelper.getCache().getUsers();
	}

	@Override
	public SysUser[] getUsers(int roleID) {
		RoleUser[] roleUsers = getRoleUsers();
		if(roleUsers == null || roleUsers.length<=0) return null;
		List<RoleUser> ruList = new ArrayList<RoleUser>();
		for (RoleUser roleUser : roleUsers) {
			if(roleID == roleUser.getRoleID()){
				ruList.add(roleUser);
			}
		}
		
		if(ruList == null || ruList.size()<=0) return null;
		SysUser[] users = getUsers();
		if(users == null || users.length<=0) return null;
		List<SysUser> uList = new ArrayList<SysUser>();
		for (RoleUser roleUser : ruList) {
			for (SysUser user : users) {
				if(user.getId() == roleUser.getUserID()){
					uList.add(user);
					break;
				}
			}
		}
		
		return (SysUser[]) uList.toArray(new SysUser[0]);
	}

	@Override
	public Role getRole(int roleID) {
		Role[] roles = getRoles();
		if(roles == null || roles.length<=0) return null;
		for (Role role : roles) {
			if(roleID == role.getId()){
				return role;
			}
		}
		return null;
	}

	@Override
	public Role getRole(String roleName) {
		Role[] roles = getRoles();
		if(roles == null || roles.length<=0) return null;
		for (Role role : roles) {
			if(roleName.equals(role.getName())){
				return role;
			}
		}
		return null;
	}

	@Override
	public Role[] getRoles() {
		return SysUserHelper.getCache().getRoles();
	}

	@Override
	public Role[] getRoles(int userID) {
		RoleUser[] roleUsers = getRoleUsers();
		if(roleUsers == null || roleUsers.length<=0) return null;
		List<RoleUser> ruList = new ArrayList<RoleUser>();
		for (RoleUser roleUser : roleUsers) {
			if(userID == roleUser.getUserID()){
				ruList.add(roleUser);
			}
		}
		
		if(ruList == null || ruList.size()<=0) return null;
		Role[] roles = getRoles();
		if(roles == null || roles.length<=0) return null;
		List<Role> uList = new ArrayList<Role>();
		for (RoleUser roleUser : ruList) {
			for (Role role : roles) {
				if(role.getId() == roleUser.getRoleID()){
					uList.add(role);
					break;
				}
			}
		}
		
		return (Role[]) uList.toArray(new Role[0]);
	}

	@Override
	public Permission[] getPermissions() {
		return SysUserHelper.getCache().getPermissions();
	}

	@Override
	public Permission getPermission(int roleID, String sourceType) {
		Permission[] permissions = getPermissions();
		if(permissions==null ||permissions.length<=0) return null;
		for (Permission permission : permissions) {
			if(roleID==permission.getRoleID() && sourceType.equals(permission.getSourceType())){
				return permission;
			}
		}
		return null;
	}

	@Override
	public String getPermissionByUser(int userID, String sourceType) {
		Role[] roles = getValidRoles(userID);
		if(roles == null || roles.length<=0) return "";
		Permission[] permissions = getPermissions();
		if(permissions==null ||permissions.length<=0) return null;
		List<Permission> pList = new ArrayList<Permission>();
		for (Role role : roles) {
			for (Permission permission : permissions) {
				if(role.getId()==permission.getRoleID() && sourceType.equals(permission.getSourceType())){
					pList.add(permission);
					break;
				}
			}
		}
		
		return SysUserHelper.mergeUserPermission((Permission[]) pList.toArray(new Permission[0]),",");
	}

	@Override
	public Role[] getValidRoles(int userID) {
		Role[] roles = getRoles(userID);
		if(roles == null || roles.length<=0) return null;
		Date curDate = DateUtils.getTimestamp();
		List<Role> rList = new ArrayList<Role>();
		for (Role role : roles) {
			RoleUser roleUser = getRoleUser(userID,role.getId());
			if(roleUser == null) continue;
			if(roleUser.getStartDate().getTime()>=curDate.getTime() 
					&& roleUser.getEndDate().getTime()<=curDate.getTime()){
				rList.add(role);
			}
		}
		
		if(rList==null || rList.size()<=0) return null;
		return (Role[]) rList.toArray(new Role[0]);
	}

	@Override
	public RoleUser[] getRoleUsers() {
		return SysUserHelper.getCache().getRoleUsers();
	}

	@Override
	public RoleUser getRoleUser(int userID, int roleID) {
		RoleUser[] roleUsers = getRoleUsers();
		if(roleUsers == null ||roleUsers.length<=0) return null;
		for (RoleUser roleUser : roleUsers) {
			if(roleUser.getUserID() == userID && roleUser.getRoleID()==roleID)
				return roleUser;
		}
		return null;
	}

}
