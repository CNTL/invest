package com.tl.common.log;

import com.tl.common.log.Log;
import com.tl.common.log.Logger;

public class LogFactory {
	public static Log getLog(String category){
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( category );
		return new Logger( category, logger );
	}
}
