package com.tl.kernel.context;

import com.tl.db.DataSource;

public interface DSReader {
	/**
	 * 读取数据源
	 * @param dsID
	 */
	public DataSource get(int id) throws Exception;
}
