package com.tl.common.archive;

import com.tl.common.archive.Tarrer;

/** BZIP2 ѹ��ִ������ */
public class BZipper extends Tarrer
{
	/** BZIP2 �ļ���׺�� */
	public static final String STUFFIX = ".tar.bz2";

	public BZipper(String source)
	{
		super(source);
	}
	
	public BZipper(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡ BZIP2 �ļ�ѹ��ģʽ */
	@Override
	protected String getCompressionMode()
	{
		return COMPRESS_MODES[2];
	}
	
	/** ��ȡѹ���ļ���׺�� */
	@Override
	public String getSuffix()
	{
		return STUFFIX;
	}
}
