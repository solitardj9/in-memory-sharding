package com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.service;

import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion.ExceptionShardingSeedNotFound;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion.ExceptionShardingStrategyManagerInternalFailure;

public interface ShardingStrategyManager {
	//
	public Double extractShardingSeed(String map, String key) throws ExceptionShardingSeedNotFound, ExceptionShardingStrategyManagerInternalFailure;
}