package org.ping.pattern.proxy;


public class JdkProxyTest {
	
	public static void main(String[] args) {
		JdkProxy a = new JdkProxyA();
		JdkProxy proxy = new ProxyInvocationHandler().getProxy(a);
		proxy.show();
		
		JdkProxy b = new JdkProxyB();
		proxy = new ProxyInvocationHandler().getProxy(b);
		proxy.show();
	}
}
