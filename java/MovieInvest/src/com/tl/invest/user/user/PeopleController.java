package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.context.Context;
/** 
 * @created 2014��12��18�� ����9:52:04 
 * @author  leijj
 * ��˵�� �� 
 */
public class PeopleController extends UserMainController {
	User user = null;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		int id = getInt(request, "id", 0);
		if(id > 0){
			UserManager userManager = (UserManager)Context.getBean(UserManager.class);
			user = userManager.getUserByID(id);
			if(user!=null){
				model.put("user", user);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", (user !=null ? user.getName():"") + "����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", user !=null ? user.getName():"����ӳ��");
	}
}