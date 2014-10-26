package com.tl.kernel.sys.param;

public class Factory {
	private static ParameterReader paramReader;
	private static ParameterManager paramManager;
	
	public static ParameterReader buildParamReader(){
		if (paramReader == null) paramReader = new ParameterReaderImpl();
		return paramReader;
	}

	public static ParameterManager buildParamManager(){
		if (paramManager == null) paramManager = new ParameterManagerImpl();
		return paramManager;
	}
}
