package com.tl.invest.constant;

import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryType;

public enum DicTypes {
	DIC_SYS_PARAM("ϵͳ����"),
	DIC_INVEST_TYPE("�ڳ�����"),
	DIC_INVEST_STAGE("��Ŀ����"),
	DIC_RECRUIT_TYPE("ӰƸ����"),
	DIC_PERSON_TYPE("Ӱ�˷���"),
	DIC_JOB_TYPE("ְҵ"),
	DIC_AREA("����");
	
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
