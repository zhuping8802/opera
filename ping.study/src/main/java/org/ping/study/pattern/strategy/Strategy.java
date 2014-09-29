package org.ping.study.pattern.strategy;

/**
 * 策略模式抽象类，业务算法规则均可提取为策略模式实现
 * @author ping
 *
 */
public abstract class Strategy {
	
	protected abstract int strategyResult(); 
}
