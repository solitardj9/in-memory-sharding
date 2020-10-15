package com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.model.BackupAndRestoreEvent;
import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.service.BackupAndRestoreManager;
import com.soliatrdj9.imsd.systemInterface.ipcInterface.service.IpcManager;

@EnableAsync
@Service("backupAndRestoreManager")
public class BackupAndRestoreManagerImpl implements BackupAndRestoreManager {
	//
	private static final Logger logger = LoggerFactory.getLogger(BackupAndRestoreManagerImpl.class);
	
	@Autowired
	IpcManager ipcManager;
	
	private ObjectMapper om = new ObjectMapper();
	
	@EventListener
	@Async
	public void onBackupAndRestoreEvent(BackupAndRestoreEvent backupAndRestoreEvent) {
		//
		logger.info("[BackupAndRestoreManager].onBackupAndRestoreEvent : start = " + backupAndRestoreEvent.toString());
		
		try {
			String requestBody = om.writeValueAsString(backupAndRestoreEvent);
			
			ipcManager.executeBackupAndRestore(backupAndRestoreEvent.getFromGroupName(), requestBody);
			
		} catch (JsonProcessingException e) {
			logger.error("[BackupAndRestoreManager].onBackupAndRestoreEvent : error = " + e);
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.error("[BackupAndRestoreManager].onBackupAndRestoreEvent : error = " + e);
		}
		
		logger.info("[BackupAndRestoreManager].onBackupAndRestoreEvent : stop = " + backupAndRestoreEvent.toString());
	}

	@Override
	public void executeBackupAndRestore(BackupAndRestoreEvent backupAndRestoreEvent) {
		// TODO Auto-generated method stub
		
	}
}