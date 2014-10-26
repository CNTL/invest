package com.tl.kernel.sys.param;

import com.tl.kernel.sys.dic.Dictionary;

public class ParameterHelper {
	public static ParameterType Dictionary2ParameterType(Dictionary dic){
		if(dic == null) return null;
		ParameterType type = new ParameterType();
		type.setId(dic.getId());
		type.setName(dic.getName());
		type.setCode(dic.getCode());
		type.setOrder(dic.getOrder());
		type.setValid(dic.getValid());
		type.setLastModified(dic.getLastModified());
		type.setMemo(dic.getMemo());
		return type;
	}
	
	public static Parameter Dictionary2Parameter(Dictionary dic){
		if(dic == null) return null;
		Parameter param = new Parameter();
		param.setId(dic.getId());
		param.setName(dic.getName());
		param.setCode(dic.getCode());
		param.setOrder(dic.getOrder());
		param.setValid(dic.getValid());
		param.setLastModified(dic.getLastModified());
		param.setMemo(dic.getMemo());
		
		param.setTypeID(dic.getPid());
		param.setText(dic.getText());
		param.setValue(dic.getValue());
		
		return param;
	}
}
