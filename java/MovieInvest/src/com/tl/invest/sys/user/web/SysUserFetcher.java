package com.tl.invest.sys.user.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.invest.sys.mu.Menu;
import com.tl.invest.sys.mu.MenuManager;
import com.tl.invest.sys.user.Permission;
import com.tl.invest.sys.user.Role;
import com.tl.invest.sys.user.SysUserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;

public class SysUserFetcher extends BaseController {
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		if("role".equalsIgnoreCase(action)){
			roleFetcher(request,model);
		}else if ("del".equalsIgnoreCase(action)) {
			String item = get(request, "item", "");
			if("role".equals(item)){
				deleteRole(request,model);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void roleFetcher(HttpServletRequest request,  Map model){
		int roleID = getInt(request, "id",0);
		SysUserManager userMgr = (SysUserManager)Context.getBean(SysUserManager.class);
		MenuManager muMgr = (MenuManager)Context.getBean(MenuManager.class);
		Menu[] menus = muMgr.getMenus(0);
		model.put("RoleID", roleID);
		model.put("menus", menus);
		model.put("RoleName", "");
		model.put("RoleMemo", "");
		model.put("permissions", "");
		if(roleID > 0){
			Role role = userMgr.getRole(roleID);
			if(role!=null){
				model.put("RoleName", role.getName());
				model.put("RoleMemo", role.getMemo());
				Permission p = userMgr.getPermission(roleID, "menu-0");
				if(p!=null){
					String ps = p.getSourceValues();
					model.put("permissions", StringUtils.isEmpty(ps)?"":ps);
				}
			}
		}
		
		model.put("@VIEWNAME@", "admin/org/role");
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void deleteRole(HttpServletRequest request,  Map model){
		String roleIDs = get(request, "id", "");
		
		model.put("ItemID", roleIDs);
		model.put("Item", get(request, "item", ""));

		model.put("@VIEWNAME@", "admin/org/delete");
	}
}
