package com.tl.invest.user.recruit;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��12��24�� ����8:46:18 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings("rawtypes")
public class RecruitResumeController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("collect".equals(action)){//�ղ�ְλ
			collect(request, response, model);
		} else if("post".equals(action)){//Ͷ�ݼ���
			post(request, response, model);
		}
	}
	/** 
	* @author  leijj 
	* ���ܣ� �ղ�ְλ����
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private void collect(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int recruitID = ParamInitUtils.getInt(request.getParameter("recruitID"));
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = 0;
		if(user != null) userId = user.getId();
		UserRecruitresume recruitresume = recruitManager.recruitresume(userId, recruitID);
		if(recruitresume != null) {
			output("�����ղظ�ְλ��", response);
			return;
		}
		
		//δ�ղ�ְλʱ�����ղ�ְλ��Ϣ
		UserRecruitresume recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setCollected(1);//�ղ�Ϊ1
  
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		
		output("ok", response);
	}
	
	private void post(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = 0;
		if(user != null) userId = user.getId();
		int recruitID = ParamInitUtils.getInt(request.getParameter("recruitID"));
		 
		UserRecruitresume recruitresume = recruitManager.recruitresumePost(userId, recruitID);
		if(recruitresume != null) {
			output("��ְλ����Ͷ�ݼ�����", response);
			return;
		}
		
		UserRecruitresume recruitResume = recruitManager.recruitresume(user.getId(), recruitID);
		if(recruitResume == null)
			recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setCollected(0);//Ͷ��Ϊ0
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		output("ok", response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}
