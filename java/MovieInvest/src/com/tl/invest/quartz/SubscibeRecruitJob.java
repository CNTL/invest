package com.tl.invest.quartz;

import java.util.Date;

import com.tl.common.DateUtils;
import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.kernel.context.Context;

public class SubscibeRecruitJob{
	Log log = Context.getLog("invest");
	
	protected void execute() {
		log.info("开始执行定时任务：SubscibeRecruitJob");
		
		
		log.info("结束执行定时任务：SubscibeRecruitJob");
	}

}
