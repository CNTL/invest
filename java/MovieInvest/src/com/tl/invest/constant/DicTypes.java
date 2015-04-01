package com.tl.invest.constant;

import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryType;

public enum DicTypes {
	DIC_SYS_PARAM("系统参数"),
	DIC_INVEST_TYPE("众筹类型"),
	DIC_INVEST_STAGE("项目进度"),
	DIC_RECRUIT_TYPE("影聘分类"),
	DIC_ORG_TYPE("机构分类"),
	DIC_JOB_TYPE("职业"),
	DIC_AGE_TYPE("年龄分类"),
	DIC_AREA("地域");
	
	private String typeName;
	
	private DicTypes(String typeName){
		this.typeName = typeName;
	}
	
	public String typeName() {
		return this.typeName;
	}
	
	public int typeID() {
		return type().getId();
	}
	
	public DictionaryType type() {
		DictionaryType dicType = null;
		DictionaryReader catReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		try {
			dicType = catReader.getType(typeName());
		} catch (TLException e) {
			e.printStackTrace();
		}
		return dicType;
	}
}
