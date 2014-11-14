package com.tl.invest.recruit.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.User;
import com.tl.kernel.web.BaseController;

/** 
 * @created 2014��11��14�� ����2:02:54 
 * @author  leijj
 * ��˵�� �� 
 */
public class RecruitController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("save".equals(action)){//��ȡ�û��б�
			save(request, response);
		}
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ������Ƹ��Ϣ
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		user.setName(ParamInitUtils.getString(request.getParameter("name")));
		user.setCode(ParamInitUtils.getString(request.getParameter("code")));
		user.setType(ParamInitUtils.getString(request.getParameter("type")));
		user.setTypeId(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		return "ok";
	}
}
