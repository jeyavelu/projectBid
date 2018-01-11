package com.intuit.demo.projectbid.domain.errorhandling;

public class AppException extends Exception {

	private static final long serialVersionUID = -8999932578270387947L;
	
	/** 
	 * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
	 * the developer does not have to look into the response headers. If null a default 
	 */
	Integer status;
	
	/** application specific error code */
	int code; 
		
	/** link documenting the exception */	
	String link;
	
	/**
	 * 
	 * @param status
	 * @param code
	 * @param message
	 * @param link
	 */
	public AppException(int status, int code, String message, String link) {
		super(message);
		this.status = status;
		this.code = code;
		this.link = link;
	}
	
	public AppException(String message) {
		super(message);
	}
	
	public AppException(String message, String link, int code) {
		super(message);
		this.link = link;
		this.code = code;
	}

	public AppException() { }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
					
}
