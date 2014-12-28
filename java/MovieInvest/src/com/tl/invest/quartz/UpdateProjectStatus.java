package com.tl.invest.quartz;

import java.util.Date;

import com.tl.common.DateUtils;
import com.tl.common.ResourceMgr;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.kernel.context.Context;

public class UpdateProjectStatus{
	Log log = Context.getLog("invest");
	
	protected void execute() {
		log.info("��ʼִ�ж�ʱ����UpdateProjectStatus");
		String td = DateUtils.format("yyyy-MM-dd");
		String sql = "update invest_project set proj_status=2 where proj_status=1 and proj_endDate<'"+td+"'";
		DBSession db = null;
		try {
			log.debug(sql);
			db = Context.getDBSession();
			db.executeUpdate(sql, null);
		} catch (Exception e) {
			log.error("����UpdateProjectStatus ִ��ʧ�ܣ�", e);
		} finally{
			ResourceMgr.closeQuietly(db);
			log.info("��ʱ����ִ����ϣ�");
		}
	}

}
