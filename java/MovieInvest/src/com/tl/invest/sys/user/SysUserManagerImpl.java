package com.tl.invest.sys.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.tl.common.DateUtils;
import com.tl.common.log.Log;
import com.tl.invest.constant.TableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

public class SysUserManagerImpl implements SysUserManager {
	Log log = Context.getLog("invest");
	
	@Override
	public void saveUser(SysUser user) {
		DAO d = new DAO();
	    try {
			if(user.getId()>0){
				d.update(user);
			}else {
				user.setId((int)TBID.getID(TableLibs.TB_SYS_USER.getTableCode()));
				d.save(user);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void saveUser(SysUser user, Session s) {
		DAO d = new DAO();		
	    try {
			if(user.getId()>0){
				d.update(user,s);
			}else {
				user.setId((int)TBID.getID(TableLibs.TB_SYS_USER.getTableCode()));
				d.save(user,s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public int deleteUser(int userID) {
		SysUser user = getUser(userID);
		user.setDeleted(1);
		saveUser(user);
		return user.getDeleted();
	}

	@Override
	public int deleteUser(int userID, Session s) {
		SysUser user = getUser(userID,s);
		user.setDeleted(1);
		saveUser(user,s);
		return user.getDeleted();
	}
	
	@Override
	public void saveRole(Role role) {
		DAO d = new DAO();
	    try {
			if(role.getId()>0){
				d.update(role);
			}else {
				role.setId((int)TBID.getID(TableLibs.TB_SYS_ROLE.getTableCode()));
				role.setOrderNo(role.getId());
				d.save(role);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void saveRole(Role role, Session s) {
		DAO d = new DAO();
	    try {
			if(role.getId()>0){
				d.update(role,s);
			}else {
				role.setId((int)TBID.getID(TableLibs.TB_SYS_ROLE.getTableCode()));
				d.save(role,s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void deleteRole(int roleID) {
		Role role = getRole(roleID);
		role.setDeleted(1);
		saveRole(role);
	}

	@Override
	public void deleteRole(int roleID, Session s) {
		Role role = getRole(roleID,s);
		role.setDeleted(1);
		saveRole(role,s);
	}
	
	@Override
	public void saveRoleUser(RoleUser roleUser) {
		DAO d = new DAO();
	    try {
	    	boolean exist = false;
	    	Role[] roles = getRoles(roleUser.getUserID());
	    	if(roles !=null && roles.length>0){
	    		for (Role role : roles) {
					if(role.getId() == roleUser.getRoleID()){
						exist = true;
						break;
					}
				}
	    	}
			if(exist){
				d.update(roleUser);
			}else {
				
				d.save(roleUser);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void saveRoleUser(RoleUser roleUser, Session s) {
		DAO d = new DAO();
	    try {
	    	boolean exist = false;
	    	Role[] roles = getRoles(roleUser.getUserID(),s);
	    	if(roles !=null && roles.length>0){
	    		for (Role role : roles) {
					if(role.getId() == roleUser.getRoleID()){
						exist = true;
						break;
					}
				}
	    	}
			if(exist){
				d.update(roleUser,s);
			}else {
				
				d.save(roleUser,s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void deleteRoleUserByRole(int roleID) throws Exception {
		deleteRoleUserByRole(roleID,null);
	}

	@Override
	public void deleteRoleUserByRole(int roleID, Session s) throws Exception {
		String HQL = "delete from com.tl.invest.sys.user.RoleUser as a where a.roleID = " + roleID;
		if (s == null) {
			DAOHelper.delete(HQL);
		}else{
			DAOHelper.delete(HQL,s);
		}
	}

	@Override
	public void deleteRoleUserByUser(int userID) throws Exception {
		deleteRoleUserByUser(userID,null);
	}

	@Override
	public void deleteRoleUserByUser(int userID, Session s) throws Exception {
		String HQL = "delete from com.tl.invest.sys.user.RoleUser as a where a.userID = " + userID;
		if (s == null) {
			DAOHelper.delete(HQL);
		}else{
			DAOHelper.delete(HQL,s);
		}
	}
	
	@Override
	public void savePermission(Permission permission) {
		DAO d = new DAO();
	    try {
	    	boolean exist = false;
	    	Permission permission2 = getPermission(permission.getRoleID(), permission.getSourceType());
	    	if(permission2 != null) exist = true;
			if(exist){
				d.update(permission);
			}else {
				
				d.save(permission);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void savePermission(Permission permission, Session s) {
		DAO d = new DAO();
	    try {
	    	boolean exist = false;
	    	Permission permission2 = getPermission(permission.getRoleID(), permission.getSourceType(), s);
	    	if(permission2 != null) exist = true;
			if(exist){
				d.update(permission,s);
			}else {
				
				d.save(permission,s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void deletePermission(int roleID,String sourceType) throws Exception {
		deletePermission(roleID,sourceType,null);
	}

	@Override
	public void deletePermission(int roleID,String sourceType, Session s) throws Exception {
		String HQL = "delete from com.tl.invest.sys.user.Permission as a where a.roleID = ? and a.sourceType = ?";
		Object[] params = new Object[]{roleID,sourceType};
		if (s == null) {
			DAOHelper.delete(HQL,params);
		}else{
			DAOHelper.delete(HQL,params,s);
		}
	}
	
	@Override
	public void deletePermission(int roleID) throws Exception {
		deletePermission(roleID,(Session)null);
	}
	
	@Override
	public void deletePermission(int roleID,Session s) throws Exception{
		String HQL = "delete from com.tl.invest.sys.user.Permission as a where a.roleID = ?";
		Object[] params = new Object[]{roleID};
		if (s == null) {
			DAOHelper.delete(HQL,params);
		}else{
			DAOHelper.delete(HQL,params,s);
		}
	}
	
	@Override
	public SysUser getUser(int userID) {
		return getUser(userID,null);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public SysUser getUser(int userID, Session s) {
		SysUser user = null;
		DAO d = new DAO();
		String HQL = "select a from com.tl.invest.sys.user.SysUser as a where a.id = ?";
		Object[] params = new Object[]{userID};
		List users = s == null ? d.find(HQL,params) : d.find(HQL, params, s);
		if(!users.isEmpty()){
			user = (SysUser)users.get(0);
		}
		return user;
	}
	
	@Override
	public SysUser getUser(String userCode) {
		return getUser(userCode,null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public SysUser getUser(String userCode, Session s) {
		SysUser user = null;
		DAO d = new DAO();
		String HQL = "select a from com.tl.invest.sys.user.SysUser as a where a.code = ?";
		Object[] params = new Object[]{userCode};
		List users = s == null ? d.find(HQL,params) : d.find(HQL,params ,s);
		if(!users.isEmpty()){
			user = (SysUser)users.get(0);
		}
		return user;
	}

	@Override
	public SysUser[] getUsers() {
		return getUsers(null);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public SysUser[] getUsers(Session s) {
		DAO d = new DAO();
		String hql = "select a from com.tl.invest.sys.user.SysUser as a where a.deleted=0 order by a.id desc";		
		List list = s==null ? d.find(hql) : d.find(hql, s);		
		return (SysUser[]) list.toArray(new SysUser[0]);
	}

	@Override
	public SysUser[] getUsers(int roleID) {
		return getUsers(roleID, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public SysUser[] getUsers(int roleID, Session s) {
		String hql = "from com.tl.invest.sys.user.SysUser as A  where A.id in("
				+"select B.userID from com.tl.invest.sys.user.RoleUser as B where B.roleID = :roleID "
				+ ") and A.deleted=0 order by A.id desc";
		Object[] params = new Object[]{roleID};
		
		DAO d = new DAO();
		List list = s==null ? d.find(hql,params) : d.find(hql,params, s);		
		return (SysUser[]) list.toArray(new SysUser[0]);
	}

	@Override
	public Role getRole(int roleID) {
		return getRole(roleID, null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Role getRole(int roleID, Session s) {
		Role role = null;
		DAO d = new DAO();
		String HQL = "select a from com.tl.invest.sys.user.Role as a where a.id = ?";
		Object[] params = new Object[]{roleID};
		List roles = s == null ? d.find(HQL,params) : d.find(HQL, params, s);
		if(!roles.isEmpty()){
			role = (Role)roles.get(0);
		}
		return role;
	}

	@Override
	public Role getRole(String roleName) {
		return getRole(roleName, null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Role getRole(String roleName, Session s) {
		Role role = null;
		DAO d = new DAO();
		String HQL = "select a from com.tl.invest.sys.user.Role as a where a.name = ? ";
		Object[] params = new Object[]{roleName};
		List roles = s == null ? d.find(HQL,params) : d.find(HQL, params, s);
		if(!roles.isEmpty()){
			role = (Role)roles.get(0);
		}
		return role;
	}

	@Override
	public Role[] getRoles() {
		return getRoles((Session)null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Role[] getRoles(Session s) {
		DAO d = new DAO();
		String hql = "select a from com.tl.invest.sys.user.Role as a where a.deleted=0 order by a.id desc";		
		List list = s==null ? d.find(hql) : d.find(hql, s);		
		return (Role[]) list.toArray(new Role[0]);
	}

	@Override
	public Role[] getRoles(int userID) {		
		return getRoles(userID,null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Role[] getRoles(int userID, Session s) {
		String hql = "from com.tl.invest.sys.user.Role as A  where A.id in("
				+"select B.roleID from com.tl.invest.sys.user.RoleUser as B where B.userID = ? "
				+ ") and A.deleted=0 order by A.id desc";
		Object[] params = new Object[]{userID};
		
		DAO d = new DAO();
		List list = s==null ? d.find(hql,params) : d.find(hql,params, s);		
		return (Role[]) list.toArray(new Role[0]);
	}
	
	@Override
	public Role[] getValidRoles(int userID) {
		return getValidRoles(userID, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Role[] getValidRoles(int userID, Session s) {
		String hql = "from com.tl.invest.sys.user.Role as A  where A.id in("
				+"select B.roleID from com.tl.invest.sys.user.RoleUser as B where B.userID =? and B.startDate>=? and B.endDate<=?"
				+ ") and A.deleted=0 order by A.id desc";
		Timestamp  startDate= DateUtils.getTimestamp();
		Timestamp  endDate= DateUtils.getTimestamp();
		
		Object[] params = new Object[]{userID,startDate,endDate};
		
		DAO d = new DAO();
		List list = s==null ? d.find(hql,params) : d.find(hql,params, s);		
		return (Role[]) list.toArray(new Role[0]);
	}

	@Override
	public Permission[] getPermissions() {
		return getPermissions((Session)null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Permission[] getPermissions(Session s) {
		DAO d = new DAO();
		String hql = "select a from com.tl.invest.sys.user.Permission as a order by a.roleID desc";		
		List list = s==null ? d.find(hql) : d.find(hql, s);		
		return (Permission[]) list.toArray(new Permission[0]);		
	}

	@Override
	public Permission getPermission(int roleID, String sourceType) {
		return getPermission(roleID, sourceType, null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Permission getPermission(int roleID, String sourceType, Session s) {
		Permission permission = null;
		DAO d = new DAO();
		String HQL = "select a from com.tl.invest.sys.user.Permission as a where a.roleID =? and a.sourceType=?";
		Object[] params = new Object[]{roleID,sourceType};
		List permissions = s == null ? d.find(HQL,params) : d.find(HQL, params, s);
		if(!permissions.isEmpty()){
			permission = (Permission)permissions.get(0);
		}
		return permission;
	}

	@Override
	public String getPermissionByUser(int userID, String sourceType) {
		return getPermissionByUser(userID, sourceType, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getPermissionByUser(int userID, String sourceType,Session s) {
		String permissionString = "";
		Role[] roles = getValidRoles(userID, s);
		if(roles==null || roles.length<=0) return permissionString;
		List<Integer> roleIDs = new ArrayList<Integer>();
		for (Role role : roles) {
			roleIDs.add(role.getId());
		}
		DAO d = new DAO();
		String HQL = "from com.tl.invest.sys.user.Permission as A where A.roleID in(:roleIDs) and A.sourceType=:sourceType ";
		Object[] params = new Object[]{roleIDs,sourceType};
		
		List list = s==null ? d.find(HQL,params) : d.find(HQL,params, s);		
		Permission[] permissions = (Permission[]) list.toArray(new Permission[0]);
		
		return SysUserHelper.mergeUserPermission(permissions, ",");
	}

	@Override
	public RoleUser[] getRoleUsers() {
		return getRoleUsers(null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public RoleUser[] getRoleUsers(Session s) {
		DAO d = new DAO();
		String hql = "select a from com.tl.invest.sys.user.RoleUser as a order by a.userID desc";		
		List list = s==null ? d.find(hql) : d.find(hql, s);		
		return (RoleUser[]) list.toArray(new RoleUser[0]);	
	}

	@Override
	public RoleUser getRoleUser(int userID, int roleID) {
		return getRoleUser(userID, roleID,null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RoleUser getRoleUser(int userID, int roleID, Session s) {
		RoleUser roleUser = null;
		DAO d = new DAO();
		String HQL = "select a from com.tl.invest.sys.user.RoleUser as a where a.roleID = :roleID and a.userID=:userID";
		Object[] params = new Object[]{roleID,userID};
		List roleUsers = s == null ? d.find(HQL,params) : d.find(HQL, params, s);
		if(!roleUsers.isEmpty()){
			roleUser = (RoleUser)roleUsers.get(0);
		}
		return roleUser;
	}
}
