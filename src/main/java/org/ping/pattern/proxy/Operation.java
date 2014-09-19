package org.ping.pattern.proxy;

/**
 * 代理模式，代理对象与被代理对象具有同一行为操作，代码对象具有被代理对象引用，代理被代理对象实行操作功能
 * @author ping
 *
 */
public interface Operation {

	void print();
	
//	public static void main(String[] args) {
//		Operation operation = new ProxyPerson();
//		operation.print();
//	}
}
