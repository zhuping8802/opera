package org.ping.pattern.factory;

/**
 * 抽象操作父类
 * @author ping
 *
 */
public abstract class Operation {

	private int numberA;
	
	private int numberB;
	
	/**
	 * 返回结果
	 * @return
	 */
	protected abstract int getResult();

	public int getNumberA() {
		return numberA;
	}

	public void setNumberA(int numberA) {
		this.numberA = numberA;
	}

	public int getNumberB() {
		return numberB;
	}

	public void setNumberB(int numberB) {
		this.numberB = numberB;
	}
}
