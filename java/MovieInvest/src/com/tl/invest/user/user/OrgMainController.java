package com.tl.invest.user.user;

import java.util.Map;

import com.tl.invest.workspace.Entry;

/** 
 * @created 2014��11��30�� ����3:44:57 
 * @author  leijj
 * ��˵�� �� 
 */
public class OrgMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "Ӱ��";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}