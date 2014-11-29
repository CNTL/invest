package com.tl.invest.proj.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.constant.DicTypes;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.web.BaseController;

public class ProjectFetcher extends BaseController{
	private ProjectService service = null;
	
	public void setService(ProjectService service) {
		this.service = service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		
		if("datas".equals(action)){
			initDatas(response);
		}
	}

	private void initDatas(HttpServletResponse response) throws Exception {
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] projTypes = dicReader.getDics(DicTypes.DIC_INVEST_TYPE.typeID());
		Dictionary[] areas = dicReader.getDics(DicTypes.DIC_AREA.typeID());
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary area : areas) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+area.getId());
			sb1.append(",\"pid\":"+area.getPid());
			sb1.append(",\"name\":\""+area.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb2 = new StringBuffer();
		for (Dictionary projType : projTypes) {
			if(sb2.length()>0) sb2.append(",");
			sb2.append("{");
			sb2.append("\"id\":"+projType.getId());
			sb2.append(",\"pid\":"+projType.getPid());
			sb2.append(",\"name\":\""+projType.getName()+"\"");
			sb2.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"ProjTypes\":["+sb2.toString()+"]");
		sb.append(",\"areas\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
}
