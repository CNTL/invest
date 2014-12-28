package com.tl.invest.user.user;

/** 
 * @created 2014��12��18�� ����10:50:25 
 * @author  leijj
 * ��˵�� �����������Ĺ�˾
 */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.Message;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
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
		int curPage = getInt(request, "curPage", 1);
		Message msg = userManager.queryCompanys(curPage, 9);
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