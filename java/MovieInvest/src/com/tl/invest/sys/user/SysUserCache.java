package com.tl.invest.sys.user;

import com.tl.common.log.Log;
import com.tl.kernel.context.Cache;
import com.tl.kernel.context.Context;

public class SysUserCache implements Cache {
	Log log = Context.getLog("invest");
	
	private SysUser[] users = null;
	private Role[] roles = null;
	private RoleUser[] roleUsers = null;
	private Permission[] permissions = null;
	
	/**
	 * ��������Ҫ����Readerʵ������<br>
	 * ������ʱ��ע�ⲻҪʹ�ù��췽�������������ʵ��<br>
	 * ��ʹ��������ʽ��<br>
	 * <code>
	 * XXXCache cache = (XXXCache)(CacheReader.find(XXXCache.class));
	 * if (cache != null)
	 * 		return cache.get(...);
	 * </code>
	 * ����XXXCache�������Ļ�����
	 */
	public SysUserCache(){
	}

	@Override
	public void refresh() throws Exception {
		SysUserManager mgr = SysUserHelper.getUserManager();
		users = mgr.getUsers();
		log.info("������£��ɹ���ȡ"+users.length+"��ϵͳ�û���");
		roles = mgr.getRoles();
		log.info("������£��ɹ���ȡ"+roles.length+"��ϵͳ�û��顣");
		roleUsers = mgr.getRoleUsers();
		log.info("������£��ɹ���ȡ"+roleUsers.length+"��ϵͳ�û���ɫ��ϵ��");
		permissions = mgr.getPermissions();
		log.info("������£��ɹ���ȡ"+permissions.length+"��ϵͳ��ɫȨ�޹�ϵ��");
	}

	@Override
	public void reset() {
		
	}
	
	public SysUser[] getUsers(){
		return users;
	}
	
	public Role[] getRoles(){
		return roles;
	}
	
	public RoleUser[] getRoleUsers(){
		return roleUsers;
	}
	
	public Permission[] getPermissions(){
		return permissions;
	}
}
