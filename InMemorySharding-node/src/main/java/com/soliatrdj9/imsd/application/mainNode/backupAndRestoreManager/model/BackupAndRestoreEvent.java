package com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.model;

import java.io.Serializable;

public class BackupAndRestoreEvent implements Serializable {

	private static final long serialVersionUID = -1446660952368045468L;
	
	private String fromGroupName;
	
	private Double fromGroupMaxSeed;
	
	private Double fromGroupMinSeed;
	
	private String toGroupName;
	
	private Double toGroupMaxSeed;
	
	private Double toGroupMinSeed;
	
	public BackupAndRestoreEvent() {
		
	}

	public BackupAndRestoreEvent(String fromGroupName, Double fromGroupMaxSeed, Double fromGroupMinSeed, String toGroupName, Double toGroupMaxSeed, Double toGroupMinSeed) {
		this.fromGroupName = fromGroupName;
		this.fromGroupMaxSeed = fromGroupMaxSeed;
		this.fromGroupMinSeed = fromGroupMinSeed;
		this.toGroupName = toGroupName;
		this.toGroupMaxSeed = toGroupMaxSeed;
		this.toGroupMinSeed = toGroupMinSeed;
	}

	public String getFromGroupName() {
		return fromGroupName;
	}

	public void setFromGroupName(String fromGroupName) {
		this.fromGroupName = fromGroupName;
	}

	public Double getFromGroupMaxSeed() {
		return fromGroupMaxSeed;
	}

	public void setFromGroupMaxSeed(Double fromGroupMaxSeed) {
		this.fromGroupMaxSeed = fromGroupMaxSeed;
	}

	public Double getFromGroupMinSeed() {
		return fromGroupMinSeed;
	}

	public void setFromGroupMinSeed(Double fromGroupMinSeed) {
		this.fromGroupMinSeed = fromGroupMinSeed;
	}

	public String getToGroupName() {
		return toGroupName;
	}

	public void setToGroupName(String toGroupName) {
		this.toGroupName = toGroupName;
	}

	public Double getToGroupMaxSeed() {
		return toGroupMaxSeed;
	}

	public void setToGroupMaxSeed(Double toGroupMaxSeed) {
		this.toGroupMaxSeed = toGroupMaxSeed;
	}

	public Double getToGroupMinSeed() {
		return toGroupMinSeed;
	}

	public void setToGroupMinSeed(Double toGroupMinSeed) {
		this.toGroupMinSeed = toGroupMinSeed;
	}

	@Override
	public String toString() {
		return "BackupAndRestoreEvent [fromGroupName=" + fromGroupName + ", fromGroupMaxSeed=" + fromGroupMaxSeed
				+ ", fromGroupMinSeed=" + fromGroupMinSeed + ", toGroupName=" + toGroupName + ", toGroupMaxSeed="
				+ toGroupMaxSeed + ", toGroupMinSeed=" + toGroupMinSeed + "]";
	}
}