package com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionMapConflict extends Exception{
    //
	private static final long serialVersionUID = -3134830424775406028L;
	
	private final int ERR_CODE;
	
	private final HttpStatus httpStatus;
	
	public ExceptionMapConflict() {
		//
    	super(ExceptionMapStatusCode.Conflict.getMessage());
    	ERR_CODE = ExceptionMapStatusCode.Conflict.getCode();
    	httpStatus = ExceptionMapStatusCode.Conflict.getHttpStatus();
    }
    
	public ExceptionMapConflict(Throwable cause) {
		//
		super(ExceptionMapStatusCode.Conflict.getMessage(), cause);
		ERR_CODE = ExceptionMapStatusCode.Conflict.getCode();
		httpStatus = ExceptionMapStatusCode.Conflict.getHttpStatus();
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