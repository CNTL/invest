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
 * @created 2014年12月5日 下午5:57:35 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public class MsgMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "影人";
	}
	
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	@Override
	protected Menu[] getInfoMenus(int infoType) {
		String class1 = "";
		switch (infoType) {
		case 1:
			class1 = "select";
			break;
		default:
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "私信","/user/MsgMa.do?infoType=1&mainType=4", class1, -999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
}