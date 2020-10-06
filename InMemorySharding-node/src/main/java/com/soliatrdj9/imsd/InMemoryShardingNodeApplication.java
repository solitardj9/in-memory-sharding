package com.soliatrdj9.imsd;

import java.util.Collection;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapConflict;
import com.soliatrdj9.imsd.application.mainNode.mapManager.model.exception.ExceptionMapManagerInternalFailure;
import com.soliatrdj9.imsd.application.mainNode.mapManager.service.MapManager;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion.ExceptionShardingSeedNotFound;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.model.exceptiion.ExceptionShardingStrategyManagerInternalFailure;
import com.soliatrdj9.imsd.application.mainNode.shardingStrategyManager.service.ShardingStrategyManager;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastDistributedObjectNameConflict;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastIMapNotFound;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.model.exception.ExceptionHazelcastServerAlreadyClosed;
import com.soliatrdj9.imsd.systemInterface.imdgInterface.service.InMemoryMainNodeManager;

@SpringBootApplication
@EnableDiscoveryClient
public class InMemoryShardingNodeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(InMemoryShardingNodeApplication.class, args);

		//((ServiceManager)context.getBean("serviceManager")).registerService();
		
		ObjectMapper om = new ObjectMapper();
		
		String strMapInfo = "{\n" + 
				"    \"map\":\"myMap\",\n" + 
				"    \"properties\": {\n" + 
				"        \"partitions\": [\n" + 
				"            {\n" + 
				"                \"key\" : \"name\",\n" + 
				"                \"type\": \"string\"\n" + 
				"            },\n" + 
				"            {\n" + 
				"                \"key\": \"age\",\n" + 
				"                \"type\": \"integer\"\n" + 
				"            }\n" + 
				"        ]\n" + 
				"    }\n" + 
				"}";
		
		try {
			((MapManager)context.getBean("mapManager")).addMapInfo(strMapInfo);
		} catch (BeansException | ExceptionMapConflict | ExceptionMapManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		String jsonInfo = "{\n" + 
				 		  "    \"name\" : \"Jhon\",\n" + 
						  "    \"age\" : 32\n" + 
						  "}";
		
		String jsonInfo1 = "{\n" + 
				  "    \"name\" : \"Rhee\",\n" + 
				  "    \"age\" : 40\n" + 
				  "}";
		
		String jsonInfo2 = "{\n" + 
				  "    \"name\" : \"Cha\",\n" + 
				  "    \"age\" : 41\n" + 
				  "}";
		
		String jsonInfo3 = "{\n" + 
				  "    \"name\" : \"wbdkwebkklwenf\",\n" + 
				  "    \"age\" : 1234124,\n" + 
				  "    \"what\" : 41\n" + 
				  "}";
		
		try {
			String map = "aaaa";
			InMemoryMainNodeManager inMemoryMainNodeManager = ((InMemoryMainNodeManager)context.getBean("inMemoryMainNodeManager"));
			inMemoryMainNodeManager.createMap(map);
			
			HazelcastJsonValue hazelcastJsonValue = new HazelcastJsonValue(jsonInfo);
			inMemoryMainNodeManager.getMap(map).put(hazelcastJsonValue, jsonInfo);
			
			HazelcastJsonValue hazelcastJsonValue1 = new HazelcastJsonValue(jsonInfo1);
			inMemoryMainNodeManager.getMap(map).put(hazelcastJsonValue1, jsonInfo1);
			
			HazelcastJsonValue hazelcastJsonValue2 = new HazelcastJsonValue(jsonInfo2);
			inMemoryMainNodeManager.getMap(map).put(hazelcastJsonValue2, jsonInfo2);
			
			IMap<Object, Object> testMap = inMemoryMainNodeManager.getMap(map);
			System.out.println("testMap =");
			for (Entry<Object, Object> iter : testMap.entrySet()) {
				System.out.println("key = " + iter.getKey());
				System.out.println("value = " + iter.getValue());
			}
			
			EntryObject e = new PredicateBuilder().getEntryObject();
			Predicate predicate = e.key().get("name").equal("Cha").and(e.key().get("age").greaterThan(40));

			Collection<Object> results = inMemoryMainNodeManager.getMap(map).keySet(predicate);
			System.out.println("filteredMap key =");
			System.out.println(results.toString());
			System.out.println("filteredMap value =");
			for (Object iter : results) {
				System.out.println(inMemoryMainNodeManager.getMap(map).get(iter));
			}
			
		} catch (BeansException | ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastDistributedObjectNameConflict | ExceptionHazelcastIMapNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(((ShardingStrategyManager)context.getBean("shardingStrategyManager")).extractShardingSeed("myMap", jsonInfo));
			System.out.println(((ShardingStrategyManager)context.getBean("shardingStrategyManager")).extractShardingSeed("myMap", jsonInfo1));
			System.out.println(((ShardingStrategyManager)context.getBean("shardingStrategyManager")).extractShardingSeed("myMap", jsonInfo2));
			System.out.println(((ShardingStrategyManager)context.getBean("shardingStrategyManager")).extractShardingSeed("myMap", jsonInfo3));
		} catch (ExceptionShardingSeedNotFound | ExceptionShardingStrategyManagerInternalFailure e) {
			e.printStackTrace();
		}
	}
}