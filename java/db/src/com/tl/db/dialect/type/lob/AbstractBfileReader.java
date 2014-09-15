package com.tl.db.dialect.type.lob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tl.db.dialect.type.IBfile;
import com.tl.db.exception.BfileException;
import com.tl.db.util.CloseHelper;

/**
 * 抽象的读取型bfile实现
 * 
 * @author rengy
 * @version 1.0
 * @created 2006-4-21 9:44:21
 */
public abstract class AbstractBfileReader implements IBfile {

	/**
	 * @see com.tl.db.dialect.type.IBfile#writeTo(java.io.File)
	 */
	public void writeTo( File file ) throws IOException, BfileException {
		FileOutputStream out = new FileOutputStream( file );
		try {
			writeTo( out );
		} finally {
			CloseHelper.closeQuietly( out );
		}
	}

	/**
	 * @see com.tl.db.dialect.type.IBfile#getPlaceHolder()
	 */
	public String getPlaceHolder() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.tl.db.dialect.type.IBfile#setParameter(java.sql.PreparedStatement,
	 *      int)
	 */
	public int setParameter( PreparedStatement pst, int index )
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.tl.db.dialect.type.IBfile#store()
	 */
	public void store() throws Exception {
		throw new UnsupportedOperationException();
	}

}
