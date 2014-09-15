package com.tl.db.exception;

public class BfileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BfileException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BfileException( String message, Throwable cause ) {
		super( message, cause );
	}

	/**
	 * @param message
	 */
	public BfileException( String message ) {
		super( message );
	}

	/**
	 * @param cause
	 */
	public BfileException( Throwable cause ) {
		super( cause );
	}

}