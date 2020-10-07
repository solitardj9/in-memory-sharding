package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionStartNodeInReplicatonModeFailure extends Exception{
    //
	private static final long serialVersionUID = -860166947104572494L;

	private final int errCode;
	
	private final String errMsg;
	
	private final HttpStatus httpStatus;
	
	public ExceptionStartNodeInReplicatonModeFailure() {
		//
    	super(ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getMessage());
    	errCode = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getCode();
    	errMsg = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getMessage();
    	httpStatus = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getHttpStatus();
    }
    
	public ExceptionStartNodeInReplicatonModeFailure(Throwable cause) {
		//
		super(ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getMessage(), cause);
		errCode = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getCode();
		errMsg = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getMessage();
		httpStatus = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Failure.getHttpStatus();
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