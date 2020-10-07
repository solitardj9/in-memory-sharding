package com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.model;

import java.io.Serializable;

public class BackupAndRestoreEvent implements Serializable {

	private static final long serialVersionUID = -1446660952368045468L;
	
	private String fromGroupName;
	
	private Float fromGroupMaxSeed;
	
	private Float fromGroupMinSeed;
	
	private String toGroupName;
	
	private Float toGroupMaxSeed;
	
	private Float toGroupMinSeed;
	
	public BackupAndRestoreEvent() {
		
	}

	public BackupAndRestoreEvent(String fromGroupName, Float fromGroupMaxSeed, Float fromGroupMinSeed,
			String toGroupName, Float toGroupMaxSeed, Float toGroupMinSeed) {
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

	public Float getFromGroupMaxSeed() {
		return fromGroupMaxSeed;
	}

	public void setFromGroupMaxSeed(Float fromGroupMaxSeed) {
		this.fromGroupMaxSeed = fromGroupMaxSeed;
	}

	public Float getFromGroupMinSeed() {
		return fromGroupMinSeed;
	}

	public void setFromGroupMinSeed(Float fromGroupMinSeed) {
		this.fromGroupMinSeed = fromGroupMinSeed;
	}

	public String getToGroupName() {
		return toGroupName;
	}

	public void setToGroupName(String toGroupName) {
		this.toGroupName = toGroupName;
	}

	public Float getToGroupMaxSeed() {
		return toGroupMaxSeed;
	}

	public void setToGroupMaxSeed(Float toGroupMaxSeed) {
		this.toGroupMaxSeed = toGroupMaxSeed;
	}

	public Float getToGroupMinSeed() {
		return toGroupMinSeed;
	}

	public void setToGroupMinSeed(Float toGroupMinSeed) {
		this.toGroupMinSeed = toGroupMinSeed;
	}

	@Override
	public String toString() {
		return "BackupAndRestoreEvent [fromGroupName=" + fromGroupName + ", fromGroupMaxSeed=" + fromGroupMaxSeed
				+ ", fromGroupMinSeed=" + fromGroupMinSeed + ", toGroupName=" + toGroupName + ", toGroupMaxSeed="
				+ toGroupMaxSeed + ", toGroupMinSeed=" + toGroupMinSeed + "]";
	}
}