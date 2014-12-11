package com.tl.invest.proj.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.StringUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.Project;
import com.tl.invest.user.Address;
import com.tl.invest.user.AddressManager;
import com.tl.sys.common.SessionHelper;

public class ProjectPayController extends ProjectController {
	AddressManager addrMgr = null;
	
	public void setAddrMgr(AddressManager addrMgr) {
		this.addrMgr = addrMgr;
	}
	
	protected Project proj = null;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		long mode_id = getLong(request, "id", 0);
		String error = "";
		if(mode_id > 0){
			ProjMode mode = service.getProjectMode(mode_id);
			if(mode == null || mode.getDeleted()==1){
				error = "该项目的支持类型已经不存在...";
			}else{
				if(mode.getCountGoal()>0 && mode.getCountGoal()<=mode.getCountSupport()){
					error = "该支持类型的限额已经满了..";
				}else {
					proj = service.get(mode.getProjId());
				}
			}
			
			if(proj!=null){
				if(proj.getDeleted()==1){
					error = "该项目已经删除..";
				}else if(proj.getStatus()==0){
					error = "该项目还没有开始众筹..";
				}else if(proj.getStatus()==2){
					error = "该项目众筹已结束..";
				}else if(proj.getStatus()==3){
					error = "该项目已被锁定..";
				}else if(proj.getStatus()==1){
					if(proj.getBeginDate().getTime()>DateUtils.getTimestamp().getTime()){
						error = "该项目还没有开始众筹..";
					}else if(proj.getEndDate().getTime()<DateUtils.getTimestamp().getTime()){
						error = "该项目已经结束..";
					}
				}				
			}else {
				error = "该项目已经不存在..";
			}
			
			if(StringUtils.isEmpty(error)){
				BigDecimal totalFee = MoneyHelper.toMoney(mode.getPrice().add(mode.getFreight()));
				model.put("totalFee", totalFee);
				
				Address[] addresses = addrMgr.getAddresses(SessionHelper.getUserID(request), null);
				long defAddr = 0L;
				for (Address address : addresses) {
					if(address.getType() == 1){
						defAddr = address.getId();
					}
					if((DateUtils.getTimestamp().getTime() - address.getLastused().getTime())<=(1 * 60 * 1000L)){
						defAddr = address.getId();
					}
				}
				if(defAddr == 0L && addresses.length>0){
					defAddr = addresses[0].getId();
				}
				model.put("defAddress", defAddr);
				model.put("addresses", addresses);
				model.put("proj", proj);
				model.put("mode", mode);
			}
		}else {
			error = "传入的参数不正确..";
		}
		model.put("error", error);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "支持--"+(proj!=null?proj.getName():"") + "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", proj!=null?proj.getSummary():"合众映画");
	}
}
