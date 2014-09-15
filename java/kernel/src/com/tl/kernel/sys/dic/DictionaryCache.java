package com.tl.kernel.sys.dic;

import java.util.ArrayList;
import java.util.HashMap;

import com.tl.kernel.context.Cache;

public class DictionaryCache implements Cache {
	private DictionaryType[] types = null;
	
	/**
	 * �ֵ�hashmap���棬key=DictionaryType,value=Dictionary[]
	 */
	@SuppressWarnings("rawtypes")
	private HashMap dicMap = new HashMap();
	
	/**
	 * ��������Ҫ����Readerʵ������<br>
	 * ������ʱ��ע�ⲻҪʹ�ù��췽�������������ʵ��<br>
	 * ��ʹ��������ʽ��<br>
	 * <code>
	 * XXXCache cache = (XXXCache)(CacheReader.find(XXXCache.class));
	 * if (cache != null)
	 * 		return cache.get(...);
	 * </code>
	 * ����XXXCache�������Ļ�����
	 */
	public DictionaryCache(){
	}
	
	
	
	@Override
	public void refresh() throws Exception {
		// TODO Auto-generated method stub
		
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
		// ȡ����
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
		// ȡ����
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
		// ȡ����
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
			// ͨ���������Ʋ�ѯ�����������ڵ㱾��
			if (allDics[i].getCascadeId().startsWith(cascadeID))
				list.add(allDics[i]);
		}

		Dictionary[] dics = (Dictionary[]) list.toArray(new Dictionary[0]);
		return dics;
	}	
}
