package com.soliatrdj9.imsd.serviceInterface.backupAndRestoreManagerController.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.model.BackupAndRestoreEvent;
import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.service.BackupAndRestoreManager;
import com.soliatrdj9.imsd.serviceInterface.common.model.ResponseDefualt;

@RestController
@RequestMapping(value="/appliication")
public class BackupAndRestoreManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(BackupAndRestoreManagerController.class);

	@Autowired
	BackupAndRestoreManager backupAndRestoreManager;
	
	private ObjectMapper om = new ObjectMapper();
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/bakcupAndRestore/start")
	public ResponseEntity startBackupAndRestore(@RequestBody(required=true) String requestBody) {
		//
		logger.info("[BackupAndRestoreManagerController].startBackupAndRestore is called.");
		
		try {
			BackupAndRestoreEvent backupAndRestoreEvent = om.readValue(requestBody, BackupAndRestoreEvent.class);
			
			logger.error("[ServiceManagerController].startBackupAndRestore : requestBody = " + requestBody);
			
			backupAndRestoreManager.executeBackupAndRestore(backupAndRestoreEvent);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (JsonProcessingException e) {
			logger.error("[ServiceManagerController].startBackupAndRestore : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(HttpStatus.BAD_REQUEST.value(), e.toString()), HttpStatus.BAD_REQUEST);
		}
    }
	
	// TODO : file upload
}