package com.omnirio.assignment.user.exception;

public class ResourceAlreadyExists extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public ResourceAlreadyExists() {
	}
	
	public ResourceAlreadyExists(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
