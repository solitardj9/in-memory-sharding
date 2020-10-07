package com.soliatrdj9.imsd.service.serviceManager.service;

import java.util.Map;

import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNodeGroup;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeNotFound;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeConflict;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeFailure;

public interface ServiceManager {
	//
	public void startService() throws ExceptionStartNodeInReplicatonModeFailure, ExceptionStartNodeInReplicatonModeNotFound;
	
	public void stopService();
	
	public void scaleOut(Float criteriaHashSeed) throws ExceptionStartNodeInShardingModeFailure, ExceptionStartNodeInShardingModeConflict;
	
	public void scaleIn();
	
	public Map<String, DataNodeGroup> getServiceTopology();
}