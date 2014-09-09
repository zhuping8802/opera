package org.ping.io;

import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SystermProperties {

	public static void main(String[] args) {
		System.out.println("=======================properties============================");
		// 打印java系统properties信息
		Properties properties = System.getProperties();
		Enumeration<Object> enumeration = properties.elements();
		while(enumeration.hasMoreElements()){
			System.out.println(enumeration.nextElement());
		}
		for(Entry<Object, Object> entry : properties.entrySet()){
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		System.out.println("=======================properties============================");
		
		System.out.println("========================env===========================");
		// 打印java环境设置信息
		Map<String, String> envMap = System.getenv();
		for(Entry<String, String> env : envMap.entrySet()){
			System.out.println(env.getKey() + "=" + env.getValue());
		}
		System.out.println("========================env===========================");
		
	}

}
