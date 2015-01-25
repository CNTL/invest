package com.tl.invest.constant;

import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryType;

/**����Ӱ��ְҵ����
 * @author wang.yq
 *
 */
public enum JobSection {
	SECTION_ACTOR("��Ա"),
	SECTION_FOWARD("ǰ������"),
	SECTION_AFTER("��������"),
	SECTION_OTHER("����Ӱ��");
	
	private String typeName;
	
	private JobSection(String typeName){
		this.typeName = typeName;
	}
	
	public String typeName() {
		return this.typeName;
	}
	
	public int typeID() {
		return type().getId();
	}
	
	public Dictionary type() {
		Dictionary dic = null;
		DictionaryReader catReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		try {
			dic = catReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), typeName);
		} catch (TLException e) {
			e.printStackTrace();
		}
		return dic;
	}
	
	
	
	
}
