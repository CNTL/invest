package com.tl.common.log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tl.common.log.ProbeConfig;
import com.tl.common.log.RTLocation;

/**
 * ���ٿ�����Ϣ���������������벢������ٿ���������Ϣ
 * 
 */
public class TraceManager {
	/**
	 * key��RTLocation���� - value��ProbeConfig����<br>
	 * ע�⣺ÿ��key��RTLocation���󣩴�����һ��̽��㣬����RTLocation���߳����ʹ���λ ������ֵ������������ʽ
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map enabledProbes = Collections.synchronizedMap( new HashMap() );

	/**
	 * ����probe��Ĭ������
	 */
	private static final ProbeConfig default_cfg = new ProbeConfig(
			false,
			false );

	/**
	 * ��ѯ��Ե���̽����������Ϣ
	 * 
	 * @param rtl ����̽���
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ProbeConfig queryCfg( RTLocation rtl ) {
		for ( Iterator i = enabledProbes.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry me = ( Map.Entry ) i.next();
			RTLocation key = ( RTLocation ) me.getKey();
			ProbeConfig value = ( ProbeConfig ) me.getValue();

			if ( key.contain( rtl ) )
				return value;
		}

		return default_cfg;
	}

	/**
	 * ������̽���Ŀ����Ƿ��
	 * 
	 * @param rtl ����̽���
	 * @return
	 */
	public static boolean isEnabled( RTLocation rtl ) {
		return queryCfg( rtl ).isEnabled();
	}

	/**
	 * ���һ��̽��㵽�Ѵ�̽����б���
	 * 
	 * @param rtl ������һ��̽��㣬����RTLocation���߳����ʹ���λ������ֵ������������ʽ
	 */
	@SuppressWarnings("unchecked")
	public static void addEnabled( RTLocation rtl ) {
		enabledProbes.put( rtl, new ProbeConfig( true ) );
	}

	/**
	 * ���һ��̽��㵽�Ѵ�̽����б���
	 * 
	 * @param rtl ������һ��̽��㣬����RTLocation���߳����ʹ���λ������ֵ������������ʽ
	 * @param needStackInfo �Ƿ���Ҫ��ջ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	public static void addEnabled( RTLocation rtl, boolean needStackInfo ) {
		enabledProbes.put( rtl, new ProbeConfig( true, needStackInfo ) );
	}

	/**
	 * ���һ��̽��㵽�Ѵ�̽����б���<br>
	 * ע�⣺Ĭ�ϲ���Ҫ��ջ��Ϣ
	 * 
	 * @param threadName �߳���ģʽ
	 * @param codeLocation ����λ��ģʽ
	 */
	public static void addEnabled( String threadName, String codeLocation ) {
		addEnabled( new RTLocation( threadName, codeLocation ) );
	}

	/**
	 * ���һ��̽��㵽�Ѵ�̽����б��У���ָ���Ƿ���Ҫ��ջ��Ϣ
	 * 
	 * @param threadName �߳���ģʽ
	 * @param codeLocation ����λ��ģʽ
	 * @param needStackInfo
	 */
	public static void addEnabled( String threadName, String codeLocation,
			boolean needStackInfo ) {
		addEnabled( new RTLocation( threadName, codeLocation ), needStackInfo );
	}

	/**
	 * ���Ѵ�̽����б���ɾ���������������ڵĻ���
	 * 
	 * @param rtl �����ܣ��������Ѵ��б��еĶ���
	 */
	public static void removeEnabled( RTLocation rtl ) {
		enabledProbes.remove( rtl );
	}

	/**
	 * ���Ѵ�̽����б���ɾ���������������ڵĻ���
	 * 
	 * @param threadName
	 * @param codeLocation
	 */
	public static void removeEnabled( String threadName, String codeLocation ) {
		removeEnabled( new RTLocation( threadName, codeLocation ) );
	}

	/**
	 * ���ص�ǰ���д򿪵�"̽���"����
	 * 
	 * @return [RTLocation - ProbeConfig]ӳ��ļ���
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map enabledProbes() {
		return Collections.unmodifiableMap( enabledProbes );
	}

	/**
	 * ������д򿪵Ŀ���
	 */
	public static void clearEnabled() {
		enabledProbes.clear();
	}
}
