package com.tl.kernel.context;

import com.tl.db.DataSource;

public interface DSManager extends DSReader {
	/**
	 * ����һ������Դ
	 * 
	 * @param ds
	 */
	public void create(DataSource ds) throws Exception;

	/**
	 * �޸�����Դ
	 * 
	 * @param ds
	 */
	public void update(DataSource ds) throws Exception;

	/**
	 * ����Դɾ��
	 * 
	 * @param dsID
	 */
	public void delete(int id) throws Exception;

	/**
	 * ��ȡ��������Դ
	 */
	public DataSource[] getAll() throws Exception;
}
