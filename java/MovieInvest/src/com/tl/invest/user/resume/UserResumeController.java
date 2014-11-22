package com.tl.invest.user.resume;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserHelper;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��11��2�� ����10:37:24 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings("rawtypes")
public class UserResumeController extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getMyResumes".equals(action)){//������Ϣ���շ������Ϣ�б�
			getMyResumes(request, response);
		} else if("saveResume".equals(action)){//�����û�����
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
		resume.setCreateTime(DateUtils.getTimestamp());
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
