package com.tl.kernel.sys.dic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tl.kernel.context.TLException;

public class DictionaryReaderImpl implements DictionaryReader{

	@Override
	public Dictionary getDic(String typeName, int dicID) throws TLException {
		return DictionaryHelper.getCache().getDic(typeName, dicID);
	}

	@Override
	public Dictionary getDic(int typeID, int dicID) throws TLException {
		return DictionaryHelper.getCache().getDic(typeID, dicID);
	}

	@Override
	public Dictionary getDicByName(int typeID, String name) throws TLException {
		return DictionaryHelper.getCache().getDicByName(typeID, name);
	}

	@Override
	public Dictionary[] getDics(String typeName) throws TLException {
		return DictionaryHelper.getCache().getDics(typeName);
	}

	@Override
	public Dictionary[] getDics(int typeID) throws TLException {
		return DictionaryHelper.getCache().getDics(typeID);
	}

	@Override
	public Dictionary[] getChildrenDics(int typeID, int dicID)
			throws TLException {
		return DictionaryHelper.getCache().getChildrenDics(typeID,dicID);
	}

	@Override
	public Dictionary[] getSubDics(String typeName, int dicID)
			throws TLException {
		DictionaryType type = getType(typeName);
		return DictionaryHelper.getCache().getChildrenDics(type.getId(),dicID);
	}

	@Override
	public Dictionary[] getSubDics(int typeID, int dicID) throws TLException {
		return DictionaryHelper.getCache().getSubDics(typeID,dicID);
	}

	@Override
	public DictionaryType getType(int id) throws TLException {
		DictionaryCache cache = DictionaryHelper.getCache();
		if (cache != null)
			return cache.getDictionaryType(id);
		return DictionaryHelper.getDictionaryManager().getType(id);
	}

	@Override
	public DictionaryType getType(String name) throws TLException {
		DictionaryCache cache = DictionaryHelper.getCache();
		if (cache != null)
			return cache.getDictionaryType(name);
		return DictionaryHelper.getDictionaryManager().getType(name);
	}

	@Override
	public DictionaryType[] getTypes() throws TLException {
		DictionaryCache cache = DictionaryHelper.getCache();
		if (cache != null)
			return cache.getDictionaryTypes();
		return DictionaryHelper.getDictionaryManager().getTypes();
	}

	@Override
	public DictionaryType[] getTypes(int sys) throws TLException {
		DictionaryType[] all = getTypes();
		if(all==null || all.length<=0) return null;
		List<DictionaryType> types = new ArrayList<DictionaryType>();
		for (DictionaryType type : all) {
			if(type.getIsSys()==sys && type.getValid()==1){
				types.add(type);
			}
		}
		
		if (types.size() == 0) return null;
		return (DictionaryType[])types.toArray(new DictionaryType[0]);
	}

	@Override
	public Dictionary[] getDics(int typeID, String dicIDs) throws TLException {
		if(dicIDs == null || "".equals(dicIDs))
			return null;		
		
		String[] dicIDArray = dicIDs.split(",");
		Dictionary[] dics = new Dictionary[dicIDArray.length];
		for(int i=0;i<dicIDArray.length;i++){
			dics[i] = this.getDic(typeID,Integer.parseInt(dicIDArray[i]));			
		}		
		return dics;
	}

	@Override
	public Dictionary[] findDics(int typeID, String name) throws TLException {
		List<Dictionary> result = new ArrayList<Dictionary>();
		
		Dictionary[] allDics = DictionaryHelper.getCache().getDics(typeID);
		for (Dictionary dic : allDics) {
			if (dic.getName() != null && dic.getName().indexOf(name) >= 0)
				result.add(dic.clone());
			else if (dic.getCode() != null && dic.getCode().indexOf(name) >= 0)
				result.add(dic.clone());
		}
		if (result.size() > 1) {
			//按级联名称排序。因为本功能主要用于输入框查找，这种排序方式比较容易查看
			Collections.sort( result, new Comparator<Dictionary>() {
				public int compare( Dictionary o1, Dictionary o2 ) {
					return o1.getCascadeName().compareTo(o2.getCascadeName());
				}
			} );
		}
		return result.toArray(new Dictionary[0]);
	}

	@Override
	public Dictionary getDicByCode(int typeID, String dicCode)
			throws TLException {
		return DictionaryHelper.getCache().getDicByCode(typeID, dicCode);
	}
	
}
