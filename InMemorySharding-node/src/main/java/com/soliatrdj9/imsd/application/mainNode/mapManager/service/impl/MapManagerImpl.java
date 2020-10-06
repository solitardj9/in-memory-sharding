package com.soliatrdj9.imsd.application.mainNode.mapManager.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.EntryEvent;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.MapInfo;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapConflict;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapManagerInternalFailure;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapNotFound;
import com.soliatrdj9.imsd.application.mainNode.mapManager.service.MapManager;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryEventListener;

@Service("mapManager")
public class MapManagerImpl implements MapManager, InMemoryEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MapManagerImpl.class);

	private static ObjectMapper om = new ObjectMapper();
	
	// TODO : make a repository on local memory, next is on hybrid(hazelcast & h2 DB), change to In-Memory
	private Map<String/*map*/, MapInfo> mapInfos = new HashMap<>();
	
	@PostConstruct
	public void init() {
		//
		// 1) Read Data(DB) from DB
		// 2) Read Data(MEM) from In-Memory Cluster
		// 3) Compare
		// 4) if Data(MEM) is null, write Data(DB) to Data(MEM)
		// 5) if Data(MEM) is not null, and Data(DB) is not equal to Data(MEM), write Data(MEM) to Data(DB)
	}
	
	@Override
	public void addMapInfo(String strMapInfo) throws ExceptionMapConflict, ExceptionMapManagerInternalFailure {
		//
		// TODO : change to In-Memory
		try {
			MapInfo mapInfo = om.readValue(strMapInfo, MapInfo.class);
			
			if (mapInfos.containsKey(mapInfo.getMap())) {
				throw new ExceptionMapConflict();
			}
			
			mapInfos.put(mapInfo.getMap(), mapInfo);
		} catch (JsonProcessingException e) {
			logger.error("[MapManager].addMapInfo : error = " + e);
			throw new ExceptionMapManagerInternalFailure();
		}
	}
	
	@Override
	public void addMapInfo(MapInfo mapInfo)  throws ExceptionMapConflict {
		//
		// TODO : change to In-Memory
		if (mapInfos.containsKey(mapInfo.getMap())) {
			throw new ExceptionMapConflict();
		}
		
		mapInfos.put(mapInfo.getMap(), mapInfo);
	}

	@Override
	public MapInfo getMapInfo(String map) throws ExceptionMapNotFound {
		//
		// TODO : change to In-Memory
		if (!mapInfos.containsKey(map)) {
			throw new ExceptionMapNotFound();
		}
		
		return mapInfos.get(map);
	}

	@Override
	public void entryAdded(EntryEvent<Object, Object> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entryRemoved(EntryEvent<Object, Object> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entryUpdated(EntryEvent<Object, Object> event) {
		// TODO Auto-generated method stub
		
	}

}