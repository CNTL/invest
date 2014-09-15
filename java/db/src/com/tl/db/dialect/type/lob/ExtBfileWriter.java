package com.tl.db.dialect.type.lob;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author rengy
 * @version 1.0
 * @created 2006-4-21 0:07:10
 * @deprecated replaced by ExtBfile
 */
public class ExtBfileWriter extends AbstractBfileWriter {

	/**
	 * @param directory
	 * @param file
	 * @param input
	 */
	public ExtBfileWriter( String directory, String file, InputStream input ) {
		super( directory, file, input );
	}

	/**
	 * @see com.tl.db.dialect.type.IBfile#getPlaceHolder()
	 */
	public String getPlaceHolder() {
		return "?";
	}

	/**
	 * @see com.tl.db.dialect.type.IBfile#setParameter(java.sql.PreparedStatement,
	 *      int)
	 */
	public int setParameter( PreparedStatement pst, int index )
			throws SQLException {
		pst.setString( index, directory + "," + file );
		return index + 1;
	}

	/**
	 * @see com.tl.db.dialect.type.IBfile#store()
	 */
	public void store() throws Exception {
		// 默认实现不处理数据存储的工作
	}

}
