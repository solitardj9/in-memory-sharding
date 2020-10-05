package com.soliatrdj9.imsd;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.ICompiler;
import org.codehaus.commons.compiler.util.ResourceFinderClassLoader;
import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.StringResource;
import org.codehaus.janino.CompilerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
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
		
		CompilerFactory compilerFactory = null;
		try {
			compilerFactory = (CompilerFactory)CompilerFactoryFactory.getDefaultCompilerFactory();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ICompiler compiler = compilerFactory.newCompiler();
		
		// Store generated .class files in a Map:
		Map<String, byte[]> classes = new HashMap<String, byte[]>();
		compiler.setClassFileCreator(new MapResourceCreator(classes));
		
		String fileExtension = ".java";
		String className = "ClassA";
		String folder = "customPackage/";
		String folder2 = "customPackage.";
		
		String fileInfo = folder + className + fileExtension;
				
		try {
			compiler.compile(new Resource[] {
			    new StringResource(
			    		fileInfo,
			    		"package customPackage; import com.soliatrdj9.imsd.*; public class "+ className + " extends ParentClass { public static int meth() { return 32; } }"
			    )
			});
		} catch (CompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		ClassLoader cl = new ResourceFinderClassLoader(
				new MapResourceFinder(classes),    // resourceFinder
				ClassLoader.getSystemClassLoader() // parent
		);
		
		String fileInfo2 = folder2 + className;
		
		try {
			System.out.println(cl.loadClass(fileInfo2).toString());
			java.lang.reflect.Method[] methods = cl.loadClass(fileInfo2).getMethods();
			for (java.lang.reflect.Method m: methods) {
				System.out.println(m.toString());
			}
			try {
				System.out.println(cl.loadClass(fileInfo2).getDeclaredMethod("meth").invoke(null));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Object o;
		try {
			o = cl.loadClass(fileInfo2).newInstance();
			System.out.println(((ParentClass)o).doTest());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Assert.assertEquals(77, cl.loadClass("pkg1.A").getDeclaredMethod("meth").invoke(null));
		
		
		try {
			String map = "aaaa";
			InMemoryMainNodeManager inMemoryMainNodeManager = ((InMemoryMainNodeManager)context.getBean("inMemoryMainNodeManager"));
			inMemoryMainNodeManager.createMap(map);
			
			for (int a = 0 ; a < 3 ; a++) {
				KeyClass keyClass = new KeyClass("a_" + a, a);
				inMemoryMainNodeManager.getMap(map).put(keyClass, a);
			}
			
			IMap<Object, Object> testMap = inMemoryMainNodeManager.getMap(map);
			for (Entry<Object, Object> iter : testMap.entrySet()) {
				System.out.println(iter.getKey());
				System.out.println(iter.getValue());
			}
			
			EntryObject e = new PredicateBuilder().getEntryObject();
			Predicate predicate = e.key().get("name").equal("a_1").and(e.key().get("age").greaterThan(0));

			Collection<Object> results = inMemoryMainNodeManager.getMap(map).keySet(predicate);
			System.out.println(results.toString());
			
		} catch (BeansException | ExceptionHazelcastServerAlreadyClosed | ExceptionHazelcastDistributedObjectNameConflict | ExceptionHazelcastIMapNotFound e) {
			e.printStackTrace();
		}
		
		KeyClass keyClass = new KeyClass("aa", 1);
		String strJson = null;
		try {
			strJson = om.writeValueAsString(keyClass);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] jsonBytes = null;
		try {
			jsonBytes = om.writeValueAsBytes(strJson);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClassLoader classLoader = new ResourceFinderClassLoader(
				new MapResourceFinder(classes),    // resourceFinder
				ClassLoader.getSystemClassLoader() // parent
		);

		if(jsonBytes != null){
		    try {
				Object jsonObj = om.readValue(jsonBytes, MyObject.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}