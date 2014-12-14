package com.tl.invest.user.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.sys.mu.Menu;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年11月30日 下午3:44:57 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class UserMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "影人";
	}
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//继承实现
		int groupID = getInt(request, "groupID");
		model.put("groupID", groupID);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画--项目");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	@Override
	protected Menu[] getInfoMenus(HttpServletRequest request, int infoType) {
		String class1 = "", class2 = "", class3 = "", class4 = "", class5 = "", class6 = "", class7 = "", class8 = "";
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
		int type = 0;
		try {
			User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
			if(user != null) type = user.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Menu> list = new ArrayList<Menu>();
		if(type == 0){//个人用户登录后
			list.add(new Menu(1, "基本资料","/user/BasicInfo.do?infoType=1", class1, -999,0));
			list.add(new Menu(2, "修改密码","/user/PwdChange.do?infoType=2", class2, -999,0));
			list.add(new Menu(3, "头像管理", "/user/HeadImg.do?infoType=3", class3,-999,0));
			list.add(new Menu(4, "实名认证", "/user/RelAuth.do?infoType=4", class4,-999,0));
			/*list.add(new Menu(41, "详细资料", "/user/DetailInfo.do?infoType=5", class5,-999,0));*/
			list.add(new Menu(5, "个人图册", "/user/PhotoGroupMa.do?infoType=6", class6,-999,0));
			list.add(new Menu(6, "个人视频", "/user/VideoMa.do?infoType=7", class7,-999,0));
			list.add(new Menu(7, "收件地址", "/user/Address.do?infoType=8", class8,-999,0));
		} else if(type == 1){//机构用户登录后
			list.add(new Menu(1, "基本资料", "/org/BasicInfo.do?infoType=1", class1, -999,0));
			list.add(new Menu(2, "修改密码", "/user/PwdChange.do?infoType=2", class2, -999,0));
			list.add(new Menu(3, "头像管理", "/user/HeadImg.do?infoType=3", class3,-999,0));
			list.add(new Menu(4, "机构认证", "/org/RelAuth.do?infoType=4", class4,-999,0));
			list.add(new Menu(5, "详细资料", "/org/DetailInfo.do?infoType=5", class5,-999,0));
			list.add(new Menu(6, "个人图册", "/user/PhotoGroupMa.do?infoType=6", class6,-999,0));
			list.add(new Menu(7, "个人视频", "/user/VideoMa.do?infoType=7", class7,-999,0));
			list.add(new Menu(8, "收件地址", "/org/Address.do?infoType=8", class8,-999,0));
		}
		
		return (Menu[]) list.toArray(new Menu[0]);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}