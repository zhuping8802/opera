package org.ping.study.ws.service.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ping.study.ws.service.HelloService;
import org.springframework.stereotype.Service;
/**
 * 
 * <p>TypeName: HelloService2Impl</p>
 * <p>Description: jdk webservice</p>
 * <p>date : 2014年10月29日 下午5:20:21 </p>
 * @author ping
 * @version 1.0
 */
@Service("helloService")
@WebService
public class HelloService2Impl implements HelloService {

	@WebMethod
	public String sayHello(String name) {
		return "Hello: " + name + "!!!!!!!";
	}
}
