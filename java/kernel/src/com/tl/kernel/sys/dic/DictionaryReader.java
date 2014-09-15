package com.tl.kernel.sys.dic;

import com.tl.kernel.context.TLException;

/**
 * �ֵ��ȡ��,���ڶ�ȡ�ֵ���ֵ�����<br>
 * ��ȡʵ���ķ�����DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
 */
public interface DictionaryReader {
	/**
	 * �����ֵ��������ơ��ֵ�IDȡ��һ�����ࡣ
	 * @param typeName    �ֵ�������
	 * @param dicID    �ֵ�ID
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDic(String typeName, int dicID) throws TLException;
	/**
	 * ��������ID��IDȡ��һ���ֵ���
	 * @param typeID ����ID
	 * @param dicID ID
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDic(int typeID, int dicID) throws TLException;
	/**
	 * ��������ID������ȡ��һ���ֵ���
	 * @param typeID ����ID
	 * @param name ����
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDicByName(int typeID, String name) throws TLException;
	/**
	 * ȡһ�����͵������ֵ���
	 * @param typeName ��������
	 * @return ����
	 * @throws TLException
	 */
	public Dictionary[] getDics(String typeName) throws TLException;
	
	/**
	 * ȡһ�����͵������ֵ���
	 * @param typeID
	 * @return ����
	 * @throws TLException
	 */
	public Dictionary[] getDics(int typeID) throws TLException;
	/**
	 * ȡһ���ֵ��������������ֵ���IDΪ0ʱ��ȡ�������µ������ֵ���
	 * @param typeID ��������ID
	 * @param dicID ����ID
	 * @return ��������
	 * @throws TLException
	 */
	public Dictionary[] getChildrenDics(int typeID, int dicID) throws TLException;
	/**
	 * ȡһ���ֵ��������"ֱ��"����
	 * @param typeName ������
	 * @param dicID ID ��IDΪ0ʱ��ȡ�������µ����и���
	 * @return ����
	 * @throws TLException
	 */
	public Dictionary[] getSubDics(String typeName, int dicID) throws TLException;
	/**
	 * ȡһ���ֵ��������"ֱ��"����
	 * @param typeID ����ID
	 * @param dicID ��DΪ0ʱ��ȡ�������µ����и���
	 * @return ����
	 * @throws TLException
	 */
	public Dictionary[] getSubDics(int typeID, int dicID) throws TLException;
	/**
	 * ��������IDȡһ������
	 * 
	 * @param id ����ID
	 * @return ����DictionaryType
	 * @throws TLException
	 */
	public DictionaryType getType(int id) throws TLException;
	/**
	 * ��������ȡһ������
	 * @param name ��������
	 * @return ����DictionaryType
	 * @throws TLException
	 */
	public DictionaryType getType(String name)throws TLException;
	/**
	 * ȡ���е�����
	 * @return ��������
	 * @throws TLException
	 */
	public DictionaryType[] getTypes() throws TLException;
	
	/**
	 * �����Ƿ�ϵͳ���ͻ�ȡ��������
	 * @param issys �Ƿ�ϵͳ
	 * @return ��������
	 * @throws TLException
	 */
	public DictionaryType[] getTypes(int sys) throws TLException;
	/**
	 * �����ֵ���ID����ȡ�÷���,���鳤��ӦС��1000
	 * @param typeID ����ID
	 * @param dicIDs ID����,��ʽΪ:1,2,3 ...,n
	 * @return ����dicIDs���ֵ�������,�����鳤�ȿ�����dicIDs�е�id������ͬ,��Щid����û�鵽
	 * @throws TLException
	 */
	public Dictionary[] getDics(int typeID,String dicIDs) throws TLException;
	
	/**
	 * ����һ���ַ������������ƻ��߷������а������ַ����������ֵ������������
	 * @param typeID ����
	 * @param name	�������ַ����������ֵ������ƺͷ������в���
	 * @return
	 * @throws TLException
	 */
	public Dictionary[] findDics(int typeID, String name) throws TLException;
	
	/**
	 * ���ݴ���ȡ��һ���ֵ���
	 * @param typeID ����ID
	 * @param dicCode ����
	 * @return Dictionary
	 * @throws TLException
	 */
	public Dictionary getDicByCode(int typeID, String dicCode) throws TLException;
}
