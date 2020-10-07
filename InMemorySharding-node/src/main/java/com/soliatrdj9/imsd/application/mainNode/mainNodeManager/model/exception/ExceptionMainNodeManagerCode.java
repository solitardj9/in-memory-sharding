package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception;

import org.springframework.http.HttpStatus;


public enum ExceptionMainNodeManagerCode {
    //
	Internal_Failure(500, "InternalFailure.", HttpStatus.INTERNAL_SERVER_ERROR),
	Start_Node_In_Replicaton_Mode_Not_Found(404, "StartNodeInReplicatonModeTargetDatNodeIsNotFound.", HttpStatus.NOT_FOUND),
	Start_Node_In_Replicaton_Mode_Failure(500, "StartNodeInReplicatonModeFailure.", HttpStatus.INTERNAL_SERVER_ERROR),
	Stop_Node_In_Replicaton_Mode_Failure(500, "StopNodeInReplicatonModeFailure.", HttpStatus.INTERNAL_SERVER_ERROR),
	Start_Node_In_Sharding_Mode_Conflict(409, "StartNodeInShardingModeTargetDataNodeAlreadyExist.", HttpStatus.CONFLICT),
	Start_Node_In_Sharding_Mode_Failure(500, "StartNodeInShardingModeFailure.", HttpStatus.INTERNAL_SERVER_ERROR),
	Stop_Node_In_Sharding_Mode_Failure(500, "StopNodeInShardingModeFailure.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
 
    private Integer code;
    private String message;
    private HttpStatus httpStatus;
 
    ExceptionMainNodeManagerCode(Integer code, String msg, HttpStatus httpStatus) {
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