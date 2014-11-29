package com.tl.invest.proj.service;

import java.util.Map;

import com.tl.invest.workspace.Entry;

public class ProjectMainController extends Entry {
	
	@Override
	protected String getCurrentMenu() {
		return "项目";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setMetaData(Map model) {
		model.put("title", "合众映画--项目");
		model.put("keywords", "合众映画");
		model.put("description", "合众映画");
	}
}
