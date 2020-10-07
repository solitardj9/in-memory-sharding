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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.service.BackupAndRestoreManager;

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
		
		// TODO : 
		
		System.out.println("requestBody = " + requestBody);
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
}