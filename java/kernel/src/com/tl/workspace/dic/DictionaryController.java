package com.tl.workspace.dic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.DictionaryManager;
import com.tl.kernel.sys.dic.DictionaryType;
import com.tl.kernel.web.BaseController;

public class DictionaryController extends BaseController {
	private DictionaryManager dicManager = null;
	
	public void setDicManager(DictionaryManager dicManager){
		this.dicManager = dicManager;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		if(dicManager == null) dicManager = (DictionaryManager)Context.getBean(DictionaryManager.class);
		
		String action = get(request, "action", "");
		
		if("new-type".equals(action)){
			saveDicType(request,response);
		}else if("new-cat".equals(action)){
			int typeid = getInt(request, "typeid", -999);
			int pid = getInt(request, "pid",-999);
		}else if("edit".equals(action)){
			
		}else if("remove".equals(action)){
			
		}
	}
	
	private void saveDicType(HttpServletRequest request,
			HttpServletResponse response) throws Exception{		
		int id = getInt(request, "ItemID", 0);
		DictionaryType dicType = null;
		if(id>0){
			dicType = dicManager.getType(id);
		}
		if(dicType == null)
			dicType = new DictionaryType();
		dicType.setId(id);
		dicType.setName(get(request, "dt_name", ""));
		dicType.setCode(get(request, "dt_code", ""));
		dicType.setIsSys(getInt(request, "dt_sys", 0));
		dicType.setMemo(get(request, "dt_memo", ""));
		dicType.setOrder(getInt(request, "dt_order", id));
		dicType.setValid(getInt(request, "dt_valid", 1));
		if(dicManager.exist(dicType)){
			outputJson("{\"success\":false,\"msg\":\"名称或者简码已经存在\"}", response);
			return;
		}
		if(dicType.getId()>0){
			dicManager.updateType(dicType);
		}else {
			dicManager.createType(dicType);
		}
		if(dicType.getId()<=0){
			outputJson("{\"success\":false,\"msg\":\"保存失败\"}", response);
		}else {
			outputJson("{\"success\":true,\"msg\":\"保存成功\"}", response);
		}
	}

}
