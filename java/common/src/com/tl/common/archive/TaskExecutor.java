package com.tl.common.archive;

import org.apache.tools.ant.Task;

import com.tl.common.archive.TaskExecutor;

/** ѹ�� / ��ѹִ���������� */
abstract public class TaskExecutor {
	protected String source;
	protected String target;
	protected String includes;
	protected String excludes;
	
	private Exception cause;
	
	/** ��ȡ������� */
	abstract protected Task getTask();
	
	/** ִ��ѹ�����ѹ����
	 * 
	 * @return	�ɹ����� true��ʧ�ܷ��� false�����ʧ�ܿ�ͨ�� {@link TaskExecutor#getCause()} ��ȡʧ��ԭ��
	 * 
	 */
	public boolean execute()
	{
		cause = null;
		
		try
		{
			Task task = getTask();
			task.execute();
		}
		catch(Exception e)
		{
			cause = e;
			return false;
		}
		
		return true;
	}
	
	/**
	 * ���캯��
	 * 
	 * @param source	: �����ļ����ļ��С�������ļ����ļ���ͨ��Ĭ�Ϲ������ɣ�
	 * */
	public TaskExecutor(String source)
	{
		this(source, null);
	}
	
	/**
	 * ���캯��
	 * 
	 * @param source	: �����ļ����ļ���
	 * @param target	: ����ļ����ļ��У���� target ����Ϊ null����ͨ��Ĭ�Ϲ�����������ļ����ļ���
	 * */
	public TaskExecutor(String source, String target)
	{
		this.source	= source;
		this.target	= target;
	}
	
	/** ��ȡ�����ļ����ļ��� */
	public String getSource()
	{
		return source;
	}

	/** ���������ļ����ļ��� */
	public void setSource(String source)
	{
		this.source = source;
	}

	/** ��ȡ����ļ����ļ��� */
	public String getTarget()
	{
		return target;
	}

	/** ��������ļ����ļ��� */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/** ��ȡ�����ļ��Ĺ�����ʽ */
	public String getIncludes()
	{
		return includes;
	}

	/** ���ð����ļ��Ĺ�����ʽ */
	public void setIncludes(String includes)
	{
		this.includes = includes;
	}

	/** ��ȡ�ų��ļ��Ĺ�����ʽ */
	public String getExcludes()
	{
		return excludes;
	}

	/** �����ų��ļ��Ĺ�����ʽ */
	public void setExcludes(String excludes)
	{
		this.excludes = excludes;
	}

	/** ��ȡѹ�����ѹ�����ʧ��ԭ�� */
	public Exception getCause()
	{
		return cause;
	}
}
