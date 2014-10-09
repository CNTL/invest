package com.tl.kernel.sys.dic;

import com.tl.kernel.context.TLException;

public interface DictionaryManager extends DictionaryReader{
	/**
	 * 创建一个新的类型
	 * @param type
	 */
	public void createType(DictionaryType type)	throws TLException;
	/**
	 * 修改一个类型
	 * @param type
	 */
	public void updateType(DictionaryType type)	throws TLException;
	
	/**
	 * 删除一个类型
	 * 同时把该类型下的所有字典项都删除
	 * @param id 类型ID
	 */
	public void deleteType(int id) throws TLException;
	
	/**
	 * 验证类型是否存在
	 * @param type
	 * @param oldID
	 * @return
	 * @throws TLException
	 */
	public boolean exist(DictionaryType type) throws TLException;
	/**
	 * 按名字查找分类
	 * 支持模糊查询
	 * @param typeID 类型ID
	 * @param dicName 字典项名称
	 */
	public Dictionary[] getDicsByName(int typeID, String dicName)throws TLException;
	
	/**
	 * 创建字典项
	 * @param dic
	 */
	public void createDic(Dictionary dic) throws TLException;
	/**
	 * <p>修改一个字典项的基本属性</p>
	 * <p>注意这里的修改并不考虑层次变化，也就是说，不考虑PID属性的变化
	 * <BR>层次变化是单独进行的动作，需要调用<code>move</code>方法</p>
	 * <BR>当字典项的名称发生变化时，它以及它所有子项的级联名称都要发生变化
	 * @param dic
	 */
	public void updatedic(Dictionary dic) throws TLException;
	/**
	 * 把一个字典项移动到另一个字典项之下
	 * 字典项的层次可能发生变化
	 * 当字典项层次变化时，它的所有子项的层次，以及级联名称也同时发生变化
	 * @param typeID 类型ID
	 * @param srcDicID 需要移动的字典项ID
	 * @param destDicID 目标根字典项ID
	 * @throws TLException
	 */
	public void move(int typeID, int srcDicID, int destDicID) throws TLException;
	/**
	 * 把对父项的某些属性的修改传递到子项
	 * @param dic 包含了已修改属性的父项
	 * @param fields 指定需要进行同步的属性
	 * @throws TLException 
	 */
	public void updateTransfer(Dictionary dic, int[] fields) throws TLException;
	/**
	 * 删除一个项
	 * 同时把所有的子项删除
	 * @param typeID 类型ID
	 * @param dicID 项ID
	 */
	public void deleteDic(int typeID, int dicID) throws TLException;
	/**
	 * 删除一个项
	 * 同时把所有的子项删除
	 * @param typeName 类型名
	 * @param dicID 项ID
	 */
	public void deleteDic(String typeName, int dicID)throws TLException;
	/**
	 * 恢复一个已删除的项
	 * @param typeID
	 * @param dicID
	 * @throws TLException
	 */
	public void restoreDic(int typeID, int dicID) throws TLException;
	/**
	 * 取所有已经删除的项
	 * @param typeID 类型ID
	 * @return 已删项的数组
	 * @throws TLException
	 */
	public Dictionary[] getAllDeleted(int typeID) throws TLException;
	
	/**
	 * 取得项的子孙节点（包含已经删除的项） 
	 * @param typeID - 类型
	 * @param dicID - 项ID
	 * @return 字典项数组
	 */
	public Dictionary[] getChildrenDicsIncludeDeleted(int typeID,int dicID)	throws TLException;
}
