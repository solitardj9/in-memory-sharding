package com.soliatrdj9.imsd.application.mainNode.mapManager.model;

import java.io.Serializable;
import java.util.List;

public class MapProperties implements Serializable {

	private static final long serialVersionUID = 81711396581753777L;
	
	private List<MapPartition> partitions;
	
	public MapProperties() {
		
	}

	public MapProperties(List<MapPartition> partitions) {
		this.partitions = partitions;
	}

	public List<MapPartition> getPartitions() {
		return partitions;
	}

	public void setPartitions(List<MapPartition> partitions) {
		this.partitions = partitions;
	}

	@Override
	public String toString() {
		return "MapProperties [partitions=" + partitions + "]";
	}
}