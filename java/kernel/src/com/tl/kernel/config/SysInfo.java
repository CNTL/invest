package com.tl.kernel.config;

public class SysInfo {
	private String name;
	private String version;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String toString(){
		return (new StringBuffer(100)
		.append("[name:").append(name)
		.append(",version:").append(version)
		.append("]")
		).toString();
	}
}
