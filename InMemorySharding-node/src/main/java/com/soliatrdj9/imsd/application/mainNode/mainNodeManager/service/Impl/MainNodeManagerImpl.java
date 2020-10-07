package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.Impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hazelcast.core.EntryEvent;
import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.model.BackupAndRestoreEvent;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNode;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNodeGroup;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.HashSeedInfo;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeNotFound;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeConflict;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStopNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStopNodeInShardingModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.MainNodeManager;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.data.MainNodeManagerParams.MainNodeMapParamEnum;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.data.ReDistributionStrategy;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.InMemoryInstane;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastDistributedObjectNameConflict;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapBadRequest;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapNotFound;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyClosed;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyOpened;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerConfigError;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryDataNodeManager;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryEventListener;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryMainNodeManager;

@Service("dataNodeManager")
public class MainNodeManagerImpl implements MainNodeManager, InMemoryEventListener {
	//
	private static final Logger logger = LoggerFactory.getLogger(MainNodeManagerImpl.class);
	
	@Autowired
	InMemoryMainNodeManager inMemoryMainNodeManager;
	
	@Autowired
	InMemoryDataNodeManager inMemoryDataNodeManager;
	
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@Value("${application.mainNodeManager.topology.map.backupCount}")
	private Integer backupCount;

	@Value("${spring.application.name}")
	private String groupName;
	
	@Value("${service.serviceManager.name}")
	private String nodeName;
	
	private DataNodeGroup ownDataNodeGroup;
	
	private Map<String, DataNodeGroup> topologyMap = new ConcurrentHashMap<>();
	
	@Override
	public DataNodeGroup getOwnDataNodeGroup() {
		//
		return ownDataNodeGroup;
	}
	
	@Override
	public Map<String, DataNodeGroup> getTopology() {
		//
		return new HashMap<String, DataNodeGroup>(topologyMap);
	}
	
	@Override
	public void startNodeInReplicatonMode() throws ExceptionStartNodeInReplicatonModeFailure, ExceptionStartNodeInReplicatonModeNotFound {
		// 
		try {
			inMemoryMainNodeManager.startServer();
			
			inMemoryDataNodeManager.startServer(groupName, nodeName);
			
			createTopologyMap();
			
			try {
				addDataNodeInReplicationModeToTopology();
			} catch (ExceptionStartNodeInReplicatonModeNotFound e) {
				//
				inMemoryMainNodeManager.stopServer();
				
				inMemoryDataNodeManager.stopServer();
				
				logger.error("[MainNodeManager].startNodeInReplicatonMode : error = " + e);
				throw e;
			}
			
			topologyMap = readTopologyMap();
			
			setOwnDataNodeGroup();
			
		} catch (ExceptionHazelcastServerAlreadyOpened | ExceptionHazelcastServerConfigError | ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastDistributedObjectNameConflict | ExceptionHazelcastIMapBadRequest | ExceptionHazelcastIMapNotFound e) {
			logger.error("[MainNodeManager].startNodeInReplicatonMode : error = " + e);
			throw new ExceptionStartNodeInReplicatonModeFailure();
		}
	}
	
	@Override
	public void stopNodeInReplicatonMode() throws ExceptionStopNodeInReplicatonModeFailure {
		// TODO : 
	}
	
	@Override
	public void startNodeInShardingMode(Float criteriaHashSeed) throws ExceptionStartNodeInShardingModeFailure, ExceptionStartNodeInShardingModeConflict {
		//
		try {
			inMemoryMainNodeManager.startServer();
			
			inMemoryDataNodeManager.startServer(groupName, nodeName);
			
			createTopologyMap();
			
			Map<String, DataNodeGroup> tmpTopologyMap = readTopologyMap();
			
			// If same group name exists already, conflict error
			if (isExistGroupAtTopologyMap()) {
				throw new ExceptionStartNodeInShardingModeConflict();
			}
			
			// calculate Hash Seed Info
			ReDistributionStrategy reDistributionStrategy = calculateHashSeed(criteriaHashSeed, tmpTopologyMap);
			logger.info("[MainNodeManager].startNodeInShardingMode : reDistributionStrategy = " + reDistributionStrategy.toString());
			
			// backupAndRestore
			backupAndRestore(reDistributionStrategy);
			
			// TODO : 
			// add data node to topology
			try {
				addDataNodeInShardingModeToTopology();
			} catch (ExceptionStartNodeInShardingModeConflict e) {
				logger.error("[MainNodeManager].startNodeInShardingMode : error = " + e);
				throw e;
			}
			
			topologyMap = readTopologyMap();
			
			setOwnDataNodeGroup();
			
		} catch (ExceptionHazelcastServerAlreadyOpened | ExceptionHazelcastServerConfigError | ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastDistributedObjectNameConflict | ExceptionHazelcastIMapBadRequest | ExceptionHazelcastIMapNotFound e) {
			logger.error("[MainNodeManager].startNodeInShardingMode : error = " + e);
			throw new ExceptionStartNodeInShardingModeFailure();
		}
	}
	
	@Override
	public void stopNodeInShardingMode() throws ExceptionStopNodeInShardingModeFailure {
		// TODO : 
	}
	
	
	
	
	
	
	
	private void createTopologyMap() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastDistributedObjectNameConflict, ExceptionHazelcastIMapBadRequest {
		//
		InMemoryInstane inMemoryInstane = new InMemoryInstane();

		inMemoryInstane.setName(MainNodeMapParamEnum.TOPOLOGY_MAP.getType());
		inMemoryInstane.setBackupCount(backupCount);
		inMemoryInstane.setReadBackupData(true);
		inMemoryInstane.setEventListener(this);
		
		inMemoryMainNodeManager.createMap(inMemoryInstane);
	}
	
	private void addDataNodeInReplicationModeToTopology() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastServerConfigError, ExceptionHazelcastIMapNotFound, ExceptionStartNodeInReplicatonModeNotFound {
		//
		DataNodeGroup dataNodeGroup = (DataNodeGroup)inMemoryMainNodeManager.getMap(MainNodeMapParamEnum.TOPOLOGY_MAP.getType()).get(groupName);
		
		if (dataNodeGroup == null) {
			throw new ExceptionStartNodeInReplicatonModeNotFound();
		}
		else {
			dataNodeGroup.getDataNodes().add(new DataNode(nodeName));
		}
		inMemoryMainNodeManager.getMap(MainNodeMapParamEnum.TOPOLOGY_MAP.getType()).put(groupName, dataNodeGroup);
				
	}
	
	private Map<String, DataNodeGroup> readTopologyMap() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastIMapNotFound, ExceptionHazelcastServerConfigError {
		//
		Map<Object, Object> resultMap = inMemoryMainNodeManager.getMap(MainNodeMapParamEnum.TOPOLOGY_MAP.getType());
		
		Map<String, DataNodeGroup> topologyMap = new ConcurrentHashMap<>();
		for (Entry<Object, Object> iter : resultMap.entrySet()) {
			//
			topologyMap.put((String)iter.getKey(), (DataNodeGroup)iter.getValue());
		}
		
		return topologyMap;
	}
	
	private void setOwnDataNodeGroup() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastServerConfigError {
		//
		ownDataNodeGroup = topologyMap.get(groupName);
	}
	
	private Boolean isExistGroupAtTopologyMap() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastServerConfigError, ExceptionHazelcastIMapNotFound {
		//
		if (inMemoryMainNodeManager.getMap(MainNodeMapParamEnum.TOPOLOGY_MAP.getType()).containsKey(groupName)) {
			return true;
		}
		else
			return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReDistributionStrategy calculateHashSeed(Float criteriaHashSeed, Map<String, DataNodeGroup> topologyMap) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastServerConfigError, ExceptionHazelcastIMapNotFound {
		//
		if (topologyMap.isEmpty()) {
			// empty topology, first group
			HashSeedInfo hashSeedInfo = new HashSeedInfo(0.0f, 1.0f);
			
			CopyOnWriteArrayList<DataNode> dataNodes = new CopyOnWriteArrayList<>();
			dataNodes.add(new DataNode(nodeName));
			
			DataNodeGroup dataNodeGroup = new DataNodeGroup(groupName, hashSeedInfo, dataNodes);
			
			inMemoryMainNodeManager.getMap(MainNodeMapParamEnum.TOPOLOGY_MAP.getType()).put(groupName, dataNodeGroup);
			
			return new ReDistributionStrategy(null, null);
		}
		else {
			// not empty topology, added group
			if (criteriaHashSeed == null) {
				Map<Float/*diff*/, Map<Float/*min*/, DataNodeGroup>> sortedMap = new TreeMap<>(Collections.reverseOrder());
				
				for (Entry<String, DataNodeGroup> iter : topologyMap.entrySet()) {
					//
					DataNodeGroup tmpDataNodeGroup = iter.getValue();
					Float tmpMaxSeed = tmpDataNodeGroup.getHashSeedInfo().getMaxSeed();
					Float tmpMinSeed = tmpDataNodeGroup.getHashSeedInfo().getMinSeed();
					Float diffSeed = tmpMaxSeed - tmpMinSeed;
					
					Map<Float, DataNodeGroup> dataNodeGroupMap = sortedMap.getOrDefault(diffSeed, new TreeMap<Float, DataNodeGroup>());
					dataNodeGroupMap.put(tmpMinSeed, tmpDataNodeGroup);
					
					sortedMap.put(diffSeed, dataNodeGroupMap);
				}
			
				logger.info("[MainNodeManager].calculateHashSeed : sortedMap = " + sortedMap.toString());
				
				Set set = sortedMap.entrySet();
			    Iterator iterator = set.iterator();
			    DataNodeGroup targetDataNodeGroup = null;
			    if (iterator.hasNext()) {
			    	Map.Entry me = (Map.Entry)iterator.next();
			    	targetDataNodeGroup = ((TreeMap<Float, DataNodeGroup>) me.getValue()).firstEntry().getValue();
			    
				    Float tmpMaxSeed = targetDataNodeGroup.getHashSeedInfo().getMaxSeed();
					Float tmpMinSeed = targetDataNodeGroup.getHashSeedInfo().getMinSeed();
					
					criteriaHashSeed = (tmpMaxSeed + tmpMinSeed) / 2.0f;
					
					Float newMaxSeedOfTargetDataNodeGroup = criteriaHashSeed - 0.001f;
					Float newMinSeedOfTargetDataNodeGroup = tmpMinSeed;
					
					DataNodeGroup fromDataNodeGroup = new DataNodeGroup(targetDataNodeGroup.getGroupName(), new HashSeedInfo(newMinSeedOfTargetDataNodeGroup, newMaxSeedOfTargetDataNodeGroup), null);
					
					Float newMaxSeedOfMyDataNodeGroup = tmpMaxSeed;
					Float newMinSeedOfMyDataNodeGroup = criteriaHashSeed;
					
					CopyOnWriteArrayList<DataNode> dataNodes = new CopyOnWriteArrayList<>();
					dataNodes.add(new DataNode(nodeName));
					
					DataNodeGroup toDataNodeGroup = new DataNodeGroup(groupName, new HashSeedInfo(newMinSeedOfMyDataNodeGroup, newMaxSeedOfMyDataNodeGroup), dataNodes);
					
					return new ReDistributionStrategy(fromDataNodeGroup, toDataNodeGroup);
			    }
			    
			    return new ReDistributionStrategy(null, null);
			}
			else {
				// TODO : 
				return null;
			}
		}
	}
	
	private void backupAndRestore(ReDistributionStrategy reDistributionStrategy) {
		//
		// TODO : BackupAndRestoreEvent 재구성, BackupAndRestoreManager 내부에서 IPC 이용해 호출 
		if (reDistributionStrategy.getFromDataNodeGroup() != null) {
			//
			String fromGroupName = reDistributionStrategy.getFromDataNodeGroup().getGroupName();
			Float fromGroupMaxSeed = reDistributionStrategy.getFromDataNodeGroup().getHashSeedInfo().getMaxSeed();
			Float fromGroupMinSeed = reDistributionStrategy.getFromDataNodeGroup().getHashSeedInfo().getMinSeed();
			
			String toGroupName = reDistributionStrategy.getToDataNodeGroup().getGroupName();
			Float toGroupMaxSeed = reDistributionStrategy.getToDataNodeGroup().getHashSeedInfo().getMaxSeed();
			Float toGroupMinSeed = reDistributionStrategy.getToDataNodeGroup().getHashSeedInfo().getMinSeed();
			
			BackupAndRestoreEvent backupAndRestoreEvent = new BackupAndRestoreEvent(fromGroupName, fromGroupMaxSeed, fromGroupMinSeed, toGroupName, toGroupMaxSeed, toGroupMinSeed);
			
			applicationEventPublisher.publishEvent(backupAndRestoreEvent);
		}
	}
	
	private void addDataNodeInShardingModeToTopology() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastServerConfigError, ExceptionHazelcastIMapNotFound, ExceptionStartNodeInShardingModeConflict {
		// TODO :
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// TODO : data node management
	// TODO : make a repository on local memory, next is on hybrid(hazelcast & h2 DB), change to In-Memory
	// need to analyze node restart & node scale out(O)/in(X)
	// when node is scaled out(O)/in(X), then new node requests re-distribute seed info and data --> a/f request, other nodes push data to new node according to new seed infos
	//   --> some node receives scale out event by entryRemoved and lock to re-distribute as 
	//   --> this node iterate other nodes to notify pushing data to new node.
	//       notify by members in hazelcast? Topic / Reliable Topic(Support Backup)
	//       	https://dzone.com/articles/publish-and-subscribe
	//		 	https://supawer0728.github.io/2018/03/11/hazelcast/
	
	
	@Override
	public String getDataNodeName(String map, String seed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void entryAdded(EntryEvent<Object, Object> event) {
		//
		try {
			topologyMap = readTopologyMap();
			
			setOwnDataNodeGroup();
			
		} catch (ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastIMapNotFound | ExceptionHazelcastServerConfigError e) {
			logger.error("[MainNodeManager].entryAdded : error = " + e);
		}
	}

	@Override
	public void entryRemoved(EntryEvent<Object, Object> event) {
		//
		try {
			topologyMap = readTopologyMap();
			
			setOwnDataNodeGroup();
			
		} catch (ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastIMapNotFound | ExceptionHazelcastServerConfigError e) {
			logger.error("[MainNodeManager].entryAdded : error = " + e);
		}
	}

	@Override
	public void entryUpdated(EntryEvent<Object, Object> event) {
		//
		try {
			topologyMap = readTopologyMap();
			
			setOwnDataNodeGroup();
			
		} catch (ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastIMapNotFound | ExceptionHazelcastServerConfigError e) {
			logger.error("[MainNodeManager].entryAdded : error = " + e);
		}
	}
	
	@Override
	public void needToRedistribute() {
		// TODO Auto-generated method stub
		
	}

}