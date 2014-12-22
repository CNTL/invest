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
 * @created 2014年12月1日 上午1:30:20 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RecruitMainController extends Entry {
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("detail".equals(action)){//获取招聘详细信息
			detail(request, response, model);
		} else if("queryNew".equals(action)){//获取最新9条招聘信息
			queryRecruits(request, response, model, "queryNew");
		}  else if("queryHot".equals(action)){//获取最热9条招聘信息
			queryRecruits(request, response, model, "queryHot");
		} else{//直接进入招聘信息列表
			Dictionary[] types = recruitManager.types();
			model.put("types", types);
		}
		
	}
	/** 
	* @author  leijj 
	* 功能： 查询最新招聘信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryRecruits(HttpServletRequest request, HttpServletResponse response, Map model, String queryType) throws Exception{
		String recruitType = get(request, "recruitType");//是否是职位管理（view-浏览所有招聘信息，edit-管理我的职位信息）
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int curPage = getInt(request, "curPage", 1);
		Message msg = setUser(recruitManager.queryRecruits(curPage, 9, recruitType, queryType, user == null ? 0 : user.getId()));
		Dictionary[] types = recruitManager.types();
		model.put("queryType", queryType);
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("more", ParamInitUtils.getString(request.getParameter("more")));
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
		model.put("title", "合众映画--项目");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	
	/** 
	* @author  leijj 
	* 功能： 获取招聘信息详细信息
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