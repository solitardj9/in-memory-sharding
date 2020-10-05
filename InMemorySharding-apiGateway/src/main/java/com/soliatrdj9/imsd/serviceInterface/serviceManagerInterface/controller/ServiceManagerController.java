package com.soliatrdj9.imsd.serviceInterface.serviceManagerInterface.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/imsd/management/slaves/")
public class ServiceManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceManagerController.class);

//	@SuppressWarnings("rawtypes")
//	@PutMapping(value="/{node}")
//	public ResponseEntity registerSlaveNode(@PathVariable("node") String node,
//												 @RequestBody(required=false) String requestBody) {
//		//
//		logger.info("[ServiceManagerController].registerSlaveNode is called.");
//		
//		logger.info("[ServiceManagerController].registerSlaveNode : node = " + node);
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//    }
}