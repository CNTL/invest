package com.tl.invest.sys.user.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.StringUtils;
import com.tl.invest.sys.user.Permission;
import com.tl.invest.sys.user.Role;
import com.tl.invest.sys.user.SysUserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.web.BaseController;

public class SysUserController extends BaseController {

	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		if("role".equals(action)){
			updateRoleInfo(request,model);
		} else if("del".equals(action)) {
			String item = get(request, "Item", "");
			if("role".equals(item)){
				deleteRole(request,model);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void deleteRole(HttpServletRequest request, Map model){
		SysUserManager userMgr = (SysUserManager)Context.getBean(SysUserManager.class);
		String idStr = get(request, "ItemID", "");
		String[] ids = idStr.split(",");
		for (String id : ids) {
			try {
				int itemID = Integer.parseInt(id);
				userMgr.deleteRole(itemID);
				userMgr.deletePermission(itemID);
				userMgr.deleteRoleUserByRole(itemID);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		String viewName = "redirect:/workspace/After.do?IDs="+idStr;
		model.put("@VIEWNAME@", viewName);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateRoleInfo(HttpServletRequest request, Map model) throws TLException{
		SysUserManager userMgr = (SysUserManager)Context.getBean(SysUserManager.class);
		int id = getInt(request, "ItemID", 0);
		Role role = null;
		if(id>0){
			role = userMgr.getRole(id);
		}
		if(role == null){
			role = new Role();
			role.setCreated(DateUtils.getTimestamp());			
			role.setId(0);
			role.setOrderNo(0);
		}
		role.setMemo(get(request, "memo", ""));
		role.setName(get(request, "name", ""));
		role.setDeleted(0);
		
		Role eRole = userMgr.getRole(role.getName());
		if(eRole!=null && eRole.getId()!=role.getId() && eRole.getDeleted()==0){
			model.put("errors", "已经存在名称为 "+role.getName()+" 的角色！");
			String viewName = "redirect:/workspace/After.do?IDs="+role.getId();
			model.put("@VIEWNAME@", viewName);
			return;
		}
		
		userMgr.saveRole(role);
		
		if(role.getId()>0){
			String[] menus = request.getParameterValues("menu");
			String permissionValueString = "";
			if(menus!=null && menus.length>0){
				for (String menu : menus) {
					if(StringUtils.isNotEmpty(permissionValueString)) permissionValueString += ",";
					permissionValueString += menu;
				}
			}
			Permission p = new Permission();
			p.setRoleID(role.getId());
			p.setSourceType("menu-0");
			p.setSourceValues(permissionValueString);
			userMgr.savePermission(p);
		}
		
		String viewName = "redirect:/workspace/After.do?IDs="+role.getId();
		model.put("@VIEWNAME@", viewName);
	}
	
	

}
