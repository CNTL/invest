package com.tl.invest.proj;

import com.tl.kernel.sys.dic.Dictionary;

public class ProjectStage {
	private Dictionary stage;
	private ProjScheduleExt schedule;

	public Dictionary getStage() {
		return stage;
	}

	public void setStage(Dictionary stage) {
		this.stage = stage;
	}

	public ProjSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(ProjScheduleExt schedule) {
		this.schedule = schedule;
	}
	
}
