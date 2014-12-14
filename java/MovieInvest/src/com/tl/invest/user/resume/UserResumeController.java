package com.tl.invest.user.resume;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.tl.common.DateUtils;
import com.tl.common.Message;
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
		} else if("curResume".equals(action)){//����id��ѯ����
			curResume(request, response);
		} else if("deleteResume".equals(action)){//ɾ������
			deleteResume(request, response);
		} else if("myRecruit".equals(action)){//���ղص�ְλ
			myRecruit(request, response);
		} else if("recruitResume".equals(action)){//��Ͷ�ݼ�����ְλ
			recruitResume(request, response);
		} else if ("uploadAtt".equals(action)) {
			String json = upload(request);
			output(json, response);
		}
	}
	
	private void saveResume(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id");
		SysSessionUser user = SessionHelper.getUser(request);
		UserResume resume = new UserResume();
		if(id > 0) resume.setId(id);
		resume.setUserId(user.getUserID());
		resume.setUserName(user.getUserName());
		resume.setName(ParamInitUtils.getString(request.getParameter("name")));
		resume.setContent(ParamInitUtils.getString(request.getParameter("content")));
		resume.setAffix(ParamInitUtils.getString(request.getParameter("affix")));
		resume.setCreateTime(DateUtils.getTimestamp());
		resumeManager.saveResume(resume);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ����id��ȡ������Ϣ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void curResume(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id");
		UserResume resume = resumeManager.curResume(id);
		String json = JSONArray.fromObject(resume).toString();
		output(json, response);
	}
	private void deleteResume(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id");
		resumeManager.delete(id);
		output("ok", response);
	}
	private String upload(HttpServletRequest request){
		return UserHelper.saveAffix(request, "upload/user/resume/");
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ�ҵļ���
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getMyResumes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		String typeFlag = get(request, "typeFlag");//����������޸Ľ�����view-���������edit-�޸Ľ�����
		int start = getInt(request, "start");
		Message result = resumeManager.getMyResumes(start, 9, typeFlag, user);
		//List<Map<String, String>> result = resumeManager.getMyResumes(user);
		String json = JSONArray.fromObject(result).toString();
		output(json, response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ���ղص�ְλ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void myRecruit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int start = getInt(request, "start");
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		Message message = resumeManager.myRecruit(start, 9, user, true);
		String json = JSONArray.fromObject(message).toString();
		output(json, response);
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ����Ͷ��������ְλ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void recruitResume(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int start = getInt(request, "start");
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		Message message = resumeManager.myRecruit(start, 9, user, false);
		String json = JSONArray.fromObject(message).toString();
		output(json, response);
	}
	UserResumeManager resumeManager = (UserResumeManager) Context.getBean(UserResumeManager.class);
	UserManager userManager = (UserManager) Context.getBean(UserManager.class);
}
