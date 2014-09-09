package org.ping.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 实现jdk动态代理代理类
 * @author ping
 *
 */
public class ProxyInvocationHandler implements InvocationHandler {
	private Object target;
	
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		System.out.println("before");
		Object result = arg1.invoke(this.target, arg2);
		System.out.println("after");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(T t){
		this.target = t;
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
}
