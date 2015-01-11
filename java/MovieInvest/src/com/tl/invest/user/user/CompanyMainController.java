package com.tl.invest.user.user;

/** 
 * @created 2014��12��18�� ����10:50:25 
 * @author  leijj
 * ��˵�� �����������Ĺ�˾
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
		if("queryCompanys".equals(action)){//��ȡ��Ƹ��ϸ��Ϣ
			queryCompanys(request, response, model);
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
		String city = get(request, "city");//��ѯ������ֵ
		Message msg = userManager.queryCompanys(curPage, 9, city);
		model.put("city", city);
		model.put("msg", msg);
	}
	@Override
	protected String getCurrentMenu() {
		return "��Ŀ";
	}

	@Override
	protected void setMetaData(HttpServletRequest request, Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}