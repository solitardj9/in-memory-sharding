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

	private String imsdMaster = "imsd-master-service";
	//private String imsdMaster = "IMSD-MASTER-SERVICE";
	
	@Override
	public String registerSlaveNode(String node) {
		// TODO Auto-generated method stub
		
		String serviceName = imsdMaster;
		
		try {
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(serviceName, false);
			String url = instance.getHomePageUrl();
			ImsdMasterNode imsdMasterNode = Feign.builder()
													.contract(new Contract.Default())
													.retryer(new Retryer.Default())
													.encoder(new Encoder.Default())
													.decoder(new Decoder.Default())
													.decode404()
													.logLevel(Logger.Level.BASIC)
													.target(new Target.HardCodedTarget<>(ImsdMasterNode.class, url));
			
			String strResult = imsdMasterNode.registerSlaveNode(node);
			return strResult;
		}
		catch (Exception e) {
			System.out.println("[IpcManager].registerSlaveNode : error = " + e);
			return null;
		}
	}
}
