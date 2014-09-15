package com.tl.kernel.sys.dic;

import com.tl.kernel.context.TLException;

public interface DictionaryManager extends DictionaryReader{
	/**
	 * ����һ���µ�����
	 * @param type
	 */
	public void createType(DictionaryType type)	throws TLException;
	/**
	 * �޸�һ������
	 * @param type
	 */
	public void updateType(DictionaryType type)	throws TLException;
	
	/**
	 * ɾ��һ������
	 * ͬʱ�Ѹ������µ������ֵ��ɾ��
	 * @param id ����ID
	 */
	public void deleteType(int id) throws TLException;
	/**
	 * �����ֲ��ҷ���
	 * ֧��ģ����ѯ
	 * @param typeID ����ID
	 * @param dicName �ֵ�������
	 */
	public Dictionary[] getDicsByName(int typeID, String dicName)throws TLException;
	
	/**
	 * �����ֵ���
	 * @param dic
	 */
	public void createDic(Dictionary dic) throws TLException;
	/**
	 * <p>�޸�һ���ֵ���Ļ�������</p>
	 * <p>ע��������޸Ĳ������ǲ�α仯��Ҳ����˵��������PID���Եı仯
	 * <BR>��α仯�ǵ������еĶ�������Ҫ����<code>move</code>����</p>
	 * <BR>���ֵ�������Ʒ����仯ʱ�����Լ�����������ļ������ƶ�Ҫ�����仯
	 * @param dic
	 */
	public void updatedic(Dictionary dic) throws TLException;
	/**
	 * ��һ���ֵ����ƶ�����һ���ֵ���֮��
	 * �ֵ���Ĳ�ο��ܷ����仯
	 * ���ֵ����α仯ʱ��������������Ĳ�Σ��Լ���������Ҳͬʱ�����仯
	 * @param typeID ����ID
	 * @param srcDicID ��Ҫ�ƶ����ֵ���ID
	 * @param destDicID Ŀ����ֵ���ID
	 * @throws TLException
	 */
	public void move(int typeID, int srcDicID, int destDicID) throws TLException;
	/**
	 * �ѶԸ����ĳЩ���Ե��޸Ĵ��ݵ�����
	 * @param dic ���������޸����Եĸ���
	 * @param fields ָ����Ҫ����ͬ��������
	 * @throws TLException 
	 */
	public void updateTransfer(Dictionary dic, int[] fields) throws TLException;
	/**
	 * ɾ��һ����
	 * ͬʱ�����е�����ɾ��
	 * @param typeID ����ID
	 * @param dicID ��ID
	 */
	public void deleteDic(int typeID, int dicID) throws TLException;
	/**
	 * ɾ��һ����
	 * ͬʱ�����е�����ɾ��
	 * @param typeName ������
	 * @param dicID ��ID
	 */
	public void deleteDic(String typeName, int dicID)throws TLException;
	/**
	 * �ָ�һ����ɾ������
	 * @param typeID
	 * @param dicID
	 * @throws TLException
	 */
	public void restoreDic(int typeID, int dicID) throws TLException;
	/**
	 * ȡ�����Ѿ�ɾ������
	 * @param typeID ����ID
	 * @return ��ɾ�������
	 * @throws TLException
	 */
	public Dictionary[] getAllDeleted(int typeID) throws TLException;
	
	/**
	 * ȡ���������ڵ㣨�����Ѿ�ɾ����� 
	 * @param typeID - ����
	 * @param dicID - ��ID
	 * @return �ֵ�������
	 */
	public Dictionary[] getChildrenDicsIncludeDeleted(int typeID,int dicID)	throws TLException;
}
