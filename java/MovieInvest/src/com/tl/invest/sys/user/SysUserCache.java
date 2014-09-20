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
	 * 缓存类主要用在Reader实现类中<br>
	 * 在引用时，注意不要使用构造方法创建缓存类的实例<br>
	 * 请使用如下形式：<br>
	 * <code>
	 * XXXCache cache = (XXXCache)(CacheReader.find(XXXCache.class));
	 * if (cache != null)
	 * 		return cache.get(...);
	 * </code>
	 * 其中XXXCache代表具体的缓存类
	 */
	public SysUserCache(){
	}

	@Override
	public void refresh() throws Exception {
		SysUserManager mgr = SysUserHelper.getUserManager();
		users = mgr.getUsers();
		log.info("缓存更新：成功获取"+users.length+"个系统用户。");
		roles = mgr.getRoles();
		log.info("缓存更新：成功获取"+roles.length+"个系统用户组。");
		roleUsers = mgr.getRoleUsers();
		log.info("缓存更新：成功获取"+roleUsers.length+"个系统用户角色关系。");
		permissions = mgr.getPermissions();
		log.info("缓存更新：成功获取"+permissions.length+"个系统角色权限关系。");
	}

	@Override
	public void reset() {
		
	}
}
