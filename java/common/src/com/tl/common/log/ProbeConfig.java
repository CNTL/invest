package com.tl.common.log;

/**
 * 该类对象代表对一个"探测点"的配置，比如是否活动、是否需要堆栈信息等
 * 
 */
public class ProbeConfig {
	private boolean enabled;

	private boolean needStackInfo;

	/**
	 * 创建一个针对某个"探测点"的配置信息对象
	 * 
	 * @param enabled 是否打开
	 */
	public ProbeConfig( boolean enabled ) {
		this.enabled = enabled;
	}

	/**
	 * 创建一个针对某个"探测点"的配置信息对象
	 * 
	 * @param enabled 是否打开
	 * @param needStackInfo 是否需要堆栈信息
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
