package com.tl.invest.user.user;

/** 
 * @created 2014��12��18�� ����10:50:25 
 * @author  leijj
 * ��˵�� �� 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.tl.common.Message;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PeopleMainController extends Entry {
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, Map model)
			throws Exception {
		String action = request.getParameter("a");
		if("queryPersons".equals(action)){//��ȡ��Ƹ��ϸ��Ϣ
			queryPersons(request, response, model);
		} else if ("getPersons".equals(action)) {
			getPersons(request, response, model);
		}
		/*
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		int type = getInt(request, "type", 0);
		if (type <= 0) {
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			Dictionary[] types = dicReader.getSubDics(DicTypes.DIC_JOB_TYPE.typeID(), 0);
			if(types == null || types.length == 0) return;
			for (Dictionary dic : types) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				int typeID = dic.getId();
				String typeName = dic.getName();
				User[] users = userMgr.getPersons(typeID);
				dataMap.put("persons", users);
				dataMap.put("perType", typeID);
				dataMap.put("perName", typeName);
				dataList.add(dataMap);
			}
			model.put("datas", dataList);
			model.put("personCount", 0);
			model.put("pageCount", 1);
			model.put("pageBegin", 1);
			model.put("pageEnd", 1);
			model.put("page", 1);
		} else {
			int page = getInt(request, "page", 1);
			User[] users = userMgr.getPersons(type);
			int personCount = userMgr.getPersonsCount(type, null);
			int pageCount = personCount / 4;
			if (personCount % 4 > 0)
				pageCount = pageCount + 1;
			if (pageCount <= 0)
				pageCount = 1;

			Dictionary dic = dicReader.getDic(DicTypes.DIC_JOB_TYPE.typeID(), type);
			if(dic == null) return;
			if(type == dic.getId()){
				model.put("persons", users);
				model.put("perType", type);
				model.put("perName", dic.getName());
			}
			model.put("personCount", personCount);
			model.put("pageCount", pageCount);
			model.put("pageBegin", getPageBegin(page));
			model.put("pageEnd", getPageEnd(page, pageCount));
			model.put("page", page);
		}
		model.put("type", type);
		
		*/
	}
	private void queryPersons(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		int type = getInt(request, "type", 0);
		if (type <= 0) {
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			Dictionary[] types = dicReader.getSubDics(DicTypes.DIC_JOB_TYPE.typeID(), 0);
			if(types == null || types.length == 0) return;
			for (Dictionary dic : types) {
				int typeID = dic.getId();
				String typeName = dic.getName();
				if(!"����".equals(typeName)){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					Message msg = userManager.queryPersons(typeID, 1, 4);
					dataMap.put("persons", msg);
					dataMap.put("perType", typeID);
					dataMap.put("perName", typeName);
					dataList.add(dataMap);
				}
			}
			model.put("datas", dataList);
		} else {
			int curPage = getInt(request, "curPage", 1);
			Message msg = userManager.queryPersons(type, curPage, 20);
			Dictionary dic = dicReader.getDic(DicTypes.DIC_JOB_TYPE.typeID(), type);
			if(dic == null) return;
			if(type == dic.getId()){
				model.put("perType", type);
				model.put("perName", dic.getName());
			}
			model.put("msg", msg);
		}
		model.put("type", type);
	}
	/** 
	* @author  leijj 
	* ���ܣ� Ӱ����ҳ��ҳ����
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void getPersons(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int type = getInt(request, "type", 0);
		int curPage = getInt(request, "curPage", 1);
		Message msg = userManager.queryPersons(type, curPage, 4);
		JSONObject jsonArray = JSONObject.fromObject(msg);  
		output(jsonArray.toString(), response);
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