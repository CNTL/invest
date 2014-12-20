package com.tl.invest.user.recruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年11月14日 下午2:02:54 
 * @author  leijj
 * 类说明 ： 招聘控制类
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RecruitController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("list".equals(action)){//获取用户列表
			//list(request, response, model);
		} else if("queryNew".equals(action)){//获取最新9条招聘信息
			queryNew(request, response);
		} else if("queryHot".equals(action)){//获取最热9条招聘信息
			queryHot(request, response);
		} else if("save".equals(action)){//保存招聘信息
			String json = save(request, response);
			output(json, response);
		} else if("detail".equals(action)){//获取招聘详情
			int id = ParamInitUtils.getInt(request.getParameter("id"));
			model.put("id", id);
			model.put("mainType", ParamInitUtils.getInt(request.getParameter("mainType")));
			model.put("@VIEWNAME@", "/recruit/DetailMain.do?a=detail");
		} else if("edit".equals(action)){//获取修改信息
			int id = ParamInitUtils.getInt(request.getParameter("id"));
			model.put("id", id);
			model.put("mainType", ParamInitUtils.getInt(request.getParameter("mainType")));
			model.put("@VIEWNAME@", "/recruit/Edit.do?a=detail");
		} else if("isCollect".equals(action)){//是否已收藏该职位
			isCollect(request, response);
		} else if("collect".equals(action)){//收藏职位
			String json = collect(request, response);
			output(json, response);
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
	private void queryNew(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String typeFlag = get(request, "typeFlag");//是否是职位管理（view-浏览所有招聘信息，edit-管理我的职位信息）
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int start = getInt(request, "start");
		Message msg = setUser(recruitManager.queryRecruits(start, 9, typeFlag, user == null ? 0 : user.getId()));
		JSONArray jsonArray = JSONArray.fromObject(msg);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* 功能： 查询最热招聘信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryHot(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String typeFlag = get(request, "typeFlag");//是否是职位管理（view-浏览所有招聘信息，edit-管理我的职位信息）
		int start = getInt(request, "start");
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		Message msg = setUser(recruitManager.queryHot(start, 9, typeFlag, user == null ? 0 : user.getId()));
		JSONArray jsonArray = JSONArray.fromObject(msg);  
		output(jsonArray.toString(), response);
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
	/** 
	* @author  leijj 
	* 功能： 保存招聘信息
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		/*
		user.setOrgFullname(ParamInitUtils.getString(request.getParameter("orgFullname")));
		user.setLocation(ParamInitUtils.getString(request.getParameter("location")));
		user.setCoordinate(ParamInitUtils.getString(request.getParameter("coordinate")));
		user.setOrgNature(ParamInitUtils.getString(request.getParameter("orgNature")));
		user.setOrgTrade(ParamInitUtils.getString(request.getParameter("orgTrade")));
		user.setOrgScale(ParamInitUtils.getString(request.getParameter("orgScale")));
		user.setOrgHomePage(ParamInitUtils.getString(request.getParameter("orgHomePage")));
		userManager.update(user);
		*/
		
		UserRecruit recruit = new UserRecruit();
		recruit.setUserId(user.getId());
		recruit.setUserName(user.getName());
		//jobName jobPictrue salary working eduReq isFulltime jobAttract
		recruit.setJobName(ParamInitUtils.getString(request.getParameter("jobName")));
		recruit.setJobPictrue(ParamInitUtils.getString(request.getParameter("jobPictrue")));
		recruit.setSalary(ParamInitUtils.getString(request.getParameter("salary")));
		recruit.setDays(ParamInitUtils.getString(request.getParameter("days")));
		recruit.setWorking(ParamInitUtils.getString(request.getParameter("working")));
		recruit.setEduReq(ParamInitUtils.getString(request.getParameter("eduReq")));
		recruit.setIsFulltime(ParamInitUtils.getInt(request.getParameter("isFulltime")));
		recruit.setJobAttract(ParamInitUtils.getString(request.getParameter("jobAttract")));
		recruit.setContent(ParamInitUtils.getString(request.getParameter("content")));
		recruit.setLinkman(ParamInitUtils.getString(request.getParameter("linkman")));
		recruit.setLinkPhone(ParamInitUtils.getString(request.getParameter("linkPhone")));
		recruit.setLinkEmail(ParamInitUtils.getString(request.getParameter("linkEmail")));
		recruit.setCreatetime(DateUtils.getTimestamp());
		//recruit.setPubTime(DateUtils.getTimestamp());
		recruitManager.save(recruit);
		return "ok";
	}
	
	private void isCollect(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = 0;
		if(user != null) userId = user.getId();
		boolean isCollect = recruitManager.isCollect(userId);
		output(String.valueOf(isCollect), response);
	}
	/** 
	* @author  leijj 
	* 功能： 收藏职位保存
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String collect(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		UserRecruitresume recruitResume = new UserRecruitresume();
		recruitResume.setUserId(user.getId());
		recruitResume.setUserName(user.getName());
		recruitResume.setRecruitId(ParamInitUtils.getInt(request.getParameter("recruitID")));
		recruitResume.setResumeId(0);
		recruitResume.setIsPostResume(0);;
		recruitResume.setCreatetime(DateUtils.getTimestamp());
		recruitManager.collect(recruitResume);
		return "ok";
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}