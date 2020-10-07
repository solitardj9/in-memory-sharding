package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.data;

import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNodeGroup;

public class ReDistributionStrategy {
	//
	private DataNodeGroup fromDataNodeGroup;
	
	private DataNodeGroup toDataNodeGroup;

	public ReDistributionStrategy() {
		
	}
	
	public ReDistributionStrategy(DataNodeGroup fromDataNodeGroup, DataNodeGroup toDataNodeGroup) {
		this.fromDataNodeGroup = fromDataNodeGroup;
		this.toDataNodeGroup = toDataNodeGroup;
	}

	public DataNodeGroup getFromDataNodeGroup() {
		return fromDataNodeGroup;
	}

	public void setFromDataNodeGroup(DataNodeGroup fromDataNodeGroup) {
		this.fromDataNodeGroup = fromDataNodeGroup;
	}

	public DataNodeGroup getToDataNodeGroup() {
		return toDataNodeGroup;
	}

	public void setToDataNodeGroup(DataNodeGroup toDataNodeGroup) {
		this.toDataNodeGroup = toDataNodeGroup;
	}

	@Override
	public String toString() {
		return "ReDistributionStrategy [fromDataNodeGroup=" + fromDataNodeGroup + ", toDataNodeGroup=" + toDataNodeGroup
				+ "]";
	}
}