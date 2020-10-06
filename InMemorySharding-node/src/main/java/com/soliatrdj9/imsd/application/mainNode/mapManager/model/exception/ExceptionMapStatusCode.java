package com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception;

import org.springframework.http.HttpStatus;


public enum ExceptionMapStatusCode {
    //
	Resource_Not_Found(404, "MapNotFoundException.", HttpStatus.NOT_FOUND),
	Conflict (409, "MapConflictException.", HttpStatus.CONFLICT),
	Internal_Failure(500, "InternalFailureException.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
 
    private Integer code;
    private String message;
    private HttpStatus httpStatus;
 
    ExceptionMapStatusCode(Integer code, String msg, HttpStatus httpStatus) {
        this.code = code;
        this.message = msg;
        this.httpStatus = httpStatus;
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}