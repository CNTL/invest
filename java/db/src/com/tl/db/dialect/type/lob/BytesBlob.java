package com.tl.db.dialect.type.lob;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.tl.db.dialect.type.IBlob;
import com.tl.db.util.CloseHelper;

/**
 * 字节数组实现的IBlob
 * 
 */
public class BytesBlob implements IBlob {

	private byte[] bytes;

	public BytesBlob( byte[] bytes ) {
		if ( bytes == null )
			//throw new NullPointerException(); 
			bytes = new byte[0];//2006-10-17 Gong Lijie
		this.bytes = bytes;
	}

	/**
	 * @see com.tl.db.IBlob#toStream()
	 * @deprecated
	 */
	public InputStream toStream() {
		return new ByteArrayInputStream( bytes );
	}

	/**
	 * @see com.tl.db.IBlob#toBytes()
	 */
	public byte[] toBytes() throws IOException {
		return bytes;
	}

	/**
	 * @see com.tl.db.IBlob#writeTo(java.io.OutputStream)
	 */
	public void writeTo( OutputStream out ) throws IOException {
		out.write( bytes );
	}

	/**
	 * @throws IOException
	 * @see com.tl.db.IBlob#writeTo(java.io.File)
	 */
	public void writeTo( File file ) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream( file );
			writeTo( out );
		} finally {
			CloseHelper.closeQuietly( out );
		}
	}

	/**
	 * @see com.tl.db.IBlob#length()
	 */
	public long length() {
		return bytes.length;
	}

	/**
	 * @see com.tl.db.IBlob#getStream()
	 */
	public InputStream getStream() {
		return new ByteArrayInputStream( bytes );
	}

}
