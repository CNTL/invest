package com.tl.invest.proj.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.Project;
import com.tl.invest.user.user.User;
import com.tl.kernel.sys.dic.Dictionary;

public class ProjectController extends ProjectMainController{
	
	protected Project proj = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		long proj_id = getLong(request, "id", 0);
		if(proj_id > 0){
			proj = service.get(proj_id);
			if(proj!=null){
				User user = userMgr.getUserByID(proj.getUserId());
				ProjMode[] modes = service.getProjectModes(proj.getId());
				Dictionary province = dicReader.getDic(DicTypes.DIC_AREA.typeID(), proj.getProvince());
				Dictionary city = dicReader.getDic(DicTypes.DIC_AREA.typeID(), proj.getCity());
				Dictionary county = dicReader.getDic(DicTypes.DIC_AREA.typeID(), proj.getCounty());
				
				//完成百分比
				BigDecimal per = MoneyHelper.ZERO;
				if(proj.getAmountGoal().compareTo(MoneyHelper.ZERO)<=0){
					per = MoneyHelper.toMoney("100");
				}
				else {
					per = proj.getAmountRaised().divide(proj.getAmountGoal()).multiply(MoneyHelper.toMoney("100"));
				}
				per = MoneyHelper.getBigDecimal(per, 0);
				//剩余时间
				String surplus = "--";
				Date endDate = proj.getEndDate();
				if(endDate != null){
					long l=endDate.getTime() - DateUtils.getDate().getTime();
					if(l<=0){
						surplus = "0天";
					}else {
						surplus = String.valueOf(l/(24*60*60*1000))+"天";
					}
				}
				
				model.put("user", user);
				model.put("modes", modes);
				model.put("province", province);
				model.put("city", city);
				model.put("county", county);
				model.put("finishPer", per);
				model.put("surplus", surplus);
			}
			model.put("proj", proj);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(Map model) {
		model.put("title", (proj!=null?proj.getName():"") + "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", proj!=null?proj.getSummary():"合众映画");
	}
}
