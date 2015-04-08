package com.tl.invest.user.user;

/** 
 * @created 2014年12月18日 上午10:50:25 
 * @author  leijj
 * 类说明 ：发布简历的公司
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.Message;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CompanyMainController extends Entry {
	
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("queryCompanys".equals(action)){//获取招聘详细信息
			queryCompanys(request, response, model);
		}else if("queryOrg".equals(action)){
			queryOrg(request, response, model);
		}
		else if("queryDetail".equals(action)){
			queryDetail(request, response, model);
		}
		else{
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
			//获得4种机构类型
			Dictionary[] types = reader.getChildrenDics(DicTypes.DIC_ORG_TYPE.typeID(), 0);
			for (int i = 0; i < types.length; i++) {
				Dictionary dic = types[i];
				Message msg = userManager.queryOrgs(1, 4, dic.getId());
				model.put("orgType"+String.valueOf(i+1), dic);
				model.put("orgUser"+String.valueOf(i+1), msg);
			}
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
	private void queryDetail(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int userID = getInt(request, "id", 0);
		User user = null;
		UserRecruit[] recList = null;
		int reclength = 0;
		try {
			user =  userManager.getUserByID(userID);
			recList = recruitManager.queryRecruitsByUserID(userID);
			reclength = recList.length;
		} catch (Exception e) {
			 
		}
		
		model.put("user", user);
		model.put("recList", recList);
		model.put("reclength", reclength);
	}
	
	/** 
	* @author  leijj 
	* 功能： 查询最新招聘信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryCompanys(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		 
		Dictionary[] types = recruitManager.getHotCitys();
	    
		model.put("hotCitys", types);
		
		int curPage = getInt(request, "curPage", 1);
		int city = getInt(request, "city",0);//查询条件的值
		int province = getInt(request, "",0);
		String name  = get(request, "name","");
		Message msg = userManager.queryCompanys(curPage, 9, province,city,name);
		model.put("city", city);
		model.put("msg", msg);
	}
	
	/** 
	* @author  leijj 
	* 功能： 查询最新招聘信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryOrg(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		 
		int curPage = getInt(request, "curPage", 1);
		int perjob = getInt(request, "perjob",0);
 
		Message msg = userManager.queryCompanys(curPage, 12, perjob);
		DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
		Dictionary type = reader.getDic(DicTypes.DIC_ORG_TYPE.typeID(), perjob);
		String perName = "";
		if(type!=null){
			perName = type.getName();
		}
		model.put("perName", perName);
		model.put("perjob", perjob);
		
		model.put("msg", msg);
	}
	
	 
	@Override
	protected String getCurrentMenu() {
		return "项目";
	}

	@Override
	protected void setMetaData(HttpServletRequest request, Map model) {
		model.put("title", "合众映画--项目");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}