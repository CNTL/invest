package com.tl.kernel.sys.param;

public interface ParameterReader  {
	public ParameterType[] getTypes();
	public ParameterType getType(String typeName);
	public Parameter getParameter(String typeName,String paramName);
	public Parameter[] getParameters(int typeID);
	public Parameter getParameter(int typeID,String paramName);
}
