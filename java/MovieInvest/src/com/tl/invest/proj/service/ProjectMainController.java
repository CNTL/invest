package com.tl.invest.proj.service;

import java.util.Map;

import com.tl.invest.workspace.Entry;

public class ProjectMainController extends Entry {
	
	@Override
	protected String getCurrentMenu() {
		return "��Ŀ";
	}
	
	@Override
	protected void setMetaData(Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}
