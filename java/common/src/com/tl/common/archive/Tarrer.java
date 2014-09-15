package com.tl.common.archive;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Tar;
import org.apache.tools.ant.taskdefs.Tar.TarCompressionMethod;
import org.apache.tools.ant.taskdefs.Tar.TarFileSet;

import com.tl.common.archive.Archiver;

/** TAR �鵵ִ������ */
public class Tarrer extends Archiver
{
	/** TAR �ļ���׺�� */
	public static final String SUFFIX = ".tar";
	/** TAR ѹ��ģʽ */
	protected static final String[] COMPRESS_MODES = {"none", "gzip", "bzip2"};

	public Tarrer(String source)
	{
		super(source);
	}
	
	public Tarrer(String source, String target)
	{
		super(source, target);
	}
	
	private TarCompressionMethod getCompressionMethod()
	{
		TarCompressionMethod method = new TarCompressionMethod();
		method.setValue(getCompressionMode());
		
		return method;
	}
	
	/** ��ȡ TAR �ļ�ѹ��ģʽ */
	protected String getCompressionMode()
	{
		return COMPRESS_MODES[0];
	}
	
	/** ��ȡѹ���ļ���׺�� */
	@Override
	public String getSuffix()
	{
		return SUFFIX;
	}
	
	/** ��ȡѹ��������� */
	@Override
	protected Task getTask()
	{
		Project project	= new Project();
		Tar tar			= new Tar();
		
		tar.setProject(project);
		createSourceFileSet(tar);

		tar.setDestFile(getTargetFile());
		tar.setCompression(getCompressionMethod());
		
		return tar;
	}

	private TarFileSet createSourceFileSet(Tar tar)
	{
		File f			= new File(source);
		TarFileSet fs	= tar.createTarFileSet();
		
		fillFileSetAttributes(fs, f);
		
		return fs;
	}
}
