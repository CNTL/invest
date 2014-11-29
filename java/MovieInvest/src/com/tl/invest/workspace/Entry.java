package com.tl.invest.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;
import com.tl.invest.sys.mu.Menu;

public class Entry extends BaseController {
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String rootPath = WebUtil.getRoot(request);
		String url = request.getRequestURL().toString();
		String path = url.substring(rootPath.length()-1);
		
		int userID = 0;
		int userType = 0;
		String userName = "";
		String userCode = "";
		String avatar = "";
		UserManager userManager = (UserManager)Context.getBean(UserManager.class);
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		if(user!=null){
			userID = user.getId();
			userCode = user.getCode();
			userName = user.getName();
			userType = user.getType();
			avatar = user.getHead();
		}
		
		model.put("userID", userID);
		model.put("userName",userName);
		model.put("userCode", userCode);
		model.put("userType", userType);
		model.put("userAvatar", StringUtils.isEmpty(avatar)?rootPath+"static/image/temp/avatar1.png":avatar);
		
		
		Menu[] menus = getMainMenus();
		Menu[] pMenus = getPersonMenus();
		for (Menu menu : menus) {
			menu.setClassName("");
			if(menu.getName().equals(getCurrentMenu())){
				menu.setClassName("current");
			}
			//重新设置菜单的URL为完整的URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		for (Menu menu : pMenus) {
			//重新设置菜单的URL为完整的URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		
		model.put("menus", menus);
		model.put("pMenus", pMenus);
		
		setMetaData(model);
		
		setOtherData(model);
	}
	
	@SuppressWarnings("rawtypes")
	protected void setOtherData(Map model) {
		//继承实现		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setMetaData(Map model) {
		model.put("title", "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}

	protected Menu[] getMainMenus() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "首页","/index.html","", -999,0));
		list.add(new Menu(2, "项目","/project/List.do","", -999,0));
		list.add(new Menu(3, "影聘","/recruit/recruitList.jsp","", -999,0));
		list.add(new Menu(4, "影人","/user/people.html","", -999,0));
		
		return (Menu[]) list.toArray(new Menu[0]);
	}

	protected String getCurrentMenu() {
		return "首页";
	}
	
	protected Menu[] getPersonMenus() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "机构设置","/user/orgSetting.jsp","set", -999,0));
		list.add(new Menu(2, "消息中心<span class=\"msg-info\"></span>","/user/msg/msgSetting.jsp","msg", -999,0));
		list.add(new Menu(3, "职位管理", "","spo",-999,0));
		list.add(new Menu(4, "项目管理", "","spo",-999,0));
		list.add(new Menu(41, "发布项目", "/project/Publish.do","spo",-999,0));
		list.add(new Menu(5, "退出", "/user/logout.do","exit bn",-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
}
