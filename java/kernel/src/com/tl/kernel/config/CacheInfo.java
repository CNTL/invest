package com.tl.kernel.config;

public class CacheInfo {
	private String name;
	private String invokeClass;
	
	/**
	 * @return Returns the invokeClass.
	 */
	public String getInvokeClass()
	{
		return invokeClass;
	}
	/**
	 * @param invokeClass The invokeClass to set.
	 */
	public void setInvokeClass(String invokeClass)
	{
		this.invokeClass = invokeClass;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	public String toString(){
		return (new StringBuffer()
				.append("[name:").append(name)
				.append(",invokeClass:").append(invokeClass)
				.append("]")
				).toString();
	}
}
