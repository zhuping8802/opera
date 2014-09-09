package org.ping.pattern.factory;

/**
 * 工厂方法抽象类，将子类实例化交由客户端自己，由自己决定具体需要什么工厂
 * @author ping
 *
 */
public abstract class FactoryMethod {

	protected abstract Operation createOperation();
	
	public static void main(String[] args) {
		FactoryMethod addFactoryMethod = new OperationAddFactory();
		Operation addOperation = addFactoryMethod.createOperation();
		addOperation.setNumberA(10);
		addOperation.setNumberB(30);
		System.out.println(addOperation.getResult());
		
		FactoryMethod mulFactoryMethod = new OperationMulFactory();
		Operation mulOperation = mulFactoryMethod.createOperation();
		mulOperation.setNumberA(10);
		mulOperation.setNumberB(30);
		System.out.println(mulOperation.getResult());
	}
}
