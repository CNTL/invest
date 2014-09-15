package com.tl.kernel.context;

public class TLException extends Exception {
	private static final long serialVersionUID = 1L;
	private int errorCode;
	
	public TLException(String message)
	{
		super(message);
	}

	public TLException(Throwable cause)
	{
		super(cause);
	}

	public TLException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public TLException(int _errorCode, String message)
	{
		super(message);
		errorCode = _errorCode;
	}

	public TLException(int _errorCode, Throwable cause)
	{
		super(cause);
		errorCode = _errorCode;
	}

	public TLException(int _errorCode, String message, Throwable cause)
	{
		super(message, cause);
		errorCode = _errorCode;
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}
	
	public String toString(){
		return errorCode + super.toString();
	}
}
