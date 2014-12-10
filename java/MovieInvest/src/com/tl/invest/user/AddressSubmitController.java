package com.tl.invest.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.tl.common.DateUtils;
import com.tl.invest.common.BackResult;
import com.tl.kernel.web.BaseController;

public class AddressSubmitController extends BaseController {
	AddressManager addrMgr = null;
	
	public void setAddrMgr(AddressManager addrMgr) {
		this.addrMgr = addrMgr;
	}
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		if("update".equals(action)){
			Address address = new Address();
			assembleAddress(address, request);
			addrMgr.save(address);
			BackResult result = new BackResult(address.getId(),true);
	    	output(JSONObject.fromObject(result).toString(), response);
		}else if ("get".equals(action)) {
			long id = getLong(request, "id", 0);
			Address address = addrMgr.get(id);
			output(JSONObject.fromObject(address).toString(), response);
		}else if("del".equals(action)){
			long id = getLong(request, "id", 0);
			Address address = addrMgr.get(id);
			address.setDeleted(1);
			BackResult result = new BackResult(address.getId(),true);
	    	output(JSONObject.fromObject(result).toString(), response);
		}
	}

	private void assembleAddress(Address address,HttpServletRequest request){
		address.setId(getLong(request, "addr_id", 0));
		address.setRecipients(get(request, "addr_userName", ""));
		address.setProvince(get(request, "addr_provinceName", ""));
		address.setProvinceId(getInt(request, "addr_province", 0));
		address.setCity(get(request, "addr_cityName", ""));
		address.setCityId(getInt(request, "addr_city", 0));
		address.setCounty(get(request, "addr_countyName", ""));
		address.setCountyId(getInt(request, "addr_county",0));
		address.setDetail(get(request, "addr_detail", ""));
		address.setMphoneNo(get(request, "addr_mphoneNo", ""));
		address.setTelphoneNo(get(request, "addr_telphoneNo", ""));
		address.setUserId(getInt(request, "addr_userId", 0));
		address.setZipcode(get(request, "addr_zipcode", ""));
		address.setType(getInt(request, "addr_type", 0));
		address.setCreated(DateUtils.getTimestamp());
		address.setOrder(getInt(request, "addr_order", 0));
		address.setDeleted(getInt(request, "addr_deleted", 0));
	}
}
