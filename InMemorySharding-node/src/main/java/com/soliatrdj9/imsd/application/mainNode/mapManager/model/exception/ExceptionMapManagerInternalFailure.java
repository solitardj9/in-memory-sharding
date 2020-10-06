package com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionMapManagerInternalFailure extends Exception{
	
	private static final long serialVersionUID = -4879539235477531865L;
	
	private final int ERR_CODE;
	
	private final HttpStatus httpStatus;
	
	public ExceptionMapManagerInternalFailure() {
		//
    	super(ExceptionMapStatusCode.Internal_Failure.getMessage());
    	ERR_CODE = ExceptionMapStatusCode.Internal_Failure.getCode();
    	httpStatus = ExceptionMapStatusCode.Internal_Failure.getHttpStatus();
    }
    
	public ExceptionMapManagerInternalFailure(Throwable cause) {
		//
		super(ExceptionMapStatusCode.Internal_Failure.getMessage(), cause);
		ERR_CODE = ExceptionMapStatusCode.Internal_Failure.getCode();
		httpStatus = ExceptionMapStatusCode.Internal_Failure.getHttpStatus();
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