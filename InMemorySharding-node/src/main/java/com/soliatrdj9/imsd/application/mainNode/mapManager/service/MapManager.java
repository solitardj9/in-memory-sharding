package com.soliatrdj9.imsd.application.mainNode.mapManager.service;

import java.util.Map;

import com.soliatrdj9.imsd.application.mainNode.mapManager.model.MapInfo;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapConflict;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapManagerInternalFailure;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapNotFound;

public interface MapManager {
	//
	public void addMapInfo(String strMapInfo) throws ExceptionMapConflict, ExceptionMapManagerInternalFailure;
	
	public void addMapInfo(MapInfo mapInfo) throws ExceptionMapConflict;
	
	public MapInfo getMapInfo(String map) throws ExceptionMapNotFound;
	
	public Map<String/*map*/, MapInfo> getMapInfos();
}