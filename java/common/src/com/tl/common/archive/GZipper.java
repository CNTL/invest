package com.tl.common.archive;

import com.tl.common.archive.Tarrer;

/** GZIP ѹ��ִ������ */
public class GZipper extends Tarrer
{
	/** GZIP �ļ���׺�� */
	public static final String STUFFIX = ".tar.gz";

	public GZipper(String source)
	{
		super(source);
	}
	
	public GZipper(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡ GZIP �ļ�ѹ��ģʽ */
	@Override
	protected String getCompressionMode()
	{
		return COMPRESS_MODES[1];
	}
	
	/** ��ȡѹ���ļ���׺�� */
	@Override
	public String getSuffix()
	{
		return STUFFIX;
	}
}
