package com.soliatrdj9.imsd.application.mainNode.dataNodeManager.service;

public interface DataNodeManager {
	//
	public String/*spring.application.name*/ getDataNodeName(String map, String seed);
	
	// TODO : need to upgrade
	public void needToRedistribute();
	
	// TODO : need to upgrade
}