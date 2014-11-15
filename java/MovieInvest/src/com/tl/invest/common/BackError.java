package com.tl.invest.common;

public class BackError {
	private long docId;
	private String code;
	private String message;
	
	@SuppressWarnings("unused")
	private BackError() {
		super();
	}
	public BackError(long docId,String code,String message){
		this.docId = docId;
		this.message = message;
		this.code = code;
	}
	public BackError(long docId,String message){
		this.docId = docId;
		this.message = message;
		this.code = "";
	}
	
	public BackError(String code,String message){
		this.docId = 0;
		this.message = message;
		this.code = code;
	}
	
	public BackError(String message){
		this.docId = 0;
		this.message = message;
		this.code = "";
	}
	
	/**
	 * @return the docId
	 */
	public long getDocId() {
		return docId;
	}
	/**
	 * @param docId the docId to set
	 */
	public void setDocId(long docId) {
		this.docId = docId;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
