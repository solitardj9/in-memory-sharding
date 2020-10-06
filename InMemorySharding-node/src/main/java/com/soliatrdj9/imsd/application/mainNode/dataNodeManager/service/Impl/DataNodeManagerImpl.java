package com.soliatrdj9.imsd.application.mainNode.dataNodeManager.service.Impl;

import org.springframework.stereotype.Service;

import com.hazelcast.core.EntryEvent;
import com.soliatrdj9.imsd.application.mainNode.dataNodeManager.service.DataNodeManager;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryEventListener;

@Service("dataNodeManager")
public class DataNodeManagerImpl implements DataNodeManager, InMemoryEventListener {
	//
	// TODO : data node management
	// TODO : make a repository on local memory, next is on hybrid(hazelcast & h2 DB), change to In-Memory
	// need to analyze node restart & node scale out(O)/in(X)
	// when node is scaled out(O)/in(X), then new node requests re-distribute seed info and data --> a/f request, other nodes push data to new node according to new seed infos
	//   --> some node receives scale out event by entryRemoved and lock to re-distribute as 
	//   --> this node iterate other nodes to notify pushing data to new node.
	//       notify by members in hazelcast? Topic / Reliable Topic(Support Backup)
	//       	https://dzone.com/articles/publish-and-subscribe
	//		 	https://supawer0728.github.io/2018/03/11/hazelcast/
	
	@Override
	public String getDataNodeName(String map, String seed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void entryAdded(EntryEvent<Object, Object> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entryRemoved(EntryEvent<Object, Object> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entryUpdated(EntryEvent<Object, Object> event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void needToRedistribute() {
		// TODO Auto-generated method stub
		
	}

}