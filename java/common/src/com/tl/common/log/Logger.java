package com.tl.common.log;

import org.apache.log4j.Level;

import com.tl.common.log.Log;
import com.tl.common.log.Logger;
import com.tl.common.log.ProbeConfig;
import com.tl.common.log.RTLocation;
import com.tl.common.log.TraceManager;

public class Logger implements Log {
	private static final String FQCN = Logger.class.getName();
	
	/**
	 * 类别名
	 */
	private String category;

	/**
	 * Log4j的日志实例
	 */
	private org.apache.log4j.Logger logger;

	/**
	 * 该日志类别对应的跟踪类别
	 */
	private String traceCategory;

	/**
	 * 该日志类别对应的性能类别
	 */
	private String performanceCategory;
	
	/**
	 * 创建一个Log4jLog实例
	 * 
	 * @param category 类别名
	 * @param logger Log4j的日志实例
	 */
	public Logger( String category, org.apache.log4j.Logger logger ) {
		this.category = category;
		this.logger = logger;

		this.traceCategory = generateTraceCategory();
		this.performanceCategory = generatePerformanceCategory();
	}
	
	/**
	 * 生成本日志类别对应的trace类别<br>
	 * 例如：本类别为“tl.flow”，则对应的trace类别为“trace.flow”
	 * 
	 * @return
	 */
	private String generateTraceCategory() {
		if ( category.startsWith( "tl." ) )
			return category.replaceFirst( "tl", "trace" );
		else
			return "trace." + category;
	}

	/**
	 * 生成本日志类别对应的performance类别<br>
	 * 例如：本类别为“tl.flow”，则对应的performance类别为“performance.flow”
	 * 
	 * @return
	 */
	private String generatePerformanceCategory() {
		if ( category.startsWith( "tl." ) )
			return category.replaceFirst( "tl", "performance" );
		else
			return "performance." + category;
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor( Level.WARN );
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isEnabledFor( Level.ERROR );
	}

	@Override
	public void debug(Object message) {
		logger.log( FQCN, Level.DEBUG, message, null );
	}

	@Override
	public void debug(Object message, Throwable t) {
		logger.log( FQCN, Level.DEBUG, message, t );
	}

	@Override
	public void info(Object message) {
		logger.log( FQCN, Level.INFO, message, null );
	}

	@Override
	public void info(Object message, Throwable t) {
		logger.log( FQCN, Level.INFO, message, t );
	}

	@Override
	public void warn(Object message) {
		logger.log( FQCN, Level.WARN, message, null );
	}

	@Override
	public void warn(Object message, Throwable t) {
		logger.log( FQCN, Level.WARN, message, t );
	}

	@Override
	public void error(Object message) {
		logger.log( FQCN, Level.ERROR, message, null );
	}

	@Override
	public void error(Object message, Throwable t) {
		logger.log( FQCN, Level.ERROR, message, t );
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isEnabledFor( Level.TRACE );
	}

	@Override
	public void trace() {
		_trace( "", new Throwable() );
	}

	@Override
	public void trace(Object message) {
		_trace( message, new Throwable() );
	}
	
	/**
	 * 生成当前运行时位置信息，向TraceManager查询当前位置是否允许输出，如是则按相应配置输出
	 * 
	 * @param message 用户附加信息
	 * @param t 包含输出点位置信息的Throwable对象
	 */
	private void _trace( Object message, Throwable t ) {
		RTLocation rtl = generateRTLocation( t );
		ProbeConfig cfg = TraceManager.queryCfg( rtl );
		if ( cfg.isEnabled() ) {
			org.apache.log4j.Logger traceLogger = org.apache.log4j.Logger.getLogger( traceCategory );
			if ( cfg.isNeedStackInfo() )
				traceLogger.info( message, t );
			else
				traceLogger.info( message );
		}
	}

	@Override
	public boolean isPerformanceEnabled() {
		org.apache.log4j.Logger performanceLogger = org.apache.log4j.Logger.getLogger( performanceCategory );
		return performanceLogger.isInfoEnabled();
	}

	@Override
	public void performance(Object message) {
		org.apache.log4j.Logger performanceLogger = org.apache.log4j.Logger.getLogger( performanceCategory );
		performanceLogger.info( message );
	}
	
	/**
	 * 生成调用者的运行时定位信息
	 * 
	 * @return 运行时定位信息
	 */
	private static final RTLocation generateRTLocation( Throwable t ) {
		StackTraceElement[] ste = t.getStackTrace();

		// 从执行new Throwable()的方法的角度来看：
		// （i=0）是当前执行点，即本方法
		// （i=1）是调用者方法，即调用本方法的方法
		// （i=2）是调用者的调用者
		StackTraceElement caller = ste[1];
		String thread = Thread.currentThread().getName();
		String codeLocation = caller.getClassName() + "."
				+ caller.getMethodName();
		
		return ( new RTLocation( thread, codeLocation ) );
	}
	
	/**
	 * 返回该日志的类别名
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return category;
	}
}
