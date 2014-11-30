package com.tl.invest.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.WebUtil;
import com.tl.invest.sys.mu.Menu;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

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
		model.put("id", getInt(request, "id"));//页面传过来的数据id
		model.put("userID", userID);
		model.put("userName",(userName == null ? userCode : userName));
		model.put("userCode", userCode);
		model.put("userType", userType);
		//model.put("userAvatar", StringUtils.isEmpty(avatar)?WebUtil.getRoot(request)+"static/image/temp/avatar1.png":(WebUtil.getRoot(request).concat(avatar)));
		model.put("userAvatar", WebUtil.getRoot(request)+"static/image/temp/avatar1.png");
		
		
		Menu[] menus = getMainMenus(getInt(request, "mainType"));
		Menu[] pMenus = getPersonMenus();
		Menu[] iMenus = getInfoMenus(getInt(request, "infoType"));
		for (Menu menu : menus) {
			//重新设置菜单的URL为完整的URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		for (Menu menu : pMenus) {
			//重新设置菜单的URL为完整的URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		for (Menu menu : iMenus) {
			//重新设置菜单的URL为完整的URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		model.put("menus", menus);
		model.put("pMenus", pMenus);
		model.put("iMenus", iMenus);
		model.put("rootPath", rootPath);
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

	protected Menu[] getMainMenus(int mainType) {
		String class1 = "", class2 = "", class3 = "", class4 = "";
		switch (mainType) {
		case 1:
			class1 = "current";
			break;
		case 2:
			class2 = "current";
			break;
		case 3:
			class3 = "current";
			break;
		case 4:
			class4 = "current";
			break;
		default:
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "首页","/index.html?mainType=1",class1, -999,0));
		list.add(new Menu(2, "项目","/project/List.do?mainType=2",class2, -999,0));
		list.add(new Menu(3, "影聘","/recruit/List.do?recruitType=view&mainType=3",class3, -999,0));
		list.add(new Menu(4, "影人","/org/BasicInfo.do?infoType=1&mainType=4",class4, -999,0));
		
		return (Menu[]) list.toArray(new Menu[0]);
	}

	protected String getCurrentMenu() {
		return "首页";
	}
	
	protected Menu[] getPersonMenus() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "机构设置","/org/BasicInfo.do?infoType=1&mainType=1","set", -999,0));
		list.add(new Menu(2, "消息中心<span class=\"msg-info\"></span>","/user/msg/msgSetting.jsp","msg", -999,0));
		list.add(new Menu(3, "职位管理", "/recruit/List.do?recruitType=edit&mainType=3","spo",-999,0));
		list.add(new Menu(4, "项目管理", "","spo",-999,0));
		list.add(new Menu(41, "发布项目", "/project/Publish.do","spo",-999,0));
		list.add(new Menu(5, "退出", "/user/logout.do","exit bn",-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
	
	protected Menu[] getInfoMenus(int infoType) {
		String class1 = "", class2 = "", class3 = "", class4 = "", class5 = "", class6 = "", class7 = "";
		switch (infoType) {
		case 1:
			class1 = "select";
			break;
		case 2:
			class2 = "select";
			break;
		case 3:
			class3 = "select";
			break;
		case 4:
			class4 = "select";
			break;
		case 5:
			class5 = "select";
			break;
		case 6:
			class6 = "select";
			break;
		case 7:
			class7 = "select";
			break;
		default:
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "基本资料", "/org/BasicInfo.do?infoType=1", class1, -999,0));
		list.add(new Menu(2, "修改密码", "/org/PwdChange.do?infoType=2", class2, -999,0));
		list.add(new Menu(3, "头像管理", "/org/HeadImg.do?infoType=3", class3,-999,0));
		list.add(new Menu(4, "机构认证", "/org/RelAuth.do?infoType=4", class4,-999,0));
		list.add(new Menu(5, "详细资料", "/org/DetailInfo.do?infoType=5", class5,-999,0));
		list.add(new Menu(6, "个人图册", "/org/Photo.do?infoType=6", class6,-999,0));
		list.add(new Menu(7, "个人视频", "/org/Video.do?infoType=7", class7,-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
}
