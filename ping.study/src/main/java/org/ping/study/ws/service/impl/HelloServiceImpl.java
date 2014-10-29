package org.ping.study.ws.service.impl;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import org.ping.study.ws.service.HelloService;
/**
 * 
 * <p>TypeName: HelloServiceImpl</p>
 * <p>Description: jdk webservice</p>
 * <p>date : 2014年10月29日 下午5:20:21 </p>
 * @author ping
 * @version 1.0
 */
@WebService(name="HelloService", serviceName="HelloService", targetNamespace="http://jws/client")
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "Hello: " + name + "!!!!!!!";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 发布服务，生成客户端调用代码：wsimport -s /home/ping/git/opera/ping.study/src/main/java -keep http://localhost:8088/ws/HelloService?wsdl
		
		Endpoint.publish("http://localhost:8088/ws/HelloService", new HelloServiceImpl());
	}

}
