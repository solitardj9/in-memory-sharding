package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionStartNodeInShardingModeConflict extends Exception{
    //
	private static final long serialVersionUID = 4316599319861004632L;

	private final int errCode;
	
	private final String errMsg;
	
	private final HttpStatus httpStatus;
	
	public ExceptionStartNodeInShardingModeConflict() {
		//
    	super(ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getMessage());
    	errCode = ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getCode();
    	errMsg = ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getMessage();
    	httpStatus = ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getHttpStatus();
    }
    
	public ExceptionStartNodeInShardingModeConflict(Throwable cause) {
		//
		super(ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getMessage(), cause);
		errCode = ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getCode();
		errMsg = ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getMessage();
		httpStatus = ExceptionMainNodeManagerCode.Start_Node_In_Sharding_Mode_Conflict.getHttpStatus();
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