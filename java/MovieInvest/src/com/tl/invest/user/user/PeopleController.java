package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.kernel.context.Context;
/** 
 * @created 2014年12月18日 上午9:52:04 
 * @author  leijj
 * 类说明 ： 
 */
public class PeopleController extends UserMainController {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response, Map model) throws Exception {
		int page = getInt(request, "page", 1);
		UserManager userManager = (UserManager)Context.getBean(UserManager.class);
		int count = 0;
		User[] users = userManager.getUsers();
		model.put("users", users);
		int pageCount = count/20;
		if(count % 20 >0) pageCount = pageCount + 1;
		if(pageCount<=0) pageCount = 1;
		
		model.put("count", count);
		model.put("pageCount", pageCount);
		model.put("pageBegin", getPageBegin(page));
		model.put("pageEnd", getPageEnd(page, pageCount));
		model.put("page", page);
	}

	protected int getPageBegin(int page) {
		int begin = page;
		
		for (int i=1;i<=4;i++) {
			if(begin == 1){
				break;
			}
			begin = begin - i;
		}
		return begin;
	}
	
	protected int getPageEnd(int page,int pageCount) {
		int end = page;
		for (int i=1;i<=4;i++) {
			if(end==pageCount){
				break;
			}
			end = end + 1;
		}
		return end;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		int m = getInt(request, "m", 1);
		model.put("title", (m==1?"发起的项目":(m==2?"支持的项目":(m==3?"喜欢的项目":"未知")))+"--合众映画");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
}
