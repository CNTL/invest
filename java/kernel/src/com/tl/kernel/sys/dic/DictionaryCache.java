package com.tl.kernel.sys.dic;

import java.util.ArrayList;
import java.util.HashMap;

import com.tl.common.log.Log;
import com.tl.kernel.context.Cache;
import com.tl.kernel.context.Context;

public class DictionaryCache implements Cache {
	Log log = Context.getLog("tl");
	private DictionaryType[] types = null;
	
	/**
	 * 字典hashmap缓存，key=DictionaryType,value=Dictionary[]
	 */
	@SuppressWarnings("rawtypes")
	private HashMap dicMap = new HashMap();
	
	/**
	 * 缓存类主要用在Reader实现类中<br>
	 * 在引用时，注意不要使用构造方法创建缓存类的实例<br>
	 * 请使用如下形式：<br>
	 * <code>
	 * XXXCache cache = (XXXCache)(CacheReader.find(XXXCache.class));
	 * if (cache != null)
	 * 		return cache.get(...);
	 * </code>
	 * 其中XXXCache代表具体的缓存类
	 */
	public DictionaryCache(){
	}
	
	
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public void refresh() throws Exception {
		DictionaryManager dicManager = DictionaryHelper.getDictionaryManager();
		
		// 分类类型和扩展类型数组缓存
		types = dicManager.getTypes();

		// 清除分类和分类扩展取值原有得缓存
		dicMap.clear();
		int dicCount = 0;
		for (int typeIndex = 0; types != null && typeIndex < types.length; typeIndex++)
		{
			Dictionary[] dics = dicManager.getDics(types[typeIndex].getId());
			if(dics!=null && dics.length>0)
				dicCount += dics.length;
			// 更新分类缓存
			dicMap.put(types[typeIndex], dics);		
		}
		log.info("缓存更新：成功获取"+(dicMap==null ? "0" : String.valueOf(dicMap.size()))+"字典类型；"+String.valueOf(dicCount)+"字典项");
	}

	@Override
	public void reset() {
	}

	DictionaryType[] getDictionaryTypes(){
		return types;
	}
	
	DictionaryType getDictionaryType(String name){
		if (types == null)
			return null;
		for (int i = 0; i < types.length; i++)
			if (types[i].getName().equals(name))
				return types[i];
		return null;
	}
	DictionaryType getDictionaryType(int id)
	{
		if (types == null)
			return null;
		for (int i = 0; i < types.length; i++)
			if (types[i].getId() == id)
				return types[i];
		return null;
	}
	
	Dictionary getDicByName(int type, String name){
		// 取分类
		Dictionary dics[] = getDics(type);
		if (dics == null) return null;

		Dictionary dic = null;
		for (int i = 0; i < dics.length; i++)
		{
			if (name.equals(dics[i].getName())
					|| name.equals(dics[i].getCascadeName()))
			{
				dic = dics[i];
				break;
			}
		}
		return dic;
	}
	
	Dictionary getDicByCode(int type, String code) {
		// 取分类
		Dictionary dics[] = getDics(type);
		if (dics == null) return null;

		Dictionary dic = null;
		for (int i = 0; i < dics.length; i++) {
			if (code.equals(dics[i].getCode())) {
				dic = dics[i];
				break;
			}
		}
		return dic;
	}
	
	Dictionary getDic(String typeName, int dicID)
	{
		DictionaryType type = getDictionaryType(typeName);
		return getDic(type.getId(), dicID);
	}
	
	Dictionary getDic(int dicType, int dicID)
	{
		// 取分类
		Dictionary dic = null;
		DictionaryType type = getDictionaryType(dicType);

		Dictionary dics[] = (Dictionary[]) dicMap.get(type);
		if (dics == null)
			return null;

		for (int i = 0; i < dics.length; i++)
		{
			if (dics[i].getId() == dicID)
			{
				dic = dics[i];
				break;
			}
		}

		return dic;
	}
	
	Dictionary[] getDics(int typeId)
	{
		Dictionary[] dics = null;

		DictionaryType type = getDictionaryType(typeId);
		dics = (Dictionary[]) dicMap.get(type);

		return dics;
	}

	Dictionary[] getDics(String typeName){
		DictionaryType type = getDictionaryType(typeName);
		return getDics(type.getId());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Dictionary[] getSubDics(int typeID, int dicID){
		DictionaryType type = getDictionaryType(typeID);

		Dictionary[] allDics = (Dictionary[]) dicMap.get(type);
		if (allDics == null) return null;
		
		ArrayList list = new ArrayList();
		for (int i = 0; i < allDics.length; i++){
			if (allDics[i].getPid() == dicID)
				list.add(allDics[i]);
		}
		Dictionary[] dics = (Dictionary[]) list.toArray(new Dictionary[0]);
		return dics;
	}
	
	Dictionary[] getSubDics(String typeName, int dicID){
		DictionaryType type = getDictionaryType(typeName);
		return getSubDics(type.getId(), dicID);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	Dictionary[] getChildrenDics(int typeID, int dicID){
		DictionaryType type = getDictionaryType(typeID);
		Dictionary[] allDics = (Dictionary[]) dicMap.get(type);
		if (dicID <= 0) return allDics;
		
		Dictionary parent = getDic(typeID, dicID);
		String cascadeID = parent.getCascadeId()+Dictionary.separator;
		
		ArrayList list = new ArrayList();
		for (int i = 0; i < allDics.length; i++){
			// 通过级联名称查询（不包括父节点本身）
			if (allDics[i].getCascadeId().startsWith(cascadeID))
				list.add(allDics[i]);
		}

		Dictionary[] dics = (Dictionary[]) list.toArray(new Dictionary[0]);
		return dics;
	}	
}
