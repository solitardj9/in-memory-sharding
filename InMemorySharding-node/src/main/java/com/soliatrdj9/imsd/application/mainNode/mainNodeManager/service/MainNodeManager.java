package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service;

import java.util.Map;

import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNodeGroup;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeNotFound;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeConflict;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStopNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStopNodeInShardingModeFailure;

public interface MainNodeManager {
	//
	public DataNodeGroup getOwnDataNodeGroup();
	
	public Map<String, DataNodeGroup> getTopology();
	
	public void startNodeInReplicatonMode() throws ExceptionStartNodeInReplicatonModeFailure, ExceptionStartNodeInReplicatonModeNotFound;
	
	public void stopNodeInReplicatonMode() throws ExceptionStopNodeInReplicatonModeFailure;
	
	public void startNodeInShardingMode(Float criteriaHashSeed) throws ExceptionStartNodeInShardingModeFailure, ExceptionStartNodeInShardingModeConflict;
	
	public void stopNodeInShardingMode() throws ExceptionStopNodeInShardingModeFailure;
	
	
	
	
	
	
	
	
	public String/*spring.application.name*/ getDataNodeName(String map, String seed);
	
	// TODO : need to upgrade
	public void needToRedistribute();
	
	// TODO : need to upgrade
}