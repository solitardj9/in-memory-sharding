package com.soliatrdj9.imsd.service.serviceManager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soliatrdj9.imsd.service.serviceManager.service.ServiceManager;
import com.soliatrdj9.imsd.systemInterface.ipcInterface.service.IpcManager;

@Service("serviceManager")
public class ServiceManagerImpl implements ServiceManager {

	private static final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);
	
	@Value("${spring.application.name}")
	private String node;
	
	@Autowired
	IpcManager ipcManager;
	
	@Override
	public void registerService() {
		//
		ipcManager.registerSlaveNode(node);
	}
}