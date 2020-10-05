package com.soliatrdj9.imsd.application.mainNode.mapManager.service.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.MapInfo;
import com.soliatrdj9.imsd.application.mainNode.mapManager.service.MapManager;

@Service("mapManager")
public class MapManagerImpl implements MapManager {

	private static ObjectMapper om = new ObjectMapper();
	
	@Override
	public void addMapInfo(String strMapInfo) {
		//
		MapInfo mapInfo = null;
		try {
			mapInfo = om.readValue(strMapInfo, MapInfo.class);
			System.out.println(mapInfo.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}