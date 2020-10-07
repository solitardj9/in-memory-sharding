package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionStopNodeInShardingModeFailure extends Exception{
    //
	private static final long serialVersionUID = -233665408893001167L;

	private final int errCode;
	
	private final String errMsg;
	
	private final HttpStatus httpStatus;
	
	public ExceptionStopNodeInShardingModeFailure() {
		//
    	super(ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getMessage());
    	errCode = ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getCode();
    	errMsg = ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getMessage();
    	httpStatus = ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getHttpStatus();
    }
    
	public ExceptionStopNodeInShardingModeFailure(Throwable cause) {
		//
		super(ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getMessage(), cause);
		errCode = ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getCode();
		errMsg = ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getMessage();
		httpStatus = ExceptionMainNodeManagerCode.Stop_Node_In_Sharding_Mode_Failure.getHttpStatus();
	}
	
	public int getErrCode() {
		//
		return errCode;
    }
	
	public String getErrMsg() {
		//
		return errMsg;
	}
	
	public HttpStatus getHttpStatus() {
		//
		return httpStatus;
    }
}