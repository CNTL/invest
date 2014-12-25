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
 * @created 2014年12月24日 下午8:46:18 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings("rawtypes")
public class RecruitResumeController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("collect".equals(action)){//收藏职位
			collect(request, response, model);
		} else if("post".equals(action)){//投递简历
			post(request, response, model);
		}
	}
	/** 
	* @author  leijj 
	* 功能： 收藏职位保存
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
			output("您已收藏该职位！", response);
			return;
		}
		
		//未收藏职位时保存收藏职位信息
		UserRecruitresume recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setResumeId(0);
		recruitResume.setIsPostResume(0);;
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		
		output("ok", response);
	}
	
	private void post(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = 0;
		if(user != null) userId = user.getId();
		int recruitID = ParamInitUtils.getInt(request.getParameter("recruitID"));
		int resumeID = ParamInitUtils.getInt(request.getParameter("resumeID"));
		UserRecruitresume recruitresume = recruitManager.recruitresume(userId, recruitID, resumeID);
		if(recruitresume != null) {
			output("该职位您已投递简历！", response);
			return;
		}
		
		UserRecruitresume recruitResume = recruitManager.recruitresume(user.getId(), recruitID);
		if(recruitResume == null)
			recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setResumeId(resumeID);
		recruitResume.setIsPostResume(1);;
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		output("ok", response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}
