package com.tl.invest.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.workspace.Entry;
import com.tl.sys.common.SessionHelper;

public class AddressMainController extends Entry {
	AddressManager addrMgr = null;
	
	public void setAddrMgr(AddressManager addrMgr) {
		this.addrMgr = addrMgr;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		Address[] addresses = addrMgr.getAddresses(SessionHelper.getUserID(request), null);
		
		model.put("addresses", addresses);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "�ռ���ַ����--����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	
	protected String getCurrentMenu() {
		return "";
	}
}
