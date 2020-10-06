package com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.MapInfo;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.MapPartition;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapNotFound;
import com.soliatrdj9.imsd.application.mainNode.mapManager.service.MapManager;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion.ExceptionShardingSeedNotFound;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion.ExceptionShardingStrategyManagerInternalFailure;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.service.ShardingStrategyManager;

@Service("shardingStrategyManager")
public class ShardingStrategyManagerImpl implements ShardingStrategyManager {

	private static final Logger logger = LoggerFactory.getLogger(ShardingStrategyManagerImpl.class);
	
	@Autowired
	MapManager mapManager;
	
	private final static Double shardingSeedLimit = 1000.0;
	
	private final static Double seedLimit = 10.0;
	
	private static ObjectMapper om = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	@Override
	public Double extractShardingSeed(String map, String key) throws ExceptionShardingSeedNotFound, ExceptionShardingStrategyManagerInternalFailure {
		//
		Map<String, Object> keyMap = new HashMap<>();
		try {
			keyMap = om.readValue(key, Map.class);
		} catch (JsonProcessingException e) {
			logger.error("[ShardingStrategyManager].extractShardingSeed : error = "  + e);
			throw new ExceptionShardingStrategyManagerInternalFailure();
		}
		
		MapInfo mapInfo = null;
		try {
			mapInfo = mapManager.getMapInfo(map);
		} catch (ExceptionMapNotFound e) {
			logger.error("[ShardingStrategyManager].extractShardingSeed : error = "  + e);
			throw new ExceptionShardingSeedNotFound();
		}
		
		SortedSet<String> shardingKeys = new TreeSet<>();
		for (MapPartition mapPartition : mapInfo.getProperties().getPartitions()) {
			shardingKeys.add(mapPartition.getKey());
		}
		
		//
		Double count = 1.0;
		Double seed = null;
		for (String shardingKey : shardingKeys) {
			//
			if (keyMap.containsKey(shardingKey)) {
				Object value = keyMap.get(shardingKey);
				
				String strHashCode = String.valueOf(value.hashCode());
				
				Double tempSeed = normalizeSeed(Double.valueOf(strHashCode), count);
				
				if (seed == null)
					seed = tempSeed;
				else
					seed += tempSeed;
			}
			
			count += 1.0;
		}
		
		seed = normalizeShardingSeed(seed, count);
		
		return seed;
	}

	//
	private Double normalizeSeed(Double seed, Double numberOfDigits) {
		//
		return Math.pow(Math.abs(seed % seedLimit), numberOfDigits);
	}
	
	private Double normalizeShardingSeed(Double shardingSeed, Double numberOfDigits) {
		//
		Double retShardingSeed = 0.0;
		
		Double maxSeed = 0.0;
		for (Double i = 1.0 ; i < numberOfDigits ; ) {
			maxSeed += Math.pow(9.0, i);
			i += 1.0;
		}
		
		retShardingSeed = shardingSeed / maxSeed;
		retShardingSeed = Math.round(retShardingSeed * shardingSeedLimit) / shardingSeedLimit;
		
		return retShardingSeed;
	}
}