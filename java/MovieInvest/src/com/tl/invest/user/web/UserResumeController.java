package com.tl.invest.user.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.User;
import com.tl.invest.user.UserHelper;
import com.tl.invest.user.UserManager;
import com.tl.invest.user.UserResume;
import com.tl.invest.user.UserResumeManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年11月2日 下午10:37:24 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings("rawtypes")
public class UserResumeController extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getMyResumes".equals(action)){//根据消息接收方查出消息列表
			getMyResumes(request, response);
		} else if("saveResume".equals(action)){//保存用户简历
			saveResume(request, response);
		} else if ("uploadAtt".equals(action)) {
			String json = upload(request);
			output(json, response);
		}
	}
	
	private void saveResume(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysSessionUser user = SessionHelper.getUser(request);
		UserResume resume = new UserResume();
		resume.setUserId(user.getUserID());
		resume.setUserName(user.getUserName());
		resume.setContent(ParamInitUtils.getString(request.getParameter("content")));
		resume.setAffix(ParamInitUtils.getString(request.getParameter("affix")));
		resume.setModifyTime(DateUtils.getTimestamp());
		resumeManager.saveResume(resume);
		output("ok", response);
	}
	
	private String upload(HttpServletRequest request){
		return UserHelper.saveAffix(request, "upload/user/relAuth/");
	}
	
	private void getMyResumes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		List<Map<String, String>> result = resumeManager.getMyResumes(user);
		String json = JSONArray.fromObject(result).toString();
		output(json, response);
	}
	UserResumeManager resumeManager = (UserResumeManager) Context.getBean(UserResumeManager.class);
	UserManager userManager = (UserManager) Context.getBean(UserManager.class);
}
