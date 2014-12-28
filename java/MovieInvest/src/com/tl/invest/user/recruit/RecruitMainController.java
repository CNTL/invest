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
			queryRecruits(request, response, model, "queryNew");
		} else if("queryHot".equals(action)){//��ȡ����9����Ƹ��Ϣ
			queryRecruits(request, response, model, "queryHot");
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
	private void queryRecruits(HttpServletRequest request, HttpServletResponse response, Map model, String queryType) throws Exception{
		request.setCharacterEncoding("utf-8");
		String recruitType = get(request, "recruitType");//�Ƿ���ְλ����view-���������Ƹ��Ϣ��edit-�����ҵ�ְλ��Ϣ��
		int searchType = getInt(request, "searchType");//0=ְλ��1=��˾
		String key = get(request, "key");//��ѯ������ֵ
		String city = get(request, "city");//��ѯ������ֵ
		int curPage = getInt(request, "curPage", 1);
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = user == null ? 0 : user.getId();
		Message msg = recruitManager.queryRecruits(curPage, 9, userId, recruitType, queryType, searchType, key, city);
		//setUser(recruitManager.queryRecruits(curPage, 9, recruitType, queryType, user == null ? 0 : user.getId()));
		Dictionary[] types = recruitManager.types();
		model.put("queryType", queryType);
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("searchType", searchType);
		model.put("key", key);
		model.put("city", city);
		model.put("more", ParamInitUtils.getString(request.getParameter("more")));
		model.put("msg", msg);
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