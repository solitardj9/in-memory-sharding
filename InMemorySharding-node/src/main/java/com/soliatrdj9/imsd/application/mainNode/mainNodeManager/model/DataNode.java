package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model;

import java.io.Serializable;

public class DataNode implements Serializable {

	private static final long serialVersionUID = -3009627091522010848L;

	private String dataNodeName;	// from spring.application.name at application.yml

	public DataNode() {
		
	}
	
	public DataNode(String dataNodeName) {
		this.dataNodeName = dataNodeName;
	}

	public String getDataNodeName() {
		return dataNodeName;
	}

	public void setDataNodeName(String dataNodeName) {
		this.dataNodeName = dataNodeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataNodeName == null) ? 0 : dataNodeName.hashCode());
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
		DataNode other = (DataNode) obj;
		if (dataNodeName == null) {
			if (other.dataNodeName != null)
				return false;
		} else if (!dataNodeName.equals(other.dataNodeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataNode [dataNodeName=" + dataNodeName + "]";
	}
}