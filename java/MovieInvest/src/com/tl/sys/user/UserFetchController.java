package com.tl.sys.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;

/** 
 * @created 2014年9月3日 上午8:52:51 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({"rawtypes"})
public class UserFetchController  extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("type".equals(action)){//获取用户注册类型
			getTypes(request, response);
		} else if("find".equals(action)){//查找用户
			//find(request, response);
		}
	}
	
	/** 
	* @author  leijj 
	* 功能： 获取注册类型
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getTypes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		TypeManager typeManager = (TypeManager) Context.getBean(TypeManager.class);
		List types = typeManager.getAll();
		JSONObject jsonArray = JSONObject.fromObject(types);  
		output(jsonArray.toString(), response);
	}
}
