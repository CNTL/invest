package com.tl.common.archive;

import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Untar;
import org.apache.tools.ant.taskdefs.Untar.UntarCompressionMethod;

import com.tl.common.archive.UnArchiver;

/** TAR �⵵ִ������ */
public class UnTarrer extends UnArchiver
{
	/** TAR ѹ��ģʽ */
	protected static final String[] COMPRESS_MODES = {"none", "gzip", "bzip2"};

	public UnTarrer(String source)
	{
		super(source);
	}
	
	public UnTarrer(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡ��ѹ������� */
	@Override
	protected Expand getExpand()
	{
		Untar ut = new Untar();
		ut.setCompression(getCompressionMethod());
		
		return ut;
	}

	private UntarCompressionMethod getCompressionMethod()
	{
		UntarCompressionMethod method = new UntarCompressionMethod();
		method.setValue(getCompressionMode());
		
		return method;
	}
	
	/** ��ȡ TAR �ļ�ѹ��ģʽ */
	protected String getCompressionMode()
	{
		return COMPRESS_MODES[0];
	}

}
