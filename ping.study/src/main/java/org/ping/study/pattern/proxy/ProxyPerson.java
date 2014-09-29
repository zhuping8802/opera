package org.ping.study.pattern.proxy;

/**
 * 静态代理
 * @author ping
 *
 */
public class ProxyPerson implements Operation {
	private RealPerson person;

	@Override
	public void print() {
		// TODO Auto-generated method stub
		if(person == null){
			person = new RealPerson();
		}
		person.print();
	}

}
