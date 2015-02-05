package com.tl.invest.user.recruit;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
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
		} else if("queryHot".equals(action)){//获取最热9条招聘信息
			queryRecruits(request, response, model, "queryHot");
		}else if("getRecruitUser".equals(action)){
			getRecruitUser(request,response,model);
		}
		else if("delRecruit".equals(action)){
			delRecruit(request,response,model);
		}
		else{//直接进入招聘信息列表
			Dictionary[] types = recruitManager.types();
			model.put("types", types);
			init(request,response,model);
		}
		
	}
	/** 管理职位
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void init(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		Integer userid = getInt(request, "userid",-1);
		if(userid!=-1){
			//获得当前公司的所有职位。
			UserRecruit[] recruitLists = recruitManager.queryRecruitsByUserID(userid);
			model.put("recruitLists", recruitLists);
		}
		
	}
	
	/** 根据职位ID获取投简历的人
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void getRecruitUser(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		int recid = getInt(request, "recid",-1);
		String result = recruitManager.getRecruitUserJson(recid);
		output(result, response);
	}
	
	/** 删除职位
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void delRecruit(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		int recid = getInt(request, "recid",-1);
		if(recid!=-1){
			try {
				UserRecruit userRecruit  = new UserRecruit();
				userRecruit = recruitManager.getRecruitByID(recid);
				userRecruit.setDeleted(1);
				recruitManager.save(userRecruit);
				output("ok", response);
			} catch (Exception e) {
				output("no", response);
			}
			
		}else{
			output("no", response);
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
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] cities = dicReader.getDics(DicTypes.DIC_RECRUIT_HOT_TYPE.typeID());
		
		request.setCharacterEncoding("utf-8");
		String recruitType = get(request, "recruitType");//是否是职位管理（view-浏览所有招聘信息，edit-管理我的职位信息）
		int searchType = getInt(request, "searchType");//0=职位，1=公司
		String key = get(request, "key");//查询条件的值
		String city = get(request, "city");//查询条件的值
		int curPage = getInt(request, "curPage", 1);
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = user == null ? 0 : user.getId();
		Message msg = recruitManager.queryRecruits(curPage, 9, userId, recruitType, queryType, searchType, key, city);
		Dictionary[] types = recruitManager.types();
		model.put("queryType", queryType);
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("searchType", searchType);
		model.put("key", key);
		model.put("city", city);
		model.put("more", ParamInitUtils.getString(request.getParameter("more")));
		model.put("msg", msg);
		model.put("cities", cities);
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
		String typeName = recruit.getTypeName();
		if(typeName != null && typeName.length() > 0){
			List<UserRecruit> simiRecruits = recruitManager.querySimiRecruits(typeName);
			model.put("simiRecruits", simiRecruits);
		}
		
		model.put("recruit", recruit);
		model.put("user", user);
		//response.sendRedirect(WebUtil.getRoot(request) + "user/recruit/recruitDetail.jsp");
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}