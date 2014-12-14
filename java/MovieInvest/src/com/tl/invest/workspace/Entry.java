package com.tl.invest.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
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
		
		setUserInfo(request,model);		
		
		Menu[] menus = getMainMenus(getInt(request, "mainType"));
		Menu[] pMenus = getPersonMenus(request);
		Menu[] iMenus = getInfoMenus(getInt(request, "infoType"));
		for (Menu menu : menus) {
			//�������ò˵���URLΪ������URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		for (Menu menu : pMenus) {
			//�������ò˵���URLΪ������URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		for (Menu menu : iMenus) {
			//�������ò˵���URLΪ������URL
			menu.setUrl(rootPath+(menu.getUrl().startsWith("/")?menu.getUrl().substring(1):menu.getUrl()));
		}
		model.put("menus", menus);
		model.put("pMenus", pMenus);
		model.put("iMenus", iMenus);
		model.put("rootPath", rootPath);
		
		setOtherData(request,response,model);
		
		setMetaData(request,model);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setUserInfo(HttpServletRequest request,Map model) throws Exception{
		UserManager userManager = (UserManager)Context.getBean(UserManager.class);
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		if(user!=null){
			user.setName(StringUtils.isEmpty(user.getName())?user.getCode():user.getName());
			user.setHead(WebUtil.getRoot(request)+(StringUtils.isEmpty(user.getHead())?"static/image/temp/avatar1.png":user.getHead()));
		}else {
			user = new User(0);
		}
		model.put("loginUser", user);
		model.put("id", getInt(request, "id"));//ҳ�洫����������id
	}
	
	@SuppressWarnings("rawtypes")
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//�̳�ʵ��		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
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
			if("��ҳ".equals(curMenu)){
				class1 = "current";
			}else if("��Ŀ".equals(curMenu)){
				class2 = "current";
			}else if("ӰƸ".equals(curMenu)){
				class3 = "current";
			}else if("Ӱ��".equals(curMenu)){
				class4 = "current";
			}
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "��ҳ","/",class1, -999,0));
		list.add(new Menu(2, "��Ŀ","/project/List.do",class2, -999,0));
		list.add(new Menu(3, "ӰƸ","/recruit/List.do?recruitType=view&mainType=3",class3, -999,0));
		list.add(new Menu(4, "Ӱ��","/org/BasicInfo.do?infoType=1&mainType=4",class4, -999,0));
		
		return (Menu[]) list.toArray(new Menu[0]);
	}

	protected String getCurrentMenu() {
		return "��ҳ";
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ����������Ϣ�������û��͸����û��ֿ�����
	* @param request
	* @return 
	*/ 
	protected Menu[] getPersonMenus(HttpServletRequest request) {
		int type = 0;
		try {
			User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
			if(user != null) type = user.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Menu> list = new ArrayList<Menu>();
		if(type == 0){//�����û���¼��
			list.add(new Menu(1, "��������","/user/BasicInfo.do?infoType=1","set", -999,0));
			list.add(new Menu(2, "��Ϣ����","/user/MsgMa.do?infoType=1&mainType=4","msg-info", -999,0));
			list.add(new Menu(3, "��������", "/resume/myresume.do?infoType=1","spo",-999,0));
			list.add(new Menu(4, "��Ŀ����", "","spo",-999,0));
			list.add(new Menu(41, "������Ŀ", "/project/Publish.do","spo",-999,0));
			list.add(new Menu(5, "�˳�", "/user/logout.do","exit bn",-999,0));
		} else if(type == 1){//�����û���¼��
			list.add(new Menu(1, "��������","/org/BasicInfo.do?infoType=1&mainType=4","set", -999,0));
			list.add(new Menu(2, "��Ϣ����","/user/MsgMa.do?infoType=1&mainType=4","msg-info", -999,0));
			list.add(new Menu(3, "ְλ����", "/recruit/List.do?recruitType=edit&mainType=3","spo",-999,0));
			list.add(new Menu(4, "��Ŀ����", "/user/Project.do","spo",-999,0));
			list.add(new Menu(41, "������Ŀ", "/project/Publish.do","spo",-999,0));
			list.add(new Menu(5, "�˳�", "/user/logout.do","exit bn",-999,0));
		}
		return (Menu[]) list.toArray(new Menu[0]);
	}

	/** 
	* @author  leijj 
	* ���ܣ� ����������ȥʵ��
	* @param infoType
	* @return 
	*/ 
	protected Menu[] getInfoMenus(int infoType) {
		return null;
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}
