package org.ping.study.pattern.factory;

/**
 * 乘法操作类
 * @author ping
 *
 */
public class OperationMul extends Operation {

	@Override
	protected int getResult() {
		// TODO Auto-generated method stub
		return this.getNumberA() * this.getNumberB();
	}

}
