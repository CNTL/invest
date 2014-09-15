package com.tl.common.archive;

import com.tl.common.archive.UnTarrer;

/** GZIP ��ѹִ������ */
public class UnGZipper extends UnTarrer
{
	public UnGZipper(String source)
	{
		super(source);
	}
	
	public UnGZipper(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡ GZIP �ļ�ѹ��ģʽ */
	@Override
	protected String getCompressionMode()
	{
		return COMPRESS_MODES[1];
	}

}
