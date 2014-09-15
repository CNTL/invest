package com.tl.db.exception;

/**
 * ͨ��DBSession.executeUpdate������������bfile����oracle��clob/blob����Ҫ��ִ���ֶθ��£��ٽ������ݴ�����������ͣ�ʱ�������ݴ�������г������׳����쳣
 * @author Guangyong
 *
 */
public class LaterDataTransferException extends RuntimeException {

	private static final long serialVersionUID = 1924615117114641271L;

	/**
	 * 
	 */
	public LaterDataTransferException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LaterDataTransferException( String message, Throwable cause ) {
		super( message, cause );
	}

	/**
	 * @param message
	 */
	public LaterDataTransferException( String message ) {
		super( message );
	}

	/**
	 * @param cause
	 */
	public LaterDataTransferException( Throwable cause ) {
		super( cause );
	}

}
