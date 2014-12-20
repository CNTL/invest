package com.tl.invest.user.recruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��12��1�� ����1:30:20 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RecruitMainController extends Entry {
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("detail".equals(action)){//��ȡ��Ƹ��ϸ��Ϣ
			detail(request, response, model);
		} else if("queryNew".equals(action)){//��ȡ����9����Ƹ��Ϣ
			queryNew(request, response, model);
		}  else if("queryHot".equals(action)){//��ȡ����9����Ƹ��Ϣ
			queryHot(request, response, model);
		} else{//ֱ�ӽ�����Ƹ��Ϣ�б�
			Dictionary[] types = recruitManager.types();
			model.put("types", types);
		}
		
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ѯ������Ƹ��Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryNew(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String recruitType = get(request, "recruitType");//�Ƿ���ְλ����view-���������Ƹ��Ϣ��edit-�����ҵ�ְλ��Ϣ��
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int start = getInt(request, "start");
		Message msg = setUser(recruitManager.queryRecruits(start, 9, recruitType,user == null ? 0 : user.getId()));
		Dictionary[] types = recruitManager.types();
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("msg", msg);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ѯ������Ƹ��Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryHot(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String recruitType = get(request, "recruitType");//�Ƿ���ְλ����view-���������Ƹ��Ϣ��edit-�����ҵ�ְλ��Ϣ��
		int start = getInt(request, "start");
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		Message msg = setUser(recruitManager.queryHot(start, 9, recruitType, user == null ? 0 : user.getId()));
		Dictionary[] types = recruitManager.types();
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("msg", msg);
	}
	private Message setUser(Message msg) throws Exception{
		if(msg == null) return null;
		List<UserRecruit> recruitList = msg.getMessages();
		if(recruitList == null || recruitList.size() == 0) return null;
		
		List<UserRecruit> newList = new ArrayList<UserRecruit>();
		for(UserRecruit recruit : recruitList){
			User user = userManager.getUserByID(recruit.getUserId());
			recruit.setCompany(user.getOrgFullname());
			recruit.setCity(user.getCity());
			recruit.setTime(DateUtils.format(recruit.getCreatetime(), "yyyy-MM-dd hh:mm:ss"));
			newList.add(recruit);
		}
		msg.setMessages(newList);
		return msg;
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ��Ƹ��Ϣ��ϸ��Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void detail(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		if(id == 0) return;
		UserRecruit recruit = recruitManager.getRecruitByID(id);
		if(recruit == null) return;
		recruit.setTime(DateUtils.format(recruit.getCreatetime(), "yyyy-MM-dd hh:mm:ss"));
		User user = userManager.getUserByID(recruit.getUserId());
		String head = user.getHead();
		if(head == null || head.length() == 0) head = "user/headImg/default.bmp";
		user.setHead(WebUtil.getRoot(request).concat(head));
		model.put("recruit", recruit);
		model.put("user", user);
		//response.sendRedirect(WebUtil.getRoot(request) + "user/recruit/recruitDetail.jsp");
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}