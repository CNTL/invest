package com.tl.invest.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.user.user.UserMainController;
import com.tl.sys.common.SessionHelper;

public class AddressOrgMainController extends UserMainController {
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
		model.put("title", "收件地址管理--合众映画");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
	
	protected String getCurrentMenu() {
		return "";
	}
}
