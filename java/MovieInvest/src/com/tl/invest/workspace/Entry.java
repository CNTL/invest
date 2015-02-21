package com.tl.invest.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.sys.mu.Menu;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;
@SuppressWarnings({ "unchecked", "rawtypes", "unused"})
public class Entry extends BaseController {
		
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String rootPath = WebUtil.getRoot(request);
		String url = request.getRequestURL().toString();
		String path = url.substring(rootPath.length()-1);
		
		setUserInfo(request,model);		
		
		Menu[] menus = getMainMenus(getInt(request, "mainType"));
		Menu[] pMenus = getPersonMenus(request);
		Menu[] iMenus = getInfoMenus(request, getInt(request, "infoType"));
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
		
		setOtherData(request,response,model);
		
		setMetaData(request,model);
	}
	
	private void setUserInfo(HttpServletRequest request,Map model) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		if(user!=null){
			user.setName(StringUtils.isEmpty(user.getName())?user.getCode():user.getName());
			user.setHead(WebUtil.getRoot(request)+(StringUtils.isEmpty(user.getHead())?"static/image/temp/avatar1.png":user.getHead()));
		}else {
			user = new User(0);
		}
		try {
			DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
			model.put("cityname", dicReader.getDic(DicTypes.DIC_AREA.typeID(), Integer.parseInt(user.getCity(), 10)).getName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		model.put("loginUser", user);
		model.put("id", getInt(request, "id"));//页面传过来的数据id
	}
	
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//继承实现		
	}

	protected void setMetaData(HttpServletRequest request,Map model) {
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
			String curMenu = getCurrentMenu();
			if("首页".equals(curMenu)){
				class1 = "current";
			}else if("项目".equals(curMenu)){
				class2 = "current";
			}else if("影聘".equals(curMenu)){
				class3 = "current";
			}else if("影人".equals(curMenu)){
				class4 = "current";
			}
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "首页","/",class1, -999,0));
		list.add(new Menu(2, "项目","/project/List.do?mainType=2",class2, -999,0));
		list.add(new Menu(3, "影聘","/recruit/ListMain.do?a=queryNew&recruitType=view&mainType=3",class3, -999,0));
		list.add(new Menu(4, "影人","/user/PeopleMain.do?a=queryPersons&mainType=4",class4, -999,0));
		 
		
		return (Menu[]) list.toArray(new Menu[0]);
	}

	protected String getCurrentMenu() {
		return "首页";
	}
	
	/** 
	* @author  leijj 
	* 功能： 获取个人设置信息（机构用户和个人用户分开处理）
	* @param request
	* @return 
	*/ 
	protected Menu[] getPersonMenus(HttpServletRequest request) {
		int type = -1;
		int userid = -1;
		try {
			User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
			if(user != null){
				type = user.getType();
				userid = user.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Menu> list = new ArrayList<Menu>();
		if(type == 0){//个人用户登录后
			list.add(new Menu(1, "个人设置","/user/BasicInfo.do?infoType=1","set", -999,0));
			list.add(new Menu(2, "消息中心","/user/MsgMa.do?infoType=1&mainType=4","msg-info", -999,0));
			list.add(new Menu(3, "职位管理", "/user/userRecruitManager.do?userid="+userid,"spo",-999,0));
			list.add(new Menu(4, "简历管理", "/resume/myresume.do?infoType=1","spo",-999,0));
			list.add(new Menu(5, "项目管理", "/user/Project.do","spo",-999,0));
			list.add(new Menu(6, "发布项目", "/project/Publish.do","spo",-999,0));
			list.add(new Menu(7, "退出", "/user/logout.do","exit bn",-999,0));
		} else if(type == 1){//机构用户登录后
			list.add(new Menu(1, "机构设置","/org/BasicInfo.do?infoType=1&mainType=4","set", -999,0));
			list.add(new Menu(2, "消息中心","/user/MsgMa.do?infoType=1&mainType=4","msg-info", -999,0));
			list.add(new Menu(3, "职位管理", "/user/userRecruitManager.do?userid="+userid,"spo",-999,0));
			list.add(new Menu(4, "项目管理", "/user/Project.do","spo",-999,0));
			list.add(new Menu(41, "发布项目", "/project/Publish.do","spo",-999,0));
			list.add(new Menu(5, "退出", "/user/logout.do","exit bn",-999,0));
		}
		return (Menu[]) list.toArray(new Menu[0]);
	}

	/** 
	* @author  leijj 
	* 功能： 各个子类中去实现
	* @param infoType
	* @return 
	*/ 
	protected Menu[] getInfoMenus(HttpServletRequest request, int infoType) {
		return new Menu[0];
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}
