package com.tl.common.archive;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.tl.common.archive.Archiver;
import com.tl.common.Utils;


/** ZIP ѹ��ִ������ */
public class Zipper extends Archiver
{
	/** ZIP �ļ���׺�� */
	public static final String SUFFIX = ".zip";
	/** Ĭ���ļ������� */
	public static final String DEFAULT_ENCODING = Utils.IS_WINDOWS_PLATFORM ? "GBK" : Utils.DEFAULT_ENCODING;

	private String comment;
	private String encoding = DEFAULT_ENCODING;

	
	public Zipper(String source)
	{
		super(source);
	}
	
	public Zipper(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡѹ���ļ����� */
	public String getComment()
	{
		return comment;
	}

	/** ����ѹ���ļ����� */
	public void setComment(String comment)
	{
		this.comment = comment;
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
		Zip zip			= new Zip();
		FileSet src		= getSourceFileSet();
		
		src.setProject(project);
		zip.setProject(project);
		zip.setDestFile(getTargetFile());
		zip.addFileset(src);
		
		if(encoding != null)
			zip.setEncoding(encoding);
		if(comment != null)
			zip.setComment(comment);
		
		return zip;
	}

	private FileSet getSourceFileSet()
	{
		File f		= new File(source);
		FileSet fs	= new FileSet();
			
		fillFileSetAttributes(fs, f);
		
		return fs;
	}

}
