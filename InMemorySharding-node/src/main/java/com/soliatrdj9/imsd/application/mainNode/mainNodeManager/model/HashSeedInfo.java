package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model;

import java.io.Serializable;

public class HashSeedInfo implements Serializable {

	private static final long serialVersionUID = 8788371877544565128L;
	
	private Double minSeed;
	
	private Double maxSeed;
	
	public HashSeedInfo() {
		
	}

	public HashSeedInfo(Double minSeed, Double maxSeed) {
		this.minSeed = minSeed;
		this.maxSeed = maxSeed;
	}

	public Double getMinSeed() {
		return minSeed;
	}

	public void setMinSeed(Double minSeed) {
		this.minSeed = minSeed;
	}

	public Double getMaxSeed() {
		return maxSeed;
	}

	public void setMaxSeed(Double maxSeed) {
		this.maxSeed = maxSeed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maxSeed == null) ? 0 : maxSeed.hashCode());
		result = prime * result + ((minSeed == null) ? 0 : minSeed.hashCode());
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
		HashSeedInfo other = (HashSeedInfo) obj;
		if (maxSeed == null) {
			if (other.maxSeed != null)
				return false;
		} else if (!maxSeed.equals(other.maxSeed))
			return false;
		if (minSeed == null) {
			if (other.minSeed != null)
				return false;
		} else if (!minSeed.equals(other.minSeed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HashSeedInfo [minSeed=" + minSeed + ", maxSeed=" + maxSeed + "]";
	}
}