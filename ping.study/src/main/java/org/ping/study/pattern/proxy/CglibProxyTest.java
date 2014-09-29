package org.ping.study.pattern.proxy;

public class CglibProxyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CglibProxyA a = CglibProxy.getProxy(new CglibProxyA());
		a.show();
		
		CglibProxyB b = CglibProxy.getProxy(new CglibProxyB());
		b.show();
	}

}
