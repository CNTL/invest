package com.tl.common.log;

/**
 * �����������һ��"̽���"�����ã������Ƿ����Ƿ���Ҫ��ջ��Ϣ��
 * 
 */
public class ProbeConfig {
	private boolean enabled;

	private boolean needStackInfo;

	/**
	 * ����һ�����ĳ��"̽���"��������Ϣ����
	 * 
	 * @param enabled �Ƿ��
	 */
	public ProbeConfig( boolean enabled ) {
		this.enabled = enabled;
	}

	/**
	 * ����һ�����ĳ��"̽���"��������Ϣ����
	 * 
	 * @param enabled �Ƿ��
	 * @param needStackInfo �Ƿ���Ҫ��ջ��Ϣ
	 */
	public ProbeConfig( boolean enabled, boolean needStackInfo ) {
		this.enabled = enabled;
		this.needStackInfo = needStackInfo;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled( boolean enabled ) {
		this.enabled = enabled;
	}

	public boolean isNeedStackInfo() {
		return needStackInfo;
	}

	public void setNeedStackInfo( boolean needStackInfo ) {
		this.needStackInfo = needStackInfo;
	}
	
	public String toString() {
		return new StringBuffer().append( "enabled:" ).append( enabled ).append(
				", needStackInfo:" ).append( needStackInfo ).toString();
	}
}
