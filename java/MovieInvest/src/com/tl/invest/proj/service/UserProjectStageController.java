package com.tl.invest.proj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tl.invest.constant.DicTypes;
import com.tl.invest.proj.ProjSchedule;
import com.tl.invest.proj.ProjScheduleExt;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectStage;
import com.tl.invest.user.user.UserManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

public class UserProjectStageController extends Entry {
	protected ProjectService service = null;
	protected UserManager userMgr = null;
	protected DictionaryReader dicReader = null;
	
	public void setService(ProjectService service) {
		this.service = service;
	}
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
		long projId = getLong(request, "projId", 0L);
		Project proj = service.get(projId);
		ProjScheduleExt[] schedules = service.getProjScheduleExts(projId, 100, 1, null);
		Dictionary[] dics = dicReader.getSubDics(DicTypes.DIC_INVEST_STAGE.typeID(), 0);
		
		List<ProjectStage> stages = new ArrayList<ProjectStage>();
		
		if(dics!=null){
			for (Dictionary dic : dics) {
				ProjectStage stage = new ProjectStage();
				stage.setStage(dic);
				if(schedules!=null){
					for (ProjScheduleExt schedule : schedules) {
						if(dic.getId() == schedule.getStage()){
							stage.setSchedule(schedule);
						}
					}
				}
				stages.add(stage);
			}
		}
		
		model.put("proj", proj);
		//model.put("schedules", schedules==null ? (new ProjSchedule[0]) : schedules);
		model.put("stages", stages);
	}
}
