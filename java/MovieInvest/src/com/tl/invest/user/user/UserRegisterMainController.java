package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.WebUtil;
import com.tl.invest.workspace.Entry;

/** 
 * @created 2014年12月14日 下午12:05:18 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class UserRegisterMainController extends Entry {
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//继承实现
		String loginCurrentUrl = request.getParameter("url");
		//String loginCurrentMenu = WebUtil.getCookie(request, "loginCurrentMenu");//登录后一级菜单
		model.put("loginCurrentUrl", loginCurrentUrl);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "合众映画--登录");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
}
