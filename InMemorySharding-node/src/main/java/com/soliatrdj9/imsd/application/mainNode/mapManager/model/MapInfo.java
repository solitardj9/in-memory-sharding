package com.soliatrdj9.imsd.application.mainNode.mapManager.model;

import java.io.Serializable;

public class MapInfo implements Serializable {

	private static final long serialVersionUID = 81711396581753777L;
	
	private String map;
	
	private MapProperties properties;
	
	public MapInfo() {
		
	}

	public MapInfo(String map, MapProperties properties) {
		this.map = map;
		this.properties = properties;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public MapProperties getProperties() {
		return properties;
	}

	public void setProperties(MapProperties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "MapInfo [map=" + map + ", properties=" + properties + "]";
	}
}