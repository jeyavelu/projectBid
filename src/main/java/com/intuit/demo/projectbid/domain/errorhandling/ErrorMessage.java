package com.intuit.demo.projectbid.domain.errorhandling;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
	
	/** contains the same HTTP Status code returned by the server */
	@JsonProperty("status")
	int status;
	
	/** application specific error code */
	@JsonProperty("errorCode")
	int code;
	
	/** message describing the error*/
	@JsonProperty("message")
	String message;
		
	/** link point to page where the error message is documented */
	@JsonProperty("link")
	String link;	

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public ErrorMessage(AppException ex){
		try {
			BeanUtils.copyProperties(this, ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ErrorMessage(NotFoundException ex){
		this.status = Response.Status.NOT_FOUND.getStatusCode();
		this.message = ex.getMessage();
		this.link = "https://jersey.java.net/apidocs/2.8/jersey/javax/ws/rs/NotFoundException.html";		
	}

	public ErrorMessage() {}
}