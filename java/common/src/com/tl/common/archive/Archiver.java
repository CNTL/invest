package com.tl.common.archive;

import java.io.File;

import org.apache.tools.ant.types.FileSet;

import com.tl.common.archive.TaskExecutor;

/** ѹ��ִ���������� */
abstract public class Archiver extends TaskExecutor
{
	/** ��ȡѹ���ļ��ĺ�׺�� */
	abstract public String getSuffix();
	
	public Archiver(String source)
	{
		super(source);
	}
	
	public Archiver(String source, String target)
	{
		super(source, target);
	}

	/** ��ȡ����ļ��� {@link File} ���� */
	protected File getTargetFile()
	{
		if(target != null)
			return new File(target);
		
		return new File(source + getSuffix());
	}
	
	/** ��� {@link FileSet} �������� */
	protected void fillFileSetAttributes(FileSet fs, File f)
	{
		if(f.isDirectory())
			fs.setDir(f);
		else
			fs.setFile(f);
		
		if(includes != null)
			fs.setIncludes(includes);
		if(excludes != null)
			fs.setExcludes(excludes);
	}

}
