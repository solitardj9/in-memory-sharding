package com.soliatrdj9.imsd.systemInterface.ipcInterface.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.soliatrdj9.imsd.systemInterface.ipcInterface.service.IpcManager;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Service("ipcManager")
public class IpcManagerImpl implements IpcManager {
	
	@Autowired
	EurekaClient discoveryClient;

	@Override
	public String executeBackupAndRestore(String groupName, String requestBody) {
		//
		try {
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(groupName, false);
			String url = instance.getHomePageUrl();
			BackupAndRestore backupAndRestore = Feign.builder()
													.contract(new Contract.Default())
													.retryer(new Retryer.Default())
													.encoder(new Encoder.Default())
													.decoder(new Decoder.Default())
													.decode404()
													.logLevel(Logger.Level.BASIC)
													.target(new Target.HardCodedTarget<>(BackupAndRestore.class, url));
			
			String strResult = backupAndRestore.executeBackupAndRestore(requestBody);
			return strResult;
		}
		catch (Exception e) {
			System.out.println("[IpcManager].executeBackupAndRestore : error = " + e);
			return null;
		}
	}
}
