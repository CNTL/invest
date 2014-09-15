package com.tl.common.archive;

import com.tl.common.archive.UnTarrer;

/** BZIP2 ��ѹִ������ */
public class UnBZipper extends UnTarrer
{
	public UnBZipper(String source)
	{
		super(source);
	}
	
	public UnBZipper(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡ BZIP2 �ļ�ѹ��ģʽ */
	@Override
	protected String getCompressionMode()
	{
		return COMPRESS_MODES[2];
	}

}
