package com.soliatrdj9.imsd.systemInterface.imdgInterface.service;

import com.hazelcast.core.IMap;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.InMemoryInstane;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastDistributedObjectNameConflict;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapBadRequest;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapNotFound;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyClosed;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyOpened;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerConfigError;

public interface InMemoryDataNodeManager {
	//
	public Boolean startServer(String groupName, String nodeName) throws ExceptionHazelcastServerAlreadyOpened, ExceptionHazelcastServerConfigError;
	
	public Boolean stopServer() throws ExceptionHazelcastServerAlreadyClosed;
	
	public IMap<Object, Object> createMap(String map) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastDistributedObjectNameConflict;
	
	public IMap<Object, Object> createMap(InMemoryInstane inMemoryInstane) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastDistributedObjectNameConflict, ExceptionHazelcastIMapBadRequest;
	
	public IMap<Object, Object> getMap(String map) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastIMapNotFound;
	
	public void clearMap(String map) throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastIMapNotFound;
	
	public String getGroupName() throws ExceptionHazelcastServerAlreadyClosed, ExceptionHazelcastServerConfigError;
}