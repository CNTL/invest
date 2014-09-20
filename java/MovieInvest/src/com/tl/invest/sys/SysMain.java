package com.tl.invest.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.invest.sys.mu.Menu;
import com.tl.invest.sys.mu.MenuReader;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;

public class SysMain extends BaseController {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		MenuReader muReader = (MenuReader)Context.getBean("MenuReader");
		Menu[] menus = muReader.getMenus(0);
		SysSessionUser user = (SysSessionUser)request.getSession().getAttribute(SysSessionUser.sessionAdminName);
		
		int muId = getInt(request, "id", 0);
		Menu menu = muReader.getMenu(muId);
		
		String jsTags = "";
		String cssTags = "";
		if(menu!=null){
			if(StringUtils.isNotEmpty(menu.getJsPath())){
				jsTags = getJsTags(menu);
			}
			if(StringUtils.isNotEmpty(menu.getCssPath())){
				cssTags = getCssTags(menu);
			}
		}
		
		model.put("menus", menus);
		model.put("JsTags", jsTags);
		model.put("CssTags", cssTags);
		model.put("userID", user.getUserID());
		model.put("userName", user.getUserName());
		model.put("userCode", user.getUserCode());
		
	}
	private String getCssTags(Menu menu) {
		StringBuffer sb = new StringBuffer();
		String[] csses = menu.getCssPath().split(",");
		for (String css : csses) {
			if(StringUtils.isNotEmpty(css)){
				if(sb.length()>0) sb.append("\n");
				sb.append("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
				sb.append(css);
				sb.append("\"/>");
			}
		}
		return sb.toString();
	}
	private String getJsTags(Menu menu) {
		StringBuffer sb = new StringBuffer();
		String[] jses = menu.getJsPath().split(",");
		for (String js : jses) {
			if(StringUtils.isNotEmpty(js)){
				if(sb.length()>0) sb.append("\n");
				sb.append("\t<script type=\"text/javascript\" src=\"");
				sb.append(js);
				sb.append("\"></script>");
			}
		}
		return sb.toString();
	}
}
