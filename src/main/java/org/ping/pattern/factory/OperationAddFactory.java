package org.ping.pattern.factory;

/**
 * 加法工厂
 * @author ping
 *
 */
public class OperationAddFactory extends FactoryMethod {

	@Override
	protected Operation createOperation() {
		// TODO Auto-generated method stub
		return new OperationAdd();
	}

}
