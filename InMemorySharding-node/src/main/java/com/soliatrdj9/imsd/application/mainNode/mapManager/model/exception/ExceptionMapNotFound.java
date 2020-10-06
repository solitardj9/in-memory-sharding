package com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionMapNotFound extends Exception{
	
	private static final long serialVersionUID = 2290047580774752493L;
	
	private final int ERR_CODE;
	
	private final HttpStatus httpStatus;
	
	public ExceptionMapNotFound() {
		//
    	super(ExceptionMapStatusCode.Resource_Not_Found.getMessage());
    	ERR_CODE = ExceptionMapStatusCode.Resource_Not_Found.getCode();
    	httpStatus = ExceptionMapStatusCode.Resource_Not_Found.getHttpStatus();
    }
    
	public ExceptionMapNotFound(Throwable cause) {
		//
		super(ExceptionMapStatusCode.Resource_Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionMapStatusCode.Resource_Not_Found.getCode();
		httpStatus = ExceptionMapStatusCode.Resource_Not_Found.getHttpStatus();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
	
	public HttpStatus getHttpStatus() {
		//
		return httpStatus;
    }
}