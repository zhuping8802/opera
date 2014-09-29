package org.ping.study.pattern.proxy;

public class RealPerson implements Operation {

	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("我是真实对象，我可以打印输出");
	}

}
