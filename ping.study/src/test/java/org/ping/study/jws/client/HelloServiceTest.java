package org.ping.study.jws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import jws.client.HelloService;
import jws.client.HelloService_Service;

public class HelloServiceTest {

	public static void main(String[] args) {
		HelloServiceTest test = new HelloServiceTest();
//		test.wsimportTest();
		test.test();
	}
	
	public void wsimportTest(){
		HelloService_Service service = new HelloService_Service();
		HelloService helloService = service.getHelloServicePort();
		System.out.println(helloService.sayHello("Xiao Min"));
		System.out.println(helloService.sayHello("Li Si"));
	}
	
	public void test(){
		try {
			URL wsdlUrl = new URL("http://localhost:8088/ws/HelloService?wsdl");
			QName qName = new QName("http://jws/client", "HelloService");
			Service service = Service.create(wsdlUrl, qName);
			HelloService hello = service.getPort(HelloService.class);
			System.out.println(hello.sayHello("zhang san"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
