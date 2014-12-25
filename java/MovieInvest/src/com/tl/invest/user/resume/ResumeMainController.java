package com.tl.invest.user.resume;

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

/** 
 * @created 2014年12月5日 下午5:57:35 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public class ResumeMainController extends Entry {
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("myrecruit".equals(action)){//用户主页
			myrecruit(request, response, model);
		} else if("recruitresume".equals(action)){//用户图册
			recruitresume(request, response, model);
		}
	}
	private void myrecruit(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		
	}
	private void recruitresume(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		
	}
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
	protected Menu[] getInfoMenus(HttpServletRequest request, int infoType) {
		String class1 = "", class2 = "", class3 = "";
		switch (infoType) {
		case 1:
			class1 = "current";
			break;
		case 2:
			class2 = "current";
			break;
		case 3:
			class3 = "current";
			break;
		default:
			break;
		}
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "我的简历","/resume/myresume.do?infoType=1", class1, -999,0));
		list.add(new Menu(2, "职位收藏","/resume/myrecruit.do?infoType=2", class2, -999,0));
		list.add(new Menu(3, "已投简历", "/resume/recruitresume.do?infoType=3", class3,-999,0));
		return (Menu[]) list.toArray(new Menu[0]);
	}
}