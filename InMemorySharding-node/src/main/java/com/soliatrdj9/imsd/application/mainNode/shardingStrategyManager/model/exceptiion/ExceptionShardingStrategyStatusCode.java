package com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion;

import org.springframework.http.HttpStatus;


public enum ExceptionShardingStrategyStatusCode {
    //
	Resource_Not_Found(404, "ShardingSeedNotFoundException.", HttpStatus.NOT_FOUND),
	Internal_Failure(500, "InternalFailureException.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
 
    private Integer code;
    private String message;
    private HttpStatus httpStatus;
 
    ExceptionShardingStrategyStatusCode(Integer code, String msg, HttpStatus httpStatus) {
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