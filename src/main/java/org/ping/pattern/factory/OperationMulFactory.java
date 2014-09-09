package org.ping.pattern.factory;

public class OperationMulFactory extends FactoryMethod {

	@Override
	protected Operation createOperation() {
		// TODO Auto-generated method stub
		return new OperationMul();
	}

}
