package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception;

import org.springframework.http.HttpStatus;

public class ExceptionStopNodeInReplicatonModeFailure extends Exception{
    //
	private static final long serialVersionUID = -5899863841854510013L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	private final String errMsg;
	
	public ExceptionStopNodeInReplicatonModeFailure() {
		//
    	super(ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getMessage());
    	errCode = ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getCode();
    	errMsg = ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getMessage();
    	httpStatus = ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getHttpStatus();
    }
    
	public ExceptionStopNodeInReplicatonModeFailure(Throwable cause) {
		//
		super(ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getMessage(), cause);
		errCode = ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getCode();
		errMsg = ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getMessage();
		httpStatus = ExceptionMainNodeManagerCode.Stop_Node_In_Replicaton_Mode_Failure.getHttpStatus();
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