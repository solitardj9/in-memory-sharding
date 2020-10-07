package com.soliatrdj9.imsd.serviceInterface.serviceManagerInterface.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.DataNodeGroup;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeFailure;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInReplicatonModeNotFound;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeConflict;
import com.soliatrdj9.imsd.application.mainNode.mainNodeManager.model.exception.ExceptionStartNodeInShardingModeFailure;
import com.soliatrdj9.imsd.service.serviceManager.service.ServiceManager;
import com.soliatrdj9.imsd.serviceInterface.common.model.ResponseDefualt;
import com.soliatrdj9.imsd.serviceInterface.serviceManagerInterface.model.RequestScaleOut;

@RestController
@RequestMapping(value="/management")
public class ServiceManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceManagerController.class);

	@Autowired
	ServiceManager serviceManager;
	
	private ObjectMapper om = new ObjectMapper();
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/service/start")
	public ResponseEntity startService(@RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceManagerController].startService is called.");
		
		try {
			serviceManager.startService();
		} catch (ExceptionStartNodeInReplicatonModeFailure e) {
			logger.error("[ServiceManagerController].startService : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(e.getErrCode(), e.getErrMsg()), e.getHttpStatus());
		} catch (ExceptionStartNodeInReplicatonModeNotFound e) {
			logger.error("[ServiceManagerController].startService : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(e.getErrCode(), e.getErrMsg()), e.getHttpStatus());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/service/stop")
	public ResponseEntity stopService(@RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceManagerController].stopService is called.");
		
		serviceManager.stopService();

		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/service/scaleOut")
	public ResponseEntity scaleOut(@RequestBody(required=true) String requestBody) {
		//
		logger.info("[ServiceManagerController].scaleOut is called.");
		
		try {
			RequestScaleOut requestScaleOut = om.readValue(requestBody, RequestScaleOut.class);
			
			serviceManager.scaleOut(requestScaleOut.getCriteriaHashSeed());
		} catch (ExceptionStartNodeInShardingModeFailure e) {
			logger.error("[ServiceManagerController].scaleOut : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(e.getErrCode(), e.getErrMsg()), e.getHttpStatus());
		} catch (ExceptionStartNodeInShardingModeConflict e) {
			logger.error("[ServiceManagerController].scaleOut : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(e.getErrCode(), e.getErrMsg()), e.getHttpStatus());
		} catch (JsonProcessingException e) {
			logger.error("[ServiceManagerController].scaleOut : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(HttpStatus.BAD_REQUEST.value(), "bad request."), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/service/scaleIn")
	public ResponseEntity scaleIn(@RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceManagerController].scaleIn is called.");
		
		serviceManager.scaleIn();
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value="/service/topology")
	public ResponseEntity getServiceTopology(@RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceManagerController].getServiceTopology is called.");
		
		Map<String, DataNodeGroup> serviceTopology = serviceManager.getServiceTopology();
		
		try {
			String responseBody = om.writeValueAsString(serviceTopology);
			return new ResponseEntity<>(responseBody, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			logger.info("[ServiceManagerController].getServiceTopology : error = " + e.getStackTrace());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}