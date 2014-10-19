package com.tl.workspace.dic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.Pair;
import com.tl.common.StringUtils;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryType;
import com.tl.kernel.web.BaseController;

public class DictionaryFetcher extends BaseController {
	private DictionaryReader dicReader = null;
	
	public void setDicReader(DictionaryReader dicReader){
		this.dicReader = dicReader;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		if("typeadd".equals(action)){
			model.put("id", 0);
			model.put("@VIEWNAME@", "dic/DicTypeEdit");
		}else if("typeedit".equals(action)){
			editType(request,model);
		}else if("add".equals(action)){
			model.put("id", 0);
			model.put("typeid", getInt(request, "typeid", 0));
			model.put("pid", getInt(request, "id",0));
			model.put("@VIEWNAME@", "dic/DicEdit");
		}else if ("edit".equals(action)) {
			editDic(request,model);
		}else if ("deltype".equals(action)) {
			deltype(request,model);
		}else if ("delete".equals(action)) {
			delete(request,model);
		}else if("mtree".equals(action)){
			outManageDicTree(request,response);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void deltype(HttpServletRequest request, Map model) throws TLException {
		int id = getInt(request, "id", 0);
		
		if(dicReader == null) dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		DictionaryType dicType = dicReader.getType(id);
		
		model.put("istype", 1);
		model.put("id", dicType==null ? 0 : dicType.getId());
		model.put("typeid", 0);
		model.put("@VIEWNAME@", "dic/delete");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void delete(HttpServletRequest request, Map model) throws TLException {
		int id = getInt(request, "id", 0);
		int typeId = getInt(request, "typeid", 0);
		
		if(dicReader == null) dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary dic = dicReader.getDic(typeId, id);
		
		model.put("istype", 0);
		model.put("id", dic==null ? 0 : dic.getId());
		model.put("typeid", typeId);
		model.put("@VIEWNAME@", "dic/delete");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void editDic(HttpServletRequest request, Map model) throws TLException {
		int id = getInt(request, "id", 0);
		int typeId = getInt(request, "typeid", 0);
		int pid = getInt(request, "pid",0);
		Dictionary dic = null;
		if(id>0){
			if(dicReader == null) dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
			dic = dicReader.getDic(typeId, id);
		}
		
		model.put("id", id);		
		model.put("typeid", typeId);
		model.put("pid", pid);
		if(dic!=null){
			model.put("dic_name", dic.getName());
			model.put("dic_code", dic.getCode());
			model.put("dic_text", dic.getText());
			model.put("dic_value", dic.getValue());
			model.put("dic_order", dic.getOrder());
			model.put("dic_valid", dic.getValid());
			model.put("dic_memo", dic.getMemo());
		}
		
		model.put("@VIEWNAME@", "dic/DicEdit");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void editType(HttpServletRequest request,Map model) throws TLException{
		int id = getInt(request, "id", 0);
		DictionaryType dicType = null;
		if(id>0){
			if(dicReader == null) dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
			dicType = dicReader.getType(id);
		}
		model.put("id", id);
		if(dicType!=null){
			model.put("dt_name", dicType.getName());
			model.put("dt_code", dicType.getCode());
			model.put("dt_sys", dicType.getIsSys());
			model.put("dt_maxlevel", dicType.getMaxLevel());
			model.put("dt_memo", dicType.getMemo());
			model.put("dt_order", dicType.getOrder());
			model.put("dt_valid", dicType.getValid());
		}
		model.put("@VIEWNAME@", "dic/DicTypeEdit");
	}
	
	private void outManageDicTree(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(dicReader == null) dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		
		int rootID = getInt(request, "root", 0);
		int loadroot = getInt(request, "loadroot", 1);
		int id = getInt(request, "id",0);
		int isSys = getInt(request, "sys",-9999);
		DictionaryType[] dicTypes = null;			
		if (rootID>0) {
			DictionaryType t = dicReader.getType(rootID);
			if(t!=null)
				dicTypes = new DictionaryType[]{t};
		}else {
			if(isSys == -9999)
				dicTypes = dicReader.getTypes();
			else
				dicTypes = dicReader.getTypes(isSys);
		}
		
		List<Pair> all = new ArrayList<Pair>();
		
		if(dicTypes!=null && dicTypes.length>0){
			for (DictionaryType dicType : dicTypes) {
				Dictionary[] dics = dicReader.getSubDics(dicType.getId(), id);
				all.add(new Pair(String.valueOf(dicType.getId()), dics));
			}
		}
		StringBuffer sb = new StringBuffer();
		
		for (Pair p : all) {
			for (DictionaryType dicType : dicTypes) {
				if(p.getKey().equals(String.valueOf(dicType.getId()))){						
					if(rootID == 0){
						String childJson = getChildrenJSON(dicType.getId(),0);
						if(sb.length()>0) sb.append(",");
						sb.append("{");
						sb.append("\"id\":"+p.getKey()+",");
						sb.append("\"text\":\""+dicType.getName()+"\",");
						sb.append("\"state\":\""+(StringUtils.isEmpty(childJson)?"open":"closed")+"\",");
						sb.append("\"attributes\":{\"typeid\":\""+p.getKey()+"\",\"istype\":\"1\",\"pid\":\"0\",\"maxlevel\":\""+dicType.getMaxLevel()+"\"}");
						sb.append("}");
					}else {
						Dictionary[] allDics = (Dictionary[])p.getValue();
						if(allDics!=null && allDics.length>0){
							for (Dictionary dic : allDics) {
								String childJson = getChildrenJSON(dic.getType(),dic.getId());
								if(sb.length()>0) sb.append(",");
								sb.append("{");
								sb.append("\"id\":"+dic.getId()+",");
								sb.append("\"text\":\""+dic.getName()+"\",");
								sb.append("\"state\":\""+(StringUtils.isEmpty(childJson)?"open":"closed")+"\",");
								sb.append("\"attributes\":{\"typeid\":\""+dic.getType()+"\",\"istype\":\"0\",\"pid\":\""+dic.getPid()+"\",\"maxlevel\":\""+dicType.getMaxLevel()+"\"}");
								sb.append("}");
							}
						}
					}
				}
			}
		}
		
		
		StringBuffer result = new StringBuffer();
		if(loadroot == 1){
			result.append("{");
			result.append("\"id\":0,");
			result.append("\"text\":\"分类类型\",");
			result.append("\"state\":\""+((dicTypes==null || dicTypes.length==0)?"open":"open")+"\",");
			result.append("\"attributes\":{\"typeid\":\"-1\"}");
			if(sb.length()>0){
				sb.insert(0, "[");
				sb.append("]");
				result.append(",\"children\":");
				result.append(sb);
			}
			result.append("}");
		}else {
			result.append(sb);
		}
		result.insert(0, "[");
		result.append("]");
		outputJson(result.toString(), response);
	}
	
	private String getChildrenJSON(int typeID,int pid) throws TLException{
		Dictionary[] subs = dicReader.getSubDics(typeID, pid);
		StringBuffer sb1 = new StringBuffer();
		if(subs !=null && subs.length >0){
			for (Dictionary dic : subs) {
				if(sb1.length()>0) sb1.append(",");
				sb1.append("{");
				sb1.append("\"id\":"+dic.getId()+",");
				sb1.append("\"text\":\""+dic.getName()+"\",");
				sb1.append("\"state\":\"closed\",");
				sb1.append("\"attributes\":{\"typeid\":\""+dic.getType()+"\"}");
				sb1.append("}");
			}
		}
		return sb1.toString();
	}

}
