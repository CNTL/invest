package com.tl.kernel.context;

import com.tl.db.DataSource;

public interface DSManager extends DSReader {
	/**
	 * 创建一个数据源
	 * 
	 * @param ds
	 */
	public void create(DataSource ds) throws Exception;

	/**
	 * 修改数据源
	 * 
	 * @param ds
	 */
	public void update(DataSource ds) throws Exception;

	/**
	 * 数据源删除
	 * 
	 * @param dsID
	 */
	public void delete(int id) throws Exception;

	/**
	 * 获取所有数据源
	 */
	public DataSource[] getAll() throws Exception;
}
