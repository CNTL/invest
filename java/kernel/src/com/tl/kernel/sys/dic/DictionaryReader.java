package com.tl.kernel.sys.dic;

import com.tl.kernel.context.TLException;

/**
 * 字典读取器,用于读取字典和字典类型<br>
 * 获取实例的方法：DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
 */
public interface DictionaryReader {
	/**
	 * 根据字典类型名称、字典ID取得一个分类。
	 * @param typeName    字典类型名
	 * @param dicID    字典ID
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDic(String typeName, int dicID) throws TLException;
	/**
	 * 根据类型ID、ID取得一个字典项
	 * @param typeID 类型ID
	 * @param dicID ID
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDic(int typeID, int dicID) throws TLException;
	/**
	 * 根据类型ID、名称取得一个字典项
	 * @param typeID 类型ID
	 * @param name 名称
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDicByName(int typeID, String name) throws TLException;
	/**
	 * 取一个类型的所有字典项
	 * @param typeName 类型名称
	 * @return 数组
	 * @throws TLException
	 */
	public Dictionary[] getDics(String typeName) throws TLException;
	
	/**
	 * 取一个类型的所有字典项
	 * @param typeID
	 * @return 数组
	 * @throws TLException
	 */
	public Dictionary[] getDics(int typeID) throws TLException;
	/**
	 * 取一个字典项的所有子项。当字典项ID为0时，取该类型下的所有字典项
	 * @param typeID 分类类型ID
	 * @param dicID 分类ID
	 * @return 分类数组
	 * @throws TLException
	 */
	public Dictionary[] getChildrenDics(int typeID, int dicID) throws TLException;
	/**
	 * 取一个字典项的所有"直接"子项
	 * @param typeName 类型名
	 * @param dicID ID 当ID为0时，取该类型下的所有根项
	 * @return 数组
	 * @throws TLException
	 */
	public Dictionary[] getSubDics(String typeName, int dicID) throws TLException;
	/**
	 * 取一个字典项的所有"直接"子项
	 * @param typeID 类型ID
	 * @param dicID 当D为0时，取该类型下的所有根项
	 * @return 数组
	 * @throws TLException
	 */
	public Dictionary[] getSubDics(int typeID, int dicID) throws TLException;
	/**
	 * 根据类新ID取一个类型
	 * 
	 * @param id 类型ID
	 * @return 类型DictionaryType
	 * @throws TLException
	 */
	public DictionaryType getType(int id) throws TLException;
	/**
	 * 根据名称取一个类型
	 * @param name 类型名称
	 * @return 类型DictionaryType
	 * @throws TLException
	 */
	public DictionaryType getType(String name)throws TLException;
	/**
	 * 取所有的类型
	 * @return 类型数组
	 * @throws TLException
	 */
	public DictionaryType[] getTypes() throws TLException;
	
	/**
	 * 根据是否系统类型获取类型数组
	 * @param issys 是否系统
	 * @return 类型数组
	 * @throws TLException
	 */
	public DictionaryType[] getTypes(int sys) throws TLException;
	/**
	 * 根据字典项ID数组取得分类,数组长度应小于1000
	 * @param typeID 类型ID
	 * @param dicIDs ID数组,格式为:1,2,3 ...,n
	 * @return 符合dicIDs的字典项数组,该数组长度可能与dicIDs中的id个数不同,有些id可能没查到
	 * @throws TLException
	 */
	public Dictionary[] getDics(int typeID,String dicIDs) throws TLException;
	
	/**
	 * 给定一个字符串，搜索名称或者分类码中包含该字符串的所有字典项，按名称排序
	 * @param typeID 类型
	 * @param name	搜索的字符串，将在字典项名称和分类码中查找
	 * @return
	 * @throws TLException
	 */
	public Dictionary[] findDics(int typeID, String name) throws TLException;
	
	/**
	 * 根据代码取得一个字典项
	 * @param typeID 类型ID
	 * @param dicCode 代码
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDicByCode(int typeID, String dicCode) throws TLException;
}
