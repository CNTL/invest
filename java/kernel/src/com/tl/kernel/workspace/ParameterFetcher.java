package com.tl.kernel.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.DictionaryManager;
import com.tl.kernel.sys.dic.DictionaryType;
import com.tl.kernel.sys.param.Parameter;
import com.tl.kernel.sys.param.ParameterManager;
import com.tl.kernel.sys.param.ParameterType;
import com.tl.kernel.web.BaseController;

public class ParameterFetcher extends BaseController {

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		if("mtree".equals(action)){
			outManageParamTree(request,response);
		}
	}

	private void outManageParamTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int typeid = getInt(request, "typeid", 0);
		int id = getInt(request, "id", 0);
		//int pid = getInt(request, "pid", 0);
		
		DictionaryType dicType = getParamDicType();
		if(dicType.getId()<=0) throw new TLException("未找到系统参数的定义。");
		
		ParameterManager paramMgr = (ParameterManager)Context.getBean(ParameterManager.class);
		StringBuffer sb1 = new StringBuffer();
		if(id>0){
			Parameter[] params = paramMgr.getParameters(id);
			if(params != null && params.length>0){
				//sb1.append("[");
				for (int i=0;i<params.length;i++) {
					Parameter param = params[i];
					sb1.append("{");
					sb1.append("\"id\":"+param.getId()+",");
					sb1.append("\"text\":\""+param.getName()+"\",");
					sb1.append("\"state\":\"open\",");
					sb1.append("\"attributes\":{\"typeid\":\""+dicType.getId()+"\",\"istype\":\"0\",\"pid\":\""+id+"\",\"maxlevel\":\""+dicType.getMaxLevel()+"\"}");
					sb1.append("}");
					if(i<params.length-1){
						sb1.append(",");
					}
				}
				//sb1.append("]");
			}
		}else{
			ParameterType[] types = paramMgr.getTypes();
			
			for (ParameterType type : types) {
				if(sb1.length()>0) sb1.append(",");
				sb1.append("{");
				sb1.append("\"id\":"+type.getId()+",");
				sb1.append("\"text\":\""+type.getName()+"\",");
				sb1.append("\"state\":\"open\",");
				sb1.append("\"attributes\":{\"typeid\":\""+dicType.getId()+"\",\"istype\":\"0\",\"pid\":\"0\",\"maxlevel\":\""+dicType.getMaxLevel()+"\"}");
				Parameter[] params = paramMgr.getParameters(type.getId());
				if(params != null && params.length>0){
					sb1.append(",\"children\":[");
					for (int i=0;i<params.length;i++) {
						Parameter param = params[i];
						sb1.append("{");
						sb1.append("\"id\":"+param.getId()+",");
						sb1.append("\"text\":\""+param.getName()+"\",");
						sb1.append("\"state\":\"open\",");
						sb1.append("\"attributes\":{\"typeid\":\""+dicType.getId()+"\",\"istype\":\"0\",\"pid\":\""+type.getId()+"\",\"maxlevel\":\""+dicType.getMaxLevel()+"\"}");
						sb1.append("}");
						if(i<params.length-1){
							sb1.append(",");
						}
					}
					sb1.append("]");
				}
				sb1.append("}");
			}
		}
		
		StringBuffer sb = new StringBuffer();
		if(typeid == 0){
			sb.append("{");
			sb.append("\"id\":"+dicType.getId()+",");
			sb.append("\"text\":\""+dicType.getName()+"\",");
			sb.append("\"state\":\"open\",");
			sb.append("\"attributes\":{\"typeid\":\""+dicType.getId()+"\",\"istype\":\"1\",\"pid\":\"0\",\"maxlevel\":\""+dicType.getMaxLevel()+"\"},");
			sb.append("\"children\":[");
			sb.append(sb1);
			sb.append("]");
			sb.append("}");
		}else {
			sb.append(sb1);
		}
		sb.insert(0, "[");
		sb.append("]");
		outputJson(sb.toString(), response);
	}
	
	private DictionaryType getParamDicType() throws TLException{
		DictionaryManager dicMgr = (DictionaryManager)Context.getBean(DictionaryManager.class);
		DictionaryType dicType = dicMgr.getType("系统参数");
		if(dicType == null){
			dicType = new DictionaryType();
			dicType.setCode("SYSPARAM");
			dicType.setName("系统参数");
			dicType.setIsSys(1);
			dicType.setMaxLevel(2);
			dicType.setMemo("系统参数，由系统自动创建");
			dicType.setValid(1);
			
			dicMgr.createType(dicType);
		}
		return dicType;
	}
}
