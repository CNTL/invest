package com.tl.kernel.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
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
			saveDic(request, response);
		}else if("edit".equals(action)){
			int istype = getInt(request, "istype", 0);
			if(istype == 1){
				saveDicType(request, response);
			}else {
				saveDic(request, response);
			}
		}else if("remove".equals(action)){
			delete(request,response);
		}
	}
	
	private void delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		int id = getInt(request, "ItemID",0);
		int isType = getInt(request, "IsType", 0);
		int typeID = getInt(request, "TypeID", 0);
		if(id<=0){
			output("{\"success\":false,\"msg\":\"请选择要删除的记录\"}", response);
			return;
		}
		try {
			if(isType == 1){
				DictionaryType dicType = dicManager.getType(id);
				if(dicType == null){
					output("{\"success\":false,\"msg\":\"要删除的分类类型不存在\"}", response);
					return;
				}
				dicManager.deleteType(id);
			}else {
				Dictionary dic = dicManager.getDic(typeID, id);
				if(dic == null){
					output("{\"success\":false,\"msg\":\"要删除的分类不存在\"}", response);
					return;
				}
				dicManager.deleteDic(typeID, id);
			}
			output("{\"success\":true,\"msg\":\"操作成功\"}", response);
		} catch (Exception e) {
			output("{\"success\":false,\"msg\":\"操作失败:"+e.getMessage()+"\"}", response);
		}
	}
	
	private void saveDic(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = getInt(request, "ItemID",0);
		int pid = getInt(request, "ItemPID",0);
		int typeid = getInt(request, "TypeID", 0);
		if(typeid<=0){
			output("{\"success\":false,\"msg\":\"没有指定类型\"}", response);
			return;
		}
		Dictionary dic = null;
		if(id>0){
			dic = dicManager.getDic(typeid, id);
		}
		if(dic == null){
			dic = new Dictionary();
		}
		dic.setId(id);
		dic.setType(typeid);
		dic.setPid(pid);
		dic.setName(get(request, "dic_name", ""));
		dic.setCode(get(request, "dic_code", ""));
		dic.setText(get(request, "dic_text", ""));
		dic.setValue(get(request, "dic_value", ""));
		dic.setMemo(get(request, "dic_memo", ""));
		dic.setOrder(getInt(request, "dic_order", id));
		dic.setValid(getInt(request, "dic_valid", 1));
		if(dicManager.exist(dic)){
			output("{\"success\":false,\"msg\":\"名称或者简码已经存在\"}", response);
			return;
		}
		try{
			if(dic.getId()>0){
				dicManager.updatedic(dic);
			}else {
				dicManager.createDic(dic);
			}
		}catch(Exception ex){
			output("{\"success\":false,\"msg\":\"保存失败:"+ex.getMessage()+"\"}", response);
		}
		if(dic.getId()<=0){
			output("{\"success\":false,\"msg\":\"保存失败\"}", response);
		}else {
			output("{\"success\":true,\"msg\":\"保存成功\"}", response);
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
			output("{\"success\":false,\"msg\":\"名称或者简码已经存在\"}", response);
			return;
		}
		try{
			if(dicType.getId()>0){
				dicManager.updateType(dicType);
			}else {
				dicManager.createType(dicType);
			}
		}catch(Exception ex){
			output("{\"success\":false,\"msg\":\"保存失败:"+ex.getMessage()+"\"}", response);
		}
		if(dicType.getId()<=0){
			output("{\"success\":false,\"msg\":\"保存失败\"}", response);
		}else {
			output("{\"success\":true,\"msg\":\"保存成功\"}", response);
		}
	}

}
