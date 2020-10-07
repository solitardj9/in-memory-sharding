package com.soliatrdj9.imsd.service.serviceManager.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNodeGroup;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeNotFound;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeConflict;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.MainNodeManager;
import com.soliatrdj9.imsd.service.serviceManager.service.ServiceManager;

@Service("serviceManager")
public class ServiceManagerImpl implements ServiceManager {

	private static final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);
	
	@Value("${spring.application.name}")
	private String groupName;
	
	@Value("${service.serviceManager.name}")
	private String nodeName;
	
	@Autowired
	MainNodeManager mainNodeManager;
	
	@Override
	public void startService() throws ExceptionStartNodeInReplicatonModeFailure, ExceptionStartNodeInReplicatonModeNotFound {
		//
		try {
			mainNodeManager.startNodeInReplicatonMode();
		} catch (ExceptionStartNodeInReplicatonModeFailure | ExceptionStartNodeInReplicatonModeNotFound e) {
			logger.error("[ServiceManager].startService : error = " + e);
			throw e;
		}
	}

	@Override
	public void stopService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scaleOut(Float criteriaHashSeed) throws ExceptionStartNodeInShardingModeFailure, ExceptionStartNodeInShardingModeConflict {
		// TODO Auto-generated method stub
		try {
			mainNodeManager.startNodeInShardingMode(criteriaHashSeed);
		} catch (ExceptionStartNodeInShardingModeFailure | ExceptionStartNodeInShardingModeConflict e) {
			logger.error("[ServiceManager].scaleOut : error = " + e);
			throw e;
		}
	}

	@Override
	public void scaleIn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Map<String, DataNodeGroup> getServiceTopology() {
		//
		return mainNodeManager.getTopology();
	}
	
}