package com.tl.invest.user.msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.sys.mu.Menu;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��12��5�� ����5:57:35 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public class MsgDetailMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "Ӱ��";
	}
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		int msg_toID = getInt(request, "msg_toID");
		User user = userManager.getUserByID(msg_toID);
		model.put("msg_toID", msg_toID);
		model.put("msg_to", user.getName());
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	@Override
	protected Menu[] getInfoMenus(HttpServletRequest request, int infoType) {
		String class1 = "";
		switch (infoType) {
		case 1:
			class1 = "select";
			break;
		default:
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "˽��","/user/MsgMa.do?infoType=1&mainType=4", class1, -999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}