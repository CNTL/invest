package com.tl.invest.workspace;

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

public class DicFetcher extends BaseController {
	private DictionaryReader dicReader = null;
	
	public void setDicReader(DictionaryReader dicReader){
		this.dicReader = dicReader;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action", "");
		
		if(dicReader == null) dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		
		if ("tree".equalsIgnoreCase(action)) {
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
							sb.append("\"attributes\":{\"typeid\":\"0\"}");
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
									sb.append("\"attributes\":{\"typeid\":\""+dic.getType()+"\"}");
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
