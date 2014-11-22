package com.tl.invest.user.recruit;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��11��14�� ����2:02:54 
 * @author  leijj
 * ��˵�� �� ��Ƹ������
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RecruitController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("list".equals(action)){//��ȡ�û��б�
			list(request, response, model);
		} else if("save".equals(action)){//��ȡ�û��б�
			String json = save(request, response);
			output(json, response);
		} else if("detail".equals(action)){//��ȡ�û��б�
			detail(request, response, model);
		}
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ��Ƹ��Ϣ�б�
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void list(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int curPage = ParamInitUtils.getInt(request.getParameter("curPage"));
		int length = ParamInitUtils.getInt(request.getParameter("length"));
		Message message = recruitManager.getMessageList(curPage, length);
		model.put("length", length);
		model.put("msg", message);
		model.put("@VIEWNAME@", WebUtil.getRoot(request) + "user/recruit/recruitList");
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
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		UserRecruit recruit = new UserRecruit();
		recruit.setUserId(user.getId());
		recruit.setUserName(user.getName());
		recruit.setJobName(ParamInitUtils.getString(request.getParameter("jobName")));
		recruit.setProvince(ParamInitUtils.getString(request.getParameter("province")));
		recruit.setCity(ParamInitUtils.getString(request.getParameter("city")));
		recruit.setArea(ParamInitUtils.getString(request.getParameter("area")));
		//recruit.setCompanyNature(ParamInitUtils.getString(request.getParameter("companyNature")));
		//recruit.setJobTrade(ParamInitUtils.getString(request.getParameter("jobTrade")));
		//recruit.setScale(ParamInitUtils.getString(request.getParameter("scale")));
		recruit.setContent(ParamInitUtils.getString(request.getParameter("content")));
		recruit.setCompany(ParamInitUtils.getString(request.getParameter("company")));
		recruit.setLinkman(ParamInitUtils.getString(request.getParameter("linkman")));
		recruit.setLinkPhone(ParamInitUtils.getString(request.getParameter("linkPhone")));
		recruit.setLinkEmail(ParamInitUtils.getString(request.getParameter("linkEmail")));
		recruit.setCreatetime(DateUtils.getTimestamp());
		recruit.setPubTime(DateUtils.getTimestamp());
		recruitManager.save(recruit);
		return "ok";
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ��Ƹ��Ϣ�б�
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void detail(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserRecruit recruit = recruitManager.getRecruitByID(id);
		model.put("recruit", recruit);
		model.put("@VIEWNAME@", WebUtil.getRoot(request) + "user/recruit/recruitDetail");//��ת��Coupon.jspҳ��
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}