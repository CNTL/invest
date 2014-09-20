package com.tl.invest.sys.user;

import java.util.ArrayList;
import java.util.List;

import com.tl.common.StringUtils;
import com.tl.kernel.context.CacheReader;
import com.tl.kernel.context.Context;

public class SysUserHelper {
	
	public static SysUserManager getUserManager(){
		SysUserManager manager = (SysUserManager)Context.getBean(SysUserManager.class);
		if (manager == null)
			manager = new SysUserManagerImpl();
		return manager;
	}
	
	public static SysUserCache getCache()
	{
		return (SysUserCache)CacheReader.find(SysUserCache.class);
	}
	
	/**
	 * 合并权限
	 * @param permissions 权限类型相同但角色不同时，将所有的权限进行合并
	 * @param separator
	 * @return
	 */
	public static String mergeUserPermission(Permission[] permissions,String separator){
		if(permissions == null || permissions.length<=0) return "";
		
		List<String> permissionList = new ArrayList<String>();
		for (Permission permission : permissions) {
			if(StringUtils.isEmpty(permission.getSourceValues())) continue;
			
			String[] permissionArray = permission.getSourceValues().split(separator);
			for (String str : permissionArray) {
				boolean hasExist = false;
				for (String l : permissionList) {
					if(l.equals(str)){
						hasExist = true;
						break;
					}
				}
				if(!hasExist)
					permissionList.add(str);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		if(permissionList.size()>0){
			for (String str : permissionList) {
				if(sb.length()>0) sb.append(separator);
				sb.append(str);
			}
		}
		
		return sb.toString();
	}
}
