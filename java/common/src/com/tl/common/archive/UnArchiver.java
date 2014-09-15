package com.tl.common.archive;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.types.PatternSet;

import com.tl.common.archive.TaskExecutor;

/** ��ѹִ���������� */
abstract public class UnArchiver extends TaskExecutor
{
	boolean overwrite = true;
	
	public UnArchiver(String source)
	{
		super(source);
	}
	
	public UnArchiver(String source, String target)
	{
		super(source, target);
	}
	
	/** ��ȡִ�н�ѹ�� {@link Expand} ���� */
	abstract protected Expand getExpand();

	/** ��ȡ��������ļ���ʶ */
	public boolean isOverwrite()
	{
		return overwrite;
	}

	/** ���ø�������ļ���ʶ */
	public void setOverwrite(boolean overWrite)
	{
		this.overwrite = overWrite;
	}
	
	/** ��ȡ�����ļ��� {@link File} ���� */
	protected File getSourceFile()
	{
		return new File(source);
	}

	/** ��ȡ����ļ��е� {@link File} ���� */
	protected File getTargetDir()
	{
		if(target != null)
			return new File(target);
		
		return getSourceFile().getParentFile();
	}
	
	/** ��ȡ��������ļ��� {@link PatternSet} ���� */
	protected PatternSet getPatternSet()
	{
		PatternSet ps = null;
		
		if(includes != null || excludes != null)
		{
			ps = new PatternSet();
			if(includes != null)
				ps.setIncludes(includes);
			if(excludes != null)
				ps.setExcludes(excludes);
		}
		
		return ps;
	}
	
	/** ��ȡ��ѹ������� */
	@Override
	protected Task getTask()
	{
		Project project	= new Project();
		Expand expand	= getExpand();
		PatternSet ps	= getPatternSet();

		expand.setProject(project);
		expand.setSrc(getSourceFile());
		expand.setDest(getTargetDir());
		expand.setOverwrite(isOverwrite());
		
		if(ps != null)
		{
			ps.setProject(project);
			expand.addPatternset(ps);
		}

		return expand;
	}
}
