package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
}