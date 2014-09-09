package org.ping.pattern.factory;

/**
 * 简单工厂模式，将对象实例化交由统一工厂类实例，返回被实例化的具体对象
 * @author ping
 *
 */
public class OperationFactory {

	public static Operation getOperation(String type){
		Operation operation = null;
		switch (type) {
			case "+":
				operation = new OperationAdd();
				break;
			case "*":
				operation = new OperationMul();
				break;
			default:
				break;
		}
		return operation;
	}
	
	public static void main(String[] args) {
		Operation addOperation = OperationFactory.getOperation("+");
		addOperation.setNumberA(10);
		addOperation.setNumberB(20);
		System.out.println(addOperation.getResult());
		
		Operation mulOperation = OperationFactory.getOperation("*");
		mulOperation.setNumberA(10);
		mulOperation.setNumberB(20);
		System.out.println(mulOperation.getResult());
	}
}
