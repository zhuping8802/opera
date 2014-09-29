package org.ping.study.pattern.factory;

/**
 * 加法操作类
 * @author ping
 *
 */
public class OperationAdd extends Operation {

	@Override
	protected int getResult() {
		// TODO Auto-generated method stub
		return this.getNumberA() + this.getNumberB();
	}

}
