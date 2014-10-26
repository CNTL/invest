package com.tl.kernel.sys.param;

import java.util.ArrayList;
import java.util.List;

import com.tl.common.log.Log;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryReaderImpl;
import com.tl.kernel.sys.dic.DictionaryType;

public class ParameterReaderImpl implements ParameterReader {	
	Log log = Context.getLog("tl");
	private DictionaryReader dicReader = new DictionaryReaderImpl();
	
	private int getDicTypeID(){
		int dicTypeID = 0;
		try {
			DictionaryType dicType = dicReader.getType("系统参数");
			
			if(dicType!=null)
				dicTypeID = dicType.getId();
		} catch (TLException e) {
			log.error(e.getMessage(),e);
		}
		return dicTypeID;
	}
	@Override
	public ParameterType[] getTypes() {
		List<ParameterType> list = new ArrayList<ParameterType>();
		try {
			Dictionary[] dics = dicReader.getSubDics(getDicTypeID(), 0);
			if(dics != null && dics.length>0){
				for (Dictionary dic : dics) {
					list.add(ParameterHelper.Dictionary2ParameterType(dic));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return (ParameterType[]) list.toArray(new ParameterType[0]);
	}

	@Override
	public ParameterType getType(String typeName) {
		Dictionary dic = null;
		try {
			dic = dicReader.getDicByName(getDicTypeID(), typeName);
			
		} catch (TLException e) {
			log.error(e.getMessage(),e);
		}
		return ParameterHelper.Dictionary2ParameterType(dic);
	}

	@Override
	public Parameter getParameter(String typeName, String paramName) {
		ParameterType type = getType(typeName);
		if(type == null) return null;
		return getParameter(type.getId(),paramName);
	}

	@Override
	public Parameter[] getParameters(int typeID) {
		List<Parameter> list = new ArrayList<Parameter>();
		try {
			Dictionary[] dics = dicReader.getSubDics(getDicTypeID(), typeID);
			if(dics != null && dics.length>0){
				for (Dictionary dic : dics) {
					list.add(ParameterHelper.Dictionary2Parameter(dic));
				}
			}
		} catch (TLException e) {
			log.error(e.getMessage(),e);
		}
		
		return (Parameter[]) list.toArray(new Parameter[0]);
	}

	@Override
	public Parameter getParameter(int typeID, String paramName) {
		Dictionary d = null;
		try {
			Dictionary[] dics = dicReader.getSubDics(getDicTypeID(), typeID);
			if(dics != null && dics.length>0){
				for (Dictionary dic : dics) {
					if(paramName.equals(dic.getName())){
						d = dic;
						break;
					}
				}
			}
		} catch (TLException e) {
			log.error(e.getMessage(),e);
		}
		return ParameterHelper.Dictionary2Parameter(d);
	}
}
