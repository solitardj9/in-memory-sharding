package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionStartNodeInReplicatonModeNotFound extends Exception{
    //
	private static final long serialVersionUID = 4595727063188490248L;

	private final int errCode;
	
	private final String errMsg;
	
	private final HttpStatus httpStatus;
	
	public ExceptionStartNodeInReplicatonModeNotFound() {
		//
    	super(ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getMessage());
    	errCode = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getCode();
    	errMsg = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getMessage();
    	httpStatus = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getHttpStatus();
    }
    
	public ExceptionStartNodeInReplicatonModeNotFound(Throwable cause) {
		//
		super(ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getMessage(), cause);
		errCode = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getCode();
		errMsg = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getMessage();
		httpStatus = ExceptionMainNodeManagerCode.Start_Node_In_Replicaton_Mode_Not_Found.getHttpStatus();
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