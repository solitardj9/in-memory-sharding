package com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion;

import org.springframework.http.HttpStatus;

public class ExceptionShardingSeedNotFound extends Exception{
	
	private static final long serialVersionUID = 2290047580774752493L;
	
	private final int ERR_CODE;
	
	private final HttpStatus httpStatus;
	
	public ExceptionShardingSeedNotFound() {
		//
    	super(ExceptionShardingStrategyStatusCode.Resource_Not_Found.getMessage());
    	ERR_CODE = ExceptionShardingStrategyStatusCode.Resource_Not_Found.getCode();
    	httpStatus = ExceptionShardingStrategyStatusCode.Resource_Not_Found.getHttpStatus();
    }
    
	public ExceptionShardingSeedNotFound(Throwable cause) {
		//
		super(ExceptionShardingStrategyStatusCode.Resource_Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionShardingStrategyStatusCode.Resource_Not_Found.getCode();
		httpStatus = ExceptionShardingStrategyStatusCode.Resource_Not_Found.getHttpStatus();
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