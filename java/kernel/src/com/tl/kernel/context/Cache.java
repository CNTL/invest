package com.tl.kernel.context;

/**
 * 缓存接口
 * 每个实现缓存的类都继承此接口
 */
public interface Cache {
	/**
	 * 缓存刷新
	 */
	public void refresh() throws Exception;
	
	/**
	 * 缓存重置
	 */
	public void reset();
}
