package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataNodeGroup implements Serializable {

	private static final long serialVersionUID = -4100048561526911504L;
	
	private String groupName;		// from group.name at Hazelcast Config
	
	private HashSeedInfo hashSeedInfo;
	
	private CopyOnWriteArrayList<DataNode> dataNodes;

	public DataNodeGroup() {
		
	}
	
	public DataNodeGroup(String groupName, HashSeedInfo hashSeedInfo, CopyOnWriteArrayList<DataNode> dataNodes) {
		this.groupName = groupName;
		this.hashSeedInfo = hashSeedInfo;
		this.dataNodes = dataNodes;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public HashSeedInfo getHashSeedInfo() {
		return hashSeedInfo;
	}

	public void setHashSeedInfo(HashSeedInfo hashSeedInfo) {
		this.hashSeedInfo = hashSeedInfo;
	}

	public List<DataNode> getDataNodes() {
		return dataNodes;
	}

	public void setDataNodes(CopyOnWriteArrayList<DataNode> dataNodes) {
		this.dataNodes = dataNodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataNodes == null) ? 0 : dataNodes.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((hashSeedInfo == null) ? 0 : hashSeedInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataNodeGroup other = (DataNodeGroup) obj;
		if (dataNodes == null) {
			if (other.dataNodes != null)
				return false;
		} else if (!dataNodes.equals(other.dataNodes))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (hashSeedInfo == null) {
			if (other.hashSeedInfo != null)
				return false;
		} else if (!hashSeedInfo.equals(other.hashSeedInfo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataNodeGroup [groupName=" + groupName + ", hashSeedInfo=" + hashSeedInfo + ", dataNodes=" + dataNodes
				+ "]";
	}
}