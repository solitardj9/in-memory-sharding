package com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion;

import org.springframework.http.HttpStatus;

public class ExceptionShardingStrategyManagerInternalFailure extends Exception{
	
	private static final long serialVersionUID = -4879539235477531865L;
	
	private final int ERR_CODE;
	
	private final HttpStatus httpStatus;
	
	public ExceptionShardingStrategyManagerInternalFailure() {
		//
    	super(ExceptionShardingStrategyStatusCode.Internal_Failure.getMessage());
    	ERR_CODE = ExceptionShardingStrategyStatusCode.Internal_Failure.getCode();
    	httpStatus = ExceptionShardingStrategyStatusCode.Internal_Failure.getHttpStatus();
    }
    
	public ExceptionShardingStrategyManagerInternalFailure(Throwable cause) {
		//
		super(ExceptionShardingStrategyStatusCode.Internal_Failure.getMessage(), cause);
		ERR_CODE = ExceptionShardingStrategyStatusCode.Internal_Failure.getCode();
		httpStatus = ExceptionShardingStrategyStatusCode.Internal_Failure.getHttpStatus();
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