package com.tl.kernel.sys.dic;

public class Factory {
	private static DictionaryReader dicReader;
	private static DictionaryManager dicManager;
	
	public static DictionaryReader buildDicReader(){
		if (dicReader == null) dicReader = new DictionaryReaderImpl();
		return dicReader;
	}

	public static DictionaryManager buildDicManager(){
		if (dicManager == null) dicManager = new DictionaryManagerImpl();
		return dicManager;
	}
}
