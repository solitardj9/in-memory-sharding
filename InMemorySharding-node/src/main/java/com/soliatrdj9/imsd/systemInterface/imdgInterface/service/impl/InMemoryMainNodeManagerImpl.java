package com.soliatrdj9.imsd.systemInterface.imdgInterface.service.impl;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.LockConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MemberAttributeConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.InMemoryInstane;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastDistributedObjectNameConflict;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapBadRequest;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapNotFound;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyClosed;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyOpened;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerConfigError;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryMainNodeManager;

@Service("inMemoryMainNodeManager")
public class InMemoryMainNodeManagerImpl implements InMemoryMainNodeManager {
	//
	private static final Logger logger = LoggerFactory.getLogger(InMemoryMainNodeManagerImpl.class);
	
	private static HazelcastInstance hazelcastInstance = null;
	
	private static Config config = null; 
	
	private String configPath = "config/hazelcast-main.xml";

	@PostConstruct
	public void init() {
		//
//		try {
//			start();
//		} catch (FileNotFoundException e) {
//			logger.info("[InMemoryMainNodeManager].init : error = " + e);
//		}
		
		logger.info("[InMemoryMainNodeManager].init : Hazelcast Server is loaded.");
	}
	
	private void start() throws FileNotFoundException {
		//
		logger.info("[InMemoryMainNodeManager].start : Hazelcast Server try to start.");
		
		try {
			config = new FileSystemXmlConfig(configPath);
		} catch (Exception e) {
			logger.error("[InMemoryMainNodeManager].start : error = " + e);
			throw new FileNotFoundException();
		}
		
		hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		logger.info("[InMemoryMainNodeManager].start : Hazelcast Server start.");
		
		Set<Member> members = hazelcastInstance.getCluster().getMembers();
		if (members!=null && !members.isEmpty()) {
			for(Member member : members) {
				logger.info("[InMemoryMainNodeManager].start : Hazelcast Server member = " + member);
			}
		}
	}
	
	private void stop() {
    	//
		if (hazelcastInstance != null) {
			hazelcastInstance.shutdown();
			hazelcastInstance = null;
			
			logger.info("[InMemoryMainNodeManager].stop : Hazelcast Server is stop.");
		}
	}
	
	@Override
	public Boolean startServer() throws ExceptionHazelcastServerAlreadyOpened, ExceptionHazelcastServerConfigError {
		//
		if (hazelcastInstance != null)
			throw new ExceptionHazelcastServerAlreadyOpened();
		
		try {
			start();
			return true;
		} catch (FileNotFoundException e) {
			logger.error("[InMemoryMainNodeManager].startServer : error = " + e);
    		throw new ExceptionHazelcastServerConfigError();
		}
	}

	@Override
	public Boolean stopServer() throws ExceptionHazelcastServerAlreadyClosed {
		//
		if (hazelcastInstance == null)
			throw new ExceptionHazelcastServerAlreadyClosed();
		
		stop();
		
		return true;
	}
	
	@Override
	public IMap<Object, Object> createMap(String map) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastDistributedObjectNameConflict {
		//
		if (hazelcastInstance == null)
			throw new ExceptionHazelcastServerAlreadyClosed();
		
		if (isExistNameWithOtherDistributedObject(map))
			throw new ExceptionHazelcastDistributedObjectNameConflict();
		
		return hazelcastInstance.getMap(map);
	}
	
	@Override
	public IMap<Object, Object> createMap(InMemoryInstane inMemoryInstane) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastDistributedObjectNameConflict, ExceptionHazelcastIMapBadRequest {
		//
		String map = inMemoryInstane.getName();
		
		if (map == null || map.isEmpty())
			throw new ExceptionHazelcastIMapBadRequest();
		
		if (hazelcastInstance == null)
			throw new ExceptionHazelcastServerAlreadyClosed();
		
		if (isExistNameWithOtherDistributedObject(map))
			throw new ExceptionHazelcastDistributedObjectNameConflict();
		
		MapConfig mapConfig = new MapConfig();
		
		mapConfig.setName(map);
		mapConfig.setBackupCount(inMemoryInstane.getBackupCount());        // sync backup
		mapConfig.setReadBackupData(inMemoryInstane.getReadBackupData());
		
		hazelcastInstance.getConfig().addMapConfig(mapConfig);
		
		if (inMemoryInstane.getLockName() != null && !inMemoryInstane.getLockName().isEmpty()) {
			LockConfig lockConfig = new LockConfig();
			lockConfig.setName(inMemoryInstane.getLockName()).setQuorumName("quorum-name");
			hazelcastInstance.getConfig().addLockConfig(lockConfig);
		}
		
		if (inMemoryInstane.getEventListener() != null)
			hazelcastInstance.getMap(map).addEntryListener(inMemoryInstane.getEventListener(), true);
		
		return hazelcastInstance.getMap(map);
	}

	@Override
	public IMap<Object, Object> getMap(String map) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastIMapNotFound {
		//
		if (hazelcastInstance == null)
			throw new ExceptionHazelcastServerAlreadyClosed();
		
		if (isExistMap(map))
			return hazelcastInstance.getMap(map);
		else
			throw new ExceptionHazelcastIMapNotFound();
	}
	
	private Boolean isExistMap(String map) {
		//
		Collection<DistributedObject> distributedObjects = hazelcastInstance.getDistributedObjects();
		
		for (DistributedObject distributedObject : distributedObjects) {
			//
			if (distributedObject.getName().equals(map) && distributedObject.toString().startsWith("IMap")) {
				return true;
			}
		}
		
		return false;
	}
	
	private Boolean isExistNameWithOtherDistributedObject(String map) {
		//
		Collection<DistributedObject> distributedObjects = hazelcastInstance.getDistributedObjects();
		
		for (DistributedObject distributedObject : distributedObjects) {
			//
			if (distributedObject.getName().equals(map)) {
				if (!distributedObject.toString().startsWith("IMap"))
					return true;
			}
		}
		
		return false;
	}
	
	public void clearMap(String map) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastIMapNotFound {
		//
		if (hazelcastInstance == null)
			throw new ExceptionHazelcastServerAlreadyClosed();
		
		if (isExistMap(map))
			hazelcastInstance.getMap(map).clear();
		else
			throw new ExceptionHazelcastIMapNotFound();
	}
}