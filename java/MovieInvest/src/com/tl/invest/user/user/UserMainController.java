package com.tl.invest.user.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.sys.mu.Menu;
import com.tl.invest.workspace.Entry;

/** 
 * @created 2014��11��30�� ����3:44:57 
 * @author  leijj
 * ��˵�� �� 
 */
public class UserMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "Ӱ��";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	protected Menu[] getPersonMenus() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "��������","/user/BasicInfo.do?infoType=1","set", -999,0));
		list.add(new Menu(2, "��Ϣ����<span class=\"msg-info\"></span>","/user/msg/msgSetting.jsp","msg", -999,0));
		list.add(new Menu(3, "��������", "","spo",-999,0));
		list.add(new Menu(4, "��Ŀ����", "","spo",-999,0));
		list.add(new Menu(41, "������Ŀ", "/project/Publish.do","spo",-999,0));
		list.add(new Menu(5, "�˳�", "/user/logout.do","exit bn",-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
	
	@SuppressWarnings("unused")
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
		list.add(new Menu(1, "��������","/user/BasicInfo.do?infoType=1", class1, -999,0));
		list.add(new Menu(2, "�޸�����","/user/PwdChange.do?infoType=2", class2, -999,0));
		list.add(new Menu(3, "ͷ�����", "/user/HeadImg.do?infoType=3", class3,-999,0));
		list.add(new Menu(4, "ʵ����֤", "/user/RelAuth.do?infoType=4", class4,-999,0));
		/*list.add(new Menu(41, "��ϸ����", "/user/DetailInfo.do?infoType=5", class5,-999,0));*/
		list.add(new Menu(5, "����ͼ��", "/user/Photo.do?infoType=6", class6,-999,0));
		list.add(new Menu(6, "������Ƶ", "/user/Video.do?infoType=7", class7,-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
}