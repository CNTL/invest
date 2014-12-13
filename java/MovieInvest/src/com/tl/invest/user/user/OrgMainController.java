package com.tl.invest.user.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.sys.mu.Menu;
import com.tl.invest.workspace.Entry;

/** 
 * @created 2014年11月30日 下午3:44:57 
 * @author  leijj
 * 类说明 ： 
 */
public class OrgMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "影人";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画--项目");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}

	protected Menu[] getInfoMenus(int infoType) {
		String class1 = "", class2 = "", class3 = "", class4 = "", class5 = "", class6 = "", class7 = "",class8="";
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
		case 8:
			class8 = "select";
			break;
		default:
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "基本资料", "/org/BasicInfo.do?infoType=1", class1, -999,0));
		list.add(new Menu(2, "修改密码", "/org/PwdChange.do?infoType=2", class2, -999,0));
		list.add(new Menu(3, "头像管理", "/user/HeadImg.do?infoType=3", class3,-999,0));
		list.add(new Menu(4, "机构认证", "/org/RelAuth.do?infoType=4", class4,-999,0));
		list.add(new Menu(5, "详细资料", "/org/DetailInfo.do?infoType=5", class5,-999,0));
		list.add(new Menu(6, "个人图册", "/user/PhotoGroupMa.do?infoType=6", class6,-999,0));
		list.add(new Menu(7, "个人视频", "/org/VideoMa.do?infoType=7", class7,-999,0));
		list.add(new Menu(8, "收件地址", "/org/Address.do?infoType=8", class8,-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
}