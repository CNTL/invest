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
	 * �����
	 */
	private String category;

	/**
	 * Log4j����־ʵ��
	 */
	private org.apache.log4j.Logger logger;

	/**
	 * ����־����Ӧ�ĸ������
	 */
	private String traceCategory;

	/**
	 * ����־����Ӧ���������
	 */
	private String performanceCategory;
	
	/**
	 * ����һ��Log4jLogʵ��
	 * 
	 * @param category �����
	 * @param logger Log4j����־ʵ��
	 */
	public Logger( String category, org.apache.log4j.Logger logger ) {
		this.category = category;
		this.logger = logger;

		this.traceCategory = generateTraceCategory();
		this.performanceCategory = generatePerformanceCategory();
	}
	
	/**
	 * ���ɱ���־����Ӧ��trace���<br>
	 * ���磺�����Ϊ��rengy.flow�������Ӧ��trace���Ϊ��trace.flow��
	 * 
	 * @return
	 */
	private String generateTraceCategory() {
		if ( category.startsWith( "rengy." ) )
			return category.replaceFirst( "rengy", "trace" );
		else
			return "trace." + category;
	}

	/**
	 * ���ɱ���־����Ӧ��performance���<br>
	 * ���磺�����Ϊ��rengy.flow�������Ӧ��performance���Ϊ��performance.flow��
	 * 
	 * @return
	 */
	private String generatePerformanceCategory() {
		if ( category.startsWith( "rengy." ) )
			return category.replaceFirst( "rengy", "performance" );
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
	 * ���ɵ�ǰ����ʱλ����Ϣ����TraceManager��ѯ��ǰλ���Ƿ������������������Ӧ�������
	 * 
	 * @param message �û�������Ϣ
	 * @param t ���������λ����Ϣ��Throwable����
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
	 * ���ɵ����ߵ�����ʱ��λ��Ϣ
	 * 
	 * @return ����ʱ��λ��Ϣ
	 */
	private static final RTLocation generateRTLocation( Throwable t ) {
		StackTraceElement[] ste = t.getStackTrace();

		// ��ִ��new Throwable()�ķ����ĽǶ�������
		// ��i=0���ǵ�ǰִ�е㣬��������
		// ��i=1���ǵ����߷����������ñ������ķ���
		// ��i=2���ǵ����ߵĵ�����
		StackTraceElement caller = ste[1];
		String thread = Thread.currentThread().getName();
		String codeLocation = caller.getClassName() + "."
				+ caller.getMethodName();
		
		return ( new RTLocation( thread, codeLocation ) );
	}
	
	/**
	 * ���ظ���־�������
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return category;
	}
}
