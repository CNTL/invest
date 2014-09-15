package com.tl.common.directorywatcher;

import java.util.Map;
import java.util.HashMap;


public class DirectorySnapshot {

    @SuppressWarnings("rawtypes")
	private static Map files = new HashMap();

    @SuppressWarnings("unchecked")
	public static void addFile(String fileName){
        files.put(fileName, fileName);
    }

    public static void removeFile(String fileName){
        files.remove(fileName);
    }

    public static boolean containsFile(String fileName){
        return files.containsKey(fileName);
    }
}
