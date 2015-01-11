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
	private void queryCompanys(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		Dictionary[] types = dicReader.getSubDics(DicTypes.DIC_RECRUIT_HOT_TYPE.typeID(), 0);
		List<String> hotCitys = new ArrayList<String>();
		if(types != null && types.length > 0){
			for(Dictionary type : types){
				hotCitys.add(type.getName());
			}
		}
		model.put("hotCitys", hotCitys);
		
		int curPage = getInt(request, "curPage", 1);
		String city = get(request, "city");//查询条件的值
		Message msg = userManager.queryCompanys(curPage, 9, city);
		model.put("city", city);
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
}