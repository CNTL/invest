package com.tl.invest.user.recruit;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
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
		if("datas".equals(action)){
			initDatas(response);
		} else if("save".equals(action)){//保存招聘信息
			String json = save(request, response);
			output(json, response);
		} else if("detail".equals(action)){//获取招聘详情
			int id = ParamInitUtils.getInt(request.getParameter("id"));
			model.put("id", id);
			model.put("mainType", ParamInitUtils.getInt(request.getParameter("mainType")));
			response.sendRedirect(WebUtil.getRoot(request) + "recruit/DetailMain.do?a=detail&id=" + id);
		} else if("edit".equals(action)){//获取修改信息
			int id = ParamInitUtils.getInt(request.getParameter("id"));
			model.put("id", id);
			model.put("mainType", ParamInitUtils.getInt(request.getParameter("mainType")));
			response.sendRedirect(WebUtil.getRoot(request) + "recruit/Edit.do?a=detail&id=" + id);
		}
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
		int firstType = getInt(request, "firstType");
		int secondType = getInt(request, "secondType");
		String typeName = "";
		Dictionary dic = null;
		if(secondType > 0){
			dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), secondType);
			typeName = dic.getCascadeName();
		} else if(firstType > 0){
			dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), firstType);
			typeName = dic.getCascadeName();
		}
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
		recruit.setFirstType(firstType);
		recruit.setSecondType(secondType);
		recruit.setTypeName(typeName);
		//recruit.setPubTime(DateUtils.getTimestamp());
		recruitManager.save(recruit);
		return "ok";
	}
	private void initDatas(HttpServletResponse response) throws Exception {
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_RECRUIT_TYPE.typeID());
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary job : jobTypes) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+job.getId());
			sb1.append(",\"pid\":"+job.getPid());
			sb1.append(",\"name\":\""+job.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"jobTypes\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
	private DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
}