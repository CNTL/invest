package com.tl.common.archive;

import org.apache.tools.ant.taskdefs.Expand;

import com.tl.common.archive.UnArchiver;
import com.tl.common.Utils;


/** ZIP ��ѹִ������ */
public class UnZipper extends UnArchiver
{
	/** Ĭ���ļ������� */
	public static final String DEFAULT_ENCODING = Utils.IS_WINDOWS_PLATFORM ? "GBK" : Utils.DEFAULT_ENCODING;

	private String encoding = DEFAULT_ENCODING;

	public UnZipper(String source)
	{
		super(source);
	}
	
	public UnZipper(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡ�ļ������� */
	public String getEncoding()
	{
		return encoding;
	}

	/** �����ļ������� */
	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}
	
	/** ��ȡ��ѹ������� */
	@Override
	protected Expand getExpand()
	{
		Expand expand = new Expand();
		
		if(encoding != null)
			expand.setEncoding(encoding);
		
		return expand;
	}

}
