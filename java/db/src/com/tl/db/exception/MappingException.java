package com.tl.db.exception;

public class MappingException extends RuntimeException {
	private static final long serialVersionUID = -3213956285338990890L;

	/**
	 * @param message
	 */
	public MappingException( String message ) {
		super( message );
	}
}
