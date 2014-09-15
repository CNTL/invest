package com.tl.kernel.config;

public class StartClass {
	private String invokeClass;
    private String invokeMethod;
    private String onlyWeb; //只在WEB环境中启动的启动项，如任务调度
    
    public String getOnlyWeb() {
    	return onlyWeb;
    }
    
    public void setOnlyWeb(String onlyWeb) {
		this.onlyWeb = onlyWeb;
	}

	public String getInvokeClass()
    {
        return invokeClass;
    }
    public void setInvokeClass(String invokeClass)
    {
        this.invokeClass = invokeClass;
    }
    public String getInvokeMethod()
    {
        return invokeMethod;
    }
    public void setInvokeMethod(String invokeMethod)
    {
        this.invokeMethod = invokeMethod;
    }
    
    public String toString(){
		return (new StringBuffer(100)
				.append("[invokeClass:").append(invokeClass)
				.append(",invokeMethod:").append(invokeMethod)
				.append("]")
				).toString();
	}
}
