package com.tl.kernel.context;

import com.tl.db.DataSource;

public interface DSReader {
	/**
	 * ��ȡ����Դ
	 * @param dsID
	 */
	public DataSource get(int id) throws Exception;
}
