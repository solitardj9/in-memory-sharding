package com.soliatrdj9.imsd.application.mainNode.mapManager.model;

import java.io.Serializable;

public class MapPartition implements Serializable {

	private static final long serialVersionUID = 4380875341012989067L;

	private String key;
	
	private String type;
	
	public MapPartition() {
		
	}

	public MapPartition(String key, String type) {
		this.key = key;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MapPartition [key=" + key + ", type=" + type + "]";
	}
}