package org.ping.study.jws.client;

import jws.client.HelloService;
import jws.client.HelloService_Service;

public class HelloServiceTest {

	public static void main(String[] args) {
		HelloService_Service service = new HelloService_Service();
		HelloService helloService = service.getHelloServicePort();
		System.out.println(helloService.sayHello("Xiao Min"));
		System.out.println(helloService.sayHello("Li Si"));
	}

}
