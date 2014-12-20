package com.tl.invest.user.user;
/** 
 * @created 2014��12��18�� ����10:50:25 
 * @author  leijj
 * ��˵�� �� 
 */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tl.invest.constant.DicTypes;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

public class PeopleMainController extends Entry {
	protected UserManager userMgr = null;
	protected DictionaryReader dicReader = null;
	
	public void setUserMgr(UserManager userMgr) {
		this.userMgr = userMgr;
	}
	public void setDicReader(DictionaryReader dicReader) {
		this.dicReader = dicReader;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response, Map model) throws Exception {
		int type = getInt(request, "type", 0);
		if(type<=0){
			Dictionary[] types = dicReader.getSubDics(DicTypes.DIC_INVEST_TYPE.typeID(), 0);
			for (Dictionary dic : types) {
				int typeID = dic.getId();
				User[] users = userMgr.getPersons(typeID);
				if(dic.getName().indexOf("����")>=0){
					model.put("persons1", users);
					model.put("perType1", typeID);
				}else if(dic.getName().indexOf("��Ա")>=0){
					model.put("persons2", users);
					model.put("perType2", typeID);
				}else if(dic.getName().indexOf("��Ӱ")>=0){
					model.put("persons3", users);
					model.put("perType3", typeID);
				}else if(dic.getName().indexOf("����")>=0){
					model.put("persons4", users);
					model.put("perType4", typeID);
				}
			}
			model.put("personCount", 0);
			model.put("pageCount", 1);
			model.put("pageBegin", 1);
			model.put("pageEnd", 1);
			model.put("page", 1);
		}else {
			int page = getInt(request, "page", 1);
			User[] users = userMgr.getPersons(type);
			int personCount = userMgr.getPersonsCount(type, null);
			int pageCount = personCount/20;
			if(personCount % 20 >0) pageCount = pageCount + 1;
			if(pageCount<=0) pageCount = 1;
			
			Dictionary dic = dicReader.getDic(DicTypes.DIC_INVEST_TYPE.typeID(), type);
			
			if(dic.getName().indexOf("����")>=0){
				model.put("persons1", users);
				model.put("perType1", type);
			}else if(dic.getName().indexOf("��Ա")>=0){
				model.put("persons2", users);
				model.put("perType2", type);
			}else if(dic.getName().indexOf("��Ӱ")>=0){
				model.put("persons3", users);
				model.put("perType3", type);
			}else if(dic.getName().indexOf("����")>=0){
				model.put("persons4", users);
				model.put("perType4", type);
			}
			model.put("personCount", personCount);
			model.put("pageCount", pageCount);
			model.put("pageBegin", getPageBegin(page));
			model.put("pageEnd", getPageEnd(page, pageCount));
			model.put("page", page);
		}
		model.put("type", type);
	};
	
	protected int getPageBegin(int page) {
		int begin = page;
		
		for (int i=1;i<=4;i++) {
			if(begin == 1){
				break;
			}
			begin = begin - i;
		}
		return begin;
	}
	
	protected int getPageEnd(int page,int pageCount) {
		int end = page;
		for (int i=1;i<=4;i++) {
			if(end==pageCount){
				break;
			}
			end = end + 1;
		}
		return end;
	}
	
	@Override
	protected String getCurrentMenu() {
		return "��Ŀ";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}