package com.tl.invest.constant;

import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryType;

/**设置影人职业分类
 * @author wang.yq
 *
 */
public enum RecruitCat {
	REC_FOWARD("前期拍摄"),
	REC_AFTER("后期制作"),
	REC_PUB("宣传发行"),
	REC_CARTOON("动画片制作");
	
	private String typeName;
	
	private RecruitCat(String typeName){
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
			dic = catReader.getDicByName(DicTypes.DIC_RECRUIT_TYPE.typeID(), typeName);
		} catch (TLException e) {
			e.printStackTrace();
		}
		return dic;
	}
	
	
	
	
}
