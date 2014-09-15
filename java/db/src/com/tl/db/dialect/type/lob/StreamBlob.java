package com.tl.db.dialect.type.lob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.tl.db.dialect.type.IBlob;
import com.tl.db.util.CloseHelper;

/**
 * 流实现的IBlob
 * 
 * @author rengy
 */
public class StreamBlob implements IBlob {

	private InputStream stream;

	private long length;

	private boolean closed;

	/**
	 * @param stream
	 */
	public StreamBlob( InputStream stream ) {
		if ( stream == null )
			throw new NullPointerException();

		this.stream = stream;
		this.length = Integer.MAX_VALUE;
	}

	/**
	 * @param stream
	 * @param length
	 */
	public StreamBlob( InputStream stream, long length ) {
		if ( stream == null )
			throw new NullPointerException();

		this.stream = stream;
		this.length = length;
	}

	private void _check() {
		if ( closed ) {
			throw new IllegalStateException( "Stream already closed!" );
		}
	}

	/**
	 * @see com.tl.db.IBlob#toStream()
	 * @deprecated
	 */
	public InputStream toStream() {
		_check();
		return stream;
	}

	/**
	 * @see com.tl.db.IBlob#toBytes()
	 */
	public byte[] toBytes() throws IOException {
		_check();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			IOUtils.copy( stream, out );
		} finally {
			CloseHelper.closeQuietly( stream );
			stream = null;
			closed = true;
		}

		return out.toByteArray();
	}

	/**
	 * @see com.tl.db.IBlob#writeTo(java.io.OutputStream)
	 */
	public void writeTo( OutputStream out ) throws IOException {
		_check();
		try {
			IOUtils.copy( stream, out );
		} finally {
			CloseHelper.closeQuietly( stream );
			stream = null;
			closed = true;
		}
	}

	/**
	 * @see com.tl.db.IBlob#writeTo(java.io.File)
	 */
	public void writeTo( File file ) throws IOException {
		_check();
		FileOutputStream out = new FileOutputStream( file );
		try {
			writeTo( new FileOutputStream( file ) );
		} finally {
			CloseHelper.closeQuietly( out );
		}
	}

	/**
	 * @see com.tl.db.IBlob#length()
	 */
	public long length() {
		return length;
	}

	/**
	 * @see com.tl.db.IBlob#getStream()
	 */
	public InputStream getStream() {
		_check();
		return stream;
	}

}
