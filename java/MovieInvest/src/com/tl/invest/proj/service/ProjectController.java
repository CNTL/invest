package com.tl.invest.proj.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.StringUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.favorite.Favorite;
import com.tl.invest.favorite.FavoriteManager;
import com.tl.invest.proj.ProjMode;
import com.tl.invest.proj.ProjScheduleExt;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectStage;
import com.tl.invest.user.user.User;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.sys.common.SessionHelper;

public class ProjectController extends ProjectMainController{
	
	protected Project proj = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		long proj_id = getLong(request, "id", 0);
		String from = get(request, "from", "");
		int payType = 0;
		if(proj_id > 0){
			proj = service.get(proj_id);
			if(proj!=null){
				User user = userMgr.getUserByID(proj.getUserId());
				ProjMode[] modes = service.getProjectModes(proj.getId());
				Dictionary province = dicReader.getDic(DicTypes.DIC_AREA.typeID(), proj.getProvince());
				Dictionary city = dicReader.getDic(DicTypes.DIC_AREA.typeID(), proj.getCity());
				Dictionary county = dicReader.getDic(DicTypes.DIC_AREA.typeID(), proj.getCounty());
				
				payType = proj.getPayType();
				
				//剩余时间
				String surplus = getSurPlus();
				
				//完成百分比
				BigDecimal per = getFinishedPer();
				
				ProjScheduleExt[] schedules = service.getProjScheduleExts(proj_id, 100, 1, null);
				Dictionary[] dics = dicReader.getSubDics(DicTypes.DIC_INVEST_STAGE.typeID(), 0);
				
				int currtStage = 1;
				int lastCurrtStage = currtStage;
				
				List<ProjectStage> stages = new ArrayList<ProjectStage>();
				if(dics!=null){
					for (Dictionary dic : dics) {
						ProjectStage stage = new ProjectStage();
						stage.setStage(dic);						
						if(schedules!=null){
							for (ProjScheduleExt schedule : schedules) {
								if(dic.getId() == schedule.getStage()){
									stage.setSchedule(schedule);
									stages.add(stage);
									if(dic.getCode().equals("2")||dic.getCode().equals("3")||dic.getCode().equals("4")){
										lastCurrtStage = 2;
									}else if(dic.getCode().equals("5")){
										lastCurrtStage = 5;
									}
									if(currtStage<lastCurrtStage){
										currtStage = lastCurrtStage;
									}
								}
							}
						}
					}
				}
				
				int favorited = 0;
				int curUserId = SessionHelper.getUserID(request);
				if(curUserId>0){
					FavoriteManager favMgr = (FavoriteManager)Context.getBean(FavoriteManager.class);
					Favorite fav = favMgr.get(curUserId, 1, proj_id);
					if(fav!=null) favorited = 1;
				}
				
				model.put("user", user);
				model.put("modes", modes);
				model.put("province", province);
				model.put("city", city);
				model.put("county", county);
				model.put("finishPer", per);
				model.put("surplus", surplus);
				model.put("stages", stages);
				model.put("curstage", currtStage);
				model.put("favorited", favorited);
			}
			model.put("proj", proj);
		}
		if(StringUtils.isEmpty(from)){
			if(payType == 1){
				model.put("@VIEWNAME@", "proj/DetailJP");
			}else {
				model.put("@VIEWNAME@", "proj/Detail");
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", (proj!=null?proj.getName():"") + "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", proj!=null?proj.getSummary():"合众映画");
	}
	
	/**
	 * 获取完成百分比
	 * @return
	 */
	private BigDecimal getFinishedPer(){
		int payType = proj.getPayType();
		BigDecimal per = MoneyHelper.ZERO;
		if(payType == 1){
			Date beginDate = proj.getBeginDate();
			if(beginDate!=null){
				long l= DateUtils.getDate().getTime() - beginDate.getTime();
				if(l>0){
					BigDecimal t = MoneyHelper.toMoney(String.valueOf(l/(24*60*60*1000)));
					per = t.divide(MoneyHelper.getBigDecimal(String.valueOf(proj.getCountDay()), 2), 2,BigDecimal.ROUND_HALF_UP).multiply(MoneyHelper.toMoney("100"));
				}
			}
		}else{
			if(proj.getAmountGoal().compareTo(MoneyHelper.ZERO)<=0){
				per = MoneyHelper.toMoney("100");
			}
			else {
				per = proj.getAmountRaised().divide(proj.getAmountGoal(),2,BigDecimal.ROUND_HALF_UP).multiply(MoneyHelper.toMoney("100"));
			}
		}
		return MoneyHelper.getBigDecimal(per, 0);
	}
	
	private String getSurPlus(){
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
		return surplus;
	}
}
