package com.tl.kernel.sys.dic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.web.BaseController;

public class DictionaryTreeFetcher extends BaseController {
	DictionaryManager dicManager = (DictionaryManager)Context.getBean(DictionaryManagerImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		int showType = getInt(request, "type",-1);
		String selected = get(request, "selected", "");
		
		String json = getTreeJson(showType, selected);
		output(json, response);
	}
	
	protected String getTreeJson(int showType,String selected) throws TLException {
		DictionaryType[] types = null;
		if(showType>1 || showType<0){
			types = dicManager.getTypes();
		}else {
			types = dicManager.getTypes(showType);
		}
		if(types==null || types.length<=0) return "[]";
		StringBuffer sb = new StringBuffer();
		for (DictionaryType type : types) {
			if(sb.length()>0) sb.append(",");
			sb.append("{");
			
			sb.append("\"id\":\""+type.getId()+"\",");
			sb.append("\"text\":\""+type.getName()+"\",");
			sb.append("\"code\":\""+type.getCode()+"\",");
			sb.append("\"treetype\":\"0\",");
			sb.append("\"issys\":\""+type.getIsSys()+"\",");
			sb.append("\"valid\":\""+type.getValid()+"\",");
			sb.append("\"order\":\""+type.getOrder()+"\",");
			sb.append("\"iconCls\":\"\",");
			sb.append("\"state\":\"open\",");
			sb.append("\"checked\":\"false\"");
			
			Dictionary[] dics = dicManager.getDics(type.getId());
			if(dics!=null && dics.length>0){
				sb.append(",\"children\":[");
				appendDics(0,dics,sb);
				sb.append("]");
			}
			
			sb.append("}");
		}
		
		return sb.toString();
	}

	private void appendDics(int PID,Dictionary[] dics, StringBuffer sb) {
		if(dics==null || dics.length<=0) return;
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary dic : dics) {
			if(dic.getPid() != PID) continue;
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":\""+dic.getId()+"\",");
			sb1.append("\"pid\":\""+PID+"\",");
			sb1.append("\"type\":\""+dic.getType()+"\",");
			sb1.append("\"treetype\":\"1\",");
			sb1.append("\"text\":\""+dic.getName()+"\",");
			sb1.append("\"code\":\""+dic.getCode()+"\",");
			sb1.append("\"text1\":\""+dic.getText()+"\",");
			sb1.append("\"value\":\""+dic.getValue()+"\",");
			sb1.append("\"valid\":\""+dic.getValid()+"\",");
			sb1.append("\"order\":\""+dic.getOrder()+"\",");
			sb1.append("\"level\":\""+dic.getLevel()+"\",");
			sb1.append("\"CascadeID\":\""+dic.getCascadeId()+"\",");
			sb1.append("\"CascadeName\":\""+dic.getCascadeName()+"\",");
			sb1.append("\"iconCls\":\"\",");
			sb1.append("\"state\":\"open\",");
			sb1.append("\"checked\":\"false\",");
			sb1.append("\"children\":[");
			
			appendDics(dic.getPid(), dics, sb1);
			
			sb1.append("]}");
		}
		sb.append(sb1);
	}

}
